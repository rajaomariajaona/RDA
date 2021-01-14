package rda.packet;
public class ClipboardPacket extends Packet {
    private String message;

    public ClipboardPacket() {
    }

    public ClipboardPacket(String message) throws Exception {
        setMessage(message);
    }
    
    public String getMessage() throws Exception {
        return (String) deserialize();
    }

    public void setMessage(String message) throws Exception {
        this.message = message;
        serialize(message);
    }
    
}
