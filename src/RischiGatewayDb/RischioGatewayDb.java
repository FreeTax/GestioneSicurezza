package RischiGatewayDb;

import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;

import java.sql.*;

public class RischioGatewayDb {
    private Connection con;
    public RischioGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRischioGenerico(RischioGenerico r) throws SQLException {
        try{
            String insertSql = "INSERT INTO RischioGenerico(codice, nome, descrizione, tipologia, corso, visita)"
                    + " VALUES('"+r.getCodice()+"', '"+r.getNome()+"', '"+r.getDescrizione()+"', '"+r.getTipologia()+"', '"+0+"', '"+0+"')"; //wating that Corso and Visita classes are implemented
            Statement stmt=con.createStatement();
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertRischioSpecifico(RischioSpecifico r) throws SQLException{
        try{
            String insertSql = "INSERT INTO RischioSpecifico(codice, nome, descrizione, tipologia, corso, visita)"
                    + " VALUES('"+r.getCodice()+"', '"+r.getNome()+"', '"+r.getDescrizione()+"', '"+r.getTipologia()+"', '"+0+"', '"+0+"')"; //wating that Corso and Visita classes are implemented
            Statement stmt=con.createStatement();
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public RischioGenerico getRischioGenerico(int id) throws SQLException{
        try{
            String selectSql = "SELECT * FROM RischioGenerico WHERE codice="+id;
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(selectSql);
            if(rs.next()){
                return new RischioGenerico(rs.getInt("codice"), rs.getString("nome"), rs.getString("descrizione"), rs.getString("tipologia"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public RischioSpecifico getRischioSpecifico(int id) throws SQLException{
        try{
            String selectSql = "SELECT * FROM RischioSpecifico WHERE codice="+id;
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(selectSql);
            if(rs.next()){
                return new RischioSpecifico(rs.getInt("codice"), rs.getString("nome"), rs.getString("descrizione"), rs.getString("tipologia"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
