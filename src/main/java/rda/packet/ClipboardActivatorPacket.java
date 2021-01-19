package rda.packet;
public class ClipboardActivatorPacket extends Packet {
    private boolean state;

    public ClipboardActivatorPacket(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }
    
}
