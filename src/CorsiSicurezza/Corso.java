package CorsiSicurezza;
import Rischi.Rischio;

import java.time.LocalDate;

public class Corso {
    public Corso(String codice, String nome, String descrizione, CorsoType type, LocalDate inizio, LocalDate fine) {
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.type = type;
        this.inizio = inizio;
        this.fine = fine;
    }

    private String codice,nome,descrizione;
    private LocalDate inizio, fine;
    private CorsoType type;
}

class CorsoType{
    private String id,nome,descrizione;
    private Rischio rischioAssociato;
}

