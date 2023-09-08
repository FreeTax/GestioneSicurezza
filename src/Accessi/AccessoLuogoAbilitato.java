package Accessi;

import java.sql.SQLException;
import java.util.ArrayList;

public class AccessoLuogoAbilitato extends Accesso {
    private final int luogo;

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

    public ArrayList<Integer> getLuoghiFrequentati(int idUtente) throws SQLException{
        return gatewayDb.getLuoghiFrequentati(idUtente);
    }
}
