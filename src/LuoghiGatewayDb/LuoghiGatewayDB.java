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
    public Luogo getLuogo(String codice) throws SQLException{
        try{
            String selectSql = "SELECT * FROM Luogo WHERE codice='"+codice+"'";
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(selectSql);
            if(rs.next()){
                return new Luogo(rs.getString("codice"), rs.getString("nome"), rs.getString("tipo"), rs.getInt("referente"), rs.getInt("dipartimento"));
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
}
