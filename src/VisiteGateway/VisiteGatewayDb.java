package VisiteGateway;

import java.sql.*;

public class VisiteGatewayDb {
    private Connection con;
    private Statement stmt;

    public VisiteGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
    }

    public void InsertSchedaVisite(int idUtente) throws SQLException {
        stmt=con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM SchedaVisita WHERE idUtente="+idUtente);
        if(resultSet.next()==false){
            String insertSql = "INSERT INTO SchedaVisita(idUtente)"
                    + " VALUES("+idUtente+")";
            stmt.executeUpdate(insertSql);
        }
    }

    public void InsertVisitaType(String nome, String descrizione, String frequenza) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO VisitaType(nome, descrizione, frequenza)"
                + " VALUES('"+nome+"', '"+descrizione+"','"+frequenza+"')";
        stmt.executeUpdate(insertSql);
    }

    public void InsertPatologia(String descrizione) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO Patologia(descrizione)"
                + " VALUES('"+descrizione+"')";
        stmt.executeUpdate(insertSql);
    }

    public void InsertVisita(String idMedico,String descrizione, Timestamp data, String stato, String esito, int idSchedaVisita, int idVisitaType) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO Visita(idMedico,descrizione,data,stato,esito,idSchedaVisita,idVisitaType)"
                + " VALUES('"+idMedico+"', '"+descrizione+"', '"+data+"', '"+stato+"', '"+esito+"',"+idSchedaVisita+","+idVisitaType+")";
        stmt.executeUpdate(insertSql);
    }

    public void InsertPatologieScheda(int idPatologia, int idSchedaVisita) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO SchedaVisita_has_Patologia(idSchedaVisita, idPatologia)"
                + " VALUES("+idSchedaVisita+","+idPatologia+")";
        stmt.executeUpdate(insertSql);
    }
}
