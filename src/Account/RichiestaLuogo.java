package Account;

import Luoghi.Luogo;

import java.sql.SQLException;

public class RichiestaLuogo extends Richiesta {
    private int idLuogo;

    public RichiestaLuogo(int idUtente, int statoRichiesta, int idLuogo) throws SQLException {
        super( idUtente, statoRichiesta);
        this.idLuogo = idLuogo;
    }

    public void insertRichiesta() throws SQLException {
        super.insertRichiesta(idLuogo, "luogo");
    }

    public int getIdLuogo() {
        return idLuogo;
    }

    @Override
    public String toString() {
        return super.toString()+", idLuogo=" + idLuogo;
    }
}
