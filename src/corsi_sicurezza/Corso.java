package corsi_sicurezza;
import rischi.Rischio;
public class Corso {
    public Corso(String codice, String nome, String descrizione, CorsoType type, String inizio, String fine) {
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.type = type;
        this.inizio = inizio;
        this.fine = fine;
    }

    private String codice,nome,descrizione,inizio, fine;
    private CorsoType type;
}

class CorsoType{
    private String id,nome,descrizione;
    private Rischio rischioAssociato;
}

