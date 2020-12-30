package rda.packet;

import javafx.scene.input.MouseButton;
import rda.packet.constant.MouseEventType;

public class MouseEventPacket extends EventPacket {

    private final double x;
    private final double y;
    private final MouseEventType eventType;
    private MouseButton mouseButton;
    private int value;

    public MouseEventPacket(double x, double y, MouseEventType eventType, MouseButton mouseButton) {
        this.x = x;
        this.y = y;
        this.eventType = eventType;
        this.mouseButton = mouseButton;
    }
    public MouseEventPacket(double x, double y, MouseEventType eventType, int value) {
        this.x = x;
        this.y = y;
        this.eventType = eventType;
        this.value = value;
    }

    public MouseButton getMouseButton() {
        return mouseButton;
    }

    public int getValue() {
        return value;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public MouseEventType getEventType() {
        return eventType;
    }

}
