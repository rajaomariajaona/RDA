package rda;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import rda.connection.HostConnection;

public class main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setResizable(true);
        stage.show();
        test();
    }

    public static void main(String[] args) {
        
        new HostConnection().start();
        launch(args);
    }

    private void test() {
        TrayIcon trayIcon = null;
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = null;
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/resource/images/tray.png"));
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
            ActionListener listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Platform.runLater(() -> {
                        main.stage.show();
                        main.stage.toFront();
                    });
                }
            };
            PopupMenu popup = new PopupMenu();
            MenuItem lol = new MenuItem("Ouvrir");
            lol.addActionListener(listener);
            popup.add(lol);
            
            MenuItem exit = new MenuItem("Quitter");
            exit.addActionListener((ae) -> {
                System.exit(0);
            });
            popup.add(exit);
            trayIcon = new TrayIcon(image, "RDA (Remote Desktop App)", popup);
            trayIcon.addActionListener(listener);
            trayIcon.setImageAutoSize(true);
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            // ...
        } else {

        }
    }

}
