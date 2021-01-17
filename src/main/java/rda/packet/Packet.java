package rda.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
public class Packet implements Serializable {

    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    
    protected Object deserialize() throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(this.getData()));
        Object res = ois.readObject();
        ois.close();
        return res;
    }
    protected void serialize(Object obj) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        this.setData(baos.toByteArray());
        oos.close();
        baos.close();
    }
}
