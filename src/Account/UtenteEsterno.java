package Account;

import Visite.SchedaVisita;

import java.sql.SQLException;
import java.time.LocalDate;

public class UtenteEsterno extends Utente{
    private int idEsterno;

    public UtenteEsterno(int codice, CreditoFormativo cfu_sostenuti, String nome, String cognome, String sesso, String dipartimento, LocalDate dataNascita, int idEsterno/*, SchedaVisita visite*/) throws SQLException {
        super(codice, cfu_sostenuti, nome, cognome, sesso, dipartimento, dataNascita/*,visite*/);
        this.idEsterno = idEsterno;
    }

    public int getIdEsterno() {
        return idEsterno;
    }
}
