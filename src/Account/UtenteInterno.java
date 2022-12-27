package Account;

import Visite.SchedaVisita;

import java.sql.SQLException;
import java.time.LocalDate;

public class UtenteInterno extends Utente{
    private int matricola;
    private int type;

    public UtenteInterno(int codice, CreditoFormativo cfu_sostenuti, String nome, String cognome, String sesso, String dipartimento, LocalDate dataNascita, int matricola, int type/*, SchedaVisita visite*/) throws SQLException {
        super(codice, cfu_sostenuti, nome, cognome, sesso, dipartimento, dataNascita/*, visite*/);
        this.matricola = matricola;
        this.type = type;
    }

    public UtenteInterno() throws SQLException {
        super();
    }

    public void insertUtente() throws SQLException {
        uGateway.InsertSql(matricola,nome,cognome);
    }
    public int getMatricola() {
        return matricola;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
