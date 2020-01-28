package sample;

public class Controller {

    public void connectionButtonClicked(){
        DBConnection test=new DBConnection();
        test.connect("localhost/test","postgres","admin");
        test.tryQuery();
    }
}
