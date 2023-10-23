package com.ruknocker.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ruknocker.KnockerApplication;
import com.ruknocker.models.Connection;
import com.ruknocker.models.ConnectionPort;
import com.ruknocker.models.Port;
import com.ruknocker.models.Protocol;
import com.ruknocker.services.KnockerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private String serverUser;
    private String serverSshPort;

    @FXML
    public void addButtonClick(ActionEvent actionEvent) {
        ports.add(new Port("", null, protocols));
        portsTable.setItems(ports);
    }

    @FXML
    public void removeButtonClick(ActionEvent actionEvent) {
        Port selectedPort = portsTable.getSelectionModel().getSelectedItem();
        if (selectedPort == null) {
            if (ports.isEmpty()) return;
            ports.remove(ports.getLast());
        }
        else ports.remove(selectedPort);
        portsTable.setItems(ports);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ports = FXCollections.observableArrayList();
        protocols = FXCollections.observableArrayList(Protocol.TCP, Protocol.UDP);
        loadConfiguration();
        portsColumn.setCellValueFactory(new PropertyValueFactory("port"));
        portsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        protocolsColumn.setCellValueFactory(new PropertyValueFactory("protocols"));
    }

    private void loadConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File jsonInputFile = new File("config.conf");
            Connection con = mapper.readValue(jsonInputFile, Connection.class);
            ipAddress.setText(con.getIp());
            serverUser = con.getLogin();
            serverSshPort = con.getPort();
            for (ConnectionPort port: con.getPorts()) {
                ports.add(new Port(port.getPort(), Protocol.get(port.getProtocol()), protocols));
            }
            portsTable.setItems(ports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void portChanged(TableColumn.CellEditEvent<Port, String> portStringCellEditEvent) {
        Port port = portsTable.getSelectionModel().getSelectedItem();
        port.setPort(portStringCellEditEvent.getNewValue());
    }

    public void knockStart(ActionEvent actionEvent) throws InterruptedException, IOException {
        for (Port port: ports) {
            knocker.knock(ipAddress.getText(),
                    Short.parseShort(port.getPort()),
                    port.getProtocols()
                            .getSelectionModel()
                            .getSelectedItem()
                            .getProtocol());
            Thread.sleep(1000);
        }
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("cmd.exe /c start cmd.exe /c ssh " + serverUser + "@" + ipAddress.getText() + " -p " + serverSshPort);
    }

    public void saveConfiguration(ActionEvent actionEvent) throws JsonProcessingException {
        Connection con = new Connection();
        con.setIp(ipAddress.getText());
        con.setLogin(serverUser);
        con.setPort(serverSshPort);
        var portList = new ArrayList<ConnectionPort>();
        for (Port port: ports) {
            var conPort = new ConnectionPort();
            conPort.setPort(port.getPort());
            conPort.setProtocol(port.getProtocols().getSelectionModel().getSelectedItem().toString());
            portList.add(conPort);
        }
        con.setPorts(portList);
        ObjectMapper om = new ObjectMapper();
        var str = om.writeValueAsString(con);
        File file = new File("config.conf");
        try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
            out.print(str);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void openSettings(ActionEvent actionEvent) throws IOException {
        var loader = new FXMLLoader(KnockerApplication.class.getResource("SettingsUI.fxml"));
        Parent root = loader.load();
        var controller = loader.<SettingsController>getController();
        controller.setServerUser(serverUser);
        controller.setServerSshPort(serverSshPort);
        Stage settingsWindow = new Stage(StageStyle.UTILITY);
        settingsWindow.setTitle("Настройка соединения");
        Scene settingsScene = new Scene(root);
        settingsWindow.setScene(settingsScene);
        settingsWindow.initModality(Modality.WINDOW_MODAL);
        settingsWindow.initOwner(KnockerApplication.getMainWindow());
        settingsWindow.showAndWait();
        if (controller.isSaved()) {
            serverUser = controller.getServerUser();
            serverSshPort = controller.getServerSshPort();
        }
    }
}