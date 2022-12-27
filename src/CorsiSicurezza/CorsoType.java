package CorsiSicurezza;

import Rischi.Rischio;

public class CorsoType {
    private String id;
    private String nome;
    private String descrizione;
    private Rischio rischioAssociato;

    public CorsoType(String id, String nome, String descrizione, Rischio rischioAssociato) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.rischioAssociato = rischioAssociato;
    }
}
