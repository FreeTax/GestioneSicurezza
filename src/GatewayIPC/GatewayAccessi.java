package GatewayIPC;

import Accessi.Accesso;
import Accessi.AccessoDipartimentoAbilitato;
import Accessi.AccessoLuogoAbilitato;

import AccessiGatewayDb.AccessoLuogoAbilitatoGatewayDb;

import Account.Utente;
import Account.UtenteEsterno;
import Account.UtenteInterno;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.sql.SQLException;


public class GatewayAccessi {
    private AccessoLuogoAbilitatoGatewayDb accessoLuogoAbilitatoGatewayDb;

    public GatewayAccessi() throws SQLException {
        accessoLuogoAbilitatoGatewayDb = new AccessoLuogoAbilitatoGatewayDb();
    }

    public void inserAccessoDipartimento(int utente, boolean ext, int dipartimento) throws SQLException{
        Utente u;
        if(ext)
            u = new UtenteEsterno(utente);
        else
            u = new UtenteInterno(utente);
        Dipartimento d= new Dipartimento(dipartimento);
        Accesso a = new AccessoDipartimentoAbilitato(u, d);
        a.insertAccesso();
    }

    public void insertAccessoLuogo(int utente, boolean ext, int luogo) throws SQLException{
        Utente u;
        if(ext)
            u = new UtenteEsterno(utente);
        else
            u = new UtenteInterno(utente);
        Luogo l= new Luogo(luogo);
        Accesso a = new AccessoLuogoAbilitato(u, l);
        a.insertAccesso();
    }

    public void updateAccessoDipartimento(int utente, boolean ext, int dipartimento) throws SQLException{
        Utente u;
        if (ext)
            u= new UtenteEsterno(utente);
        else
            u= new UtenteInterno(utente);
        Dipartimento d= new Dipartimento(dipartimento);
        Accesso a = new AccessoDipartimentoAbilitato(u, d);
        a.updateAccesso();
    }

    public void updateAccessoLuogo(int utente, boolean ext, int luogo) throws SQLException{
        Utente u;
        if (ext)
            u= new UtenteEsterno(utente);
        else
            u= new UtenteInterno(utente);
        Luogo l= new Luogo(luogo);
        Accesso a = new AccessoLuogoAbilitato(u, l);
        a.updateAccesso();
    }

    public void deleteAccessoDipartimento(int utente, boolean ext, int id) throws SQLException{
        Utente u;
        if (ext)
            u= new UtenteEsterno(utente);
        else
            u= new UtenteInterno(utente);
        Dipartimento d= new Dipartimento(id);
        Accesso a = new AccessoDipartimentoAbilitato(u, d);
        a.deleteAccesso();
    }

    public void deleteAccessoLuogo(int utente, boolean ext, int id) throws SQLException{
        Utente u;
        if (ext)
            u= new UtenteEsterno(utente);
        else
            u= new UtenteInterno(utente);
        Luogo l= new Luogo(id);
        Accesso a = new AccessoLuogoAbilitato(u, l);
        a.deleteAccesso();
    }


}
