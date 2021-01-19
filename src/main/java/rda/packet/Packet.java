package rda.packet;

import java.io.Serializable;
public class Packet implements Serializable {

    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
