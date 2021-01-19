module rda {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;
    requires java.logging;
    requires java.base;
    requires com.google.common;

    opens rda to javafx.fxml;
    opens rda.file to javafx.fxml;
    exports rda;
    exports rda.file;
}