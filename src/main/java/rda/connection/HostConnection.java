package rda.connection;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import rda.App;
import rda.clipboard.ClipboardEvent;
import rda.packet.handler.PacketReceiver;
import rda.screenshot.ScreenShotSender;

public class HostConnection extends Connection implements Runnable {

    private ServerSocket serverSocket;
    private ClipboardEvent ce;

    public HostConnection() {
    }

    public void start() {
        Thread hostThread = new Thread(this);
        hostThread.setPriority(Thread.MIN_PRIORITY);
        hostThread.start();

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            serverSocket.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(HOST_PORT);
        } catch (Exception ex) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex1) {
                Logger.getLogger(HostConnection.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Platform.runLater(() -> {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("PORT ERROR");
                a.setTitle("Another program use the port " + HOST_PORT);
                a.initOwner(App.stage);
                a.showAndWait();
                System.exit(1);
            });
        }
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                super.initStreams(socket);
                TrayNotification tray = new TrayNotification(socket.getInetAddress().getHostName(), "Control your computer", Notifications.NOTICE);
                tray.showAndWait();
                ScreenShotSender screenShotSender = new ScreenShotSender(this);
                Thread screenShotThread = new Thread(screenShotSender);
                screenShotThread.setPriority(Thread.NORM_PRIORITY);
                screenShotThread.start();

                PacketReceiver packetReceiver = new PacketReceiver(this);
                Thread packetReceiverThread = new Thread(packetReceiver);
                packetReceiverThread.setPriority(Thread.NORM_PRIORITY);
                packetReceiverThread.start();
                ce = new ClipboardEvent(this);
                ce.start();
            }
        } catch (Exception e) {

        }
    }

    public ClipboardEvent getClipboardEvent() {
        return ce;
    }

}
