package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLogin {

    private DBConnection connection=null;

    @FXML
    TextField indirizzoTextField;
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordPasswordField;
    @FXML
    Label errorLabel;


    //connesione al db e cambio scena sul menu

    public void connectionButtonClicked(ActionEvent event) throws IOException {
        connection=new DBConnection(indirizzoTextField.getText(),usernameTextField.getText(),passwordPasswordField.getText());
        boolean connRes=false;
        connRes=connection.connect();

        if(connRes) {
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("sample.fxml"));
            Parent ViewParent = loader.load();
            Scene ViewScene = new Scene(ViewParent,800,500);

            Controller controller= loader.getController();
            controller.verifyConnection(connection);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            //window.setTitle("ECommerce Nuova Autoricambi");
            window.setScene(ViewScene);
            window.show();
        }
        else
            errorLabel.setText("Errore di connessione, Riprova");
    }
}
