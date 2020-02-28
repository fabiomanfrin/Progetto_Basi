package sample;

public class Reso {
    private String Id;
    private String Motivazione;
    private String Data;
    private String Saldato;
    private String Prodotto; //numeroprodotto-prodotto formato utilizzato

   /* public Reso(String id,String motivazione,String data,String saldato,String prodotto){

    }*/

    public Reso(String id, String motivazione, String data, String saldato, String prodotto) {
        Id = id;
        Motivazione = motivazione;
        Data = data;
        Saldato = saldato;
        Prodotto = prodotto;
    }

    public String getId() {
        return Id;
    }

    public String getMotivazione() {
        return Motivazione;
    }

    public String getData() {
        return Data;
    }

    public String getSaldato() {
        return Saldato;
    }

    public String getProdotto() {
        return Prodotto;
    }
}
