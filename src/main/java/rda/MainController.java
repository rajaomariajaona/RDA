package rda;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import rda.connection.Connection;
import rda.connection.GuestConnection;
import rda.event.EventPacketFactory;
import rda.event.EventPacketSender;
import rda.file.FileSender;
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
    private Node imgContainer;

    @FXML
    private TextField hostAddress;

    @FXML
    private ChoiceBox<NetworkInterface> choiceNetwork;

    @FXML
    private Label myIP;

    private EventPacketSender eventPacketSender;

    private Connection connection = null;
    boolean in = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<NetworkInterface> listInterface = Collections.list(NetworkInterface.getNetworkInterfaces());
            choiceNetwork.getItems().addAll(listInterface);
            choiceNetwork.setValue(listInterface.get(0));
            setValue();
            choiceNetwork.setConverter(new StringConverter<NetworkInterface>() {
                List<NetworkInterface> list = Collections.list(NetworkInterface.getNetworkInterfaces());
                @Override
                public String toString(NetworkInterface t) {
                    return t.getDisplayName();
                }

                @Override
                public NetworkInterface fromString(String string) {
                    for (NetworkInterface t : list) {
                        if (t.getDisplayName().equals(string)) {
                            return t;
                        }
                    }
                    return null;
                }
            });
            choiceNetwork.setOnAction((t) -> {
                setValue();
            });
            
        } catch (SocketException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        imgContainer.setVisible(false);
        MainController._imgView = imgView;
        imgView.fitHeightProperty().bind(parent.heightProperty());
        imgView.fitWidthProperty().bind(parent.widthProperty());
    }

    private void setValue() {
        List<InetAddress> addresses = Collections.list(choiceNetwork.getValue().getInetAddresses());
        for (InetAddress ad : addresses) {
            if (ad instanceof Inet4Address) {
                myIP.setText(ad.getHostAddress());
            }
        }
    }

    @FXML
    private void handleEvents(InputEvent ie) throws Exception {
        EventPacket ep = EventPacketFactory.createEventPacket(ie);
        this.eventPacketSender.send(ep);
    }

    @FXML
    private void startAction(ActionEvent ae) {
        progress.setVisible(true);
        startButton.setDisable(true);
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        ListenableFuture future = service.submit(new Callable() {
            public Object call() throws Exception {
                initConnection();
                initEventPacketSender();
                return null;
            }
        }
        );
        Futures.addCallback(future,
                new FutureCallback() {
            public void onSuccess(Object o) {
                connectionSuccessful();
                reset();
            }

            public void onFailure(Throwable thrown) {
                showConnectionError();
                reset();
            }

        }, service);
    }

    @FXML
    private void quitAction(ActionEvent ae) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Do you really want to quit?");
        a.showAndWait();
        if (a.getResult().equals(ButtonType.OK)) {
            System.exit(0);
        } else if (a.getResult().equals(ButtonType.NO)) {

        }
    }

    @FXML
    private void networkSettingAction(ActionEvent ae) {

    }

    private void reset() {
        Platform.runLater(() -> {
            progress.setVisible(false);
            startButton.setDisable(false);
        });
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

    private void connectionSuccessful() {
        Platform.runLater(() -> {
            imgContainer.setVisible(true);
            imgContainer.toFront();
            try {

                imgContainer.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                    try {
                        handleEvents((InputEvent) event);
                        event.consume();
                    } catch (Exception ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                imgContainer.getScene().addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                    try {
                        handleEvents((InputEvent) event);
                        event.consume();
                    } catch (Exception ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } catch (Exception ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void showConnectionError() {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Connection aborted");
            a.show();
        });
    }

    @FXML
    private void sendFile(ActionEvent ae) {
        try {
            Thread t = new Thread(new FileSender(connection, "/home/snowden/copy/"));
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
