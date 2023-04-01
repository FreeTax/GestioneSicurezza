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
            String insertSql = "INSERT INTO Rischio/*Generico*/(codice, nome, descrizione, tipo)"
                    + " VALUES('"+r.getCodice()+"', '"+r.getNome()+"', '"+r.getDescrizione()+"', 'generico' )"; //wating that Corso and Visita classes are implemented
            
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }

    }

    public void insertRischioSpecifico(RischioSpecifico r) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String insertSql = "INSERT INTO Rischio/*Specifico*/ (codice, nome, descrizione, tipo)"
                    + " VALUES('"+r.getCodice()+"', '"+r.getNome()+"', '"+r.getDescrizione()+"', 'specifico' )"; //wating that Corso and Visita classes are implemented
            
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }
    }

    public RischioGenerico getRischioGenerico(int id) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String selectSql = "SELECT * FROM Rischio/*Generico*/ WHERE tipo='generico' AND codice="+id;
            
            ResultSet rs=stmt.executeQuery(selectSql);
            if(rs.next()){
                return new RischioGenerico(rs.getInt("codice"), rs.getString("nome"), rs.getString("descrizione")/*, rs.getString("tipologia")*/);
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
            String selectSql = "SELECT * FROM Rischio/*Specifico*/  WHERE tipo='specifico' AND codice="+id;
            
            ResultSet rs=stmt.executeQuery(selectSql);
            if(rs.next()){
                return new RischioSpecifico(rs.getInt("codice"), rs.getString("nome"), rs.getString("descrizione")/*, rs.getString("tipologia")*/);
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
            String deleteSql = "DELETE FROM Rischio/*Generico*/ WHERE codice="+id;
            
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
            String deleteSql = "DELETE FROM Rischio/*Specifico*/  WHERE codice="+id;
            
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
            String updateSql = "UPDATE Rischio/*Generico*/ SET nome='"+r.getNome()+"', descrizione='"+r.getDescrizione()+"', tipologia='"+r.getTipologia()+"' WHERE codice="+r.getCodice();
            
            stmt.executeUpdate(updateSql);
        } finally {
            con.close();
        }
    }
    public void updateRischioSpecifico(RischioSpecifico r) throws SQLException{
        try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            stmt=con.createStatement();
            String updateSql = "UPDATE Rischio/*Specifico*/ SET nome='"+r.getNome()+"', descrizione='"+r.getDescrizione()+"', tipologia='"+r.getTipologia()+"' WHERE codice="+r.getCodice();
            stmt.executeUpdate(updateSql);
        } finally {
            con.close();
        }
    }
}
