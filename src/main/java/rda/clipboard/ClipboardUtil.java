package rda.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipboardUtil {
    public static String getClipboardText(){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        if(clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)){
            try {
                String text = (String) clipboard.getData(DataFlavor.stringFlavor);
                if (text != null && text.length() > 0)
                {
                    try {
                        return text;
                    } catch (Exception ex) {
                        Logger.getLogger(ClipboardFactory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(ClipboardFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClipboardFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    public static void setClipboardText(String text){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }
}
