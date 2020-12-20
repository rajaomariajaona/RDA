/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.packet;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author snowden
 */
public class PacketToSend {
    public static final ConcurrentLinkedQueue<Packet> packets = new ConcurrentLinkedQueue<Packet>();
}
