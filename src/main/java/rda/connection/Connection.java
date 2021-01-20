package rda.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import rda.clipboard.ClipboardEvent;
import rda.packet.Packet;

public abstract class Connection {

    protected static final int HOST_PORT = 34123;
    private static int counter = 0;
    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;
    private boolean available = false;
    
    protected void initStreams(Socket socket) throws IOException {
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        available = true;
    }
    
    public boolean isAvailable(){
        return available;
    }

    public void closeStreams() throws IOException {
        oos.close();
        ois.close();
    }

    public void sendPacket(Packet packet) throws IOException {
        synchronized (this) {
            oos.writeObject(packet);
            oos.flush();
            if(++counter > 200){
                counter = 0;
                oos.reset();
            }
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
    public abstract ClipboardEvent getClipboardEvent();
}
