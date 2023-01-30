package Account;

import Visite.SchedaVisita;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class UtenteEsterno extends Utente{
    private int idEsterno;

    public UtenteEsterno(int codice, String password, String nome, String cognome, String sesso, String dipartimento, Date dataNascita, int idEsterno/*, SchedaVisita visite*/) throws SQLException {
        super(codice, password, nome, cognome, sesso, dipartimento, dataNascita/*,visite*/);
        this.idEsterno = idEsterno;
    }

    public UtenteEsterno(int idesterno) throws SQLException {
       UtenteEsterno ue=uGateway.GetUtenteEsterno(idesterno);
       this.idEsterno=ue.idEsterno;
       this.codice=ue.codice;
       this.password=ue.password;
       this.nome=ue.nome;
       this.cognome=ue.cognome;
       this.sesso=ue.sesso;
       this.dipartimento=ue.dipartimento;
       this.dataNascita=ue.dataNascita;
    }
    public void insertUtente() throws SQLException {
        uGateway.InsertUtenteEsterno(idEsterno,password,nome,cognome,sesso,dataNascita,dipartimento);
    }
    public int getIdEsterno() {
        return idEsterno;
    }

    public void updateUtenteDb() throws SQLException {
        uGateway.updateUtenteEsterno(idEsterno,nome,cognome,sesso, String.valueOf(dataNascita),dipartimento);
    }
}
