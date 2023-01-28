package Luoghi;
import Account.Utente;
import LuoghiGatewayDb.LuoghiGatewayDB;
import Rischi.Rischio;

import java.sql.SQLException;
//FIX ME: Responsabile should be of type Utente
public class Dipartimento {
    private int codice;
    private String nome;
    private int responsabile;
    private Luogo luoghi[];
    private Rischio rischi[];
    private LuoghiGatewayDB luoghiGatewayDB;
    public Dipartimento(int codice, String nome, int responsabile, Luogo[] luoghi, Rischio[] rischi) throws SQLException {
        luoghiGatewayDB=new LuoghiGatewayDB();
        this.codice = codice;
        this.nome = nome;
        this.responsabile = responsabile;
        this.luoghi = luoghi;
        this.rischi=rischi;
    }

    public Dipartimento(int id) throws SQLException {
        luoghiGatewayDB=new LuoghiGatewayDB();
        Dipartimento d=luoghiGatewayDB.getDipartimento(id);
        this.codice = d.getCodice();
        this.nome = d.getNome();
        this.responsabile = d.getResponsabile();
        this.luoghi = d.getLuoghi();
        this.rischi=d.getRischi();
    }

    public Dipartimento(int codice, String nome, int responsabile) throws SQLException {
        luoghiGatewayDB=new LuoghiGatewayDB();
        this.codice = codice;
        this.nome = nome;
        this.responsabile = responsabile;
    }

    public int getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public int getResponsabile() {
        return responsabile;
    }

    public Luogo[] getLuoghi() {
        return luoghi;
    }

    public Rischio[] getRischi() {
        return rischi;
    }

    public LuoghiGatewayDB getLuoghiGatewayDB() {
        return luoghiGatewayDB;
    }

    public void insertDipartimento() throws SQLException {
        luoghiGatewayDB.insertDipartimento(this);
    }
}

