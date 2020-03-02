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

public class ControllerModificaReso {

    @FXML
    private TextField idTextField;
    @FXML
    private TextField dataTextField;
    @FXML
    private TextField prodottoTextField;
    @FXML
    private CheckBox saldatoCheckBox;
    @FXML
    private TextArea motivazioneTextArea;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Reso> resoTableView;

    private DBConnection connection;
    public void verifyConnection(DBConnection conn) {
        if (conn != null) {
            connection = conn;
            loadScene();
        }
    }

    private void loadScene() {

        loadTableReso();




    }



    private void loadTableReso() {
        //load dei resi
        //colonna id
        TableColumn<Reso,String> idColumn=new TableColumn<>("Id");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        //colonna motivazione
        TableColumn<Reso,String> motivazioneColumn=new TableColumn<>("Motivazione");
        motivazioneColumn.setMinWidth(100);
        motivazioneColumn.setCellValueFactory(new PropertyValueFactory<>("motivazione"));
        //colonna data
        TableColumn<Reso,String> dataColumn=new TableColumn<>("Data");
        dataColumn.setMinWidth(100);
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        //colonna saldato
        TableColumn<Reso,String> saldatoColumn=new TableColumn<>("Saldato");
        saldatoColumn.setMinWidth(100);
        saldatoColumn.setCellValueFactory(new PropertyValueFactory<>("saldato"));
        //colonna prodotto
        TableColumn<Reso,String> prodottoColumn=new TableColumn<>("Prodotto");
        prodottoColumn.setMinWidth(100);
        prodottoColumn.setCellValueFactory(new PropertyValueFactory<>("prodotto"));


        resoTableView.setItems(getReso());
        resoTableView.getColumns().addAll(idColumn,motivazioneColumn,dataColumn,saldatoColumn,prodottoColumn);

        resoTableView.setRowFactory(tv -> {
            TableRow<Reso> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Reso rowData = row.getItem();
                    //System.out.println("Double click on: "+rowData.getId());
                    idTextField.setText(rowData.getId());
                    dataTextField.setText(rowData.getData());
                    prodottoTextField.setText(rowData.getProdotto());
                    motivazioneTextArea.setText(rowData.getMotivazione());
                    if(rowData.getSaldato().equals("Saldato"))
                        saldatoCheckBox.setSelected(true);
                }
            });
            return row ;
        });
    }


    private ObservableList<Reso> getReso(){
        ObservableList<Reso> o= FXCollections.observableArrayList();
        ResultSet rs=connection.getResultSet("SELECT * FROM Reso;");
        try{
            if(rs!=null){
                String saldato="";
                while (rs.next())
                {
                    if(rs.getString(4).equals("f"))
                        saldato="Non saldato";
                    else
                        saldato="Saldato";
                    o.add(new Reso(rs.getString(1),rs.getString(2),rs.getString(3),saldato,rs.getString(5)+"-"+rs.getString(6)));

                }
                rs.close();
            }
        }
        catch (SQLException e){
            System.out.println("errore in getReso");
        }

        return o;
    }


    public void applicaButtonClicked() {
        SQLException e=null;
        String id=idTextField.getText();
        String motivazione=motivazioneTextArea.getText();
       /* String data=dataTextField.getText();
        String numeroprodotto="";
        //prendo il numero prodotto nella stringa formattata numeroprodotto-prodotto
        for (int i=0;i<prodottoTextField.getText().length();i++){
            if(prodottoTextField.getText().charAt(i)=='-')
                break;
            numeroprodotto=numeroprodotto+prodottoTextField.getText().charAt(i);
        }

        String prodotto="";

        //prendo il prodotto nella stringa formattata numeroprodotto-prodotto
        for (int i=prodottoTextField.getText().length()-1;i>=0;i--){
            if(prodottoTextField.getText().charAt(i)=='-')
                break;
            prodotto=prodottoTextField.getText().charAt(i)+prodotto;
        }*/

        String saldato="";
        if(saldatoCheckBox.isSelected())
            saldato="t";
        else
            saldato="f";

        e=connection.execQuery("UPDATE Reso SET Motivazione = '"+motivazione+"',Saldato = '"+saldato+"' WHERE Id = '"+id+"';");
        if(e!=null){
            errorLabel.setText(e.getMessage());
        }
        else{
            errorLabel.setText("Aggiornato con successo");
            errorLabel.setTextFill(Color.web("#1dff00"));
        }
       /* ObservableList<Reso> o=getReso();
        resoTableView.getItems().add(o.get(o.size()-1));*/
        resoTableView.getColumns().remove(0,resoTableView.getColumns().size());
        loadTableReso();
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
