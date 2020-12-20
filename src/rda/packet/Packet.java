/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author snowden
 */
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
        return ois.readObject();
    }
    protected void serialize(Object obj) throws Exception {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        this.setData(baos.toByteArray());
    }
}
