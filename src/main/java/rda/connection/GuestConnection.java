package rda.connection;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import rda.clipboard.ClipboardEvent;
import rda.file.FileSender;
import rda.packet.handler.PacketReceiver;

public class GuestConnection extends Connection {

    private Socket socket;

    public GuestConnection(InetAddress hostAddress) throws Exception {
        socket = new Socket();
        SocketAddress sa = new InetSocketAddress(hostAddress, HOST_PORT);
        socket.connect(sa, 5000);
        super.initStreams(socket);
        PacketReceiver packetReceiver = new PacketReceiver(this);
        Thread packetReceiverThread = new Thread(packetReceiver);
        packetReceiverThread.setPriority(Thread.NORM_PRIORITY);
        packetReceiverThread.start();
        ClipboardEvent ce = new ClipboardEvent(this);
        ce.startListening();
        Thread.sleep(5000);
        Thread t = new Thread(new FileSender(this, "/home/snowden/data.mp4"));
        t.start();
//        new Thread(new FileSender(this, "/home/snowden/hdd.db")).start();
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
