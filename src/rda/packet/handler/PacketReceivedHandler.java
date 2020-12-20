/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.packet.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import rda.packet.ImagePacket;
import rda.packet.Packet;

/**
 *
 * @author snowden
 */
public class PacketReceivedHandler implements Runnable {
    
    private Packet packet;
    
    public PacketReceivedHandler(Packet packet) {
        this.packet = packet;
    }
    
    @Override
    public void run() {
        try {
            if (this.packet instanceof ImagePacket) {
                ImagePacket ip = (ImagePacket) packet;
                ImageObservable.getInstance().setImage(ip.getImage());
            }
        } catch (Exception ex) {
            Logger.getLogger(PacketReceivedHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
