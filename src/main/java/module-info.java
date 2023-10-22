module com.ruknocker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.ruknocker to javafx.fxml;
    exports com.ruknocker;
    exports com.ruknocker.controllers;
    exports com.ruknocker.models;
    opens com.ruknocker.models to javafx.fxml;
    opens com.ruknocker.controllers to javafx.fxml;
}