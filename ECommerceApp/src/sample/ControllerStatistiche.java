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
    private Label prodottiPiuLabel;

   /* @FXML
    private TableView<Citta> cittaTableView;*/

    @FXML
    private TableView<Ordine> ordineTableView;

    public void verifyConnection(DBConnection conn){
        if(conn!=null){
            connection=conn;
            loadScene();

        }


    }

    private void loadScene(){

        getFatturato();
        //getProdottiPiuVenduti();
        loadTableOrdine();
        /*test getCitta
        ObservableList<Citta> test=getCitta();
        System.out.println(test.get(0).getNome());
        */
        /*load delle citta
        TableColumn<Citta,String> nomeColumn=new TableColumn<>("Nome");
        nomeColumn.setMinWidth(100);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        cittaTableView.setItems(getCitta());
        cittaTableView.getColumns().addAll(nomeColumn);
        */
    }

    private void getProdottiPiuVenduti() {
        try{
            ResultSet prodottiPiuRS=connection.getResultSet("CREATE VIEW Tot AS(" +
                    "SELECT Prodotto, COUNT(*) AS Qta" +
                    "FROM ProdottoAcquistato" +
                    "GROUP BY Prodotto" +
                    ")" +
                    "SELECT Prodotto" +
                    "FROM Tot" +
                    "WHERE Qta IN (" +
                    "SELECT MAX(Qta)" +
                    "FROM Tot" +
                    ");");

            if(!prodottiPiuRS.equals(null)){
                while (prodottiPiuRS.next())
                {
                    prodottiPiuLabel.setText(prodottiPiuLabel.getText()+", "+prodottiPiuRS.getString(1));
                }
                prodottiPiuRS.close();
            }

        }
        catch (SQLException e){
            prodottiPiuLabel.setText("errore nella query");
            System.out.println("errore query prodotti più acquistati");
        }
    }

    private void getFatturato() {
        try{
            ResultSet fatturatoRS=connection.getResultSet("SELECT SUM(P.PrezzoAcquisto) FROM Ordine AS O JOIN ProdottoAcquistato AS P ON O.Id=P.Ordine WHERE Data>'1/1/2019' AND Data<'31/12/2019'");

            if(!fatturatoRS.equals(null)){
                while (fatturatoRS.next())
                {
                    fatturatoLabel.setText(fatturatoRS.getString(1));
                }
                fatturatoRS.close();
            }

        }
        catch (SQLException e){
            fatturatoLabel.setText("errore nella query");
            System.out.println("errore query fatturato");
        }
    }

    private void loadTableOrdine() {
        //load degli ordini
        //colonna id
        TableColumn<Ordine,String> idColumn=new TableColumn<>("Id");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        //colonna data
        TableColumn<Ordine,String> dataColumn=new TableColumn<>("Data");
        dataColumn.setMinWidth(100);
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        //colonna indirizzo
        TableColumn<Ordine,String> indirizzoColumn=new TableColumn<>("Indirizzo");
        indirizzoColumn.setMinWidth(200);
        indirizzoColumn.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
        //colonna cliente
        TableColumn<Ordine,String> clienteColumn=new TableColumn<>("Codice Cliente");
        clienteColumn.setMinWidth(100);
        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("codice"));
        //colonna nome cliente
        TableColumn<Ordine,String> nomeClienteColumn=new TableColumn<>("Nome Cliente");
        nomeClienteColumn.setMinWidth(100);
        nomeClienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        ordineTableView.setItems(getOrdine());
        ordineTableView.getColumns().addAll(idColumn,dataColumn,indirizzoColumn,clienteColumn,nomeClienteColumn);
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
            System.out.println("errore in getCitta");
        }

        return citta;
    }

    //prende la lista degli ordini
    private ObservableList<Ordine> getOrdine(){
        ObservableList<Ordine> o= FXCollections.observableArrayList();
        ResultSet rs=connection.getResultSet("SELECT Id, Data, Ordine.Numero, Ordine.Via, Ordine.Citta, Ordine.Cliente, Nome, Cognome FROM Ordine INNER JOIN Cliente ON cliente=codice");
        try{
            if(!rs.equals(null)){
                while (rs.next())
                {
                    o.add(new Ordine(rs.getString(1),rs.getString(2),"Via "+rs.getString(4)+" "+rs.getString(3)+" "+rs.getString(5),rs.getString(6),rs.getString(7)+" "+rs.getString(8)));
                }
                rs.close();
            }
        }
        catch (SQLException e){
            System.out.println("errore in getCitta");
        }

        return o;
    }
}
