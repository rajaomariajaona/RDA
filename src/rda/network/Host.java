package rda.network;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.packet.ImagePacket;

/**
 *
 * @author snowden
 */
public class Host implements Runnable {
    
    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            ServerSocket serverSocket = new ServerSocket(rda.other.ENV.PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedImage img = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImagePacket p = new ImagePacket();
                p.setImage(img);
                p.setImageType("jpg");
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(p);
                Thread.sleep(10);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
