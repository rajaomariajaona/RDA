package rda.event;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import rda.packet.EventPacket;
import rda.packet.KeyboardEventPacket;
import rda.packet.MouseEventPacket;
import rda.packet.constant.KeyboardEventType;
import rda.packet.constant.MouseEventType;

public class EventPacketFactory {

    public static EventPacket createEventPacket(InputEvent event) {
        EventPacket p = null;
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
            ImageView source = (ImageView) event.getSource();
            double x = me.getX();
            double maxX = source.getFitWidth();
            double y = me.getY();
            double maxY = source.getFitHeight();
            x = x / maxX;
            y = y / maxY;
            if (me.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {

                p = new MouseEventPacket(x, y, MouseEventType.PRESS, me.getButton());
            } else if (me.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                p = new MouseEventPacket(x, y, MouseEventType.RELEASE, me.getButton());
            } else if (me.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
                p = new MouseEventPacket(x, y, MouseEventType.MOVE, 0);
            }
        } else if (event instanceof ScrollEvent) {
            ScrollEvent se = (ScrollEvent) event;
            ImageView source = (ImageView) se.getSource();
            double x = se.getX();
            double maxX = source.getFitWidth();
            double y = se.getY();
            double maxY = source.getFitHeight();
            x = x / maxX;
            y = y / maxY;
            int v = Double.valueOf(se.getDeltaY()).intValue();
            p = new MouseEventPacket(x, y, MouseEventType.WHEEL, Double.valueOf(-v / 3).intValue());
        } else if (event instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) event;
            if (ke.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                p = new KeyboardEventPacket(KeyboardEventType.PRESS, ke.getCode());
            } else if (ke.getEventType().equals(KeyEvent.KEY_RELEASED)) {
                p = new KeyboardEventPacket(KeyboardEventType.RELEASED, ke.getCode());
            }
        } else {
            System.out.println(event.getClass());
        }
        return p;
    }
}
