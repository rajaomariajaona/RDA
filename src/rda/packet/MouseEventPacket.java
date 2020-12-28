package rda.packet;

import rda.packet.constant.MouseEventType;

public class MouseEventPacket extends EventPacket {

    private final double x;
    private final double y;
    private final MouseEventType eventType;

    public MouseEventPacket(double x, double y, MouseEventType eventType, int value) {
        this.x = x;
        this.y = y;
        this.eventType = eventType;
        setValue(value);
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
