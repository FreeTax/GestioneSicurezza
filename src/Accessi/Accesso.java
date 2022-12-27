package Accessi;
import Account.Utente;
import Luoghi.Dipartimento;
import Luoghi.Luogo;
public abstract class Accesso {
    private Utente utente;

    protected Accesso(Utente utente) {
        this.utente = utente;
    }
}

