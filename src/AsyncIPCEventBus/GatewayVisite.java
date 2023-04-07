package AsyncIPCEventBus;

import AsyncIPCEventBus.PublishSubscribe.*;
import Visite.SchedaVisita;
import Visite.Visita;
import Visite.VisitaType;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GatewayVisite {
    private EventBusService eventBusService;
    private Subscriber sub;
    private Publisher pub;
    public GatewayVisite(EventBusService service) {
        eventBusService = service;
        sub = new SubscriberConcr("Visite", service );
        pub = new PublisherConcr();
    }

    public GatewayVisite() {
    }
    public void addVisitaType(int id,String nome, String descrizione, String frequenza, int rischioAssociato) throws SQLException { //FIXME: gestire inserimento: passo dal package visite o vado direttamente al gatewayFB? inserisco anche l'id del tipo?
        VisitaType vT=new VisitaType( id,nome, descrizione, frequenza, rischioAssociato);
        vT.saveToDb();
    }

    public void addVisitaUtente(int idUtente, int codiceVisita, String dottore, String descrizione, Timestamp data, String stato, int idType) throws SQLException {
        SchedaVisita sv = new SchedaVisita(idUtente);
        Visita v = new Visita(codiceVisita, dottore, descrizione, data, stato, "",sv.getId(), idType);
        sv.insertVisitaDaSostentere(v);
    }

    public void addSchedaVisita(int idUtente) throws SQLException {
        SchedaVisita sv = new SchedaVisita(idUtente);
        sv.saveNewSchedaVisita();
    }

    public ArrayList<Visita> getVisiteSostenute(int idUtente) throws SQLException {
        SchedaVisita sv = new SchedaVisita(idUtente);
        return sv.getVisiteEffettuate();
    }

    public ArrayList<Visita> getVisiteDaSostentere(int idUtente) throws SQLException {
        SchedaVisita sv = new SchedaVisita(idUtente);
        return sv.getVisiteDaSostentere();
    }

    public void addVisita(int id, String dottore, String descrizione, Timestamp data, String stato, String esito, int schedavisite, int idType) throws SQLException {
        Visita v = new Visita(id, dottore, descrizione, data, stato, esito, schedavisite,idType);
        v.saveToDB();
    }

    public void sostieniVisita(int idVisita, String esito, int idUtente) throws SQLException {
        SchedaVisita sv = new SchedaVisita(idUtente);
        List<Object> parameters = Arrays.asList(idVisita, esito);
        pub.publish(new Message("Visite", "sostieniVisita", sv, parameters), eventBusService);
        System.out.println("Utente"+idUtente+"ha sostenuto la visita");
        //sv.sostieniVisita(idVisita, esito);
    }
}
