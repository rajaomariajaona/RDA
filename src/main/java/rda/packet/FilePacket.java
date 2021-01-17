package rda.packet;

public class FilePacket extends Packet {

    private final Long size;
    private final String path;
    private final boolean isDirectory = false;
    private final byte[] fileData;
    private final int position;

    public FilePacket(String path, Long size, byte[] fileData, int position) throws Exception {
        this.size = size;
        this.path = path;
        this.fileData = fileData;
        this.position = position;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public boolean isDirectory() {
        return isDirectory;
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
