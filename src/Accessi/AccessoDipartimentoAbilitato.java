package Accessi;

import Account.Utente;
import Luoghi.Dipartimento;

import java.sql.SQLException;

public class AccessoDipartimentoAbilitato extends Accesso {
    private Utente utente;
    private Dipartimento dipartimento;

    public AccessoDipartimentoAbilitato(Utente utente, Dipartimento dipartimento) throws SQLException {
        super(utente);
        this.dipartimento = dipartimento;
    }


    @Override
    public void insertAccesso() throws SQLException {
        gatewayDb.inserAccessoDipartimento(utente.getCodice(), dipartimento.getId());
    }

    @Override
    public void updateAccesso() throws SQLException {
        gatewayDb.updateAccessoDipartimento(utente.getCodice(), dipartimento.getId());

    }

    @Override
    public void deleteAccesso() throws SQLException {
        gatewayDb.deleteAccessoDipartimento(utente.getCodice(), dipartimento.getId());
    }

    public Utente getUtente() {
        return utente;
    }

    public Dipartimento dipartimento() {
        return dipartimento;
    }
}
