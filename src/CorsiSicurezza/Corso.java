package CorsiSicurezza;
import CorsiSicurezzaGateway.CorsiSicurezzaGatewayDb;
import Rischi.Rischio;

import java.sql.SQLException;
import java.time.LocalDate;

public class Corso {
    private int codice;
    private final String nome;
    private final String descrizione;
    private final LocalDate inizio;
    private final LocalDate fine;
    private final int type;

    private final CorsiSicurezzaGatewayDb db;
    public Corso(int codice, String nome, String descrizione, int type, LocalDate inizio, LocalDate fine) throws SQLException {
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.type = type;
        this.inizio = inizio;
        this.fine = fine;
        db=new CorsiSicurezzaGatewayDb();
    }

    public Corso(String nome, String descrizione, int type, LocalDate inizio, LocalDate fine) throws SQLException {
        this.nome = nome;
        this.descrizione = descrizione;
        this.type = type;
        this.inizio = inizio;
        this.fine = fine;
        db=new CorsiSicurezzaGatewayDb();
    }

    public void saveToDB() throws SQLException {
        db.InsertCorso(nome, descrizione, type, inizio, fine);
    }
}

