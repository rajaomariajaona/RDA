package rda;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        OnlyOneApp ooa = new OnlyOneApp();
        if (!ooa.isAppActive()) {
            new HostConnection().start();
            launch();
        } else {
            final Runnable showDialog = () -> {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Another instance of program is Running");
                a.setContentText("If you are sure that no other app is Running, delete this file. " + new File(System.getProperty("user.home"), "RDA.tmp")
                        .toPath().toString()
                );
                a.getDialogPane().setMinHeight(180);
                a.show();
            };
            if (Platform.isFxApplicationThread()) {
                showDialog.run();
            } else {
                FutureTask<Void> showDialogTask = new FutureTask<Void>(showDialog, null);
                Platform.startup(showDialogTask);
                try {
                    showDialogTask.get();
                } catch (InterruptedException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
