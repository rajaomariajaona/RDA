/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.network.threads;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.network.SocketHandler;
import rda.packet.ImagePacket;

/**
 *
 * @author snowden
 */
public class ScreenShotThread implements Runnable {

    private SocketHandler socketHandler;

    public ScreenShotThread(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            while (!Thread.currentThread().isInterrupted()) {
                BufferedImage img = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImagePacket p = new ImagePacket();
                p.setImage(img);
                p.setImageType("jpg");
                this.socketHandler.send(p);
                Thread.sleep(10);
            }
        } catch (AWTException ex) {
            Logger.getLogger(ScreenShotThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ScreenShotThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
