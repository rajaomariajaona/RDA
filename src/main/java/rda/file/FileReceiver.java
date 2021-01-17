package rda.file;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.packet.FilePacket;

public class FileReceiver {

    final static String DEFAULT_PATH = "C:\\Users\\jane\\Desktop\\";
    private static String currentFile;
    private static FileOutputStream fos;
    private static Long currentSize;

    public static void write(FilePacket filePacket) {
        try {
            if (currentFile == null ? false : currentFile.equals(filePacket.getPath())) {
                if (currentSize <= 4 * 1024) {
                    fos.write(filePacket.getFileData(), 0, filePacket.getPosition());
                    fos.flush();
                    try {
                        fos.flush();
                        fos.close();
                        currentFile = null;
                        currentSize = null;
                        System.out.println("CLOSED");
                    } catch (Exception ex) {
                        Logger.getLogger(FileReceiver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    fos.write(filePacket.getFileData(), 0, filePacket.getPosition());
                    fos.flush();
                    currentSize -= 4 * 1024;
                }
                filePacket = null;
            } else {
                currentFile = filePacket.getPath();
                currentSize = filePacket.getSize();
                File f = Path.of(DEFAULT_PATH, Path.of(currentFile).toString()).toFile();
                if (filePacket.isDirectory()) {
                    f.mkdirs();
                } else {
                    fos = new FileOutputStream(f);
                    write(filePacket);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}
