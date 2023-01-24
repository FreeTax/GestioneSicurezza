package Visite;


import Account.Utente;
import VisiteGateway.VisiteGatewayDb;

import java.sql.SQLException;
import java.util.ArrayList;

public class SchedaVisita {
    private int codice;
    private String elencoPatologie [];
    private int idUtente;
    private ArrayList<Visita> visiteEffettuate;
    private ArrayList<Visita> visiteDaSostentere;

    private VisiteGatewayDb vGateway;
    public SchedaVisita(int codice, int idUtente, String [] elencoPatologie, ArrayList<Visita> visiteEffettuate, ArrayList<Visita> visiteDaSostentere) throws SQLException {
        this.codice = codice;
        this.idUtente = idUtente;
        this.elencoPatologie = elencoPatologie;
        this.visiteEffettuate = visiteEffettuate;
        this.visiteDaSostentere = visiteDaSostentere;
        this.vGateway=new VisiteGatewayDb();
    }

    public void syncVisiteEffettuate() throws SQLException {
        this.visiteEffettuate=vGateway.getVisiteEffettuate(codice);
    }

    public void syncVisiteDaSostentere() throws SQLException {
        this.visiteDaSostentere=vGateway.getVisiteDaSostenere(codice);
    }

    public void insertVisitaDaSostentere(Visita visita) throws SQLException {
       visiteDaSostentere.add(visita);
       vGateway.insertVisita(visita.getDottore(), visita.getDescrizione(), visita.getData(), visita.getStato(), visita.getEsito(), visita.getIdType(), this.codice);
    }

    public void insertVisitaEffettuata(Visita visita) throws SQLException {
        visiteEffettuate.add(visita);
        vGateway.insertVisita(visita.getDottore(), visita.getDescrizione(), visita.getData(), visita.getStato(), visita.getEsito(), visita.getIdType(), this.codice);
    }

    public void sostieniVisita(Visita visita) throws SQLException {
        visiteEffettuate.add(visita);
        visiteDaSostentere.remove(visita);
        vGateway.updateVisita(visita.getId(), visita.getDescrizione(), visita.getData(), visita.getStato(), visita.getEsito(), visita.getIdType());
    }
}
