package rda.connection;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import rda.CallbackException;
import rda.clipboard.ClipboardEvent;
import rda.packet.handler.PacketReceiver;

public class GuestConnection extends Connection {

    private Socket socket;
    private ClipboardEvent ce;
    private CallbackException callback;
    private PacketReceiver packetReceiver;

    public void setOnException(CallbackException callback) {
        this.callback = callback;
        packetReceiver.setOnException(callback);
    }
    

    public GuestConnection(InetAddress hostAddress) throws Exception {
        socket = new Socket();
        SocketAddress sa = new InetSocketAddress(hostAddress, HOST_PORT);
        socket.connect(sa, 5000);
        super.initStreams(socket);
        packetReceiver = new PacketReceiver(this);
        Thread packetReceiverThread = new Thread(packetReceiver);
        packetReceiverThread.setPriority(Thread.NORM_PRIORITY);
        packetReceiverThread.start();
        ce = new ClipboardEvent(this);
        ce.start();
    }

    public ClipboardEvent getClipboardEvent() {
        return ce;
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
