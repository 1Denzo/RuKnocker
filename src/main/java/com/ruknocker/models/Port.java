package com.ruknocker.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class Port {
    private SimpleStringProperty port;
    private ComboBox<Protocol> protocols;

    public Port(String port, ObservableList<Protocol> protocols) {
        port = port.isEmpty() ? String.valueOf(0) : port;
        this.port = new SimpleStringProperty(port);
        initProtocolsComboBox(protocols);
    }

    public String getPort() {
        return port.get();
    }
    public void setPort(String port) {
        this.port.set(port);
    }

    public ComboBox<Protocol> getProtocols() {
        return protocols;
    }

    public void setProtocols(ComboBox<Protocol> protocols) {
        this.protocols = protocols;
    }

    public void initProtocolsComboBox(ObservableList<Protocol> protocols) {
        this.protocols = new ComboBox<>(protocols);
        this.protocols.setPrefWidth(Double.MAX_VALUE);
        this.protocols.setValue(this.protocols.getItems().getFirst());
    }
}
