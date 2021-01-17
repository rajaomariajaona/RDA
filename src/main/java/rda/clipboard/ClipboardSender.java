package rda.clipboard;

import rda.connection.Connection;
import rda.packet.ClipboardPacket;

public class ClipboardSender {

    Connection conn;

    public ClipboardSender(Connection conn) {
        this.conn = conn;

    }

    public void send(ClipboardPacket clipboardPacket) throws Exception {
        conn.sendPacket(clipboardPacket);
    }

}
