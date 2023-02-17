package GatewayIPC;
import Visite.Visita;
import Visite.SchedaVisita;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class GatewayVisite {
    public void aggiungiVisistaUtente(int idUtente, int codiceVisita, String dottore, String descrizione, Timestamp data, String stato, int idType) throws SQLException {
        try{
            SchedaVisita sv= new SchedaVisita(idUtente);
            Visita v=new Visita(codiceVisita, dottore, descrizione, data, stato, null, idType);
            sv.insertVisitaDaSostentere(v);
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void aggiungiNuovaSchedaVisita(int idUtente) throws SQLException {
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
