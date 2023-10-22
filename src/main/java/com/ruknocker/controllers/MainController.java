package com.ruknocker.controllers;

import com.ruknocker.models.Port;
import com.ruknocker.models.Protocol;
import com.ruknocker.services.KnockerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextField ipAddress;
    @FXML
    private TableColumn<Port, String> portsColumn;
    @FXML
    private TableColumn<Port, ComboBox<Protocol>> protocolsColumn;
    @FXML
    private TableView<Port> portsTable;
    @FXML
    private ObservableList<Port> ports;

    private ObservableList<Protocol> protocols;
    private final KnockerService knocker = new KnockerService();

    @FXML
    public void addButtonClick(ActionEvent actionEvent) {
        ports.add(new Port("", protocols));
        portsTable.setItems(ports);
    }

    @FXML
    public void removeButtonClick(ActionEvent actionEvent) {
        Port selectedPort = portsTable.getSelectionModel().getSelectedItem();
        if (selectedPort == null) ports.remove(ports.getLast());
        else ports.remove(selectedPort);
        portsTable.setItems(ports);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ports = FXCollections.observableArrayList();
        protocols = FXCollections.observableArrayList(Protocol.TCP, Protocol.UDP);
        portsColumn.setCellValueFactory(new PropertyValueFactory("port"));
        portsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        protocolsColumn.setCellValueFactory(new PropertyValueFactory("protocols"));
    }

    public void portChanged(TableColumn.CellEditEvent<Port, String> portStringCellEditEvent) {
        Port port = portsTable.getSelectionModel().getSelectedItem();
        port.setPort(portStringCellEditEvent.getNewValue());
    }

    public void knockStart(ActionEvent actionEvent) {
        try {
            for (Port port: ports) {
                knocker.knock(ipAddress.getText(),
                        Short.parseShort(port.getPort()),
                        port.getProtocols()
                                .getSelectionModel()
                                .getSelectedItem()
                                .getProtocol());
                Thread.currentThread().sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}