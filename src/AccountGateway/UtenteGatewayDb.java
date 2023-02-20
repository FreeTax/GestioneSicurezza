package AccountGateway;

import Account.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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
    public void InsertUtente(String password,String nome,String cognome, String sesso, Date datanascita, String dipartimento, String tipologia) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO Utente(password,nome, cognome, sesso, datanascita, dipartimento, tipologia)"
                + " VALUES('"+password+"', '"+nome+"', '"+cognome+"', '"+sesso+"','"+datanascita.toString()+"','"+dipartimento+"','"+tipologia+"')";
        stmt.executeUpdate(insertSql);
    }
    public void InsertUtenteInterno(int matricola, String password,String nome,String cognome, String sesso, Date datanascita, String dipartimento, String tipo) throws SQLException {
        stmt=con.createStatement();
        String idUtente = null;
        String getidUtenteSql="SELECT idUtente FROM Utente WHERE nome='"+nome+"' AND cognome='"+cognome+"' AND datanascita='"+datanascita.toString()+"' AND dipartimento='"+dipartimento+"' AND tipologia='interno' AND password='"+password+"'";
        ResultSet resultSet = stmt.executeQuery(getidUtenteSql);
        if(resultSet.next()==false) {
            InsertUtente(password,nome,cognome,sesso,datanascita,dipartimento,"interno");
            ResultSet resultSet2 = stmt.executeQuery(getidUtenteSql);
            resultSet2.next();
            idUtente = resultSet2.getString("idUtente");
        }
        else{
            throw new SQLException("Utente già presente");
        }
        String insertSql = "INSERT INTO UtenteInterno(idUtente, matricola,tipo)" + " VALUES('"+idUtente+"', '"+matricola+"', '"+tipo+"')";
        stmt.executeUpdate(insertSql);
    }
    public void InsertUtenteEsterno(int idEsterno,String password, String nome,String cognome, String sesso, Date datanascita, String dipartimento) throws SQLException {
        stmt=con.createStatement();
        int idUtente = 0;
        String getidUtenteSql="SELECT idUtente FROM Utente WHERE nome='"+nome+"' AND cognome='"+cognome+"' AND datanascita='"+datanascita.toString()+"' AND dipartimento='"+dipartimento+"' AND tipologia='esterno' AND password='"+password+"'";
        ResultSet resultSet = stmt.executeQuery(getidUtenteSql);

        if(resultSet.next()==false) {
            InsertUtente(password,nome,cognome,sesso,datanascita,dipartimento,"esterno");
            ResultSet resultSet2 = stmt.executeQuery(getidUtenteSql);
            resultSet2.next();
            idUtente = resultSet2.getInt("idUtente");
        }
        else{
            throw new SQLException("Utente già presente");
        }
        String insertSql = "INSERT INTO UtenteEsterno(idUtente, idEsterno)"
                + " VALUES('"+idUtente+"', '"+idEsterno+"')";
        stmt.executeUpdate(insertSql);
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

    public void sostieniCreditoFormativo(int idUtente, int idCredito, String certificazione) throws SQLException {
        stmt=con.createStatement();
        String insertCredito="INSERT INTO CreditoFormativoSostenuto(idCreditoFormativo,idUtente, CertificazioneEsterna) " +
                "VALUES("+idCredito+", '"+idUtente+"', '"+certificazione+"')";
        stmt.executeUpdate(insertCredito);
    }

    public void InsertRichiesta(int idUtente, int idRiferimento, String tipo) throws SQLException {
        stmt=con.createStatement();
        String insertSql = "INSERT INTO Richiesta(stato, idUtente, idRiferimento, tipo)"
                + " VALUES('"+0+"',"+idUtente+","+idRiferimento+",'"+tipo+"')";
        stmt.executeUpdate(insertSql);
    }

    public UtenteInterno GetUtenteInterno(int matricola) throws SQLException {
        stmt=con.createStatement();
        String getSql="SELECT * FROM UtenteInterno ui INNER JOIN Utente u on u.idUtente=ui.idUtente WHERE matricola="+matricola;
        ResultSet resultSet = stmt.executeQuery(getSql);
        UtenteInterno utente=null;
        while (resultSet.next()) {
            int idUtente = resultSet.getInt("idUtente");
            String password = resultSet.getString("password");
            String name = resultSet.getString("nome");
            String cognome = resultSet.getString("cognome");
            String sesso = resultSet.getString("sesso");
            Date datanascita = resultSet.getDate("datanascita");
            String dipartimento = resultSet.getString("dipartimento");
            int idIn = resultSet.getInt("matricola");
            String tipo = resultSet.getString("tipo");
            utente = new UtenteInterno(idUtente, password, name, cognome, sesso,dipartimento,datanascita,matricola,tipo);
        }
        return utente;
    }

    public UtenteEsterno GetUtenteEsterno(int idEsterno) throws SQLException {
        stmt=con.createStatement();
        String getSql="SELECT * FROM UtenteEsterno ue INNER JOIN Utente u on u.idUtente=ue.idUtente WHERE idEsterno="+idEsterno;
        ResultSet resultSet = stmt.executeQuery(getSql);
        UtenteEsterno utente=null;
        while (resultSet.next()) {
            int idUtente = resultSet.getInt("idUtente");
            String password = resultSet.getString("password");
            String name = resultSet.getString("nome");
            String cognome = resultSet.getString("cognome");
            String sesso = resultSet.getString("sesso");
            Date datanascita = resultSet.getDate("datanascita");
            String dipartimento = resultSet.getString("dipartimento");
            int ides = resultSet.getInt("idEsterno");
            utente = new UtenteEsterno(idUtente, password, name, cognome, sesso,dipartimento,datanascita,ides);
        }
        return utente;
    }

    public int getIdUtente(int riferimento, boolean interno) throws SQLException {
        stmt=con.createStatement();
        String getSql;
        if(interno==true){
            getSql="SELECT idUtente FROM UtenteInterno WHERE matricola="+riferimento;
        }
        else {
            getSql="SELECT idUtente FROM UtenteEsterno WHERE idEsterno="+riferimento;
        }
        int idUtente=0;
        ResultSet resultSet = stmt.executeQuery(getSql);
        while (resultSet.next()) {
            idUtente = resultSet.getInt("idUtente");
        }
        return idUtente;
    }

    public ArrayList<CreditoFormativo> GetCFUSostenuti(int idUtente) throws SQLException {
        stmt=con.createStatement();
        String getSql="SELECT * FROM CreditoFormativoSostenuto cfs INNER JOIN CreditoFormativo cf on cf.idCreditoFormativo=cfs.idCreditoFormativo WHERE idUtente="+idUtente;
        ResultSet resultSet = stmt.executeQuery(getSql);
        ArrayList<CreditoFormativo> risultati=new ArrayList<CreditoFormativo>();
        while (resultSet.next()) {
            int idCreditoFormativo = resultSet.getInt("idCreditoFormativo");
            String idRischio = resultSet.getString("idRischio");
            String certificazioneEsterna = resultSet.getString("CertificazioneEsterna");
            risultati.add(new CreditoFormativo(idCreditoFormativo, idRischio, certificazioneEsterna));
        }
        return risultati;
    }

    public ArrayList<RichiestaLuogo> GetRichiesteLuogo() throws SQLException {
        stmt=con.createStatement();
        String getSql="SELECT * FROM Richiesta r WHERE  r.tipo='luogo'";
        ResultSet resultSet = stmt.executeQuery(getSql);
        ArrayList<RichiestaLuogo> risultati=new ArrayList<RichiestaLuogo>();
        while (resultSet.next()) {
            //int idRichiesta = resultSet.getInt("idRichiesta");
            int stato = resultSet.getInt("stato");
            int idRiferimento = resultSet.getInt("idRiferimento");
            int idUtente = resultSet.getInt("idUtente");
            //String tipo = resultSet.getString("tipo");
            risultati.add(new RichiestaLuogo(idUtente, stato, idRiferimento));
        }
        return risultati;
    }

    public ArrayList<RichiestaDipartimento> GetRichiesteDipartimento() throws SQLException {
        stmt=con.createStatement();
        String getSql="SELECT * FROM Richiesta r WHERE r.tipo='dipartimento'";
        ResultSet resultSet = stmt.executeQuery(getSql);
        ArrayList<RichiestaDipartimento> risultati=new ArrayList<RichiestaDipartimento>();
        while (resultSet.next()) {
            //int idRichiesta = resultSet.getInt("idRichiesta");
            int stato = resultSet.getInt("stato");
            int idRiferimento = resultSet.getInt("idRiferimento");
            int idUtente = resultSet.getInt("idUtente");
            //String tipo = resultSet.getString("tipo");
            risultati.add(new RichiestaDipartimento(idUtente, stato, idRiferimento));
        }
        return risultati;
    }

    public void updateUtenteInterno(int matricola, String nome, String cognome, String sesso, String datanascita, String dipartimento, String tipo) throws SQLException {
        stmt=con.createStatement();
        String updateSql = "UPDATE UtenteInterno ui INNER JOIN Utente u on u.idUtente=ui.idUtente SET nome='"+nome+"', cognome='"+cognome+"', sesso='"+sesso+"', datanascita='"+datanascita+"', dipartimento='"+dipartimento+"', tipo='"+tipo+"' WHERE matricola='"+matricola+"'";
        stmt.executeUpdate(updateSql);
    }

    public void updateUtenteEsterno(int idEsterno, String nome, String cognome, String sesso, String datanascita, String dipartimento) throws SQLException {
        stmt=con.createStatement();
        String updateSql = "UPDATE UtenteEsterno ue INNER JOIN Utente u on u.idUtente=ue.idUtente SET nome='"+nome+"', cognome='"+cognome+"', sesso='"+sesso+"', datanascita='"+datanascita+"', dipartimento='"+dipartimento+"' WHERE idEsterno='"+idEsterno+"'";
        stmt.executeUpdate(updateSql);
    }

    public boolean loginInterno(int matricola, String password) throws SQLException {
        stmt=con.createStatement();
        String getSql="SELECT * FROM UtenteInterno ui INNER JOIN Utente u on u.idUtente=ui.idUtente WHERE matricola='"+matricola+"' AND password='"+password+"'";
        ResultSet resultSet = stmt.executeQuery(getSql);
        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public boolean loginEsterno(int idEsterno, String password) throws SQLException {
        stmt=con.createStatement();
        String getSql="SELECT * FROM UtenteEsterno ue INNER JOIN Utente u on u.idUtente=ue.idUtente WHERE idEsterno='"+idEsterno+"' AND password='"+password+"'";
        ResultSet resultSet = stmt.executeQuery(getSql);
        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public boolean checkSupervisore(int idUtente) throws SQLException {
        stmt=con.createStatement();
        String getSql="SELECT * FROM UtenteInterno WHERE idUtente="+idUtente+" and tipo='supervisore'";
        ResultSet resultSet = stmt.executeQuery(getSql);
        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public boolean checkAvanzato(int idUtente) throws SQLException {
        stmt=con.createStatement();
        String getSql="SELECT * FROM UtenteInterno WHERE idUtente="+idUtente+" and tipo='avanzato'";
        ResultSet resultSet = stmt.executeQuery(getSql);
        if(resultSet.next()){
            return true;
        }
        return false;
    }

}

