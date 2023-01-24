package Account;

import Visite.SchedaVisita;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class UtenteEsterno extends Utente{
    private int idEsterno;

    public UtenteEsterno(int codice, String nome, String cognome, String sesso, String dipartimento, Date dataNascita, int idEsterno/*, SchedaVisita visite*/) throws SQLException {
        super(codice, nome, cognome, sesso, dipartimento, dataNascita/*,visite*/);
        this.idEsterno = idEsterno;
    }

    public UtenteEsterno(int idesterno) throws SQLException {
       ArrayList<String> resultset=uGateway.GetUtenteEsterno(idesterno);
       codice=Integer.parseInt(resultset.get(0));
       nome=resultset.get(1);
       cognome=resultset.get(2);
       sesso=resultset.get(3);
       dataNascita=Date.valueOf(resultset.get(4));
       dipartimento=resultset.get(5);
       this.idEsterno = idesterno;
    }
    public void insertUtente() throws SQLException {
        uGateway.InsertUtenteEsterno(idEsterno,nome,cognome,sesso,dataNascita,dipartimento);
    }
    public int getIdEsterno() {
        return idEsterno;
    }

    public void updateUtenteDb() throws SQLException {
        uGateway.updateUtenteEsterno(idEsterno,nome,cognome,sesso, String.valueOf(dataNascita),dipartimento);
    }
}
