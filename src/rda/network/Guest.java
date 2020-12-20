/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.network;

import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.FXMLDocumentController;
import rda.other.ENV;
import rda.packet.Packet;
import rda.packet.handler.PacketReceivedHandler;

/**
 *
 * @author snowden
 */
public class Guest implements Runnable {

    private InetAddress hostAddress;

    public Guest(InetAddress hostAddress) {
        this.hostAddress = hostAddress;
    }

    @Override
    public void run() {
        try {   
            while (true) {
                try {
                    Socket socket = new Socket(hostAddress, ENV.PORT);
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Packet p = (Packet) ois.readObject();
                    new Thread(new PacketReceivedHandler(p)).start();
                } catch (Exception ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Guest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
