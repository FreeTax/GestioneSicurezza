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
    }

    //this method is private because it is used only by the other methods of this class. no one else should be able to use it
    private void InsertUtente(String password,String nome,String cognome, String sesso, Date datanascita, String dipartimento, String tipologia) throws SQLException {
            String insertSql = "INSERT INTO Utente(password,nome, cognome, sesso, datanascita, dipartimento, tipologia)"
                    + " VALUES('"+password+"', '"+nome+"', '"+cognome+"', '"+sesso+"','"+datanascita.toString()+"','"+dipartimento+"','"+tipologia+"')";
            stmt.executeUpdate(insertSql);
    }

    /*controlla se è gia presente un utente nel db dopodichè lo inserisce nella tabella Utente e UtenteInterno*/
    public void InsertUtenteInterno(int matricola, String password,String nome,String cognome, String sesso, Date datanascita, String dipartimento, String tipo) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            int idUtente = 0;
            String getidUtenteSql="SELECT idUtente FROM Utente WHERE nome='"+nome+"' AND cognome='"+cognome+"' AND datanascita='"+datanascita.toString()+"' AND dipartimento='"+dipartimento+"' AND tipologia='interno' AND password='"+password+"'";
            ResultSet resultSet = stmt.executeQuery(getidUtenteSql);
            if(resultSet.next()==false) {
                InsertUtente(password,nome,cognome,sesso,datanascita,dipartimento,"interno");
                ResultSet resultSet2 = stmt.executeQuery(getidUtenteSql);
                resultSet2.next();
                idUtente = resultSet2.getInt("idUtente");
            }
            else{
                throw new SQLException("Utente già presente");
            }
            String insertSql = "INSERT INTO UtenteInterno(idUtente, matricola,tipo)" + " VALUES('"+idUtente+"', '"+matricola+"', '"+tipo+"')";
            stmt.executeUpdate(insertSql);
        }finally {
            con.close();
        }

    }
    /*controlla se è gia presente un utente nel db dopodichè lo inserisce nella tabella Utente e UtenteEsterno*/
    public void InsertUtenteEsterno(int idEsterno,String password, String nome,String cognome, String sesso, Date datanascita, String dipartimento) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
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
        }finally {
            con.close();
        }

    }
    /*inserisce certificazione CFU*/
    public void insertCreditoFormativo(int idRischio, String certificazione) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String insertCredito="INSERT INTO CreditoFormativo(idRischio, CertificazioneEsterna) " +
                    "VALUES('"+idRischio+"', '"+certificazione+"')";
            stmt.executeUpdate(insertCredito);
        }finally {
            con.close();
        }
        

    }
    /*inserisce CFU sostenuto senza certificazione*/
    public void insertCreditoFormativo(int idRischio) throws SQLException {
        insertCreditoFormativo(idRischio, "");
    }
    /* inserisce CFU nel db*/
    public void sostieniCreditoFormativo(int idUtente, int idCredito, String certificazione) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String insertCredito="INSERT INTO CreditoFormativoSostenuto(idCreditoFormativo,idUtente, CertificazioneEsterna) " +
                    "VALUES("+idCredito+", '"+idUtente+"', '"+certificazione+"')";
            stmt.executeUpdate(insertCredito);
        }finally {
            con.close();
        }
    }

    public void InsertRichiesta(int idUtente, int idRiferimento, String tipo) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String insertSql = "INSERT INTO Richiesta(stato, idUtente, idRiferimento, tipo)"
                    + " VALUES('"+0+"',"+idUtente+","+idRiferimento+",'"+tipo+"')";
            stmt.executeUpdate(insertSql);
        }finally {
            con.close();
        }
    }

    public UtenteInterno GetUtenteInterno(int matricola) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
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
        }finally {
            con.close();
        }
    }

    public UtenteEsterno GetUtenteEsterno(int idEsterno) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
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
        }finally {
            con.close();
        }
    }

    public int getIdUtente(int riferimento, boolean interno) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
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
        }finally {
            con.close();
        }
    }

    public ArrayList<CreditoFormativo> GetCFUSostenuti(int idUtente) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String getSql="SELECT * FROM CreditoFormativoSostenuto cfs INNER JOIN CreditoFormativo cf on cf.idCreditoFormativo=cfs.idCreditoFormativo WHERE idUtente="+idUtente;
            ResultSet resultSet = stmt.executeQuery(getSql);
            ArrayList<CreditoFormativo> risultati=new ArrayList<CreditoFormativo>();
            while (resultSet.next()) {
                int idCreditoFormativo = resultSet.getInt("idCreditoFormativo");
                int idRischio = resultSet.getInt("idRischio");
                String certificazioneEsterna = resultSet.getString("CertificazioneEsterna");
                risultati.add(new CreditoFormativo(idCreditoFormativo, idRischio, certificazioneEsterna));
            }
            return risultati;
        }finally {
            con.close();
        }
    }

    public ArrayList<RichiestaLuogo> GetRichiesteLuogo() throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
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
        } finally {
            con.close();
        }
    }

    public ArrayList<RichiestaLuogo> getRichiesteLuogo(int idUtente) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String getSql="SELECT * FROM Richiesta r WHERE  r.tipo='luogo' AND r.idUtente="+idUtente;
            ResultSet resultSet = stmt.executeQuery(getSql);
            ArrayList<RichiestaLuogo> risultati=new ArrayList<RichiestaLuogo>();
            while (resultSet.next()) {
                //int idRichiesta = resultSet.getInt("idRichiesta");
                int stato = resultSet.getInt("stato");
                int idRiferimento = resultSet.getInt("idRiferimento");
                int utente = resultSet.getInt("idUtente");
                //String tipo = resultSet.getString("tipo");
                risultati.add(new RichiestaLuogo(utente, stato, idRiferimento));
            }
            return risultati;
        } finally {
            con.close();
        }
    }

    public ArrayList<RichiestaDipartimento> GetRichiesteDipartimento() throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
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
        } finally {
            con.close();
        }
    }

    public ArrayList<RichiestaDipartimento> getRichiesteDipartimento(int utente) throws SQLException{
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String getSql="SELECT * FROM Richiesta r WHERE r.tipo='dipartimento' and r.idUtente="+utente;
            ResultSet resultSet = stmt.executeQuery(getSql);
            ArrayList<RichiestaDipartimento> risultati=new ArrayList<RichiestaDipartimento>();
            while (resultSet.next()) {
                int stato = resultSet.getInt("stato");
                int idRiferimento = resultSet.getInt("idRiferimento");
                int idUtente = resultSet.getInt("idUtente");
                risultati.add(new RichiestaDipartimento(idUtente, stato, idRiferimento));
            }
            return risultati;
        } finally {
            con.close();
        }
    }
    public void updateUtenteInterno(int matricola, String nome, String cognome, String sesso, String datanascita, String dipartimento, String tipo) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String updateSql = "UPDATE UtenteInterno ui INNER JOIN Utente u on u.idUtente=ui.idUtente SET nome='"+nome+"', cognome='"+cognome+"', sesso='"+sesso+"', datanascita='"+datanascita+"', dipartimento='"+dipartimento+"', tipo='"+tipo+"' WHERE matricola='"+matricola+"'";
            stmt.executeUpdate(updateSql);
        }finally {
            con.close();
        }
    }

    public void updateUtenteEsterno(int idEsterno, String nome, String cognome, String sesso, String datanascita, String dipartimento) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String updateSql = "UPDATE UtenteEsterno ue INNER JOIN Utente u on u.idUtente=ue.idUtente SET nome='"+nome+"', cognome='"+cognome+"', sesso='"+sesso+"', datanascita='"+datanascita+"', dipartimento='"+dipartimento+"' WHERE idEsterno='"+idEsterno+"'";
            stmt.executeUpdate(updateSql);
        }finally {
            con.close();
        }
    }

    public boolean loginInterno(int matricola, String password) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String getSql="SELECT * FROM UtenteInterno ui INNER JOIN Utente u on u.idUtente=ui.idUtente WHERE matricola='"+matricola+"' AND password='"+password+"'";
            ResultSet resultSet = stmt.executeQuery(getSql);
            if(resultSet.next()){
                return true;
            }
            return false;
        }finally {
            con.close();
        }
    }

    public boolean loginEsterno(int idEsterno, String password) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String getSql="SELECT * FROM UtenteEsterno ue INNER JOIN Utente u on u.idUtente=ue.idUtente WHERE idEsterno='"+idEsterno+"' AND password='"+password+"'";
            ResultSet resultSet = stmt.executeQuery(getSql);
            if(resultSet.next()){
                return true;
            }
            return false;
        }finally {
            con.close();
        }
    }

    public boolean checkSupervisore(int idUtente) throws SQLException {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String getSql="SELECT * FROM UtenteInterno WHERE idUtente="+idUtente+" and tipo='supervisore'";
            ResultSet resultSet = stmt.executeQuery(getSql);
            if(resultSet.next()){
                return true;
            }
            return false;
        }finally {
            con.close();
        }
    }

    public boolean checkAvanzato(int idUtente) throws SQLException {

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String getSql="SELECT * FROM UtenteInterno WHERE idUtente="+idUtente+" and tipo='avanzato'";
            ResultSet resultSet = stmt.executeQuery(getSql);
            if(resultSet.next()){
                return true;
            }
            return false;
        } finally {
            con.close();
        }
    }

    public CreditoFormativo getCreditoFormativo(int rischio) throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String getSql="SELECT * FROM CreditoFormativo cf WHERE idRischio="+rischio;
            ResultSet resultSet = stmt.executeQuery(getSql);
            CreditoFormativo creditoFormativo=null;
            while (resultSet.next()) {
                int idCreditoFormativo = resultSet.getInt("idCreditoFormativo");
                int idRischio = resultSet.getInt("idRischio");
                String Certificazione = resultSet.getString("CertificazioneEsterna");
                creditoFormativo= new CreditoFormativo(idCreditoFormativo, idRischio, Certificazione);
            }
            return creditoFormativo;

        }finally {
            con.close();
        }
    }

    public ArrayList<String> getUtenti() throws SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            stmt=con.createStatement();
            String getSql="SELECT * FROM Utente";
            ResultSet resultSet = stmt.executeQuery(getSql);
            ArrayList<String> infoUtenti=new ArrayList<String>();
            while (resultSet.next()) {
                int idCreditoFormativo = resultSet.getInt("idUtente");
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                infoUtenti.add(idCreditoFormativo+" "+nome+" "+cognome);
            }
            return infoUtenti;

        }finally {
            con.close();
        }
    }
}

