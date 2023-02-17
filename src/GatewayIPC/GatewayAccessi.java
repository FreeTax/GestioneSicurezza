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

    public void inserAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
        a.insertAccesso();
    }

    public void insertAccessoLuogo(int utente, int luogo) throws SQLException{

        Accesso a = new AccessoLuogoAbilitato(utente, luogo);
        a.insertAccesso();
    }

    public void updateAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
        a.updateAccesso();
    }

    public void updateAccessoLuogo(int utente, int dipartimento) throws SQLException{
        Accesso a = new AccessoLuogoAbilitato(utente, dipartimento);
        a.updateAccesso();
    }

    public void deleteAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
        a.deleteAccesso();
    }

    public void deleteAccessoLuogo(int utente, int dipartimento) throws SQLException{
        Accesso a = new AccessoLuogoAbilitato(utente, dipartimento);
        a.deleteAccesso();
    }


}
