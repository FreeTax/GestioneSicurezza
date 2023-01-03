package Account;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import AccountGateway.UtenteGatewayDb;
import Visite.SchedaVisita;

public abstract class Utente{

    protected int codice;
    protected CreditoFormativo cfuSostenuti;
    protected String nome;
    protected String cognome;
    protected String sesso;
    protected String dipartimento;
    protected Date dataNascita;
    protected SchedaVisita visite;

    protected UtenteGatewayDb uGateway;

    public Utente(int codice, String nome, String cognome, String sesso, String dipartimento, Date dataNascita/*, SchedaVisita visite*/) throws SQLException {
        this.codice = codice;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.dipartimento = dipartimento;
        this.dataNascita = dataNascita;
        this.visite=visite;
        uGateway=new UtenteGatewayDb();
    }

    public Utente() throws SQLException {
        uGateway=new UtenteGatewayDb();
    }

    public void insertUtente() throws SQLException {
        uGateway.InsertUtente(nome,cognome,sesso,dataNascita,dipartimento,"");
    }

    public ArrayList<String> nomeUtenti() throws SQLException {
        return uGateway.SelectSql();
    }
    public CreditoFormativo getCfu_sostenuti() {
        return cfuSostenuti;
    }

    public void setCfu_sostenuti(CreditoFormativo cfu_sostenuti) {
        this.cfuSostenuti = cfuSostenuti;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getDipartimento() {
        return dipartimento;
    }

    public void setDipartimento(String dipartimento) {
        this.dipartimento = dipartimento;
    }

    public Date getDataNascita() {
        return dataNascita;
    }
}


