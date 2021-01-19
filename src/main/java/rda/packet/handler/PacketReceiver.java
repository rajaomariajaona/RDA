package rda.packet.handler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.connection.Connection;
import rda.event.EventExecutor;
import rda.packet.ClipboardActivatorPacket;
import rda.packet.Packet;

public class PacketReceiver implements Runnable {

    public Connection connection;

    public void run() {
        try {
            initEventExecutor();
            while (!Thread.currentThread().isInterrupted()) {
                Packet packet = connection.receivePacket();
                if (packet instanceof ClipboardActivatorPacket) {
                    PacketHandler.setClipboardActive(((ClipboardActivatorPacket) packet).getState());
                } else {
                    PacketHandler.handle(packet, connection);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PacketReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PacketReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PacketReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PacketReceiver(Connection connection) {
        this.connection = connection;
    }

    private void initEventExecutor() {
        EventExecutor.getInstance().start();
    }

}
