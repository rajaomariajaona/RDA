/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.packet.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import rda.network.SocketHandler;
import rda.packet.ImagePacket;
import rda.packet.Packet;

/**
 *
 * @author snowden
 */
public class PacketReceivedHandler implements Runnable {

    private SocketHandler socketHandler;

    public PacketReceivedHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Packet packet = this.socketHandler.receive();
                if (packet instanceof ImagePacket) {
                    ImagePacket ip = (ImagePacket) packet;
                    ImageObservable.getInstance().setImage(ip.getImage());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PacketReceivedHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
