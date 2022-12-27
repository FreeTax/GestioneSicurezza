package Accessi;

import Account.Utente;
import Luoghi.Dipartimento;

class AccessoDipartimentoAbilitato extends Accesso {
    private Utente utente;
    private Dipartimento dipartimentiAbilitati[];

    public AccessoDipartimentoAbilitato(Utente utente, Dipartimento[] dipartimentiAbilitati) {
        super(utente);
        this.dipartimentiAbilitati = dipartimentiAbilitati;
    }
}
