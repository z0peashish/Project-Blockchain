import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

class MedicalObject implements Serializable {
    String TYPE;
    String NAME;
    int QUANTITY;
    float AMOUNT;
    // Basic Attributes of a medical object
    // Maybe this will be a base class for other specific classes, like Drugs, syringes etc.
    // This may be populated by a database.
    MedicalObject(String type, String name, int quant, float amt){
        this.TYPE = type;
        this.NAME = name;
        this.QUANTITY = quant;
        this.AMOUNT = amt;
    }

    void store(){
        return;
    }

    void getFromDB(String obj,String bg){

        String url = "jdbc:sqlite:Object.db";
        Connection conn = null;
        try {
            String SQL;
            SQL="select QUANTITY from Organs where NAME='obj' and BLOOD_TYPE='bg'";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
class MedicalHistory implements Serializable {
    int ID;
    String NAME;
    String ADDRESS;
    String BLOODGROUP;
    int AGE;
    ArrayList<String> DISEASES;
    ArrayList<String> ALLERGIES;
    MedicalHistory(String name, String address, String bg, int age){
        this.NAME = name;
        this.ADDRESS = address;
        this.BLOODGROUP = bg;
        this.AGE = age;
    }
    void setDISEASES(ArrayList<String> d){
        DISEASES = d;
    }
    void setALLERGIES(ArrayList<String>a){
        ALLERGIES = a;
    }

    void store(){
        return;
    }

    void getFromDB(int id){
        return;
    }
}

class CreateDB{
    String url1,url2;
    Connection conn;
    void create_conn_history(String name1,String name2){
        url1 = "jdbc:sqlite:" + name1;
        url2 = "jdbc:sqlite:" + name2;
        try {

            // Creating a structure for history database
            conn = DriverManager.getConnection(url1);
            Statement stmt = conn.createStatement();
            stmt.execute("create table Blood(ID INTEGER PRIMARY KEY AUTOINCREMENT, type varchar(3));");
            stmt.execute("create table history(ID integer primary key autoincrement, name varchar(25), address varchar(50), bg_id int, foreign key(bg_id) references Blood(ID));");
            stmt.execute("create table Diseases(ID integer, disease varchar(15), foreign key('ID') references history('ID'));");
            stmt.execute("create table Allergies(ID integer, allergy varchar(15), foreign key('ID') references history('ID'));");

            stmt.execute("insert into Blood values(0,'A+');");
            stmt.execute("insert into Blood values(NULL,'A-');");
            stmt.execute("insert into Blood values(NULL,'B+');");
            stmt.execute("insert into Blood values(NULL,'B-');");
            stmt.execute("insert into Blood values(NULL,'O+');");
            stmt.execute("insert into Blood values(NULL,'O-');");
            stmt.execute("insert into Blood values(NULL,'AB+');");
            stmt.execute("insert into Blood values(NULL,'AB-');");

            // Creating a structure for object database
            conn = DriverManager.getConnection(url2);
            stmt = conn.createStatement();
            stmt.execute("create table TYPE(ID INTEGER PRIMARY KEY AUTOINCREMENT, type varchar(10));");
            stmt.execute("create table Organs(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME varchar(20),QUANTITY integer,BLOOD_TYPE varchar(3));");




        } catch (SQLException e) {
            System.out.println("Database creation failed!");
            e.printStackTrace();
            return;
        }
    }



    public static void main(String[] args) {
        CreateDB db = new CreateDB();
        db.create_conn_history("History.db", "Object.db");
    }
}