package rda.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import rda.packet.handler.PacketReceiver;

public class GuestConnection extends Connection {

    private Socket socket;

    public GuestConnection(InetAddress hostAddress) {
        try {
            socket = new Socket(hostAddress, HOST_PORT);
            super.initStreams(socket);

            PacketReceiver packetReceiver = new PacketReceiver(this);
            Thread packetReceiverThread = new Thread(packetReceiver);
            packetReceiverThread.setPriority(Thread.NORM_PRIORITY);
            packetReceiverThread.start();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            socket.close();
        } catch (Exception e) {
        }
    }

}
