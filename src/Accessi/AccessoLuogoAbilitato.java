package Accessi;

import Account.Utente;
import Luoghi.Luogo;

import java.sql.SQLException;

public class AccessoLuogoAbilitato extends Accesso {
    private Utente utente;
    private Luogo luoghoAbilitato;

    public AccessoLuogoAbilitato(Utente utente, Luogo luoghoAbilitato) throws SQLException {
        super(utente);
        this.luoghoAbilitato = luoghoAbilitato;
    }

    @Override
    public void insertAccesso() throws SQLException {
        gatewayDb.insertAccessoLuogo(utente.getCodice(), luoghoAbilitato.getCodice());
    }

    @Override
    public void updateAccesso() throws SQLException {
        gatewayDb.updateAccessoLuogo(utente.getCodice(), luoghoAbilitato.getCodice());
    }

    @Override
    public void deleteAccesso() throws SQLException {
        gatewayDb.deleteAccessoLuogo(utente.getCodice(), luoghoAbilitato.getCodice());
    }
}
