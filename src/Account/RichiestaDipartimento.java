package Account;

import Luoghi.Dipartimento;

class RichiestaDipartimento extends Richiesta {
    private Dipartimento dipartimento;

    public RichiestaDipartimento(Utente utente, int statoRichiesta, Dipartimento dipartimento) {
        super(utente, statoRichiesta);
        this.dipartimento = dipartimento;
    }

    public void setIdDipartimento(String idDipartimento) {
        this.dipartimento = dipartimento;
    }
}
