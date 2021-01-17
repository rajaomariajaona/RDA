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

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Platform.setImplicitExit(false);
        App.stage = stage;
        scene = new Scene(loadFXML("main"));
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setResizable(true);
        stage.setTitle("RDA");
        stage.show();
        test();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void launch(String[] args) {
        new HostConnection().start();
        launch();
    }

    private void test() {
        TrayIcon trayIcon = null;
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = null;
            try {
                image = ImageIO.read(App.class.getResourceAsStream("images/tray.png"));
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            ActionListener listener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Platform.runLater(() -> {
                        App.stage.show();
                        App.stage.toFront();
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
