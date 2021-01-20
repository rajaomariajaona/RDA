package rda.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import rda.connection.Connection;
import rda.packet.ClipboardPacket;

public class ClipboardEvent extends Thread implements ClipboardOwner {

    private final Connection connection;
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public void setClipboardText(String text) {
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, this);
        this.setPriority(MIN_PRIORITY);
        System.out.println("CLIPBOARD SET: " + text);
    }

    @Override
    public void lostOwnership(Clipboard clpbrd, Transferable t) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            regainOwnership(clpbrd.getContents(this));
            processContents(clpbrd.getContents(this));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    void processContents(Transferable t) throws Exception {
        System.out.println("Inside processing");
        if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            String clip = (String) t.getTransferData(DataFlavor.stringFlavor);
            connection.sendPacket(new ClipboardPacket(clip));
            System.out.println("CLIPBOARD SENT: " + clip);
        }
    }
    void regainOwnership(Transferable t) {
        clipboard.setContents(t, this);
    }

    public void run() {
        try {
            Transferable trans = clipboard.getContents(this);
            regainOwnership(trans);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Listening to board...");
    }

    public ClipboardEvent(Connection connection) {
        this.connection = connection;
    }
}
