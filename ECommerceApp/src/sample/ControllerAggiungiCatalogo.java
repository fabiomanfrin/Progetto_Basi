package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerAggiungiCatalogo {

    @FXML
    private ChoiceBox<String> fornitoreChoiceBox;
    @FXML
    private TextField annoTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private Label errorLabel;

    private DBConnection connection;
    public void verifyConnection(DBConnection conn) {
        if (conn != null) {
            connection = conn;
            loadScene();
        }
    }

    private void loadScene() {
        ObservableList<Fornitore> l=getFornitore();
        for (int i=0;i<l.size();i++){
            fornitoreChoiceBox.getItems().add(l.get(i).getPIVA());
        }
        if(l.size()>0)
            fornitoreChoiceBox.setValue(l.get(0).getPIVA());

    }

    private ObservableList<Fornitore> getFornitore(){
        ObservableList<Fornitore> fornitore= FXCollections.observableArrayList();
        ResultSet rs=connection.getResultSet("SELECT * FROM Fornitore");
        try{
            if(!rs.equals(null)){
                while (rs.next())
                {
                    fornitore.add(new Fornitore(rs.getString(1),rs.getString(2)));

                }
                rs.close();
            }
        }
        catch (SQLException e){
            System.out.println("errore in getCitta");
        }

        return fornitore;
    }


    public void aggiungiButtonClicked() {
        SQLException e=null;
        e=connection.execQuery("INSERT INTO Catalogo VALUES('"+annoTextField.getText()+"','"+fornitoreChoiceBox.getSelectionModel().getSelectedItem()+"','"+nomeTextField.getText()+"');");
        if(e!=null){
            errorLabel.setText(e.getMessage());
        }
        else{
            errorLabel.setText("Aggiunto con successo");
            errorLabel.setTextFill(Color.web("#1dff00"));
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
        window.setTitle("ECommerce Nuova Autoricambi");
        window.setScene(ViewScene);
        window.show();
    }

}
