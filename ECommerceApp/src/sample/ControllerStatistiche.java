package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerStatistiche {

    private DBConnection connection=null;

    @FXML
    private Label fatturatoLabel;

    @FXML
    private TableView<Citta> cittaTableView;

    public void verifyConnection(DBConnection conn){
        if(conn!=null){
            connection=conn;
            loadScene();

        }


    }

    private void loadScene(){
        fatturatoLabel.setText(connection.tryQuery());
        /*test getCitta
        ObservableList<Citta> test=getCitta();
        System.out.println(test.get(0).getNome());
        */
        TableColumn<Citta,String> nomeColumn=new TableColumn<>("Nome");
        nomeColumn.setMinWidth(100);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        cittaTableView.setItems(getCitta());
        cittaTableView.getColumns().addAll(nomeColumn);

    }

    //torna menù principale

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


    //provando a caricare la tabella con le citta
    private ObservableList<Citta> getCitta(){
        ObservableList<Citta> citta= FXCollections.observableArrayList();
        ResultSet rs=connection.getResultSet("SELECT * FROM Citta");
        try{
            if(!rs.equals(null)){
                while (rs.next())
                {
                    citta.add(new Citta(rs.getString(1)));

                }
                rs.close();
            }
        }
        catch (SQLException e){
            System.out.println("errore");
        }

        return citta;
    }
}
