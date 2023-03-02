package GatewayIPC;

import CorsiSicurezza.CorsoType;
import Visite.Visita;
import Visite.SchedaVisita;
import Visite.VisitaType;
import VisiteGateway.VisiteGatewayDb;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class GatewayVisite {
    public void addVisitaType(int id,String nome, String descrizione, String frequenza) throws SQLException { //FIXME: gestire inserimento: passo dal package visite o vado direttamente al gatewayFB? inserisco anche l'id del tipo?
        VisitaType vT=new VisitaType( id,nome, descrizione, frequenza);
        vT.saveToDb();
    }

    public void addVisitaUtente(int idUtente, int codiceVisita, String dottore, String descrizione, Timestamp data, String stato, int idType) throws SQLException {
        SchedaVisita sv = new SchedaVisita(idUtente);
        Visita v = new Visita(codiceVisita, dottore, descrizione, data, stato, null, idType);
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
}
