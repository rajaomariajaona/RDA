package rda.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.connection.Connection;
import rda.packet.FilePacket;

public class FileSender implements Runnable {

    private final Connection connection;
    private long size = -1;
    private String path;

    public FileSender(Connection connection, String path) {
        this.connection = connection;
        this.path = path;
    }

    public void send(FilePacket filePacket) throws IOException {
        connection.sendPacket(filePacket);
    }

    @Override
    public void run() {
        File f = new File(path);
        if (f.exists()) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(f);
                fos = new FileOutputStream(new File("test.mp4"));
                byte[] block = new byte[4 * 1024];
                int len = -1;
                FilePacket fp = null;
                int i = 0;
                while ((len = fis.read(block)) != -1) {
                    byte[] data = null;
                    if (len == 4 * 1024) {
                        data = block;
                    } else {
                        data = new byte[len];
                        System.arraycopy(block, 0, data, 0, len);
                    }
                    block = new byte[4 * 1024];
                    fp = new FilePacket(path, f.length(), data, len, i++);
                    connection.sendPacket(fp);
                    fos.write(fp.getFileData(), 0, fp.getPosition());
                }
                fos.flush();
                fos.close();
                System.out.println(Arrays.toString(fp.getFileData()));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
