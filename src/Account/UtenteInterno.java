package Account;

import Visite.SchedaVisita;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class UtenteInterno extends Utente{
    private int matricola;
    private String tipo;

    public UtenteInterno(int codice, String password,String nome, String cognome, String sesso, String dipartimento, Date dataNascita, int matricola, String tipo/*, SchedaVisita visite*/) throws SQLException {
        super(codice, password, nome, cognome, sesso, dipartimento, dataNascita);
        this.matricola = matricola;
        this.tipo = tipo;
    }

    public UtenteInterno() throws SQLException {
        super();
    }

    public UtenteInterno(int matricola) throws SQLException {
            UtenteInterno ui=uGateway.GetUtenteInterno(matricola);
            this.matricola=ui.matricola;
            this.codice=ui.codice;
            this.password=ui.password;
            this.nome=ui.nome;
            this.cognome=ui.cognome;
            this.sesso=ui.sesso;
            this.dipartimento=ui.dipartimento;
            this.dataNascita=ui.dataNascita;
            this.tipo=ui.tipo;
    }

    public void insertUtente() throws SQLException {
        uGateway.InsertUtenteInterno(matricola,password,nome,cognome,sesso,dataNascita,dipartimento);
    }

    public int getMatricola() {
        return matricola;
    }

    public String getType() {
        return tipo;
    }

    public void setType(String type) {
        this.tipo = tipo;
    }

    public void updateUtenteDb() throws SQLException {
        uGateway.updateUtenteInterno(matricola,nome,cognome,sesso, String.valueOf(dataNascita),dipartimento,tipo);
    }
}
