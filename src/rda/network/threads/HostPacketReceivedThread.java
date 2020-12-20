/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.network.threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import rda.network.SocketHandler;
import rda.packet.ImagePacket;
import rda.packet.Packet;
import rda.packet.handler.ImageObservable;
import rda.packet.handler.PacketReceivedHandler;

/**
 *
 * @author snowden
 */
public class HostPacketReceivedThread implements Runnable {

    private SocketHandler socketHandler;

    public HostPacketReceivedThread(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Packet packet = (Packet) socketHandler.receive();
                if (packet instanceof ImagePacket) {
                    ImagePacket ip = (ImagePacket) packet;
                    ImageObservable.getInstance().setImage(ip.getImage());
                } else {
                    System.out.println(new String(packet.getData()));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PacketReceivedHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
