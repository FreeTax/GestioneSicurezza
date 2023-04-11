package LuoghiGatewayDb;

import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.sql.*;
import java.util.ArrayList;

public class LuoghiGatewayDB {
    private Connection con;
    private Statement stmt;

    public LuoghiGatewayDB() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void insertLuogo(Luogo l) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String insertSql = "INSERT INTO Luoghi(codice, nome, tipo, referente, dipartimento)"
                    + " VALUES('" + l.getCodice() + "', '" + l.getNome() + "', '" + l.getTipo() + "', '" + l.getReferente() + "', '" + l.getDipartimento() + "')";

            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }
    }

    public Luogo getLuogo(int codice) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String selectSql = "SELECT * FROM Luoghi WHERE codice =" + codice;
            ResultSet rs = stmt.executeQuery(selectSql);
            if (rs.next()) {
                Luogo l = new Luogo(rs.getInt("codice"), rs.getString("nome"), rs.getString("tipo"), rs.getInt("referente"), rs.getInt("dipartimento"));
                l.setRischi(getRischiLuogo(codice));
                return l;
            }
            return null;
        } finally {
            con.close();
        }
    }

    public Dipartimento getDipartimento(int codice) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String selectSql = "SELECT * FROM Dipartimenti WHERE codice=" + codice;
            ResultSet rs = stmt.executeQuery(selectSql);

            if (rs.next()) {
                Dipartimento d = new Dipartimento(rs.getInt("codice"), rs.getString("nome"), rs.getInt("responsabile"));
                d.setLuoghi(getLuoghiFromDipartimento(codice));
                d.setRischi(getRischiDipartimento(codice));
                return d;
            }
            return null;
        } finally {
            con.close();
        }
    }

    public void insertDipartimento(Dipartimento d) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String insertSql = "INSERT INTO Dipartimenti(codice, nome, responsabile)"
                    + " VALUES('" + d.getCodice() + "', '" + d.getNome() + "', '" + d.getResponsabile() + "')";
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }
    }

    public void updateDipartimento(Dipartimento d) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String updateSql = "UPDATE Dipartimenti SET nome='" + d.getNome() + "', responsabile='" + d.getResponsabile() + "' WHERE codice='" + d.getCodice() + "'";
            stmt.executeUpdate(updateSql);
        } finally {
            con.close();
        }
    }


    public void updateLuogo(Luogo l) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String updateSql = "UPDATE Luoghi SET nome='" + l.getNome() + "', tipo='" + l.getTipo() + "', referente='" + l.getReferente() + "', dipartimento='" + l.getDipartimento() + "' WHERE codice='" + l.getCodice() + "'";
            stmt.executeUpdate(updateSql);
        } finally {
            con.close();
        }
    }

    public void deleteLuogo(int codice) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String deleteSql = "DELETE FROM Luoghi WHERE codice='" + codice + "'";
            stmt.executeUpdate(deleteSql);
        } finally {
            con.close();
        }
    }

    public void deleteDipartimento(int id) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String deleteSql = "DELETE FROM Dipartimenti WHERE codice='" + id + "'";
            stmt.executeUpdate(deleteSql);
        } finally {
            con.close();
        }
    }

    public ArrayList<Integer> getLuoghiFromDipartimento(int idDipartimento) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String getLuoghiSql = "SELECT codice FROM Luoghi WHERE dipartimento='" + idDipartimento + "'";
            ArrayList<Integer> luoghi = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(getLuoghiSql);
            while (rs.next()) {
                luoghi.add(rs.getInt("codice"));
            }
            return luoghi;
        } finally {
            con.close();
        }
    }

    public ArrayList<Integer> getRischiFromDipartimento(int idDipartimento) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            ArrayList<Integer> rischi = new ArrayList<>();
            String getRischiSql = "SELECT * FROM RischiDipartimento INNER JOIN RischiDB.RischioGenerico as rg on RischiDipartimento.rischioGenerico ==  rg.codice WHERE dipartimento='" + idDipartimento + "'";
            ResultSet rs = stmt.executeQuery(getRischiSql);
            while (rs.next()) {
                System.out.println(rs.getString("nome"));
            }
            return null;
        } finally {
            con.close();
        }

    }

    public void insertRischioDipartimento(int idDipartimento, int idRischio) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String insertSql = "INSERT INTO RischiDipartimento(dipartimento, rischioGenerico)"
                    + " VALUES('" + idDipartimento + "', '" + idRischio + "')";
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }

    }

    public void insertRischioLuogo(int idLuogo, int idRischio) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            String insertSql = "INSERT INTO RischiLuogo(luogo, rischioSpecifico)"
                    + " VALUES(" + idLuogo + ", " + idRischio + ")";
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }

    }

    public ArrayList<Integer> getRischiDipartimento(int idDipartimento) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            ArrayList<Integer> rischi = new ArrayList<>();
            String getRischiSql = "SELECT * FROM RischiDipartimento rd WHERE rd.dipartimento=" + idDipartimento;
            ResultSet rs = stmt.executeQuery(getRischiSql);
            while (rs.next()) {
                rischi.add(rs.getInt("rischioGenerico"));
            }
            return rischi;
        } finally {
            con.close();
        }

    }

    public ArrayList<Integer> getRischiLuogo(int idLuogo) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            stmt = con.createStatement();
            ArrayList<Integer> rischi = new ArrayList<>();
            String getRischiSql = "SELECT * FROM RischiLuogo rl WHERE rl.luogo=" + idLuogo;
            ResultSet rs = stmt.executeQuery(getRischiSql);
            while (rs.next()) {
                rischi.add(rs.getInt("rischioSpecifico"));
            }
            return rischi;
        } finally {
            con.close();
        }

    }

}
