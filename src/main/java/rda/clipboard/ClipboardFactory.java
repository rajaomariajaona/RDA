package rda.clipboard;

import rda.packet.ClipboardPacket;

public class ClipboardFactory {

    public static ClipboardPacket createClipboardPacket() throws Exception {
        String clipText = ClipboardUtil.getClipboardText();
        if (clipText == null) {
            return null;
        } else {
            return new ClipboardPacket(clipText);
        }
    }
}
