package Visite;

import VisiteGateway.VisiteGatewayDb;

import java.sql.SQLException;

public class VisitaType {
    private int id;
    private String nome;
    private String descrizione;
    private String frequenza;

    public VisitaType(int id, String nome, String descrizione, String frequenza) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.frequenza = frequenza;
    }

    public VisitaType(int id) throws SQLException {
        VisiteGatewayDb gV = new VisiteGatewayDb();
        VisitaType v = gV.getVisitaType(id);
        if(v!=null) {
            this.id = v.getId();
            this.nome = v.getNome();
            this.descrizione = v.getDescrizione();
            this.frequenza = v.getFrequenza();
        }
        else{
            throw new SQLException("VisitaType non trovata");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getFrequenza() {
        return frequenza;
    }

    public void setFrequenza(String frequenza) {
        this.frequenza = frequenza;
    }

    public void saveToDb() throws SQLException {
        VisiteGatewayDb gV = new VisiteGatewayDb();
        gV.addVisitaType(id,nome, descrizione, frequenza);
    }
}
