package sample;

public class Ordine {

    private String Id;
    private String Data;
    private String Indirizzo;
    private String Cliente;
    private String Codice;
    public Ordine(String id, String data, String indirizzo, String codice, String cliente){
        Id=id;
        Data=data;
        Indirizzo=indirizzo;
        Codice=codice;
        Cliente=cliente;
    }

    public String getId() {
        return Id;
    }

    public String getData() {
        return Data;
    }

    public String getIndirizzo() {
        return Indirizzo;
    }

    public String getCodice() {
        return Codice;
    }

    public String getCliente() {
        return Cliente;
    }
}
