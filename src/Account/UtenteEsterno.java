package Account;

import Visite.SchedaVisita;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class UtenteEsterno extends Utente{
    private int idEsterno;

    public UtenteEsterno(int codice, String nome, String cognome, String sesso, String dipartimento, Date dataNascita, int idEsterno/*, SchedaVisita visite*/) throws SQLException {
        super(codice, nome, cognome, sesso, dipartimento, dataNascita/*,visite*/);
        this.idEsterno = idEsterno;
    }
    public void insertUtente() throws SQLException {
        uGateway.InsertUtenteEsterno(idEsterno,nome,cognome,sesso,dataNascita,dipartimento);
    }

    public int getIdEsterno() {
        return idEsterno;
    }
}
