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


    /*login utente interno e esterno*/
    public boolean loginInterno(int matricola, String password) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        return uGateway.loginInterno(matricola, password);
    }

    public boolean loginEsterno(int idEsterno, String password) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        return uGateway.loginEsterno(idEsterno, password);
    }
    /* inserimento richiesta accesso a luogo e dipartimento*/
    public void insertRichiestaLuogo(int idUtente, int idLuogo) throws SQLException {
        RichiestaLuogo rl=new RichiestaLuogo( idUtente,0, idLuogo);
        rl.insertRichiesta(idLuogo);
    }

    public void insertRichiestaDipartimento(int idUtente,int idDipartimento) throws SQLException {
        RichiestaDipartimento rd=new RichiestaDipartimento(idUtente,0,idDipartimento);
        rd.insertRichiesta(idDipartimento);
    }
    /* aggiorna dati utente interno e esterno*/
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
    /* caricamento certificazione*/
    public void caricaCertificazione( int idUtente, int codice, String certificazione) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        uGateway.sostieniCreditoFormativo(idUtente, codice, certificazione);
    }
    /*
    public void sostieniCredito( int idUtente, int codice) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        uGateway.sostieniCreditoFormativo(idUtente, codice, null);
    }*/
    /* TODO: capire come gestire utilizzo dei metodi sottostanti per utenti specifici*/
    /*FIXME: get cfu sostenuti per rischi generici e specifici*/
    public ArrayList<String> getCFUSostenuti(int idUtente) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        ArrayList<CreditoFormativo> cfus=uGateway.GetCFUSostenuti(idUtente);
        ArrayList<String> cfusString=new ArrayList<>();
        cfus.forEach(cf->cfusString.add(cf.toString()));
        return cfusString;
    }
    /* get richieste luogo e dipartimento*/
    public ArrayList<String> getRichiesteLuogo(int idUtente) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        ArrayList<RichiestaLuogo> richiesteLuogo=uGateway.GetRichiesteLuogo(idUtente);
        ArrayList<String> richiesteLuogoString=new ArrayList<>();
        richiesteLuogo.forEach(rl->richiesteLuogoString.add(rl.toString()));
        return richiesteLuogoString;
    }

    public ArrayList<String> getRichiesteDipartimento(int idUtente) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        ArrayList<RichiestaDipartimento> richiesteDipartimento=uGateway.GetRichiesteDipartimento(idUtente);
        ArrayList<String> richiesteDipartimentoString=new ArrayList<>();
        richiesteDipartimento.forEach(rd->richiesteDipartimentoString.add(rd.toString()));
        return richiesteDipartimentoString;
    }
}
