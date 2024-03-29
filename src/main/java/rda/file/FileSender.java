package rda.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import rda.connection.Connection;
import rda.packet.FilePacket;

public class FileSender implements Runnable {

    private final Connection connection;
    private final String path;
    private final CopyProgressShower cps;
    private final Queue<File> queue = new LinkedList();
    private FileInputStream fis = null;

    public FileSender(Connection connection, String path) throws IOException {
        this.connection = connection;
        this.path = path;
        cps = new CopyProgressShower();
    }

    public FileSender(Connection connection, List<File> files) throws IOException {
        this.connection = connection;
        this.path = Path.of("/").toString();
        cps = new CopyProgressShower();
        for (File f : files) {
            System.out.println(f.toPath().toString());
        }
        queue.addAll(files);
    }

    public void send(FilePacket filePacket) throws IOException {
        connection.sendPacket(filePacket);
    }

    private void addFilesToQueue(File f) {
        queue.add(f);
        if (f.isDirectory()) {
            for (File f1 : f.listFiles()) {
                addFilesToQueue(f1);
            }
        }
    }

    @Override
    public void run() {
        Thread curr = Thread.currentThread();
        cps.setOnCancel((t) -> {
            curr.interrupt();
            try {
                fis.close();
            } catch (Exception ex) {
                Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        File f;
        if (queue.isEmpty()) {
            f = new File(path);
            addFilesToQueue(f);
        }
        final int total = queue.size();
        cps.setAllProgress(0, total);
        cps.showAndWait();
        Path basePath;
        if (Path.of(path).equals(Path.of("/"))) {
            basePath = Path.of(path);
        } else {
            basePath = Path.of(path).getParent();
        }
        int current = 0;
        while (!queue.isEmpty() && !Thread.currentThread().isInterrupted()) {
            current++;
            f = queue.poll();
            cps.setCurrentFile(f.toPath().toString());
            if (f.isDirectory()) {
                try {
                    send(new FilePacket(relativePath(basePath, f.toPath()).toString()));
                } catch (Exception ex) {
                    Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                long size = f.length();
                double sent = 0;
                if (f.exists()) {
                    try {
                        fis = new FileInputStream(f);
                        byte[] block = new byte[4 * 1024];
                        int len;
                        FilePacket fp = null;
                        while ((len = fis.read(block)) != -1) {
                            byte[] data = null;
                            if (len == 4 * 1024) {
                                data = block;
                            } else {
                                data = new byte[len];
                                System.arraycopy(block, 0, data, 0, len);
                            }
                            block = new byte[4 * 1024];
                            fp = new FilePacket(relativePath(basePath, f.toPath()).toString(), f.length(), data, len);
                            sent += len;
                            send(fp);
                            cps.setCurrentProgress(sent / size);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fis.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            cps.setAllProgress(current, total);
        }
        cps.setAllProgress(total, total);
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(FileSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        cps.close();
    }

    private Path relativePath(Path base, Path orginal) {
        if (base.equals(Path.of("/"))) {
            return orginal.getFileName();
        }
        String b = base.toString();
        String o = orginal.toString();
        String res = o.replaceFirst(b, "");
        if (res.startsWith("/")) {
            res = res.substring(1);
        }
        return Path.of(res);
    }
}
