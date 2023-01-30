package Account;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import AccountGateway.UtenteGatewayDb;
import Visite.SchedaVisita;

public abstract class Utente{

    protected int codice;
    protected ArrayList<CreditoFormativo> cfuSostenuti;
    protected String password;
    protected String nome;
    protected String cognome;
    protected String sesso;
    protected String dipartimento;
    protected Date dataNascita;
    protected SchedaVisita visite;

    protected UtenteGatewayDb uGateway;

    public Utente(int codice, String password, String nome, String cognome, String sesso, String dipartimento, Date dataNascita/*, SchedaVisita visite*/) throws SQLException {
        this.codice = codice;
        this.password = password;
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
        uGateway.InsertUtente(password,nome,cognome,sesso,dataNascita,dipartimento,"");
    }

    public ArrayList<CreditoFormativo> getCfu_sostenuti() {
        return cfuSostenuti;
    }

    public void setCfu_sostenuti(CreditoFormativo cfu_sostenuti) {
        this.cfuSostenuti = cfuSostenuti;
    }

    public void setCfuSostenuti() throws SQLException {
        this.cfuSostenuti.addAll(uGateway.GetCFUSostenuti(this.codice));
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

    public int getCodice() {
        return codice;
    }
}


