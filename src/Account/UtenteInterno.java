package Account;

import Visite.SchedaVisita;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class UtenteInterno extends Utente{
    private int matricola;
    private String type;

    public UtenteInterno(int codice, String nome, String cognome, String sesso, String dipartimento, Date dataNascita, int matricola, String type/*, SchedaVisita visite*/) throws SQLException {
        super(codice, nome, cognome, sesso, dipartimento, dataNascita/*, visite*/);
        this.matricola = matricola;
        this.type = type;
    }

    public UtenteInterno() throws SQLException {
        super();
    }

    public void insertUtente() throws SQLException {
        uGateway.InsertUtenteInterno(matricola,nome,cognome,sesso,dataNascita,dipartimento);
    }

    public int getMatricola() {
        return matricola;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
