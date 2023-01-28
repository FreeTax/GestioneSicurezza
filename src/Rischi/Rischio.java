package Rischi;
import CorsiSicurezza.Corso;
import RischiGatewayDb.RischioGatewayDb;
import Visite.Visita;

import java.sql.SQLException;

public abstract class Rischio {
    private int codice;
    private String nome;
    private String descrizione;
    private String tipologia;
    private Corso corso=null;
    private Visita visita=null;
    public Rischio() throws SQLException { //wating that Corso and Visita classes are implemented

    }
    public Rischio(int codice, String nome, String descrizione, String tipologia /*, Corso corso, Visita visita*/) throws SQLException { //wating that Corso and Visita classes are implemented
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.tipologia = tipologia;
/*        this.corso = corso;
        this.visita = visita;*/
    }

    public int getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getTipologia() {
        return tipologia;
    }

    public Corso getCorso() {
        return corso;
    }

    public Visita getVisita() {
        return visita;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }

    public void saveToDB() throws SQLException { }

    public abstract String toString();
    public abstract Boolean removeRischio() throws SQLException;
}

