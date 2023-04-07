package Account;

import Luoghi.Dipartimento;

import java.sql.SQLException;

public class RichiestaDipartimento extends Richiesta {
    private int idDipartimento;

    public RichiestaDipartimento(int idUtente, int statoRichiesta, int idDipartimento) throws SQLException {
        super(idUtente, statoRichiesta);
        this.idDipartimento = idDipartimento;
    }
    public void insertRichiesta(/*int idDipartimento*/) throws SQLException {
        super.insertRichiesta(idDipartimento, "dipartimento");
    }
   /* public void setIdDipartimento(int idDipartimento) {
        this.idDipartimento = idDipartimento;
    }*/

    public int getIdDipartimento() {
        return idDipartimento;
    }
    @Override
    public String toString() {
        return super.toString()+", idDipartimento=" + idDipartimento;
    }
}
