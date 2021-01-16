package rda.file;

import java.io.File;
import java.nio.file.Path;
import rda.packet.FilePacket;

public class FileFactory {

    public FilePacket createFilePacket(Path path) throws Exception {
        File f = path.toFile();
        if (!f.exists()) {
            throw new Exception("File not existing");
        }
//        FilePacket fp = new FilePacket();
//        if (f.isDirectory()) {
//            fp.setPath(path);
//            fp.setDirectory(true);
//            
//        } else {
//            fp.setPath(path);
//            fp.setSize(f.length());
//        }
        return null;
    }
}
