package rda.packet;

import javafx.scene.input.KeyCode;
import rda.packet.constant.KeyboardEventType;

public class KeyboardEventPacket extends EventPacket {

    private KeyboardEventType eventType;
    private KeyCode keyCode;

    public KeyboardEventPacket(KeyboardEventType eventType, KeyCode kc) {
        this.eventType = eventType;
        this.keyCode = kc;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }
    
    public KeyboardEventType getEventType() {
        return eventType;
    }
    
}
