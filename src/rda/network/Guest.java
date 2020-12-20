/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.other.ENV;
import rda.packet.Packet;
import rda.packet.PacketToSend;
import rda.packet.handler.PacketReceivedHandler;

/**
 *
 * @author snowden
 */
public class Guest extends SocketHandler implements Runnable {

    private InetAddress hostAddress;

    public Guest(InetAddress hostAddress) {
        this.hostAddress = hostAddress;
    }
    public static int i = 0;

    @Override
    public void run() {
        try {
            Socket socket = new Socket(hostAddress, ENV.PORT);
            initStream(socket);
            Thread t = new Thread(new PacketReceivedHandler(this));
            t.start();

            //TODO: MANDEFA LE PACKET DE CONTROLE
            Thread t3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        while (!PacketToSend.packets.isEmpty()) {
                            try {
                                send(PacketToSend.packets.poll());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Guest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Packet p = new Packet();
                        p.setData(Integer.toString(++i).getBytes());
                        try {
                            send(p);
                            Thread.sleep(1000);
                        } catch (IOException ex) {
                            Logger.getLogger(Guest.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Guest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            t3.start();
            t2.start();
            t.join();
            t2.join();
            t3.join();
        } catch (Exception ex) {
            Logger.getLogger(Guest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                closeStream();
            } catch (IOException ex) {
            }
        }
    }
}
