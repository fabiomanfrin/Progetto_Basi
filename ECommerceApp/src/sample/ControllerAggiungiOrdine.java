package sample;

public class ControllerAggiungiOrdine {

    private DBConnection connection;
    public void verifyConnection(DBConnection conn) {
        if (conn != null) {
            connection = conn;
        }
    }

}
