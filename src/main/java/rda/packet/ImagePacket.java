package rda.packet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
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

    private Object deserialize() throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(this.getData());
        Object res = ImageIO.read(bais);
        bais.close();
        return res;
    }

    private void serialize(BufferedImage bufferedImage) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, this.imageType, baos);
        bufferedImage = null;
        setData(baos.toByteArray());
        baos.close();
    }

    public ImagePacket(BufferedImage image) throws Exception {
        setImage(image);
    }

}
