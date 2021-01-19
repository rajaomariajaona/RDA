package rda.packet.handler;

import rda.connection.Connection;
import rda.connection.HostConnection;
import rda.event.EventExecutor;
import rda.file.FileReceiver;
import rda.packet.ClipboardPacket;
import rda.packet.EventPacket;
import rda.packet.FilePacket;
import rda.packet.ImagePacket;
import rda.packet.Packet;
import rda.screenshot.ScreenShotShower;

public class PacketHandler {

    private static boolean clipboardActive = false;

    public static void handle(Packet packet, Connection connection) throws Exception {
        if (packet instanceof ImagePacket) {
            ScreenShotShower.show((ImagePacket) packet);
        }
        if (packet instanceof EventPacket) {
            EventExecutor.getInstance().pushEventPacket((EventPacket) packet);
        }
        if (packet instanceof ClipboardPacket && isClipboardActive()) {
            System.out.println(isClipboardActive());
            System.out.println("TAFIDITRA HO AZY");
            connection.getClipboardEvent().setClipboardText((((ClipboardPacket) packet).getMessage()));
        }
        if (packet instanceof FilePacket) {
            FileReceiver.write((FilePacket) packet);
        }
        packet = null;
    }

    public static boolean isClipboardActive() {
        return clipboardActive;
    }

    public static void setClipboardActive(boolean clipboardActive) {
        PacketHandler.clipboardActive = clipboardActive;
    }

}
