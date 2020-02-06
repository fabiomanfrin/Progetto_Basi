package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private DBConnection connection=null;

    @FXML
    Label connectionLabel;
    @FXML
    Label errorLabel;
    @FXML
    Button statisticheButton;
    @FXML
    Button modificaButton;
    @FXML
    MenuButton aggiungiMenuButton;


    public void verifyConnection(DBConnection conn){
        if(conn!=null){
            connection=conn;
            connectionLabel.setText("Connected");
        }


    }

   /* public void connectionButtonClicked(){
        connection=new DBConnection("localhost/test","postgres","admin");
        boolean connRes=false;
        connRes=connection.connect();

        if(connRes){
            connectionLabel.setText("Connected");
            connectionButton.setDisable(true);
            statisticheButton.setDisable(false);
            aggiungiButton.setDisable(false);
            modificaButton.setDisable(false);
        }
        //String result=connection.tryQuery();
    }*/


    //apre il sotto menù statistiche
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
        window.setTitle("ECommerce Nuova Autoricambi");
       window.setScene(ViewScene);
       window.show();
    }

    public void aggiungiCatalogoButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("aggiungiCatalogo.fxml"));
        Parent ViewParent = loader.load();
        Scene ViewScene = new Scene(ViewParent,800,500);

        ControllerAggiungiCatalogo controller= loader.getController();
        controller.verifyConnection(connection);

//        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); //per il bottone, visto che uso il menù non serve più
        Stage window = (Stage)aggiungiMenuButton.getScene().getWindow();
        window.setTitle("ECommerce Nuova Autoricambi");
        window.setScene(ViewScene);
        window.show();
    }

    public void aggiungiProdottoButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("aggiungiProdotto.fxml"));
        Parent ViewParent = loader.load();
        Scene ViewScene = new Scene(ViewParent,800,500);

        ControllerAggiungiProdotto controller= loader.getController();
        controller.verifyConnection(connection);

//        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); //per il bottone, visto che uso il menù non serve più
        Stage window = (Stage)aggiungiMenuButton.getScene().getWindow();
        window.setTitle("ECommerce Nuova Autoricambi");
        window.setScene(ViewScene);
        window.show();
    }

}
