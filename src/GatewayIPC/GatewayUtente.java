package GatewayIPC;

import Account.*;
import AccountGateway.UtenteGatewayDb;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class GatewayUtente {
    private Utente u;
    private CreditoFormativo cf;

    public void insertUtenteInterno(int matricola,String password, String nome, String cognome,String sesso,String datanascita,String Dipartimento) throws SQLException {
        Date date = Date.valueOf(datanascita);
        u=new UtenteInterno(1, password, nome,  cognome, sesso,  Dipartimento, date,matricola,"base" );
        u.insertUtente();
    }
    public void insertUtenteEsterno(int idEsterno,String password, String nome, String cognome,String sesso,String datanascita,String Dipartimento) throws SQLException {
        Date date = Date.valueOf(datanascita);
        u=new UtenteEsterno(1, password, nome,  cognome, sesso,  Dipartimento, date,idEsterno );
        u.insertUtente();
    }

    public void insertCreditoFormativo(int codice) throws SQLException {
        cf=new CreditoFormativo(codice, "0","");
        cf.insertCreditoFormativo();
    }

    public boolean loginInterno(int matricola, String password) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        return uGateway.loginInterno(matricola, password);
    }

    public boolean loginEsterno(int idEsterno, String password) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        return uGateway.loginEsterno(idEsterno, password);
    }
    public void insertRichiestaLuogo(int idUtente, int idLuogo) throws SQLException {
        RichiestaLuogo rl=new RichiestaLuogo( idUtente,0, idLuogo);
        rl.insertRichiesta(idLuogo);
    }

    public void insertRichiestaDipartimento(int idUtente,int idDipartimento) throws SQLException {
        RichiestaDipartimento rd=new RichiestaDipartimento(idUtente,0,idDipartimento);
        rd.insertRichiesta(idDipartimento);
    }

    public void aggiornaUtenteInterno(int matricola,String password, String nome, String cognome,String sesso,String datanascita,String Dipartimento,String tipo) throws SQLException {
        Date date = Date.valueOf(datanascita);
        UtenteInterno u=new UtenteInterno(1, password, nome,  cognome, sesso,  Dipartimento, date,matricola,tipo );
        u.updateUtenteDb();
    }

    public void aggiornaUtenteEsterno(int idEsterno,String password, String nome, String cognome,String sesso,String datanascita,String Dipartimento) throws SQLException {
        Date date = Date.valueOf(datanascita);
        UtenteEsterno u=new UtenteEsterno(1, password, nome,  cognome, sesso,  Dipartimento, date,idEsterno );
        u.updateUtenteDb();
    }

    public void caricaCertificazione( int idUtente, int codice, String certificazione) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        uGateway.sostieniCreditoFormativo(idUtente, codice, certificazione);
    }
    /*
    public void sostieniCredito( int idUtente, int codice) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        uGateway.sostieniCreditoFormativo(idUtente, codice, null);
    }*/

}
