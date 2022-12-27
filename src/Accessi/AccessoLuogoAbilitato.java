package Accessi;

import Account.Utente;
import Luoghi.Luogo;

class AccessoLuogoAbilitato extends Accesso {
    private Utente utente;
    private Luogo luoghiAbilitati[];

    public AccessoLuogoAbilitato(Utente utente, Luogo[] luoghiAbilitati) {
        super(utente);
        this.luoghiAbilitati = luoghiAbilitati;
    }
}
