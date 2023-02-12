package CorsiSicurezzaGateway;

import java.sql.*;
import java.time.LocalDate;

public class CorsiSicurezzaGatewayDb {
    private Connection con;
    private Statement stmt;

    public CorsiSicurezzaGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CorsiSicurezzaDB", "root", "root");
    }

    public void InsertCorsoType(String nome, String descrizione) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO CorsoType(nome, descrizione)"
                + " VALUES('"+nome+"', '"+descrizione+"')";
        stmt.executeUpdate(insertSql);
    }

    public void InsertCorso(String nome, String descrizione, int idCorsoType, LocalDate inizio, LocalDate fine) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO Corso(nome, descrizione, inizio, fine, idCorsoType)"
                + " VALUES('"+nome+"', '"+descrizione+"','"+inizio+"','"+fine+"',"+idCorsoType+")";
        stmt.executeUpdate(insertSql);
    }

    public void updateCorsoType(int idCorsoType, String nome, String descrizione) throws SQLException {
        stmt=con.createStatement();
        String updateSql = "UPDATE CorsoType SET nome='"+nome+"', descrizione='"+descrizione+"' WHERE idCorsoType="+idCorsoType;
        stmt.executeUpdate(updateSql);
    }
    public void updateCorso(int idCorso, String nome, String descrizione, Date inizio, Date fine, int idCorsoType) throws SQLException {
        stmt=con.createStatement();
        String updateSql = "UPDATE Corso SET nome='"+nome+"', descrizione='"+descrizione+"', inizio='"+inizio+"', fine='"+fine+"', idCorsoType="+idCorsoType+" WHERE idCorso="+idCorso;
        stmt.executeUpdate(updateSql);
    }
    public void deleteCorsoType(int idCorsoType) throws SQLException {
        stmt=con.createStatement();
        String deleteSql = "DELETE FROM CorsoType WHERE idCorsoType="+idCorsoType;
        stmt.executeUpdate(deleteSql);
    }
    public void deleteCorso(int idCorso) throws SQLException {
        stmt=con.createStatement();
        String deleteSql = "DELETE FROM Corso WHERE idCorso="+idCorso;
        stmt.executeUpdate(deleteSql);
    }
}
