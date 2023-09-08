package VisiteGateway;

import Visite.SchedaVisita;
import Visite.Visita;
import Visite.VisitaType;

import java.sql.*;
import java.util.ArrayList;

public class VisiteGatewayDb {
    private Connection con;
    private Statement stmt;

    public VisiteGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void InsertSchedaVisite(int idUtente) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM SchedaVisita WHERE idUtente=" + idUtente);
            if (!resultSet.next()) {
                String insertSql = "INSERT INTO SchedaVisita(idUtente)"
                        + " VALUES(" + idUtente + ")";
                stmt.executeUpdate(insertSql);
            }
        }finally {
            con.close();
        }

    }

    public void InsertPatologia(String descrizione) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            String insertSql = "INSERT INTO Patologia(descrizione)"
                    + " VALUES('" + descrizione + "')";
            stmt.executeUpdate(insertSql);
        }finally {
            con.close();
        }
    }

    public void insertVisita(int id,String idMedico,String descrizione, Timestamp data, String stato, String esito, int idSchedaVisita, int idVisitaType) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            String insertSql = "INSERT INTO Visita(idVisita,idMedico,descrizione,data,stato,esito,idSchedaVisita,idVisitaType)"
                    + " VALUES("+id+", '" + idMedico + "', '" + descrizione + "', '" + data + "', '" + stato + "', '" + esito + "'," + idSchedaVisita + "," + idVisitaType + ")";
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }
    }

    public void InsertPatologieScheda(int idPatologia, int idSchedaVisita) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            String insertSql = "INSERT INTO SchedaVisita_has_Patologia(idSchedaVisita, idPatologia)"
                    + " VALUES(" + idSchedaVisita + "," + idPatologia + ")";
            stmt.executeUpdate(insertSql);
        } finally {
            con.close();
        }
    }

    public ArrayList<Visita> getVisiteDaSostenere(int idSchedaVisita) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Visita WHERE idSchedaVisita=" + idSchedaVisita + " AND stato='da sostenere'");
            ArrayList<Visita> visite = new ArrayList<>();
            while (resultSet.next()) {
                visite.add(new Visita(resultSet.getInt("idVisita"), resultSet.getString("idMedico"), resultSet.getString("descrizione"), Timestamp.valueOf(resultSet.getString("data")), resultSet.getString("stato"), resultSet.getString("esito"),resultSet.getInt("idSchedaVisita") ,resultSet.getInt("idVisitaType")));
            }
            return visite;
        } finally {
            con.close();
        }
    }
    public ArrayList<Visita> getVisiteEffettuate(int idSchedaVisita) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Visita WHERE idSchedaVisita=" + idSchedaVisita + " AND stato='sostenuta'");
            ArrayList<Visita> visite = new ArrayList<>();
            while (resultSet.next()) {
                visite.add(new Visita(resultSet.getInt("idVisita"), resultSet.getString("idMedico"), resultSet.getString("descrizione"), Timestamp.valueOf(resultSet.getString("data")), resultSet.getString("stato"), resultSet.getString("esito"),resultSet.getInt("idSchedaVisita"), resultSet.getInt("idVisitaType")));
            }
            return visite;
        } finally {
            con.close();
        }
    }


    public void updateVisita(int idVisita, String descrizione, Timestamp data, String stato, String esito, int idVisitaType) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            String updateSql = "UPDATE Visita SET descrizione='" + descrizione + "', data='" + data + "', stato='" + stato + "', esito='" + esito + "', idVisitaType=" + idVisitaType + " WHERE idVisita=" + idVisita;
            stmt.executeUpdate(updateSql);
        }finally {
            con.close();
        }
    }

    public void deleteVisita(int idVisita) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            String deleteSql = "DELETE FROM Visita WHERE idVisita=" + idVisita;
            stmt.executeUpdate(deleteSql);
        } finally {
            con.close();
        }
    }

    public void deleteSchedaVisita(int idSchedaVisita) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            String deleteSql = "DELETE FROM SchedaVisita WHERE idSchedaVisita="+idSchedaVisita;
            stmt.executeUpdate(deleteSql);
        }finally {
            con.close();
        }
    }

    public String [] elencoPatologie(int idSchedaVisite) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM SchedaVisita_has_Patologia WHERE idSchedaVisita=" + idSchedaVisite);
            if (!resultSet.next()) {
                return null;
            } else {
                ArrayList<String> patologie = new ArrayList<>();
                while (resultSet.next()) {
                    patologie.add(resultSet.getString("idPatologia"));
                }
                String[] patologieArray = new String[patologie.size()];
                for (int i = 0; i < patologie.size(); i++) { //fix me pls
                    patologieArray[i] = patologie.get(i);
                }
                return patologieArray;
            }
        }  finally {
            con.close();
        }
    }

    public SchedaVisita getSchedaVisistaFromUserID(int idUtente) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM SchedaVisita WHERE idUtente="+idUtente);
            if(resultSet.next()){
                SchedaVisita sv= new SchedaVisita(resultSet.getInt("idSchedaVisita"),resultSet.getInt("idUtente"), null, null, null);
                sv.setElencoPatologie(elencoPatologie(sv.getId()));
                sv.setVisiteEffettuate(getVisiteEffettuate(sv.getId()));
                sv.setVisiteDaSostentere(getVisiteDaSostenere(sv.getId()));
                return sv;
            }
            return null;
        }finally {
            con.close();
        }
    }

    public void addVisitaType(int id,String nome, String descrizione, String frequenza, int rischio) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
                stmt = con.createStatement();
            String insertSql = "INSERT INTO VisitaType(idVisitaType,nome,descrizione, frequenza, rischio)"
                    + " VALUES('"+id+"', '"+nome+"', '"+descrizione+"', '"+frequenza+"', "+rischio+")";
            stmt.executeUpdate(insertSql);
        }finally {
            con.close();
        }
    }

    public VisitaType getVisitaType(int id) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM VisitaType WHERE idVisitaType="+id);
            if(resultSet.next()){
                VisitaType vT= new VisitaType(resultSet.getInt("idVisitaType"),resultSet.getString("nome"), resultSet.getString("descrizione"), resultSet.getString("frequenza"), resultSet.getInt("rischio"));
                return vT;
            }
            return null;
        }finally {
            con.close();
        }
    }

}
