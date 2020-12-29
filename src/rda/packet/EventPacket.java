package rda.packet;


public class EventPacket extends Packet {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {
        value = newValue;
    }

}
