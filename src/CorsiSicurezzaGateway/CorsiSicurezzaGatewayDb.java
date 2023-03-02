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
    }

    public void InsertCorsoType(String nome, String descrizione) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CorsiSicurezzaDB", "root", "root");
            stmt= con.createStatement();
            String insertSql = "INSERT INTO CorsoType(nome, descrizione)" + " VALUES('"+nome+"', '"+descrizione+"')";
            stmt.executeUpdate(insertSql);
        }finally {
            con.close();
        }
    }

    public void InsertCorso(String nome, String descrizione, int idCorsoType, LocalDate inizio, LocalDate fine) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CorsiSicurezzaDB", "root", "root");
            stmt= con.createStatement();
            String insertSql = "INSERT INTO Corso(nome, descrizione, inizio, fine, idCorsoType)"
                    + " VALUES('"+nome+"', '"+descrizione+"','"+inizio+"','"+fine+"',"+idCorsoType+")";
            stmt.executeUpdate(insertSql);
        }finally {
            con.close();
        }
    }

    public void updateCorsoType(int idCorsoType, String nome, String descrizione) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CorsiSicurezzaDB", "root", "root");
            stmt= con.createStatement();
            String updateSql = "UPDATE CorsoType SET nome='"+nome+"', descrizione='"+descrizione+"' WHERE idCorsoType="+idCorsoType;
            stmt.executeUpdate(updateSql);
        }finally {
            con.close();
        }
    }
    public void updateCorso(int idCorso, String nome, String descrizione, Date inizio, Date fine, int idCorsoType) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CorsiSicurezzaDB", "root", "root");
            stmt= con.createStatement();
            String updateSql = "UPDATE Corso SET nome='"+nome+"', descrizione='"+descrizione+"', inizio='"+inizio+"', fine='"+fine+"', idCorsoType="+idCorsoType+" WHERE idCorso="+idCorso;
            stmt.executeUpdate(updateSql);
        }finally {
            con.close();
        }
    }
    public void deleteCorsoType(int idCorsoType) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CorsiSicurezzaDB", "root", "root");
            stmt= con.createStatement();
            String deleteSql = "DELETE FROM CorsoType WHERE idCorsoType="+idCorsoType;
            stmt.executeUpdate(deleteSql);
        }finally {
            con.close();
        }
    }
    public void deleteCorso(int idCorso) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CorsiSicurezzaDB", "root", "root");
            stmt= con.createStatement();
            String deleteSql = "DELETE FROM Corso WHERE idCorso="+idCorso;
            stmt.executeUpdate(deleteSql);
        }finally {
            con.close();
        }
    }
}
