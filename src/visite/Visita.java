package visite;

public class Visita {
    public Visita(String id, String dottore, String descrizione, String data, String stato, String esito, VisitaType type) {
        this.id = id;
        this.dottore = dottore;
        this.descrizione = descrizione;
        this.data = data;
        this.stato = stato;
        this.esito = esito;
        this.type = type;
    }

    private String id, dottore, descrizione, data, stato, esito;
    private VisitaType type;
}

class VisitaType{
    public VisitaType(String id, String nome, String descrizione, String frequenza) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.frequenza = frequenza;
    }

    private String id,nome,descrizione,frequenza;
}

class SchedaVisita {
    public SchedaVisita(String codice, String codiceUtente, String elencoPatologie, Visita[] visiteEffettuate, Visita[] visiteDaSostentere) {
        this.codice = codice;
        this.codiceUtente = codiceUtente;
        this.elencoPatologie = elencoPatologie;
        this.visiteEffettuate = visiteEffettuate;
        this.visiteDaSostentere = visiteDaSostentere;
    }

    private String codice,codiceUtente,elencoPatologie;
    private Visita visiteEffettuate[], visiteDaSostentere[];
}
