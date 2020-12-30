package rda.connection;

import java.net.ServerSocket;
import java.net.Socket;
import rda.packet.handler.PacketReceiver;
import rda.screenshot.ScreenShotSender;

public class HostConnection extends Connection implements Runnable {

    private ServerSocket serverSocket;
    
    public void start(){
        Thread hostThread = new Thread(this);
        hostThread.setPriority(Thread.MIN_PRIORITY);
        hostThread.start();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            serverSocket.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(HOST_PORT);
            Socket socket = serverSocket.accept();
            super.initStreams(socket);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ScreenShotSender screenShotSender = new ScreenShotSender(this);
        Thread screenShotThread = new Thread(screenShotSender);
        screenShotThread.setPriority(Thread.NORM_PRIORITY);
        screenShotThread.start();
        
        PacketReceiver packetReceiver = new PacketReceiver(this);
        Thread packetReceiverThread = new Thread(packetReceiver);
        packetReceiverThread.setPriority(Thread.NORM_PRIORITY);
        packetReceiverThread.start();
    }

}
