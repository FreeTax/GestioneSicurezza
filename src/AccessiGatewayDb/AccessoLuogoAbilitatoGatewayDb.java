package AccessiGatewayDb;

import java.sql.*;

public class AccessoLuogoAbilitatoGatewayDb {
    private Connection con;
    private Statement stmt;

    public AccessoLuogoAbilitatoGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void inserAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/AccessiDB", "root", "root");
            stmt=con.createStatement();
            String insertSql = "INSERT INTO AccessoDipartimentoAbilitato(utente, dipartimento)" + " VALUES("+utente+", "+dipartimento+")";
            stmt.executeUpdate(insertSql);
        }finally {
            con.close();
        }

    }

    public void insertAccessoLuogo(int utente, int luogo) throws SQLException{
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/AccessiDB", "root", "root");
            stmt=con.createStatement();
            String insertSql = "INSERT INTO AccessoLuogoAbilitato(utente, luogo)"
                    + " VALUES("+utente+", "+luogo+")";
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }
    }

    public void updateAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/AccessiDB", "root", "root");
            stmt=con.createStatement();
            String updateSql = "UPDATE AccessoDipartimentoAbilitato SET dipartimento = "+dipartimento+" WHERE utente = "+utente;
            stmt.executeUpdate(updateSql);
        } finally {
            con.close();
        }
    }

    public void updateAccessoLuogo(int utente, int luogo) throws SQLException{
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/AccessiDB", "root", "root");
            stmt=con.createStatement();
            String updateSql = "UPDATE AccessoLuogoAbilitato SET luogo = "+luogo+" WHERE utente = "+utente;
            stmt.executeUpdate(updateSql);
        } finally {
            con.close();
        }
    }

    public void deleteAccessoDipartimento(int utente, int id) throws SQLException{
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/AccessiDB", "root", "root");
            stmt=con.createStatement();
            String deleteSql = "DELETE FROM AccessoDipartimentoAbilitato WHERE utente = "+utente;
            stmt.executeUpdate(deleteSql);
        } finally {
            con.close();
        }
    }

    public void deleteAccessoLuogo(int utente, int codice) throws SQLException{
        try{
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/AccessiDB", "root", "root");
            stmt=con.createStatement();
            String deleteSql = "DELETE FROM AccessoLuogoAbilitato WHERE utente = "+utente;
            stmt.executeUpdate(deleteSql);
        } finally {
            con.close();
        }
    }
}
