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

public class ControllerAggiungiProdotto {

    @FXML
    private ChoiceBox<String> catalogoChoiceBox;
    @FXML
    private ChoiceBox<String> categoriaChoiceBox;
    @FXML
    private TextField codiceABarreTextField;
    @FXML
    private TextField prezzoAttualeTextField;
    @FXML
    private TextArea descrizioneTextArea;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Prodotto> prodottoTableView;

    private DBConnection connection;
    public void verifyConnection(DBConnection conn) {
        if (conn != null) {
            connection = conn;
            loadScene();
        }
    }

    private void loadScene() {
        ObservableList<Catalogo> f=getCatalogo();
        ObservableList<Categoria> c=getCategoria();
        loadTableProdotti();
        //carico cataloghi
        for (int i=0;i<f.size();i++){
            catalogoChoiceBox.getItems().add(f.get(i).getAnno()+"-"+f.get(i).getFornitore());
        }

        if(f.size()>0)
            catalogoChoiceBox.setValue(f.get(0).getAnno()+"-"+f.get(0).getFornitore());

        //carico categoria
        for (int i=0;i<c.size();i++){
            categoriaChoiceBox.getItems().add(c.get(i).getNome());
        }

        if(c.size()>0)
            categoriaChoiceBox.setValue(c.get(0).getNome());




    }



    private void loadTableProdotti() {
        //load dei prodotti
        //colonna codice
        TableColumn<Prodotto,String> codiceABarreColumn=new TableColumn<>("Codice");
        codiceABarreColumn.setMinWidth(100);
        codiceABarreColumn.setCellValueFactory(new PropertyValueFactory<>("codice"));
        //colonna descrizione
        TableColumn<Prodotto,String> descrizioneColumn=new TableColumn<>("Descrizione");
        descrizioneColumn.setMinWidth(150);
        descrizioneColumn.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        //colonna catalogo
        TableColumn<Prodotto,String> catalogoColumn=new TableColumn<>("Catalogo");
        catalogoColumn.setMinWidth(200);
        catalogoColumn.setCellValueFactory(new PropertyValueFactory<>("catalogo"));
        //colonna categoria
        TableColumn<Prodotto,String> categoriaColumn=new TableColumn<>("Categoria");
        categoriaColumn.setMinWidth(150);
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        //colonna prezzo
        TableColumn<Prodotto,String> prezzoAttualeColumn=new TableColumn<>("Prezzo Attuale");
        prezzoAttualeColumn.setMinWidth(100);
        prezzoAttualeColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));

        prodottoTableView.setItems(getProdotti());
        prodottoTableView.getColumns().addAll(codiceABarreColumn,descrizioneColumn,catalogoColumn,categoriaColumn,prezzoAttualeColumn);
    }

    private ObservableList<Prodotto> getProdotti() {
        ObservableList<Prodotto> o= FXCollections.observableArrayList();
        ResultSet rs=connection.getResultSet("SELECT codiceabarre,descrizione,annocatalogo,fornitore,categoria,prezzoattuale,ragionesociale FROM Prodotto JOIN Fornitore ON fornitore=piva;");
        try{
            if(rs!=null){
                while (rs.next())
                {
                    o.add(new Prodotto(rs.getString(1),rs.getString(2),rs.getString(3)+"-"+rs.getString(7)+"-"+rs.getString(4),rs.getString(5),rs.getString(6)+" €"));
                }
                rs.close();
            }
        }
        catch (SQLException e){
            System.out.println("errore in getProdotti");
        }

        return o;
    }

    private ObservableList<Catalogo> getCatalogo(){
        ObservableList<Catalogo> o= FXCollections.observableArrayList();
        ResultSet rs=connection.getResultSet("SELECT Catalogo.anno,Fornitore.ragionesociale,Catalogo.fornitore,Catalogo.nome FROM Catalogo JOIN Fornitore ON fornitore=piva");
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
            System.out.println("errore in getCatalogo");
        }

        return o;
    }

    private ObservableList<Categoria> getCategoria() {
        ObservableList<Categoria> o= FXCollections.observableArrayList();
        ResultSet rs=connection.getResultSet("SELECT * FROM Categoria");
        try{
            if(rs!=null){
                while (rs.next())
                {
                    o.add(new Categoria(rs.getString(1)));

                }
                rs.close();
            }
        }
        catch (SQLException e){
            System.out.println("errore in getCategoria");
        }

        return o;
    }

    public void aggiungiButtonClicked() {
        SQLException e=null;
        String codice=codiceABarreTextField.getText();
        String descrizione=descrizioneTextArea.getText();
        String annoCat=getAnnoCat(catalogoChoiceBox.getSelectionModel().getSelectedItem());
        String fornitoreCat=getFornitoreCat(catalogoChoiceBox.getSelectionModel().getSelectedItem());
        String categoria=categoriaChoiceBox.getSelectionModel().getSelectedItem();
        String prezzo=formatPrezzo(prezzoAttualeTextField.getText());
        e=connection.execQuery("INSERT INTO Prodotto VALUES('"+codice+"','"+descrizione+"','"+annoCat+"','"+fornitoreCat+"','"+categoria+"','"+prezzo+"');");
        //System.out.println("INSERT INTO Prodotto VALUES('"+codice+"','"+descrizione+"','"+annoCat+"','"+fornitoreCat+"','"+categoria+"','"+prezzo+"');");
        if(e!=null){
            errorLabel.setText(e.getMessage());
        }
        else{
            errorLabel.setText("Aggiunto con successo");
            errorLabel.setTextFill(Color.web("#1dff00"));
        }
     /*   ObservableList<Prodotto> o=getProdotti();
        prodottoTableView.getItems().add(o.get(o.size()-1));*/
        prodottoTableView.getColumns().remove(0,prodottoTableView.getColumns().size());
        loadTableProdotti();
    }

    private String formatPrezzo(String t) {
        String result="";
        for (int i=0;i<t.length();i++){
            if(t.charAt(i)==',')
                result=result+'.';
            else
                result=result+t.charAt(i);
        }
        return result;
    }

    private String getFornitoreCat(String t) {
        String result="";
        for (int i=t.length()-1;i>=0;i--){ //anno(4 caratteri)-fornitore
            if(t.charAt(i)=='-')
                break;
            result=t.charAt(i)+result;
        }
        return result;
    }

    private String getAnnoCat(String t) {
        String result="";
        for (int i=0;i<t.length();i++){
            if(t.charAt(i)=='-')
               break;
            else
                result=result+t.charAt(i);
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
        window.setTitle("ECommerce Nuova Autoricambi");
        window.setScene(ViewScene);
        window.show();
    }

}
