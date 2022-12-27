package CorsiSicurezza;
import Rischi.Rischio;

import java.time.LocalDate;

public class Corso {
    private String codice;
    private String nome;
    private String descrizione;
    private LocalDate inizio;
    private LocalDate fine;
    private CorsoType type;
    public Corso(String codice, String nome, String descrizione, CorsoType type, LocalDate inizio, LocalDate fine) {
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.type = type;
        this.inizio = inizio;
        this.fine = fine;
    }
}

