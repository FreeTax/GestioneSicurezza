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
    private Corso corso;
    private Visita visita;
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

    public void saveToDB(int codice) throws SQLException { }

    public abstract String toString();
}

