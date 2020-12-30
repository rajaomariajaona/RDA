package rda;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import rda.connection.Connection;
import rda.connection.GuestConnection;
import rda.event.EventPacketFactory;
import rda.event.EventPacketSender;
import rda.packet.EventPacket;

/**
 *
 * @author snowden
 */
public class MainController implements Initializable {
    
    @FXML
    private ImageView imgView;
    
    @FXML
    private ProgressBar progress;
    
    @FXML
    private Button startButton;
    
    private static ImageView _imgView;
    
    @FXML
    private StackPane parent;
    
    @FXML
    private AnchorPane imgContainer, homeContainer;
    
    @FXML
    private TextField hostAddress;
    
    private EventPacketSender eventPacketSender;
    
    private Connection connection = null;
    boolean in = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgContainer.setVisible(false);
        MainController._imgView = imgView;
        imgView.fitHeightProperty().bind(parent.heightProperty());
        imgView.fitWidthProperty().bind(parent.widthProperty());
    }
    
    @FXML
    private void handleEvents(InputEvent ie) throws Exception {
        EventPacket ep = EventPacketFactory.createEventPacket(ie);
        this.eventPacketSender.send(ep);
    }
    
    @FXML
    private void startAction(ActionEvent ae) {
        try {
            initConnection();
            initEventPacketSender();
            imgContainer.setVisible(true);
            imgContainer.toFront();
            try {
                imgContainer.getScene().setOnKeyPressed(t -> {
                    try {
                        handleEvents(t);
                    } catch (Exception ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                imgContainer.getScene().setOnKeyReleased(t -> {
                    try {
                        handleEvents(t);
                    } catch (Exception ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            showConnectionError();
        }
    }
    
    private void initConnection() throws Exception {
        connection = new GuestConnection(InetAddress.getByName(hostAddress.getText()));
    }
    
    public static void showImage(BufferedImage image) {
        _imgView.setImage(SwingFXUtils.toFXImage(image, null));
    }
    
    private void initEventPacketSender() {
        if (connection == null) {
            throw new NullPointerException("Connection must be initialized");
        }
        eventPacketSender = new EventPacketSender(connection);
    }
    
    private void showConnectionError() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText("Error lors du connection vers l'hote");
        a.show();
    }
    
}
