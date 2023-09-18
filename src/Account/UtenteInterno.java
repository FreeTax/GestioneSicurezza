package Account;

import Delay.Delay;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteInterno extends Utente {
    private int matricola;
    private String tipo;

    public UtenteInterno(int codice, String password, String nome, String cognome, String sesso, String dipartimento, Date dataNascita, int matricola, String tipo) throws SQLException {
        super(codice, password, nome, cognome, sesso, dipartimento, dataNascita);
        this.matricola = matricola;
        this.tipo = tipo;
    }

    public UtenteInterno() throws SQLException {
        super();
    }

    public UtenteInterno(int matricola) throws SQLException {
        UtenteInterno ui = uGateway.GetUtenteInterno(matricola);
        if (ui != null) {
            this.matricola = ui.matricola;
            this.codice = ui.codice;
            this.password = ui.password;
            this.nome = ui.nome;
            this.cognome = ui.cognome;
            this.sesso = ui.sesso;
            this.dipartimento = ui.dipartimento;
            this.dataNascita = ui.dataNascita;
            this.tipo = ui.tipo;
            this.cfuSostenuti = uGateway.GetCFUSostenuti(this.codice);
        } else {
            new UtenteInterno();
        }
    }

    public void insertUtente() throws SQLException {
        uGateway.InsertUtenteInterno(matricola, password, nome, cognome, sesso, dataNascita, dipartimento, tipo);
    }

    public int getMatricola() {
        return matricola;
    }

    @Override
    public String getType() {
        return tipo;
    }

    public void setType(String type) {
        this.tipo = tipo;
    }

    public void updateUtenteDb() throws SQLException {
        uGateway.updateUtenteInterno(matricola, password, nome, cognome, sesso, String.valueOf(dataNascita), dipartimento, tipo);
    }

    public ArrayList<CreditoFormativo> getCfuSostenuti(Integer idUtente) throws SQLException {
        Delay.delayWithProbability("UtenteInterno");
        return super.getCfuSostenuti(idUtente);
    }


    public boolean loginInterno(int matricola, String password) throws SQLException {
        return uGateway.loginInterno(matricola, password);
    }

    public ArrayList<RichiestaLuogo> getRichiesteLuogoUtenti() throws SQLException {
        return uGateway.GetRichiesteLuogo();
    }

    public ArrayList<RichiestaLuogo> getRichiesteLuogo() throws SQLException {
        int idUtente = uGateway.getIdUtente(this.matricola, true);
        return uGateway.getRichiesteLuogo(idUtente);
    }

    public ArrayList<RichiestaDipartimento> getRichiesteDipartimentoUtenti() throws SQLException {
        return uGateway.GetRichiesteDipartimento();
    }

    public ArrayList<RichiestaDipartimento> getRichiesteDipartimento() throws SQLException {
        int idUtente = uGateway.getIdUtente(this.matricola, true);
        return uGateway.getRichiesteDipartimento(idUtente);
    }

}
