package rda.packet.handler;

import rda.event.EventExecutor;
import rda.packet.EventPacket;
import rda.packet.ImagePacket;
import rda.packet.Packet;
import rda.screenshot.ScreenShotShower;

public class PacketHandler {
    public static void handle(Packet packet) throws Exception{
        if(packet instanceof ImagePacket){
            ScreenShotShower.show((ImagePacket) packet);
        }
        if(packet instanceof EventPacket){
            EventExecutor.execute((EventPacket) packet);
        }
    }
}
