package rda.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.network.threads.HostPacketReceivedThread;
import rda.network.threads.ScreenShotThread;

/**
 *
 * @author snowden
 */
public class Host extends SocketHandler implements Runnable {

    @Override
    public void run() {
        try {

            ServerSocket serverSocket = new ServerSocket(rda.other.ENV.PORT);
            Socket socket = serverSocket.accept();
            initStream(socket);
            
            Thread t = new Thread(new ScreenShotThread(this));
            t.start();
            
            Thread t2 = new Thread(new HostPacketReceivedThread(this));
            t2.start();

            t.join();
            t2.join();

        } catch (Exception ex) {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                closeStream();
            } catch (Exception ex) {
            }
        }
    }
}
