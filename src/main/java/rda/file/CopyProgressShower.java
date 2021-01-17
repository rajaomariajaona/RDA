package rda.file;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import rda.App;

public class CopyProgressShower {

    private final Stage stage;
    private final CopyProgressController cpc;
    final private double WIDTH = 300;
    final private double HEIGHT = 170;

    public CopyProgressShower() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("CopyProgress.fxml"));
        Parent p = fxmlLoader.load();
        Scene scene = new Scene(p, WIDTH, HEIGHT);
        stage = new Stage();
        stage.setMinWidth(WIDTH);
        stage.setMaxWidth(WIDTH);
        stage.setMinHeight(HEIGHT);
        stage.setMaxHeight(HEIGHT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        cpc = (CopyProgressController) fxmlLoader.getController();
    }
    
    public void setOnCancel(EventHandler<ActionEvent> eh) {
        cpc.setOnCancel(eh);
    }

    public void setCurrentProgress(double d) {
        Platform.runLater(() -> {
            cpc.setCurrentProgress(d);
        });
    }

    public void setAllProgress(int current, int total) {
        Platform.runLater(() -> {
            cpc.setAllProgress(current, total);
        });
        
    }

    public void showAndWait() {
        Platform.runLater(() -> {
            stage.showAndWait();
        });
    }
}
