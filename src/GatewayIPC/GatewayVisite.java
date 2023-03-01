package GatewayIPC;
import CorsiSicurezza.CorsoType;
import Visite.Visita;
import Visite.SchedaVisita;
import VisiteGateway.VisiteGatewayDb;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class GatewayVisite {
    public void addVisitaType(String nome, String descrizione, String frequenza) throws SQLException { //FIXME: gestire inserimento: passo dal package visite o vado direttamente al gatewayFB? inserisco anche l'id del tipo?
        try{
            VisiteGatewayDb gV=new VisiteGatewayDb();
            gV.addVisitaType(nome, descrizione, frequenza);
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public void addVisitaUtente(int idUtente, int codiceVisita, String dottore, String descrizione, Timestamp data, String stato, int idType) throws SQLException {
        try{
            SchedaVisita sv= new SchedaVisita(idUtente);
            Visita v=new Visita(codiceVisita, dottore, descrizione, data, stato, null, idType);
            sv.insertVisitaDaSostentere(v);
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void addSchedaVisita(int idUtente) throws SQLException {
        try{
            SchedaVisita sv= new SchedaVisita(idUtente);
            sv.saveNewSchedaVisita();
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public ArrayList<Visita> getVisiteSostenute(int idUtente) throws SQLException {
        try{
            SchedaVisita sv= new SchedaVisita(idUtente);
            return sv.getVisiteEffettuate();
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }
}
