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

    private RichiestaLuogo rl;
    public void insertUtenteInterno(int matricola,String nome, String cognome,String sesso,String datanascita,String Dipartimento) throws SQLException {
        Date date = Date.valueOf(datanascita);
        u=new UtenteInterno(1, nome,  cognome, sesso,  Dipartimento, date,matricola,"base" );
        u.insertUtente();
    }
    public void insertUtenteEsterno(int idEsterno,String nome, String cognome,String sesso,String datanascita,String Dipartimento) throws SQLException {
        Date date = Date.valueOf(datanascita);
        u=new UtenteEsterno(1, nome,  cognome, sesso,  Dipartimento, date,idEsterno );
        u.insertUtente();
    }
    public ArrayList<String> nomeUtenti() throws SQLException {
        u=new UtenteInterno();
        return u.nomeUtenti();
    }

    public void insertCreditoFormativo(int codice) throws SQLException {
        cf=new CreditoFormativo(codice, 0,"");
        cf.insertCreditoFormativo();
    }

    public void sostieniCredito( int idUtente, int codice) throws SQLException {
        UtenteGatewayDb uGateway=new UtenteGatewayDb();
        uGateway.sostieniCreditoFormativo(idUtente, codice);
    }

    public void insertRichiestaLuogo(int idLuogo) throws SQLException {
        rl=new RichiestaLuogo(u,0);
        rl.insertRichiesta(idLuogo);
    }
}
