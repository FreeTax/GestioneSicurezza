package luoghi;
import rischi.Rischio;
public class Dipartimento {
    public Dipartimento(String codice, String nome, String responsabile, Luogo[] luoghi) {
        this.codice = codice;
        this.nome = nome;
        this.responsabile = responsabile;
        this.luoghi = luoghi;
    }
    private String codice,nome,responsabile;
    private Luogo luoghi[];
}

public class Luogo{
    public Luogo(String codice, String nome, String tipo, String referente, Rischio[] rischi, Dipartimento dipartimento) {
        this.codice = codice;
        this.nome = nome;
        this.tipo = tipo;
        this.referente = referente;
        this.rischi = rischi;
        this.dipartimento = dipartimento;
    }

    private String codice,nome,tipo,referente;
    private Rischio rischi[];
    private Dipartimento dipartimento;
}
