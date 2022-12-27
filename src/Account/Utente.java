package Account;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import AccountGateway.UtenteGatewayDb;
import Rischi.Rischio;
import Visite.SchedaVisita;
import Luoghi.Luogo;
import Luoghi.Dipartimento;
public abstract class Utente{

    protected int codice;
    protected CreditoFormativo cfuSostenuti;
    protected String nome;
    protected String cognome;
    protected String sesso;
    protected String dipartimento;
    protected LocalDate dataNascita;
    protected SchedaVisita visite;

    protected UtenteGatewayDb uGateway;

    public Utente(int codice, CreditoFormativo cfuSstenuti, String nome, String cognome, String sesso, String dipartimento, LocalDate dataNascita/*, SchedaVisita visite*/) throws SQLException {
        this.codice = codice;
        this.cfuSostenuti = cfuSostenuti;
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
        uGateway.InsertSql(0,nome,cognome);
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

    public LocalDate getDataNascita() {
        return dataNascita;
    }
}


