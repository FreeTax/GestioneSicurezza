package GatewayIPC;

import CorsiSicurezza.Corso;
import CorsiSicurezza.CorsoType;
import Rischi.Rischio;

import java.sql.SQLException;
import java.time.LocalDate;

public class GatewayCorsiSicurezza {
    public void addCorsoType(int id, String nome, String descrizione, Rischio rischioAssociato) throws SQLException {
        CorsoType type = new CorsoType(id, nome, descrizione, rischioAssociato);
        type.saveToDB();
    }

    public void addCorso(String nome, String descrizione, int type, LocalDate inizio, LocalDate fine) throws SQLException {
        Corso corso = new Corso(nome, descrizione, type, inizio, fine);
        corso.saveToDB();
    }
}
