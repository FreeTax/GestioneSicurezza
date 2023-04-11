package CorsiSicurezza;

import CorsiSicurezzaGateway.CorsiSicurezzaGatewayDb;

import java.sql.SQLException;
import java.time.LocalDate;

public class Corso {
    private int codice;
    private String nome;
    private String descrizione;
    private LocalDate inizio;
    private LocalDate fine;
    private int type;

    private CorsiSicurezzaGatewayDb db;

    public Corso(int codice, String nome, String descrizione, int type, LocalDate inizio, LocalDate fine) throws SQLException {
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.type = type;
        this.inizio = inizio;
        this.fine = fine;
        db = new CorsiSicurezzaGatewayDb();
    }

    public Corso(String nome, String descrizione, int type, LocalDate inizio, LocalDate fine) throws SQLException {
        this.nome = nome;
        this.descrizione = descrizione;
        this.type = type;
        this.inizio = inizio;
        this.fine = fine;
        db = new CorsiSicurezzaGatewayDb();
    }

    public void saveToDB() throws SQLException {
        db.InsertCorso(nome, descrizione, type, inizio, fine);
    }
}

