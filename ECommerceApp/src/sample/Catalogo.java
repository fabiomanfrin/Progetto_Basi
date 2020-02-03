package sample;

public class Catalogo {
    private String Anno;
    private String Fornitore;
    private String Nome;
    public Catalogo(String anno, String fornitore, String nome){
        Anno=anno;
        Fornitore=fornitore;
        Nome=nome;
    }

    public String getAnno() {
        return Anno;
    }

    public String getFornitore() {
        return Fornitore;
    }

    public String getNome() {
        return Nome;
    }
}
