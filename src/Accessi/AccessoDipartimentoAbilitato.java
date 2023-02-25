package Accessi;

import Account.Utente;
import Luoghi.Dipartimento;

import java.sql.SQLException;

public class AccessoDipartimentoAbilitato extends Accesso {
    private int idDipartimento;

    public AccessoDipartimentoAbilitato(int utente, int dipartimento) throws SQLException {
        super(utente);
        this.idDipartimento = dipartimento;
    }


    @Override
    public void insertAccesso() throws SQLException {
        gatewayDb.inserAccessoDipartimento(idUtente, idDipartimento);
    }

    @Override
    public void updateAccesso() throws SQLException {
        gatewayDb.updateAccessoDipartimento(idUtente, idDipartimento);

    }

    @Override
    public void deleteAccesso() throws SQLException {
        gatewayDb.deleteAccessoDipartimento(idUtente, idDipartimento);
    }

    public int getIdUtente() {
        return idUtente;
    }

    public int getIdDipartimento() {
        return idDipartimento;
    }
}
