/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import rda.packet.Packet;

/**
 *
 * @author snowden
 */
public class SocketHandler {

    public ObjectOutputStream oos;
    public ObjectInputStream ois;

    public void initStream(Socket socket) throws IOException {
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void closeStream() throws IOException {
        oos.close();
        ois.close();
    }

    public void send(Packet p) throws IOException {
        oos.writeObject(p);
        oos.flush();
    }

    public Packet receive() throws IOException, ClassNotFoundException {
        return (Packet) ois.readObject();
    }
}
