package Luoghi;
import Account.Utente;
import LuoghiGatewayDb.LuoghiGatewayDB;
import Rischi.Rischio;

import java.sql.SQLException;
import java.util.ArrayList;

//FIX ME: Responsabile should be of type Utente
public class Dipartimento {
    private int codice;
    private String nome;
    private int responsabile;
    private ArrayList<Integer> luoghi;
    private ArrayList<Integer> rischi;
    private LuoghiGatewayDB luoghiGatewayDB;
    public Dipartimento(int codice, String nome, int responsabile, ArrayList<Integer> luoghi, ArrayList<Integer> rischi) throws SQLException {
        luoghiGatewayDB=new LuoghiGatewayDB();
        this.codice = codice;
        this.nome = nome;
        this.responsabile = responsabile;
        this.luoghi = luoghi;
        this.rischi=rischi;
    }

    public Dipartimento(int codice, String nome, int responsabile) throws SQLException {
        luoghiGatewayDB=new LuoghiGatewayDB();
        this.codice = codice;
        this.nome = nome;
        this.responsabile = responsabile;
    }
    public Dipartimento(int id) throws SQLException {
        luoghiGatewayDB=new LuoghiGatewayDB();
        Dipartimento d=luoghiGatewayDB.getDipartimento(id);
        if(d==null){
            this.codice = d.getCodice();
            this.nome = d.getNome();
            this.responsabile = d.getResponsabile();
            this.luoghi = d.getLuoghi();
            this.rischi=d.getRischi();
        }
        else{
            throw  new SQLException("Dipartimento non trovato");
        }
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

    public ArrayList<Integer> getLuoghi() {
        return luoghi;
    }

    public ArrayList<Integer> getRischi() {
        return rischi;
    }

    public LuoghiGatewayDB getLuoghiGatewayDB() {
        return luoghiGatewayDB;
    }

    public void saveToDB() throws SQLException {
        luoghiGatewayDB.insertDipartimento(this);
    }

    public void updateDipartimento() throws SQLException {
        luoghiGatewayDB.updateDipartimento(this);
    }

    public void deleteDipartimento() throws SQLException {
        luoghiGatewayDB.deleteDipartimento(this.codice);
    }

    public int getId() {
        return codice;
    }



    public void addRischio(Integer r) throws SQLException {
        rischi.add(r);
    }

    public void setLuoghi(ArrayList<Integer> luoghi) {
        this.luoghi = luoghi;
    }

    public void setRischi(ArrayList<Integer> rischi) {
        this.rischi = rischi;
    }
}

