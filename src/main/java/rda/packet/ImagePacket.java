package rda.packet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
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
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if(!writers.hasNext()) throw new IllegalStateException();
        ImageWriter writer = writers.next();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writer.setOutput(ImageIO.createImageOutputStream(baos));
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.5f);
        writer.write(null, new IIOImage(bufferedImage, null, null), param);
        bufferedImage = null;
        setData(baos.toByteArray());
        baos.close();
        writer.dispose();
    }

    public ImagePacket(BufferedImage image) throws Exception {
        setImage(image);
    }

}
