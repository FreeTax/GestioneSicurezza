package Luoghi;
import Account.Utente;
import Rischi.Rischio;
public class Dipartimento {
    public Dipartimento(String codice, String nome, Utente responsabile, Luogo[] luoghi, Rischio[] rischi) {
        this.codice = codice;
        this.nome = nome;
        this.responsabile = responsabile;
        this.luoghi = luoghi;
        this.rischi=rischi;
    }
    private String codice,nome;
    private Utente responsabile;
    private Luogo luoghi[];
    private Rischio rischi[];
}

