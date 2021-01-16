package rda.packet.handler;

import rda.clipboard.ClipboardUtil;
import rda.event.EventExecutor;
import rda.file.FileReceiver;
import rda.packet.ClipboardPacket;
import rda.packet.EventPacket;
import rda.packet.FilePacket;
import rda.packet.ImagePacket;
import rda.packet.Packet;
import rda.screenshot.ScreenShotShower;

public class PacketHandler {

    public synchronized static void handle(Packet packet) throws Exception {
        if (packet instanceof ImagePacket) {
            ScreenShotShower.show((ImagePacket) packet);
        }
        if (packet instanceof EventPacket) {
            EventExecutor.getInstance().pushEventPacket((EventPacket) packet);
        }
        if (packet instanceof ClipboardPacket) {
            ClipboardUtil.setClipboardText((((ClipboardPacket) packet).getMessage()));
        }
        if (packet instanceof FilePacket) {
            FileReceiver.write((FilePacket) packet);
        }
    }
}
