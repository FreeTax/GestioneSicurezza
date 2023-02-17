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

    public void insertUtenteInterno(int matricola,String password, String nome, String cognome,String sesso,String datanascita,String Dipartimento,String tipo) throws SQLException {
        try{
            Date date = Date.valueOf(datanascita);
            u=new UtenteInterno(matricola, password, nome,  cognome, sesso,  Dipartimento, date,matricola,tipo);
            u.insertUtente();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void insertUtenteEsterno(int idEsterno,String password, String nome, String cognome,String sesso,String datanascita,String Dipartimento) throws SQLException {
        try{
            Date date = Date.valueOf(datanascita);
            u=new UtenteEsterno(1, password, nome,  cognome, sesso,  Dipartimento, date,idEsterno );
            u.insertUtente();
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    public void insertCreditoFormativo(int codice, String idRischio) throws SQLException {
        try{
            cf=new CreditoFormativo(codice, idRischio,"");
            cf.insertCreditoFormativo(idRischio);
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }


    /*login utente interno e esterno*/
    public boolean loginInterno(int matricola, String password) throws SQLException {
        try{
            UtenteGatewayDb uGateway=new UtenteGatewayDb();
            return uGateway.loginInterno(matricola, password);
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }

    }

    public boolean loginEsterno(int idEsterno, String password) throws SQLException {
        try{
            UtenteGatewayDb uGateway=new UtenteGatewayDb();
            return uGateway.loginEsterno(idEsterno, password);
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
    /* inserimento richiesta accesso a luogo e dipartimento*/
    public void insertRichiestaLuogo(int idUtente, int idLuogo) throws SQLException {
        try{
            RichiestaLuogo rl=new RichiestaLuogo( idUtente,0, idLuogo);
            rl.insertRichiesta(idLuogo);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void insertRichiestaDipartimento(int idUtente,int idDipartimento) throws SQLException {
        try{
            RichiestaDipartimento rd=new RichiestaDipartimento(idUtente,0,idDipartimento);
            rd.insertRichiesta(idDipartimento);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }
    /* aggiorna dati utente interno e esterno*/
    public void aggiornaUtenteInterno(int matricola,String password, String nome, String cognome,String sesso,String datanascita,String Dipartimento,String tipo) throws SQLException {
        try {
            Date date = Date.valueOf(datanascita);
            UtenteInterno u=new UtenteInterno(1, password, nome,  cognome, sesso,  Dipartimento, date,matricola,tipo );
            u.updateUtenteDb();
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    public void aggiornaUtenteEsterno(int idEsterno,String password, String nome, String cognome,String sesso,String datanascita,String Dipartimento) throws SQLException {
        try{
            Date date = Date.valueOf(datanascita);
            UtenteEsterno u=new UtenteEsterno(1, password, nome,  cognome, sesso,  Dipartimento, date,idEsterno );
            u.updateUtenteDb();
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
    /* caricamento certificazione*/
    public void caricaCertificazione( int idUtente, int codice, String certificazione) throws SQLException {
        try{
            UtenteGatewayDb uGateway=new UtenteGatewayDb();
            uGateway.sostieniCreditoFormativo(idUtente, codice, certificazione);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }
    /*
    public void sostieniCredito( int idUtente, int codice) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        uGateway.sostieniCreditoFormativo(idUtente, codice, null);
    }*/
    /* TODO: capire come gestire utilizzo dei metodi sottostanti per utenti specifici*/
    /*FIXME: get cfu sostenuti per rischi generici e specifici*/
    public ArrayList<String> getCFUSostenuti(int idAutorizzato ,int idUtente) throws SQLException {
        try{
            UtenteGatewayDb uGateway=new UtenteGatewayDb();
            if(uGateway.checkSupervisore(idAutorizzato) || uGateway.checkAvanzato(idAutorizzato)){
                ArrayList<CreditoFormativo> cfus=uGateway.GetCFUSostenuti(idUtente);
                ArrayList<String> cfusString=new ArrayList<>();
                cfus.forEach(cf->cfusString.add(cf.toString()));
                return cfusString;
            }
            else{
                throw new SQLException("Utente non autorizzato");
            }
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }
    /* get richieste luogo e dipartimento*/
    public ArrayList<String> getRichiesteLuogo(int idUtente) throws SQLException {
        try{
            UtenteGatewayDb uGateway=new UtenteGatewayDb();
            if(uGateway.checkSupervisore(idUtente)){
                ArrayList<RichiestaLuogo> richiesteLuogo=uGateway.GetRichiesteLuogo();
                ArrayList<String> richiesteLuogoString=new ArrayList<>();
                richiesteLuogo.forEach(rl->richiesteLuogoString.add(rl.toString()));
                return richiesteLuogoString;
            }
            else{
                throw new SQLException("Utente non autorizzato");
            }
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<String> getRichiesteDipartimento(int idUtente) throws SQLException {
        try{
            UtenteGatewayDb uGateway=new UtenteGatewayDb();
            if(uGateway.checkAvanzato(idUtente)){
                ArrayList<RichiestaDipartimento> richiesteDipartimento=uGateway.GetRichiesteDipartimento();
                ArrayList<String> richiesteDipartimentoString=new ArrayList<>();
                richiesteDipartimento.forEach(rd->richiesteDipartimentoString.add(rd.toString()));
                return richiesteDipartimentoString;
            }
            else{
                throw new SQLException("Utente non autorizzato");
            }
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }
}
