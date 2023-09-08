package Visite;

import VisiteGateway.VisiteGatewayDb;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Visita {
    private int id;
    private String dottore;
    private String descrizione;
    private String stato;
    private String esito;
    private Timestamp data;
    private int idType;

    private final int schedaVisita;
    public Visita(int id, String dottore, String descrizione, Timestamp data, String stato, String esito, int idType, int schedaVisita) {
        this.id = id;
        this.dottore = dottore;
        this.descrizione = descrizione;
        this.data = data;
        this.stato = stato;
        this.esito = esito;
        this.idType = idType;
        this.schedaVisita = schedaVisita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDottore() {
        return dottore;
    }

    public void setDottore(String dottore) {
        this.dottore = dottore;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public void saveToDB() throws SQLException {
        VisiteGatewayDb vG = new VisiteGatewayDb();
        vG.insertVisita(id, dottore, descrizione, data, stato, esito,schedaVisita, idType);
    }


}

