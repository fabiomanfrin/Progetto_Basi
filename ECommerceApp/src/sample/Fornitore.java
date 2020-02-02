package sample;

public class Fornitore {
    private String PIVA;
    private String RagioneSociale;
    public Fornitore(String piva, String ragioneSociale) {
        PIVA=piva;
        RagioneSociale=ragioneSociale;
    }

    public String getPIVA() {
        return PIVA;
    }

    public String getRagioneSociale() {
        return RagioneSociale;
    }
}
