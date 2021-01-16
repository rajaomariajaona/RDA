package rda.file;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.packet.FilePacket;

public class FileReceiver {

    final static String DEFAULT_PATH = "C:\\Users\\jane\\Desktop\\";
    private static String currentFile;
    private static FileOutputStream fos;
    private static Long currentSize;
    private static int last;

    public static void write(FilePacket filePacket) {
        try {
            System.out.println(filePacket.getIndex());
//            System.out.println(filePacket.getSize());
//            System.out.println((filePacket.getSize() - currentSize) * 100 / filePacket.getSize() + " %");
            if (currentFile == null ? false : currentFile.equals(filePacket.getPath())) {
                if (currentSize <= 4 * 1024) {
                    System.out.println(Arrays.toString(filePacket.getFileData()));
                    fos.write(filePacket.getFileData(), 0, filePacket.getPosition());
                    try {
                        fos.flush();
                        fos.close();
                        currentFile = null;
                        currentSize = null;
                        System.out.println("CLOSED");
                    } catch (Exception ex) {
                        Logger.getLogger(FileReceiver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    fos.write(filePacket.getFileData(), 0, filePacket.getPosition());
                    currentSize -= 4 * 1024;
                }
            } else {
                currentFile = filePacket.getPath();
                currentSize = filePacket.getSize();
                File f = Path.of(DEFAULT_PATH, Path.of(currentFile).getFileName().toString()).toFile();
                fos = new FileOutputStream(f);
                write(filePacket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}
