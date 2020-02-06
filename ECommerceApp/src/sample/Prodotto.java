package sample;

public class Prodotto {

    private String Codice;
    private String Descrizione;
    private String Catalogo; //stringa formata da anno-fornitore
    private String Categoria;
    private String Prezzo;

    public Prodotto(String codice,String descrizione,String catalogo, String categoria,String prezzo){
        Codice=codice;
        Descrizione=descrizione;
        Catalogo=catalogo;
        Categoria=categoria;
        Prezzo=prezzo;
    }

    public String getCodice() {
        return Codice;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public String getCatalogo() {
        return Catalogo;
    }

    public String getCategoria() {
        return Categoria;
    }

    public String getPrezzo() {
        return Prezzo;
    }
}
