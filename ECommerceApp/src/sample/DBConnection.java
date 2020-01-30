package sample;

import java.sql.*;
import java.util.Properties;

public class DBConnection {

    private Connection connection;
    private String URL;
    private String Username;
    private String Password;

    public DBConnection(String url, String username, String password){
        connection=null;
        URL=url;
        Username=username;
        Password=password;
    }
    /*private String URL;
    private String Username;
    private String Password;

    public DBConnection(String url, String username, String password){
        URL=url;
        Username=username;
        Password=password;
    }*/

    public boolean connect(){
       /* String url = "jdbc:postgresql://localhost/test";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","admin");
        props.setProperty("ssl","false");*/

       boolean result=false;
        String url = "jdbc:postgresql://"+URL;
        Properties props = new Properties();
        props.setProperty("user",Username);
        props.setProperty("password",Password);
        props.setProperty("ssl","false");

        try {
            connection = DriverManager.getConnection(url, props);
            System.out.println("Connected");
            result=true;

        }catch (SQLException ex){
            System.out.println("error connection DB");
        }
        return result;
    }

    public String tryQuery() {
        String result="";
        try {
            Statement st = connection.createStatement();
            ResultSet rs=null;
            if(!connection.equals(null)){
                rs= st.executeQuery("SELECT * FROM Citta");
                if(!rs.equals(null)){
                    while (rs.next())
                    {
                        //System.out.println(rs.getString(1));
                        result=result+rs.getString(1);
                    }
                    rs.close();
                    st.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("error in query");
        }
        return result;
    }

    //prendo solo il result set di una query

    public ResultSet getResultSet(String query){

        ResultSet rs=null;

        try {
            Statement st = connection.createStatement();
            rs=null;
            if(!connection.equals(null)){
                rs= st.executeQuery(query);
            }
        } catch (SQLException ex) {
            System.out.println("error in query");
        }
        return rs;
    }
}
