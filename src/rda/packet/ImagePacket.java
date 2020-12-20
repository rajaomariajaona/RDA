/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.packet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author snowden
 */
public class ImagePacket extends Packet {

    private String imageType = "jpg";

    public BufferedImage getImage() throws Exception {
        return (BufferedImage) deserialize();
    }

    public void setImage(BufferedImage image) throws Exception {
        serialize(image);
    }
    
    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
    
    @Override
    protected Object deserialize() throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(this.getData());
        return ImageIO.read(bais);
    }
    
    @Override
    protected void serialize(Object obj) throws Exception {
        BufferedImage bufferedImage = (BufferedImage) obj;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, this.imageType, baos);
        setData(baos.toByteArray());
    }
    
}
