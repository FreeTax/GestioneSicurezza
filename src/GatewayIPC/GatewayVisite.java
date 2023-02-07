package GatewayIPC;
import Visite.Visita;
import Visite.SchedaVisita;

import java.sql.SQLException;
import java.sql.Timestamp;

public class GatewayVisite {
    public void aggiungiVisistaUtente(int idUtente, int codiceVisita, String dottore, String Descrizione, Timestamp data, String stato, int idType) throws SQLException {
        SchedaVisita sv= new SchedaVisita(idUtente);
        Visita v=new Visita(codiceVisita, dottore, Descrizione, data, stato, null, idType);
        sv.insertVisitaDaSostentere(v);

    }

    public void aggiungiNuovaSchedaVisita(int idUtente) throws SQLException {
        SchedaVisita sv= new SchedaVisita(idUtente);
        sv.saveNewSchedaVisita();
    }
}
