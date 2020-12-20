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
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import rda.network.Guest;
import rda.packet.handler.ImageObservable;

/**
 *
 * @author snowden
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView imgView;

    @FXML
    private StackPane parent;
    
    @FXML
    private AnchorPane imgContainer, homeContainer;
    
    @FXML
    private TextField hostAddress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgView.fitHeightProperty().bind(parent.heightProperty());
        imgView.fitWidthProperty().bind(parent.widthProperty());
        ImageObservable.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object image) {
                BufferedImage bi = (BufferedImage) image;
                imgView.setImage(SwingFXUtils.toFXImage(bi, null));
            }
        });
    }

    @FXML
    private void handleMouseMove(MouseEvent t) {
//        try {
//            ImageView source = (ImageView) t.getSource();
//            double x = t.getX();
//            double maxX = source.getFitWidth();
//            double y = t.getY();
//            double maxY = t.getSceneY();
//            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//            x = x * d.getWidth() / maxX;
//            y = y * d.getHeight() / maxY;
//            Robot r = new Robot();
//            r.mouseMove(Double.valueOf(x).intValue(), Double.valueOf(y).intValue());
//        } catch (AWTException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    @FXML
    private void startAction(ActionEvent t) throws UnknownHostException{
        imgContainer.toFront();
        new Thread(new Guest(InetAddress.getByName(hostAddress.getText()))).start();
    }

}
