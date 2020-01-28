package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerStatistiche {

    private DBConnection connection=null;

    @FXML
    private Label fatturatoLabel;

    public void verifyConnection(DBConnection conn){
        if(conn==null){
            System.out.println("null");
        }
        else{
            connection=conn;
            fatturatoLabel.setText(connection.tryQuery());
        }



    }


    public void backButtonClicked(ActionEvent event) throws IOException {
        /*Parent ViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(ViewParent,800,500));
        window.show();*/

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent ViewParent = loader.load();
        Scene ViewScene = new Scene(ViewParent,800,500);

        Controller controller= loader.getController();
        controller.verifyConnection(connection);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(ViewScene);
        window.show();
    }
}
