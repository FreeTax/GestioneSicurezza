package Account;

import AccountGateway.UtenteGatewayDb;
import Visite.SchedaVisita;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Utente {

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

    public Utente(int codice, String password, String nome, String cognome, String sesso, String dipartimento, Date dataNascita) throws SQLException {
        this.codice = codice;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.dipartimento = dipartimento;
        this.dataNascita = dataNascita;
        this.visite = visite;
        uGateway = new UtenteGatewayDb();
    }

    public Utente() throws SQLException {
        uGateway = new UtenteGatewayDb();
    }

    public abstract String getType();

    public SchedaVisita getVisite() {
        return visite;
    }

    public void insertUtente() throws SQLException {
    }

    public ArrayList<CreditoFormativo> getCfuSostenuti() {
        return cfuSostenuti;
    }

    public ArrayList<CreditoFormativo> getCfuSostenuti(Integer idUtente) throws SQLException {
        return uGateway.GetCFUSostenuti(idUtente);
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

    public void sostieniCredito(Integer idUtente, Integer idCredito, String certificazione) throws SQLException {
        uGateway.sostieniCreditoFormativo(idUtente, idCredito, certificazione);
    }

    public ArrayList<String> getUtenti() throws SQLException {
        return uGateway.getUtenti();
    }

}


