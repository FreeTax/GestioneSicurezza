package Luoghi;

import Account.Utente;
import Rischi.Rischio;

public class Luogo{
    public Luogo(String codice, String nome, String tipo, Utente referente, Rischio[] rischi, Dipartimento dipartimento) {
        this.codice = codice;
        this.nome = nome;
        this.tipo = tipo;
        this.referente = referente;
        this.rischi = rischi;
        this.dipartimento = dipartimento;
    }

    private String codice,nome,tipo;
    private Utente referente;
    private Rischio rischi[];
    private Dipartimento dipartimento;
}
