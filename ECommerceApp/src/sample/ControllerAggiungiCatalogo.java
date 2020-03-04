package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.text.TabableView;
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
    @FXML
    private TableView<Catalogo> catalogoTableView;

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
            fornitoreChoiceBox.getItems().add(l.get(i).getRagioneSociale()+"-"+l.get(i).getPIVA());
        }
        if(l.size()>0)
            fornitoreChoiceBox.setValue(l.get(0).getRagioneSociale()+"-"+l.get(0).getPIVA());
        loadTableCatalogo();


    }

    private void loadTableCatalogo() {
            //load dei cataloghi
            //colonna anno
            TableColumn<Catalogo,String> annoColumn=new TableColumn<>("Anno");
            annoColumn.setMinWidth(100);
            annoColumn.setCellValueFactory(new PropertyValueFactory<>("anno"));
            //colonna Fornitore
            TableColumn<Catalogo,String> fornitoreColumn=new TableColumn<>("Fornitore");
            fornitoreColumn.setMinWidth(200);
            fornitoreColumn.setCellValueFactory(new PropertyValueFactory<>("fornitore"));
            //colonna nome
            TableColumn<Catalogo,String> nomeColumn=new TableColumn<>("Nome");
            nomeColumn.setMinWidth(200);
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

            catalogoTableView.setItems(getCataloghi());
            catalogoTableView.getColumns().addAll(annoColumn,fornitoreColumn,nomeColumn);
    }

    private ObservableList<Catalogo> getCataloghi() {
        ObservableList<Catalogo> o= FXCollections.observableArrayList();
        ResultSet rs=connection.getResultSet("SELECT Catalogo.anno, Fornitore.ragionesociale,Fornitore.piva, Catalogo.nome FROM Catalogo JOIN Fornitore ON Catalogo.fornitore=Fornitore.piva;");
        try{
            if(rs!=null){
                while (rs.next())
                {
                    o.add(new Catalogo(rs.getString(1),rs.getString(2)+"-"+rs.getString(3),rs.getString(4)));
                }
                rs.close();
            }
        }
        catch (SQLException e){
            System.out.println("errore in getCataloghi");
        }

        return o;
    }

    private ObservableList<Fornitore> getFornitore(){
        ObservableList<Fornitore> fornitore= FXCollections.observableArrayList();
        ResultSet rs=connection.getResultSet("SELECT * FROM Fornitore");
        try{
            if(rs!=null){
                while (rs.next())
                {
                    fornitore.add(new Fornitore(rs.getString(1),rs.getString(2)));

                }
                rs.close();
            }
        }
        catch (SQLException e){
            System.out.println("errore in getFornitore");
        }

        return fornitore;
    }


    public void aggiungiButtonClicked() {
        SQLException e=null;
        String piva=getPiva(fornitoreChoiceBox.getSelectionModel().getSelectedItem());
        //check lunghezza stringa anno
        if(annoTextField.getText().length()==4){
            e=connection.execQuery("INSERT INTO Catalogo VALUES('"+annoTextField.getText()+"','"+piva+"','"+nomeTextField.getText()+"');");
            if(e!=null){
                errorLabel.setText(e.getMessage());
                errorLabel.setTextFill(Color.web("#ff0000"));
            }
            else{
                errorLabel.setText("Aggiunto con successo");
                errorLabel.setTextFill(Color.web("#1dff00"));
            /*ObservableList<Catalogo> o=getCataloghi();
            catalogoTableView.getItems().add(o.get(o.size()-1));*/
                catalogoTableView.getColumns().remove(0,catalogoTableView.getColumns().size());
                loadTableCatalogo();
            }
        }
        else{
            errorLabel.setText("Anno non valido");
            errorLabel.setTextFill(Color.web("#ff0000"));
        }


    }

    //get piva from selected item ragionesociale-piva
    private String getPiva(String s) {
        String result="";
        for (int i=s.length()-1;i>=0;i--){
            if(s.charAt(i)=='-')
                break;
            else
                result=s.charAt(i)+result;
        }
        return result;
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
        //window.setTitle("E-Commerce Nuova Autoricambi");
        window.setScene(ViewScene);
        window.show();
    }

}
