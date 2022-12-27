package GatewayIPC;

import Account.CreditoFormativo;
import Account.Utente;
import Account.UtenteInterno;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class GatewayUtente {
    private Utente u;
    public void insertUtente(int matricola,String nome, String cognome) throws SQLException {
        LocalDate LocalDate = null;
        u=new UtenteInterno(1, new CreditoFormativo(2,"visite"), nome,  cognome, "M",  "fis", LocalDate,matricola,1 );
        u.insertUtente();
    }

    public ArrayList<String> nomeUtenti() throws SQLException {
        u=new UtenteInterno();
        return u.nomeUtenti();
    }
}
