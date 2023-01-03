package Account;

import Luoghi.Luogo;

import java.sql.SQLException;

public class RichiestaLuogo extends Richiesta {
    private Luoghi.Luogo Luogo;

    public RichiestaLuogo(Utente utente, int statoRichiesta/*, Luoghi.Luogo luogo*/) throws SQLException {
        super(utente, statoRichiesta);
        //Luogo = luogo;
    }

    public void insertRichiesta(int idRiferimento) throws SQLException {
        super.insertRichiesta(idRiferimento, "luogo");
    }
}
