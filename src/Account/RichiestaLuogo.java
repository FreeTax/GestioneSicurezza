package Account;

import Luoghi.Luogo;

class RichiestaLuogo extends Richiesta {
    private Luoghi.Luogo Luogo;

    public RichiestaLuogo(Utente utente, int statoRichiesta, Luoghi.Luogo luogo) {
        super(utente, statoRichiesta);
        Luogo = luogo;
    }
}
