package sample;

public class Ordine {

    private String Id;
    private String Data;
    private String Indirizzo;
    private String Cliente;
    public Ordine(String id, String data, String indirizzo, String cliente){
        Id=id;
        Data=data;
        Indirizzo=indirizzo;
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



    public String getCliente() {
        return Cliente;
    }
}
