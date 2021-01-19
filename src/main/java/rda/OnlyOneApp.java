package rda;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class OnlyOneApp {
    private File file;
    private FileChannel channel;
    private FileLock lock;

    public boolean isAppActive() {
        try {
            file = new File
                 (System.getProperty("user.home"), "RDA.tmp");
            channel = new RandomAccessFile(file, "rw").getChannel();

            try {
                lock = channel.tryLock();
            }
            catch (OverlappingFileLockException e) {
                closeLock();
                return true;
            }

            if (lock == null) {
                closeLock();
                return true;
            }

            Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        closeLock();
                        deleteFile();
                    }
                });
            return false;
        }
        catch (Exception e) {
            closeLock();
            return true;
        }
    }

    private void closeLock() {
        try { lock.release();  }
        catch (Exception e) {  }
        try { channel.close(); }
        catch (Exception e) {  }
    }

    private void deleteFile() {
        try { file.delete(); }
        catch (Exception e) { }
    }
}
