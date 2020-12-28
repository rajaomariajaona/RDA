package rda.packet;


public class EventPacket extends Packet {

    private int value;
    private long timestamp;

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {
        value = newValue;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long newTimestamp) {
        timestamp = newTimestamp;
    }

}
