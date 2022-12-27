package Rischi;
import CorsiSicurezza.Corso;
import Visite.Visita;
public abstract class Rischio {
    private String codice;
    private String nome;
    private String descrizione;
    private String tipologia;
    private Corso corso;
    private Visita visita;
    public Rischio(String codice, String nome, String descrizione, String tipologia, Corso corso, Visita visita) {
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.tipologia = tipologia;
        this.corso = corso;
        this.visita = visita;
    }
}

