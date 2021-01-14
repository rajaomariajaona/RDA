package rda.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.connection.Connection;

public class ClipboardEvent {
    FlavorListener listener = new FlavorListener() {
        @Override
        public void flavorsChanged(FlavorEvent fe) {
            try {
                new ClipboardSender(connection).send(ClipboardFactory.createClipboardPacket());
            } catch (Exception ex) {
                Logger.getLogger(ClipboardEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    private Connection connection;

    public ClipboardEvent(Connection connection) {
        this.connection = connection;
    }
    
    public void startListening(){
        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(listener);
    }
    public void stopListening(){
        Toolkit.getDefaultToolkit().getSystemClipboard().removeFlavorListener(listener);
    }
}
