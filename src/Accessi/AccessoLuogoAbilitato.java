package Accessi;

import java.sql.SQLException;

public class AccessoLuogoAbilitato extends Accesso {
    private int luogo;

    public AccessoLuogoAbilitato(int utente, int luogo) throws SQLException {
        super(utente);
        this.luogo = luogo;
    }

    @Override
    public void insertAccesso() throws SQLException {
        gatewayDb.insertAccessoLuogo(idUtente,luogo);
    }

    @Override
    public void updateAccesso() throws SQLException {
        gatewayDb.updateAccessoLuogo(idUtente, luogo);
    }

    @Override
    public void deleteAccesso() throws SQLException {
        gatewayDb.deleteAccessoLuogo(idUtente, luogo);
    }
}
