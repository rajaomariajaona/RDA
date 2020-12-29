package rda.packet;

import rda.packet.constant.KeyboardEventType;

public class KeyboardEventPacket extends EventPacket {

    private KeyboardEventType eventType;

    public KeyboardEventPacket(KeyboardEventType eventType, int value) {
        this.eventType = eventType;
        setValue(value);
    }

    public KeyboardEventType getEventType() {
        return eventType;
    }
    
}
