package CorsiSicurezzaGateway;

import java.sql.*;

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

    public void InsertCorso(String nome, String descrizione, Date inizio, Date fine, int idCorsoType) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO Corso(nome, descrizione, inizio, fine, idCorsoType)"
                + " VALUES('"+nome+"', '"+descrizione+"','"+inizio+"','"+fine+"',"+idCorsoType+")";
        stmt.executeUpdate(insertSql);
    }
}