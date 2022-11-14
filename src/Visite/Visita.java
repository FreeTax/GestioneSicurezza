package Visite;

import java.time.LocalDate;

public class Visita {
    public Visita(String id, String dottore, String descrizione, LocalDate data, int stato, int esito, VisitaType type) {
        this.id = id;
        this.dottore = dottore;
        this.descrizione = descrizione;
        this.data = data;
        this.stato = stato;
        this.esito = esito;
        this.type = type;
    }

    private String id, dottore, descrizione;
    private int stato, esito;
    private LocalDate data;
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