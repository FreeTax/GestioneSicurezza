package GatewayIPC;

import CorsiSicurezza.Corso;
import CorsiSicurezza.CorsoType;
import Rischi.Rischio;

import java.sql.SQLException;
import java.time.LocalDate;

public class GatewayCorsiSicurezza {
    public void addCorsoType(int id, String nome, String descrizione, int rischioAssociato, int authorizerUser) throws SQLException {
            if(!GatewayUtente.checkAvanzato(authorizerUser)) throw new RuntimeException("l'utente che tenta di inserire il corsotype non è avanzato");
            else {
                CorsoType type = new CorsoType(id, nome, descrizione, rischioAssociato);
                type.saveToDB();
            }

    }

    public void addCorso(String nome, String descrizione, int type, LocalDate inizio, LocalDate fine, int authorizerUser) throws SQLException {
            if(!GatewayUtente.checkAvanzato(authorizerUser)) throw new RuntimeException("l'utente che tenta di inserire il corso non è avanzato");
            else {
                Corso corso = new Corso(nome, descrizione, type, inizio, fine);
                corso.saveToDB();
            }
    }
}
