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
    private ArrayList<Visita> visiteDaSostenere;

    private VisiteGatewayDb vGateway;
    public SchedaVisita(int codice, int idUtente, String [] elencoPatologie, ArrayList<Visita> visiteEffettuate, ArrayList<Visita> visiteDaSostentere) throws SQLException {
        this.codice = codice;
        this.idUtente = idUtente;
        this.elencoPatologie = elencoPatologie;
        this.visiteEffettuate = visiteEffettuate;
        this.visiteDaSostenere = visiteDaSostentere;
        this.vGateway=new VisiteGatewayDb();
    }

    public SchedaVisita(int idUtente) throws SQLException {
        this.vGateway=new VisiteGatewayDb();
        SchedaVisita sv= vGateway.getSchedaVisistaFromUserID(idUtente);

        this.idUtente=idUtente;
        if(sv!=null) {
            this.codice = sv.getId();
            this.elencoPatologie = sv.getElencoPatologie();
            this.visiteEffettuate = sv.getVisiteEffettuate();
            this.visiteDaSostenere = sv.getVisiteDaSostenere();
        }
        else { //i dont like to let ScheduleVisita with null values on codice if this is not save on db yet. Talk whit marco
            this.elencoPatologie = new String[0];
            this.visiteEffettuate = new ArrayList<Visita>();
            this.visiteDaSostenere = new ArrayList<Visita>();
        }
    }
    public void syncVisiteEffettuate() throws SQLException {
        this.visiteEffettuate=vGateway.getVisiteEffettuate(codice);
    }

    public void syncVisiteDaSostentere() throws SQLException {
        this.visiteDaSostenere=vGateway.getVisiteDaSostenere(codice);
    }

    public void insertVisitaDaSostentere(Visita visita) throws SQLException {
       visiteDaSostenere.add(visita);
       vGateway.insertVisita(visita.getId(),visita.getDottore(), visita.getDescrizione(), visita.getData(), visita.getStato(), visita.getEsito(),  this.codice, visita.getIdType());
    }

    public void insertVisitaEffettuata(Visita visita) throws SQLException {
        visiteEffettuate.add(visita);
        vGateway.insertVisita(visita.getId(),visita.getDottore(), visita.getDescrizione(), visita.getData(), visita.getStato(), visita.getEsito(), visita.getIdType(), this.codice);
    }

    public void sostieniVisita(Integer idVisita, String esito) throws SQLException {
        Visita visita=null;
        for (Visita v:visiteDaSostenere) {
            if(v.getId()==idVisita)
                visita=v;
        }
        visita.setEsito(esito);
        visita.setStato("sostenuta");
        visiteEffettuate.add(visita);
        visiteDaSostenere.remove(visita);
        vGateway.updateVisita(visita.getId(), visita.getDescrizione(), visita.getData(), visita.getStato(), visita.getEsito(), visita.getIdType());
    }

    public void setElencoPatologie(String[] elencoPatologie) {
        this.elencoPatologie = elencoPatologie;
    }

    public void setVisiteEffettuate(ArrayList<Visita> visiteEffettuate) {
        this.visiteEffettuate = visiteEffettuate;
    }

    public void setVisiteDaSostentere(ArrayList<Visita> visiteDaSostentere) {
        this.visiteDaSostenere = visiteDaSostentere;
    }

    public int getId() {
        return codice;
    }

    public String[] getElencoPatologie() {
        return elencoPatologie;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public ArrayList<Visita> getVisiteDaSostenere() {
        return visiteDaSostenere;
    }

    public ArrayList<Visita> getVisiteEffettuate() {
        return visiteEffettuate;
    }

    public void saveNewSchedaVisita() throws SQLException {
        vGateway.InsertSchedaVisite(this.idUtente);
    }

}
