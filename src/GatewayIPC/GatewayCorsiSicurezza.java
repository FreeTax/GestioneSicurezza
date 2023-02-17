package GatewayIPC;

import CorsiSicurezza.Corso;
import CorsiSicurezza.CorsoType;
import Rischi.Rischio;

import java.sql.SQLException;
import java.time.LocalDate;

public class GatewayCorsiSicurezza {
    public void addCorsoType(int id, String nome, String descrizione, Rischio rischioAssociato) throws SQLException {
        try {
            CorsoType type = new CorsoType(id, nome, descrizione, rischioAssociato);
            type.saveToDB();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }

    public void addCorso(String nome, String descrizione, int type, LocalDate inizio, LocalDate fine) throws SQLException {
        try{
            Corso corso = new Corso(nome, descrizione, type, inizio, fine);
            corso.saveToDB();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
