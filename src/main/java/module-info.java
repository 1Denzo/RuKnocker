module com.ruknocker.ruknocker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.ruknocker to javafx.fxml;
    exports com.ruknocker;
    exports com.ruknocker.controller;
    opens com.ruknocker.controller to javafx.fxml;
}