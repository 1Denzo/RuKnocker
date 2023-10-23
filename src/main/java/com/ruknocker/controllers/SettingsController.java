package com.ruknocker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private TextField serverUser;
    @FXML
    private TextField serverSshPort;

    private Boolean isSaved;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isSaved = false;
    }

    public String getServerUser() {
        return this.serverUser.getText();
    }

    public String getServerSshPort() {
        return this.serverSshPort.getText();
    }

    public void setServerUser(String serverUser) {
        this.serverUser.setText(serverUser);
    }

    public void setServerSshPort(String serverSshPort) {
        this.serverSshPort.setText(serverSshPort);
    }

    public void saveCommand(ActionEvent actionEvent) {
        isSaved = true;

    }

    public Boolean isSaved() {
        return isSaved;
    }
}
