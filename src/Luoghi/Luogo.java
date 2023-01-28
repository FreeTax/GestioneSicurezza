package Luoghi;

import Account.Utente;
import LuoghiGatewayDb.LuoghiGatewayDB;
import Rischi.Rischio;

import java.sql.SQLException;

public class Luogo{
    //FIX ME: dipartiento and referente should be of type Dipartimento and Utente
    private LuoghiGatewayDB gateway;
    private int codice;
    private String nome;
    private String tipo;
    private int referente;
    private Rischio rischi[];
    private int dipartimento;
    public Luogo(int codice, String nome, String tipo, int referente, Rischio[] rischi, int dipartimento) throws SQLException {
        this.codice = codice;
        this.nome = nome;
        this.tipo = tipo;
        this.referente = referente;
        this.rischi = rischi;
        this.dipartimento = dipartimento;
        gateway= new LuoghiGatewayDB();
    }
    public Luogo(int codice, String nome, String tipo, int referente, int dipartimento) throws SQLException {
        this.codice = codice;
        this.nome = nome;
        this.tipo = tipo;
        this.referente = referente;
        this.dipartimento = dipartimento;
        gateway= new LuoghiGatewayDB();
    }

    public Luogo(int codice) throws SQLException {
        gateway= new LuoghiGatewayDB();
        Luogo l=gateway.getLuogo(codice);
        this.codice = l.getCodice();
        this.nome = l.getNome();
        this.tipo = l.getTipo();
        this.referente = l.getReferente();
        this.dipartimento = l.getDipartimento();
    }

    public Luogo(Luogo l) throws SQLException {
        this.codice = l.getCodice();
        this.nome = l.getNome();
        this.tipo = l.getTipo();
        this.referente = l.getReferente();
        this.rischi = l.getRischi();
        this.dipartimento = l.getDipartimento();
        gateway= new LuoghiGatewayDB();
    }

    public void insertLuogo() throws SQLException{
        gateway.insertLuogo(this);
    }
    public void updateLuogo() throws SQLException{
        gateway.updateLuogo(this);
    }
    public void deleteLuogo() throws SQLException{
        gateway.deleteLuogo(this.codice);
    }

    public int getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getReferente() {
        return referente;
    }

    public Rischio[] getRischi() {
        return rischi;
    }

    public int getDipartimento() {
        return dipartimento;
    }


}
