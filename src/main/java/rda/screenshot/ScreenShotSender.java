package rda.screenshot;

import java.awt.image.BufferedImage;
import rda.CallbackException;
import rda.connection.Connection;
import rda.packet.ImagePacket;

public class ScreenShotSender implements Runnable {

    private final Connection connection;
    private CallbackException ce;

    @Override
    public void run() {
        try {
            int counter = 0;
            while (!Thread.currentThread().isInterrupted()) {
                BufferedImage bi = ScreenShotFactory.createScreenShot();
                ImagePacket ip = new ImagePacket(bi);
                connection.sendPacket(ip);
                bi.flush();
                if (++counter > 250) {
                    System.gc();
                }
                Thread.sleep(40);
            }
        } catch (Exception ex) {
            if (ce != null) {
                this.ce.execute();
            } else {
                ex.printStackTrace();
            }
        }
    }

    public void setOnException(CallbackException ce) {
        this.ce = ce;
    }

    public ScreenShotSender(Connection connection) {
        this.connection = connection;
    }

}
