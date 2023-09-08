package CorsiSicurezza;

import Rischi.Rischio;

import CorsiSicurezzaGateway.CorsiSicurezzaGatewayDb;

import java.sql.SQLException;

public class CorsoType {
    private final int id;
    private final String nome;
    private final String descrizione;
    private final int idRischioAssociato; //problema, penso vada fatta una nuova tabella che associa Rischi a CorsoType. Discuterne con Marco
    private final CorsiSicurezzaGatewayDb db;

    public CorsoType(int id, String nome, String descrizione, int rischioAssociato) throws SQLException {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.idRischioAssociato = rischioAssociato;
        db=new CorsiSicurezzaGatewayDb();
    }

    public void saveToDB() throws SQLException {
        db.InsertCorsoType(nome, descrizione, idRischioAssociato);
    }
}
