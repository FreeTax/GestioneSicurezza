package Accessi;

import java.sql.SQLException;

public class AccessoLuogoAbilitato extends Accesso {
    private int luogo;
    private int utente;

    public AccessoLuogoAbilitato(int utente, int luogo) throws SQLException {
        super(utente);
        this.luogo = luogo;
    }

    @Override
    public void insertAccesso() throws SQLException {
        gatewayDb.insertAccessoLuogo(utente,luogo );
    }

    @Override
    public void updateAccesso() throws SQLException {
        gatewayDb.updateAccessoLuogo(utente, luogo);
    }

    @Override
    public void deleteAccesso() throws SQLException {
        gatewayDb.deleteAccessoLuogo(utente, luogo);
    }
}
