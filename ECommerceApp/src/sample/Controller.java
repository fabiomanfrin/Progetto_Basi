package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;

public class Controller {

    private DBConnection connection=null;

    @FXML
    Label connectionLabel;
    @FXML
    Button connectionButton;
    @FXML
    Button statisticheButton;
    @FXML
    Button aggiungiButton;
    @FXML
    Button modificaButton;

    public void verifyConnection(DBConnection conn){
        if(conn!=null){
            connection=conn;
            connectionLabel.setText("Connected");
            connectionButton.setDisable(true);
            statisticheButton.setDisable(false);
            aggiungiButton.setDisable(false);
            modificaButton.setDisable(false);
        }


    }

    public void connectionButtonClicked(){
        connection=new DBConnection();
        boolean connRes=false;
        connRes=connection.connect("localhost/test","postgres","admin");

        if(connRes){
            connectionLabel.setText("Connected");
            connectionButton.setDisable(true);
            statisticheButton.setDisable(false);
            aggiungiButton.setDisable(false);
            modificaButton.setDisable(false);
        }
        String result=connection.tryQuery();
    }

    public void statisticheButtonClicked(ActionEvent event) throws IOException {
       /* Parent ViewParent = FXMLLoader.load(getClass().getResource("statistiche.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene ViewScene = new Scene(ViewParent,800,500);
        window.setScene(ViewScene);
        window.show();
        */

       FXMLLoader loader= new FXMLLoader();
       loader.setLocation(getClass().getResource("statistiche.fxml"));
       Parent ViewParent = loader.load();
       Scene ViewScene = new Scene(ViewParent,800,500);

       ControllerStatistiche controllerStat= loader.getController();
       controllerStat.verifyConnection(connection);

       Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(ViewScene);
       window.show();
    }

}
