package AccessiGatewayDb;

import java.sql.*;

public class AccessoLuogoAbilitatoGatewayDb {
    private Connection con;

    public AccessoLuogoAbilitatoGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/AccessiDB", "root", "root");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void inserAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        try{
            String insertSql = "INSERT INTO AccessoDipartimentoAbilitato(utente, dipartimento)"
                    + " VALUES("+utente+", "+dipartimento+")";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertAccessoLuogo(int utente, int luogo) throws SQLException{
        try{
            String insertSql = "INSERT INTO AccessoLuogoAbilitato(utente, luogo)"
                    + " VALUES("+utente+", "+luogo+")";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        try{
            String updateSql = "UPDATE AccessoDipartimentoAbilitato SET dipartimento = "+dipartimento+" WHERE utente = "+utente;
            Statement stmt=con.createStatement();
            stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccessoLuogo(int utente, int luogo) throws SQLException{
        try{
            String updateSql = "UPDATE AccessoLuogoAbilitato SET luogo = "+luogo+" WHERE utente = "+utente;
            Statement stmt=con.createStatement();
            stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccessoDipartimento(int utente, int id) throws SQLException{
        try{
            String deleteSql = "DELETE FROM AccessoDipartimentoAbilitato WHERE utente = "+utente;
            Statement stmt=con.createStatement();
            stmt.executeUpdate(deleteSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccessoLuogo(int utente, int codice) throws SQLException{
        try{
            String deleteSql = "DELETE FROM AccessoLuogoAbilitato WHERE utente = "+utente;
            Statement stmt=con.createStatement();
            stmt.executeUpdate(deleteSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
