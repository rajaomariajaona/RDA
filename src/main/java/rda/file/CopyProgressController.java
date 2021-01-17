package rda.file;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class CopyProgressController implements Initializable {

    @FXML
    private ProgressBar currentProgress, allProgress;
    @FXML
    private Label currentLabel, allLabel;
    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setOnCancel(EventHandler<ActionEvent> eh) {
        btnCancel.setOnAction(eh);
    }

    public void setCurrentProgress(double d) {
        currentLabel.setText(Math.floor(d * 100) + " %");
        currentProgress.setProgress(d);
    }

    public void setAllProgress(int current, int total) {
        allLabel.setText(current + " / " + total);
        allProgress.setProgress((current + 0.0) / total);
    }
}
