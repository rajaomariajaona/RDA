package rda.connection;

import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import rda.App;
import rda.CallbackException;
import rda.clipboard.ClipboardEvent;
import rda.packet.handler.PacketReceiver;
import rda.screenshot.ScreenShotSender;

public class HostConnection extends Connection implements Runnable {
    
    private ServerSocket serverSocket;
    private ClipboardEvent ce;
    private CallbackException callback = new CallbackException() {
        @Override
        public void execute() {
            returnToHome();
        }
    };
    
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
                Platform.runLater(() -> {
                    App.stage.close();
                    Stage s = new Stage();
                    try {
                        s.setScene(new Scene(new FXMLLoader(App.class.getResource("Connected.fxml")).load()));
                    } catch (IOException ex) {
                        Logger.getLogger(HostConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    s.setResizable(false);
                    s.initStyle(StageStyle.UNDECORATED);
                    s.show();
                    App.currStage = s;
                    TrayNotification tray = new TrayNotification(socket.getInetAddress().getHostName(), "Control your computer", Notifications.NOTICE);
                    tray.setAnimation(Animations.POPUP);
                    tray.showAndDismiss(Duration.seconds(5));
                });
                super.initStreams(socket);
                ScreenShotSender screenShotSender = new ScreenShotSender(this);
                screenShotSender.setOnException(callback);
                Thread screenShotThread = new Thread(screenShotSender);
                screenShotThread.setPriority(Thread.MIN_PRIORITY);
                screenShotThread.start();
                
                PacketReceiver packetReceiver = new PacketReceiver(this);
                Thread packetReceiverThread = new Thread(packetReceiver);
                packetReceiverThread.setPriority(Thread.MIN_PRIORITY);
                packetReceiverThread.start();
                ce = new ClipboardEvent(this);
                ce.start();
            }
        } catch (Exception e) {
            returnToHome();
        }
    }
    
    public ClipboardEvent getClipboardEvent() {
        return ce;
    }
    
    private void returnToHome() {
        Platform.runLater(() -> {
            App.currStage.close();
            App.currStage = App.stage;
            App.currStage.show();
            App.currStage.toFront();
        });
    }
    
}
