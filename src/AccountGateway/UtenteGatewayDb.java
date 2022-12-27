package AccountGateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

public class UtenteGatewayDb {
    private Connection con;
    private Statement stmt;

    public UtenteGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account", "root", "root");
        //createTable();
    }

    public void createTable() throws SQLException {
        String tableSql ="DROP TABLE IF EXISTS Utente";
        /*try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account", "root", "root")){
            stmt=con.createStatement();
            stmt.execute(tableSql);
        }*/
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account", "root", "root")) {
            stmt=con.createStatement();
            tableSql = "CREATE TABLE IF NOT EXISTS Utente"
                    + "(matricola int PRIMARY KEY AUTO_INCREMENT, nome varchar(30),"
                    + "cognome varchar(30), salary double)";
            stmt.execute(tableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void InsertSql(int m,String n, String c) throws SQLException {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account", "root", "root")) {
            stmt=con.createStatement();
            String insertSql = "INSERT INTO Utente(matricola, nome, cognome)"
                    + " VALUES("+m+", '"+n+"', '"+c+"')";
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ArrayList<String> SelectSql() throws SQLException {
        ArrayList<String> risultati=new ArrayList<String>();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Account", "root", "root")) {
            stmt=con.createStatement();
            String selectSql = "SELECT * FROM Utente";
            try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                while (resultSet.next()) {
                    String name = resultSet.getString("nome");
                    risultati.add(name);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return risultati;
    }
    /*
    public String find(int id){
        // find person record by id.
    }
    public String findByFirstName(){
        // find person by first name.
    }
    public void update(String firstName, String lastName, String age){
        // Update person entity.
    }
    public void insert(String firstName, String lastName, String gender,String age){
        // insert person entity.
    }
    public void delete(int id){
        // Delete person record by id from database.
    }*/

}

