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

 class AccessoLuogoAbilitato extends Accesso{
    public AccessoLuogoAbilitato(Utente utente, Luogo[] luoghiAbilitati) {
        super(utente);
        this.luoghiAbilitati = luoghiAbilitati;
    }

    private Utente utente;
    private Luogo luoghiAbilitati[];
}

class AccessoDipartimentoAbilitato extends Accesso{
    public AccessoDipartimentoAbilitato(Utente utente, Dipartimento[] dipartimentiAbilitati) {
        super(utente);
        this.dipartimentiAbilitati = dipartimentiAbilitati;
    }

    private Utente utente;
    private Dipartimento dipartimentiAbilitati[];
}