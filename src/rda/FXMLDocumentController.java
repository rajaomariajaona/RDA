/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ImageView imgView;
    
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
        
        FXMLDocumentController._imgView = imgView;
        imgView.fitHeightProperty().bind(parent.heightProperty());
        imgView.fitWidthProperty().bind(parent.widthProperty());
    }
    
    @FXML
    private void handleMouse(InputEvent ie) throws Exception {
        EventPacket ep = EventPacketFactory.createEventPacket(ie);
        this.eventPacketSender.send(ep);
    }
    
    @FXML
    private void startAction(ActionEvent t) throws UnknownHostException {
        imgContainer.toFront();
        initConnection();
        initEventPacketSender();
    }
    
    private void initConnection() {
        try {
            connection = new GuestConnection(InetAddress.getByName(hostAddress.getText()));
        } catch (UnknownHostException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
}
