package Rischi;
import CorsiSicurezza.Corso;
import Visite.Visita;
public abstract class Rischio {
    public Rischio(String codice, String nome, String descrizione, String tipologia, Corso corso, Visita visita) {
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.tipologia = tipologia;
        this.corso = corso;
        this.visita = visita;
    }

    private String codice,nome,descrizione,tipologia;
    private Corso corso;
    private Visita visita;
}

class RischioSpecifico extends Rischio{
    public RischioSpecifico(String codice, String nome, String descrizione, String tipologia, Corso corso, Visita visita) {
        super(codice, nome, descrizione, tipologia, corso, visita);
    }
}

class RischioGenerico extends Rischio{
    public RischioGenerico(String codice, String nome, String descrizione, String tipologia, Corso corso, Visita visita) {
        super(codice, nome, descrizione, tipologia, corso, visita);
    }
}
