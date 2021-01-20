package rda;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rda.file.FileSender;

public class ConnectedController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void quitAction(ActionEvent ae) {
        ((Stage) getSource(ae).getScene().getWindow()).close();
    }

    @FXML
    private void sendAction(ActionEvent ae) {
        try {
            FileChooser fileChooser = new FileChooser();

            List<File> files = fileChooser.showOpenMultipleDialog((Stage) getSource(ae).getScene().getWindow());
            FileSender fs = new FileSender(App.host, files);
            Thread t = new Thread(fs);
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void stopAction(ActionEvent ae) {
        try {
            App.host.closeStreams();
        } catch (IOException ex) {
            Logger.getLogger(ConnectedController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Button getSource(ActionEvent ae) {
        return (Button) ae.getSource();
    }
}
