/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda;

import rda.screenshot.ScreenShotSender;
import rda.event.EventPacketSender;
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
import rda.connection.GuestConnection;
import rda.connection.HostConnection;

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

    private ScreenShotSender screenShotSender;

    boolean in = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLDocumentController._imgView = imgView;
        imgView.fitHeightProperty().bind(parent.heightProperty());
        imgView.fitWidthProperty().bind(parent.widthProperty());
    }

    @FXML
    private void handleMouse(InputEvent ie) throws Exception {
//        MousePacket p = null;
//        if (ie instanceof MouseEvent) {
//
//            MouseEvent t = (MouseEvent) ie;
//            if (t.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
//                in = true;
//            }
//            if (t.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
//                in = false;
//            }
//            if (in) {
//                ImageView source = (ImageView) t.getSource();
//                double x = t.getX();
//                double maxX = source.getFitWidth();
//                double y = t.getY();
//                double maxY = source.getFitHeight();
//                x = x / maxX;
//                y = y / maxY;
//                if (t.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
//                    p = new MousePacket(x, y, MouseAction.PRESS, MouseButtons.fromMouseButtonJFX(t.getButton()));
//                } else if (t.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
//                    p = new MousePacket(x, y, MouseAction.RELEASE, MouseButtons.fromMouseButtonJFX(t.getButton()));
//                } else if (t.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
//                    p = new MousePacket(x, y, MouseAction.MOVE, 0);
//                }
//            }
//        } else if (ie instanceof ScrollEvent) {
//            if (in) {
//                ScrollEvent s = (ScrollEvent) ie;
//                ImageView source = (ImageView) s.getSource();
//                double x = s.getX();
//                double maxX = source.getFitWidth();
//                double y = s.getY();
//                double maxY = source.getFitHeight();
//                x = x / maxX;
//                y = y / maxY;
//                System.out.println(s.getDeltaY() > 0 ? 1 : -1);
//                p = new MousePacket(x, y, MouseAction.WHEEL, s.getDeltaY() > 0 ? 1 : -1);
//            }
//        }
//        if (p != null) {
//            PacketToSend.mousePacket = p;
//            Thread.sleep(10);
//        }

    }

    @FXML
    private void startAction(ActionEvent t) throws UnknownHostException {
        imgContainer.toFront();
        initConnection();
    }

    private void initConnection() {
        try {
            new GuestConnection(InetAddress.getByName(hostAddress.getText()));
        } catch (UnknownHostException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void showImage(BufferedImage image) {
        _imgView.setImage(SwingFXUtils.toFXImage(image, null));
    }

}
