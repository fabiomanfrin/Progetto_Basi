package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerAggiungi {
    @FXML
    Button ordineButton;
    private DBConnection connection;

    public void verifyConnection(DBConnection conn) {
        if (conn != null) {
            connection = conn;
        }
    }

    public void ordineButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("aggiungiOrdine.fxml"));
        Parent ViewParent = loader.load();
        Scene ViewScene = new Scene(ViewParent,800,500);

        ControllerAggiungiOrdine controller= loader.getController();
        controller.verifyConnection(connection);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("ECommerce Nuova Autoricambi");
        window.setScene(ViewScene);
        window.show();
    }

    public void backButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent ViewParent = loader.load();
        Scene ViewScene = new Scene(ViewParent,800,500);

        Controller controller= loader.getController();
        controller.verifyConnection(connection);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("ECommerce Nuova Autoricambi");
        window.setScene(ViewScene);
        window.show();
    }
}
