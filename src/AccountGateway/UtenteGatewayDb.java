package AccountGateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

public class UtenteGatewayDb {
    private Connection con;
    private Statement stmt;

    public UtenteGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
    }
    public void InsertUtente(String nome,String cognome, String sesso, Date datanascita, String dipartimento, String tipologia) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO Utente(nome, cognome, sesso, datanascita, dipartimento, tipologia)"
                + " VALUES('"+nome+"', '"+cognome+"', '"+sesso+"','"+datanascita.toString()+"','"+dipartimento+"','"+tipologia+"')";
        stmt.executeUpdate(insertSql);
    }
    public void InsertUtenteInterno(int matricola,String nome,String cognome, String sesso, Date datanascita, String dipartimento) throws SQLException {
        stmt=con.createStatement();
        String idUtente = null;
        String getidUtenteSql="SELECT idUtente FROM Utente WHERE nome='"+nome+"' AND cognome='"+cognome+"' AND datanascita='"+datanascita.toString()+"'";
        ResultSet resultSet = stmt.executeQuery(getidUtenteSql);
        if(resultSet.next()==false) {
            InsertUtente(nome,cognome,sesso,datanascita,dipartimento,"interno");
            ResultSet resultSet2 = stmt.executeQuery(getidUtenteSql);
            resultSet2.next();
            idUtente = resultSet2.getString("idUtente");
        }
        else{
            idUtente = resultSet.getString("idUtente");
        }
        String insertSql = "INSERT INTO UtenteInterno(idUtente, matricola,tipo)"
                + " VALUES('"+idUtente+"', '"+matricola+"', '"+"base"+"')";
        stmt.executeUpdate(insertSql);
    }
    public void InsertUtenteEsterno(int idEsterno,String nome,String cognome, String sesso, Date datanascita, String dipartimento) throws SQLException {
        stmt=con.createStatement();
        String idUtente = null;
        String getidUtenteSql="SELECT idUtente FROM Utente WHERE nome='"+nome+"' AND cognome='"+cognome+"' AND datanascita='"+datanascita.toString()+"'";
        ResultSet resultSet = stmt.executeQuery(getidUtenteSql);
        if(resultSet.next()==false) {
            InsertUtente(nome,cognome,sesso,datanascita,dipartimento,"esterno");
            ResultSet resultSet2 = stmt.executeQuery(getidUtenteSql);
            resultSet2.next();
            idUtente = resultSet2.getString("idUtente");
        }
        else{
            idUtente = resultSet.getString("idUtente");
        }
        String insertSql = "INSERT INTO UtenteEsterno(idUtente, idEsterno)"
                + " VALUES('"+idUtente+"', '"+idEsterno+"')";
        stmt.executeUpdate(insertSql);
    }
    public ArrayList<String> SelectSql() throws SQLException {
        ArrayList<String> risultati=new ArrayList<String>();

        //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
        stmt=con.createStatement();
        String selectSql = "SELECT * FROM Utente";
        ResultSet resultSet = stmt.executeQuery(selectSql);
        while (resultSet.next()) {
            String name = resultSet.getString("nome");
            risultati.add(name);
        }
        return risultati;
    }

    public void insertCreditoFormativo(String idRischio, String certificazione) throws SQLException {
        stmt=con.createStatement();
        String insertCredito="INSERT INTO CreditoFormativo(idRischio, CertificazioneEsterna) " +
                "VALUES('"+idRischio+"', '"+certificazione+"')";
        stmt.executeUpdate(insertCredito);
    }

    public void insertCreditoFormativo(String idRischio) throws SQLException {
        insertCreditoFormativo(idRischio, "");
    }

    public void sostieniCreditoFormativo(int idUtente, int idCredito) throws SQLException {
        stmt=con.createStatement();
        String insertCredito="INSERT INTO CreditoFormativoSostenuto(idCreditoFormativo,idUtente) " +
                "VALUES("+idCredito+", '"+idUtente+"')";
        stmt.executeUpdate(insertCredito);
    }

    public void InsertRichiesta(int idUtente, int idRiferimento, String tipo) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO Richiesta(stato, idUtente, idRiferimento, tipo)"
                + " VALUES('"+"proposta"+"',"+idUtente+","+idRiferimento+",'"+tipo+"')";
        stmt.executeUpdate(insertSql);
    }

}

