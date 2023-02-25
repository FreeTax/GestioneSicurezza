package LuoghiGatewayDb;
import Luoghi.Dipartimento;
import Luoghi.Luogo;
import Rischi.Rischio;

import java.io.Console;
import java.sql.*;
import java.util.ArrayList;

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
            String insertSql = "INSERT INTO Luoghi(codice, nome, tipo, referente, dipartimento)"
                    + " VALUES('"+l.getCodice()+"', '"+l.getNome()+"', '"+l.getTipo()+"', '"+l.getReferente()+"', '"+l.getDipartimento()+"')";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Luogo getLuogo(int codice) throws SQLException{
        try{
            String selectSql = "SELECT * FROM Luoghi WHERE codice = '"+codice+"'";
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

    public Dipartimento getDipartimento(int codice) throws SQLException{
        try{
            String selectSql = "SELECT * FROM Dipartimenti WHERE codice='"+codice+"'";
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(selectSql);

            if(rs.next()){
                Dipartimento d= new Dipartimento(rs.getInt("codice"), rs.getString("nome"), rs.getInt("responsabile"));
                //d.setLuoghi(null);
                //d.setRischi(null);
                return d;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertDipartimento(Dipartimento d) throws SQLException{
        try{
            String insertSql = "INSERT INTO Dipartimenti(codice, nome, responsabile)"
                    + " VALUES('"+d.getCodice()+"', '"+d.getNome()+"', '"+d.getResponsabile()+"')";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDipartimento(Dipartimento d) throws SQLException{
        try{
            String updateSql = "UPDATE Dipartimenti SET nome='"+d.getNome()+"', responsabile='"+d.getResponsabile()+"' WHERE codice='"+d.getCodice()+"'";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateLuogo(Luogo l) throws SQLException{
        try{
            String updateSql = "UPDATE Luoghi SET nome='"+l.getNome()+"', tipo='"+l.getTipo()+"', referente='"+l.getReferente()+"', dipartimento='"+l.getDipartimento()+"' WHERE codice='"+l.getCodice()+"'";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLuogo(int codice) throws SQLException{
        try{
            String deleteSql = "DELETE FROM Luoghi WHERE codice='"+codice+"'";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(deleteSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDipartimento(int id) throws SQLException{
        try{
            String deleteSql = "DELETE FROM Dipartimenti WHERE codice='"+id+"'";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(deleteSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> getLuoghiFromDipartimento(int idDipartimento) throws SQLException {
        String getLuoghiSql = "SELECT codice FROM Luoghi WHERE dipartimento='"+idDipartimento+"'";
        ArrayList<Integer> luoghi = new ArrayList<>();
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(getLuoghiSql);
        while(rs.next()){
             luoghi.add(rs.getInt("codice"));
             }
        return luoghi;
        }
    public ArrayList<Integer> getRischiFromDipartimento( int idDipartimento) throws SQLException {
        ArrayList<Integer> rischi = new ArrayList<>();
        String getRischiSql = "SELECT * FROM RischiDipartimento INNER JOIN RischiDB.RischioGenerico as rg on RischiDipartimento.rischioGenerico ==  rg.codice WHERE dipartimento='"+idDipartimento+"'";
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(getRischiSql);
        while(rs.next()){
            System.out.println(rs.getString("nome"));
        }
        return null;
    }

    public void insertRischioDipartimento(int idDipartimento, int idRischio) throws SQLException {
        String insertSql = "INSERT INTO RischiDipartimento(dipartimento, rischioGenerico)"
                + " VALUES('"+idDipartimento+"', '"+idRischio+"')";
        Statement stmt=con.createStatement();
        stmt.executeUpdate(insertSql);
    }

    public void insertRischioLuogo(int idLuogo, int idRischio) throws SQLException {
        String insertSql = "INSERT INTO RischiLuogo(luogo, rischioSpecifico)"
                + " VALUES('"+idLuogo+"', '"+idRischio+"')";
        Statement stmt=con.createStatement();
        stmt.executeUpdate(insertSql);
    }
    public ArrayList<Integer> getRischiDipartimento(int idDipartimento) throws SQLException {
        ArrayList<Integer> rischi = new ArrayList<>();
        String getRischiSql = "SELECT * FROM RischiDipartimento rd WHERE rd.dipartimento="+idDipartimento;
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(getRischiSql);
        while(rs.next()){
            rischi.add(rs.getInt("rischioGenerico"));
        }
        return rischi;
    }
    public ArrayList<Integer> getRischiLuogo(int idLuogo) throws SQLException {
        ArrayList<Integer> rischi = new ArrayList<>();
        String getRischiSql = "SELECT * FROM RischiLuogo rl WHERE rl.luogo="+idLuogo;
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery(getRischiSql);
        while(rs.next()){
            rischi.add(rs.getInt("rischioSpecifico"));
        }
        return rischi;
    }

}
