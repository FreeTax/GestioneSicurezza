package LuoghiGatewayDb;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.sql.*;

public class LuoghiGatewayDB {
    private Connection con;
    public LuoghiGatewayDB() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB","root", "root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertLuogo(Luogo l) throws SQLException{
        try{
            String insertSql = "INSERT INTO Luogo(codice, nome, tipo, referente, dipartimento)"
                    + " VALUES('"+l.getCodice()+"', '"+l.getNome()+"', '"+l.getTipo()+"', '"+l.getReferente()+"', '"+l.getDipartimento()+"')";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Luogo getLuogo(int codice) throws SQLException{
        try{
            String selectSql = "SELECT * FROM Luogo WHERE codice='"+codice+"'";
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(selectSql);
            if(rs.next()){
                return new Luogo(rs.getInt("codice"), rs.getString("nome"), rs.getString("tipo"), rs.getInt("referente"), rs.getInt("dipartimento"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dipartimento getDipartimento(int id) throws SQLException{
        try{
            String selectSql = "SELECT * FROM Dipartimento WHERE codice="+id;
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(selectSql);
            if(rs.next()){
                return new Dipartimento(rs.getInt("codice"), rs.getString("nome"), rs.getInt("responsabile"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertDipartimento(Dipartimento d) throws SQLException{
        try{
            String insertSql = "INSERT INTO Dipartimento(codice, nome, responsabie)"
                    + " VALUES('"+d.getCodice()+"', '"+d.getNome()+"', '"+d.getResponsabile()+"')";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDipartimento(Dipartimento d) throws SQLException{
        try{
            String updateSql = "UPDATE Dipartimento SET nome='"+d.getNome()+"', responsabile='"+d.getResponsabile()+"' WHERE codice='"+d.getCodice()+"'";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateLuogo(Luogo l) throws SQLException{
        try{
            String updateSql = "UPDATE Luogo SET nome='"+l.getNome()+"', tipo='"+l.getTipo()+"', referente='"+l.getReferente()+"', dipartimento='"+l.getDipartimento()+"' WHERE codice='"+l.getCodice()+"'";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLuogo(int codice) throws SQLException{
        try{
            String deleteSql = "DELETE FROM Luogo WHERE codice='"+codice+"'";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(deleteSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDipartimento(int id) throws SQLException{
        try{
            String deleteSql = "DELETE FROM Dipartimento WHERE codice='"+id+"'";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(deleteSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
