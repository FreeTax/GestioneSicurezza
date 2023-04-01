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

    public SchedaVisita(int idUtente) throws SQLException {
        this.vGateway=new VisiteGatewayDb();
        SchedaVisita sv= vGateway.getSchedaVisistaFromUserID(idUtente);

        this.idUtente=idUtente;
        if(sv!=null) {
            this.codice = sv.getId();
            this.elencoPatologie = sv.getElencoPatologie();
            this.visiteEffettuate = sv.getVisiteEffettuate();
            this.visiteDaSostentere = sv.getVisiteDaSostentere();
        }
        else { //i dont like to let ScheduleVisita with null values on codice if this is not save on db yet. Talk whit marco
            this.elencoPatologie = new String[0];
            this.visiteEffettuate = new ArrayList<Visita>();
            this.visiteDaSostentere = new ArrayList<Visita>();
        }
    }
    public void syncVisiteEffettuate() throws SQLException {
        this.visiteEffettuate=vGateway.getVisiteEffettuate(codice);
    }

    public void syncVisiteDaSostentere() throws SQLException {
        this.visiteDaSostentere=vGateway.getVisiteDaSostenere(codice);
    }

    public void insertVisitaDaSostentere(Visita visita) throws SQLException {
       visiteDaSostentere.add(visita);
       vGateway.insertVisita(visita.getId(),visita.getDottore(), visita.getDescrizione(), visita.getData(), visita.getStato(), visita.getEsito(),  this.codice, visita.getIdType());
    }

    public void insertVisitaEffettuata(Visita visita) throws SQLException {
        visiteEffettuate.add(visita);
        vGateway.insertVisita(visita.getId(),visita.getDottore(), visita.getDescrizione(), visita.getData(), visita.getStato(), visita.getEsito(), visita.getIdType(), this.codice);
    }

    public void sostieniVisita(int idVisita, String esito) throws SQLException {
        Visita visita=null;
        for (Visita v:visiteDaSostentere) {
            if(v.getId()==idVisita)
                visita=v;
        }
        visita.setEsito(esito);
        visita.setStato("sostenuta");
        visiteEffettuate.add(visita);
        visiteDaSostentere.remove(visita);
        vGateway.updateVisita(visita.getId(), visita.getDescrizione(), visita.getData(), visita.getStato(), visita.getEsito(), visita.getIdType());
    }

    public void setElencoPatologie(String[] elencoPatologie) {
        this.elencoPatologie = elencoPatologie;
    }

    public void setVisiteEffettuate(ArrayList<Visita> visiteEffettuate) {
        this.visiteEffettuate = visiteEffettuate;
    }

    public void setVisiteDaSostentere(ArrayList<Visita> visiteDaSostentere) {
        this.visiteDaSostentere = visiteDaSostentere;
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


    public ArrayList<Visita> getVisiteDaSostentere() {
        return visiteDaSostentere;
    }

    public ArrayList<Visita> getVisiteEffettuate() {
        return visiteEffettuate;
    }

    public void saveNewSchedaVisita() throws SQLException {
        vGateway.InsertSchedaVisite(this.idUtente);
    }

}
