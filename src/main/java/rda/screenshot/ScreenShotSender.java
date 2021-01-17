package rda.screenshot;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.connection.Connection;
import rda.packet.ImagePacket;

public class ScreenShotSender implements Runnable {

    private final Connection connection;

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                BufferedImage bi = ScreenShotFactory.createScreenShot();
                ImagePacket ip = new ImagePacket(bi);
                connection.sendPacket(ip);
                bi.flush();
                bi = null;
                ip = null;
                Thread.sleep(100);
            }
        } catch (AWTException ex) {
            Logger.getLogger(ScreenShotSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ScreenShotSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ScreenShotSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ScreenShotSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ScreenShotSender(Connection connection) {
        this.connection = connection;
    }

}
