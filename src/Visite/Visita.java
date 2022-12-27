package Visite;

import java.time.LocalDate;

public class Visita {
    private String id;
    private String dottore;
    private String descrizione;
    private int stato;
    private int esito;
    private LocalDate data;
    private VisitaType type;
    public Visita(String id, String dottore, String descrizione, LocalDate data, int stato, int esito, VisitaType type) {
        this.id = id;
        this.dottore = dottore;
        this.descrizione = descrizione;
        this.data = data;
        this.stato = stato;
        this.esito = esito;
        this.type = type;
    }
}

