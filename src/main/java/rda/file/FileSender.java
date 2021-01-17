package rda.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import rda.connection.Connection;
import rda.packet.FilePacket;

public class FileSender implements Runnable {

    private final Connection connection;
    private final ProgressBar pb;
    private final String path;

    public FileSender(Connection connection, String path, ProgressBar pb) {
        this.connection = connection;
        this.path = path;
        this.pb = pb;
    }

    public void send(FilePacket filePacket) throws IOException {
        connection.sendPacket(filePacket);
    }

    @Override
    public void run() {
        File f = new File(path);
        long size = f.length();
        double sent = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {
                fis = new FileInputStream(f);
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
                    fp = new FilePacket(path, f.length(), data, len);
                    sent += len;
                    connection.sendPacket(fp);
                    Platform.runLater(new ProgressSetter(pb, sent / size));
                }
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

class ProgressSetter implements Runnable {

    private double d;
    private ProgressBar pb;

    public ProgressSetter(ProgressBar pb, double d) {
        this.d = d;
        this.pb = pb;
    }

    @Override
    public void run() {
        pb.setProgress(d);
    }
}
