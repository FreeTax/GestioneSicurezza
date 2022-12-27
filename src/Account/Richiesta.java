package Account;

import Luoghi.Luogo;

abstract class Richiesta {
    private Utente utente;
    private int statoRichiesta;
    Richiesta(Utente utente, int statoRichiesta) {
        this.utente = utente;
        this.statoRichiesta = statoRichiesta;
    }
}

