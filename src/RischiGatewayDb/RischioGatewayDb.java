package RischiGatewayDb;

import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;

import java.sql.*;

public class RischioGatewayDb {
    private Connection con;
    private Statement stmt;
    public RischioGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRischioGenerico(RischioGenerico r) throws SQLException {
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String insertSql = "INSERT INTO RischioGenerico(codice, nome, descrizione, tipologia, corso, visita)"
                    + " VALUES('"+r.getCodice()+"', '"+r.getNome()+"', '"+r.getDescrizione()+"', '"+r.getTipologia()+"', '"+0+"', '"+0+"')"; //wating that Corso and Visita classes are implemented
            
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }

    }

    public void insertRischioSpecifico(RischioSpecifico r) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String insertSql = "INSERT INTO RischioSpecifico(codice, nome, descrizione, tipologia, corso, visita)"
                    + " VALUES('"+r.getCodice()+"', '"+r.getNome()+"', '"+r.getDescrizione()+"', '"+r.getTipologia()+"', '"+0+"', '"+0+"')"; //wating that Corso and Visita classes are implemented
            
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }
    }

    public RischioGenerico getRischioGenerico(int id) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String selectSql = "SELECT * FROM RischioGenerico WHERE codice="+id;
            
            ResultSet rs=stmt.executeQuery(selectSql);
            if(rs.next()){
                return new RischioGenerico(rs.getInt("codice"), rs.getString("nome"), rs.getString("descrizione"), rs.getString("tipologia"));
            }
            return null;
        } finally {
            con.close();
        }
    }

    public RischioSpecifico getRischioSpecifico(int id) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String selectSql = "SELECT * FROM RischioSpecifico WHERE codice="+id;
            
            ResultSet rs=stmt.executeQuery(selectSql);
            if(rs.next()){
                return new RischioSpecifico(rs.getInt("codice"), rs.getString("nome"), rs.getString("descrizione"), rs.getString("tipologia"));
            }
            return null;
        } finally {
            con.close();
        }
    }

    public Boolean removeRischioGenerico(int id) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String deleteSql = "DELETE FROM RischioGenerico WHERE codice="+id;
            
            stmt.executeUpdate(deleteSql);
            return true;
        } finally {
            con.close();
        }
    }

    public Boolean removeRischioSpecifico(int id) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String deleteSql = "DELETE FROM RischioSpecifico WHERE codice="+id;
            
            stmt.executeUpdate(deleteSql);
            return true;
        } finally {
            con.close();
        }
    }
    public void updateRischioGenerico(RischioGenerico r) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String updateSql = "UPDATE RischioGenerico SET nome='"+r.getNome()+"', descrizione='"+r.getDescrizione()+"', tipologia='"+r.getTipologia()+"' WHERE codice="+r.getCodice();
            
            stmt.executeUpdate(updateSql);
        } finally {
            con.close();
        }
    }
    public void updateRischioSpecifico(RischioSpecifico r) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String updateSql = "UPDATE RischioSpecifico SET nome='"+r.getNome()+"', descrizione='"+r.getDescrizione()+"', tipologia='"+r.getTipologia()+"' WHERE codice="+r.getCodice();
            stmt.executeUpdate(updateSql);
        } finally {
            con.close();
        }
    }
}
