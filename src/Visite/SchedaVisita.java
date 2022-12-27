package Visite;


import Account.Utente;

public class SchedaVisita {
    private String codice;
    private String elencoPatologie [];
    private Utente utente;
    private Visita visiteEffettuate[];
    private Visita visiteDaSostentere[];
    public SchedaVisita(String codice, Utente utente, String [] elencoPatologie, Visita[] visiteEffettuate, Visita[] visiteDaSostentere) {
        this.codice = codice;
        this.utente = utente;
        this.elencoPatologie = elencoPatologie;
        this.visiteEffettuate = visiteEffettuate;
        this.visiteDaSostentere = visiteDaSostentere;
    }
}
