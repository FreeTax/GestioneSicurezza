package Account;

import Luoghi.Dipartimento;

import java.sql.SQLException;

class RichiestaDipartimento extends Richiesta {
    private Dipartimento dipartimento;

    public RichiestaDipartimento(Utente utente, int statoRichiesta, Dipartimento dipartimento) throws SQLException {
        super(utente, statoRichiesta);
        this.dipartimento = dipartimento;
    }
    public void insertRichiesta(int idRiferimento) throws SQLException {
        super.insertRichiesta(idRiferimento, "dipartimento");
    }
    public void setIdDipartimento(String idDipartimento) {
        this.dipartimento = dipartimento;
    }
}
