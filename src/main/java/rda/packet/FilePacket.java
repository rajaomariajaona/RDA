package rda.packet;

public class FilePacket extends Packet {
    
    private Long size;
    private final String path;
    private boolean isDirectory = false;
    private byte[] fileData;
    private int position;

    public FilePacket(String path, Long size, byte[] fileData, int position) throws Exception {
        this.size = size;
        this.path = path;
        this.fileData = fileData;
        this.position = position;
    }
    public FilePacket(String path) throws Exception {
        this.path = path;
        this.isDirectory = true;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public boolean isDirectory() {
        return isDirectory;
    }
    public void setDirectory(boolean t){
        this.isDirectory = t;
    }

    public Long getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public int getPosition() {
        return position;
    }
}
