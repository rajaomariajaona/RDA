package rda.event;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import rda.packet.EventPacket;
import rda.packet.MouseEventPacket;
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

                p = new MouseEventPacket(x, y, MouseEventType.PRESS, getMouseButton(me.getButton()));
            } else if (me.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                p = new MouseEventPacket(x, y, MouseEventType.RELEASE, getMouseButton(me.getButton()));
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
            System.out.println(se.getDeltaY() > 0 ? 1 : -1);
            p = new MouseEventPacket(x, y, MouseEventType.WHEEL, se.getDeltaY() > 0 ? 1 : -1);
        }
        return p;
    }

    private static int getMouseButton(javafx.scene.input.MouseButton m) {
        switch (m) {
            case MIDDLE:
                return java.awt.event.InputEvent.BUTTON2_DOWN_MASK;
            case PRIMARY:
                return java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
            case SECONDARY:
                return java.awt.event.InputEvent.BUTTON3_DOWN_MASK;
            default:
                return 0;
        }
    }

}
