package rda.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import rda.packet.Packet;

public class Connection {

    protected static final int HOST_PORT = 34123;

    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;

    protected void initStreams(Socket socket) throws IOException {
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    protected void closeStreams() throws IOException {
        oos.close();
        ois.close();
    }

    public void sendPacket(Packet packet) throws IOException {
        synchronized (oos) {
            oos.writeObject(packet);
            oos.flush();
        }
    }

    public Packet receivePacket() throws IOException, ClassNotFoundException {
        synchronized (ois) {
            return (Packet) ois.readObject();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            this.closeStreams();
        } catch (Exception ex) {
        }
    }

}
