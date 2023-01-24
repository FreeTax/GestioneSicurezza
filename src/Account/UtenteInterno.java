package Account;

import Visite.SchedaVisita;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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

    public UtenteInterno(int matricola) throws SQLException {
       ArrayList<String> resultset=uGateway.GetUtenteInterno(matricola);
       codice=Integer.parseInt(resultset.get(0));
       nome=resultset.get(1);
       cognome=resultset.get(2);
       sesso=resultset.get(3);
       dataNascita=Date.valueOf(resultset.get(4));
       dipartimento=resultset.get(5);
       this.matricola = matricola;
       this.type=resultset.get(6);
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

    public void updateUtenteDb() throws SQLException {
        uGateway.updateUtenteInterno(matricola,nome,cognome,sesso, String.valueOf(dataNascita),dipartimento,type);
    }
}
