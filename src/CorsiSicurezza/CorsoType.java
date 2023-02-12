package CorsiSicurezza;

import Rischi.Rischio;

import CorsiSicurezzaGateway.CorsiSicurezzaGatewayDb;

import java.sql.SQLException;

public class CorsoType {
    private int id;
    private String nome;
    private String descrizione;
    private Rischio rischioAssociato; //problema, penso vada fatta una nuova tabella che associa Rischi a CorsoType. Discuterne con Marco
    private CorsiSicurezzaGatewayDb db;

    public CorsoType(int id, String nome, String descrizione, Rischio rischioAssociato) throws SQLException {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.rischioAssociato = rischioAssociato;
        db=new CorsiSicurezzaGatewayDb();
    }

    public void saveToDB() throws SQLException {
        db.InsertCorsoType(nome, descrizione);
    }
}
