package AsyncIPCEventBus;

import Account.*;
import AsyncIPCEventBus.PublishSubscribe.*;
import Luoghi.Dipartimento;
import Luoghi.Luogo;
import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;
import Visite.Visita;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class GatewayUtente {
    private Utente u;
    private CreditoFormativo cf;

    private EventBusService eventBusService;
    private Publisher pub;

    //Executor deleyed=CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS);


    public GatewayUtente(EventBusService eventBusService) {
        this.eventBusService = eventBusService;
        this.pub = new PublisherConcr();
    }

    public static boolean checkSupervisore(int matricola) throws SQLException {
        UtenteInterno u = new UtenteInterno(matricola);
        if (u.getType() == null) {
            return false;
        } else {
            return u.getType().equals("supervisore");
        }
    }

    public static boolean checkAvanzato(int matricola) throws SQLException {
        UtenteInterno u = new UtenteInterno(matricola);
        if (u.getType() == null) {
            return false;
        } else {
            return u.getType().equals("avanzato");
        }
    }

    public void insertUtenteInterno(int matricola, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento, String tipo) throws SQLException {
        Date date = Date.valueOf(datanascita);
        u = new UtenteInterno(matricola, password, nome, cognome, sesso, Dipartimento, date, matricola, tipo);
        pub.publish(new Message("Account", "insertUtente", u), eventBusService);
        System.out.println("Utente" + matricola + "creato");
    }

    public void insertUtenteEsterno(int idEsterno, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento) throws SQLException {
        Date date = Date.valueOf(datanascita);
        u = new UtenteEsterno(1, password, nome, cognome, sesso, Dipartimento, date, idEsterno);
        pub.publish(new Message("Account", "insertUtente", u), eventBusService);
        System.out.println("Utente" + idEsterno + "creato");
    }

    public void insertCreditoFormativo(int codice, int idRischio) throws SQLException {
        cf = new CreditoFormativo(codice, idRischio, "");
        pub.publish(new Message("Account", "insertCreditoFormativo", cf), eventBusService);
        System.out.println("Credito formativo creato");
    }

    /*login utente interno e esterno*/
    public boolean loginInterno(int matricola, String password) throws SQLException {
        UtenteInterno u = new UtenteInterno(matricola);
        return u.loginInterno(matricola, password);
    }

    public boolean loginEsterno(int idEsterno, String password) throws SQLException {
        UtenteEsterno u = new UtenteEsterno(idEsterno);
        return u.loginEsterno(idEsterno, password);
    }

    /* inserimento richiesta accesso a luogo e dipartimento*/
    public void insertRichiestaLuogo(int idUtente, int idLuogo) throws SQLException {
        RichiestaLuogo rl = new RichiestaLuogo(idUtente, 0, idLuogo);
        pub.publish(new Message("Account", "insertRichiestaLuogo", rl), eventBusService);
        System.out.println("Richiesta utente" + idUtente + "luogo creato");
    }

    public void insertRichiestaDipartimento(int idUtente, int idDipartimento) throws SQLException {
        RichiestaDipartimento rd = new RichiestaDipartimento(idUtente, 0, idDipartimento);
        pub.publish(new Message("Account", "insertRichiestaDipartimento", rd), eventBusService);
        System.out.println("Richiesta utente" + idUtente + "dipartimento creato");
    }

    /* aggiorna dati utente interno e esterno*/
    public void aggiornaUtenteInterno(int matricola, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento, String tipo) throws SQLException {
        Date date = Date.valueOf(datanascita);
        UtenteInterno u = new UtenteInterno(1, password, nome, cognome, sesso, Dipartimento, date, matricola, tipo);
        pub.publish(new Message("Account", "updateUtenteInterno", u), eventBusService);
        System.out.println("Utente" + matricola + "aggiornato");
    }

    public void aggiornaUtenteEsterno(int idEsterno, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento) throws SQLException {
        Date date = Date.valueOf(datanascita);
        UtenteEsterno u = new UtenteEsterno(1, password, nome, cognome, sesso, Dipartimento, date, idEsterno);
        pub.publish(new Message("Account", "updateUtenteEsterno", u), eventBusService);
        System.out.println("Utente" + idEsterno + "aggiornato");
    }

    /* caricamento certificazione*/
    public void sostieniCredito(int idUtente, int codice, String certificazione) throws SQLException {
        Utente u = new UtenteInterno();//FIXME not good
        List<Object> param = Arrays.asList(idUtente, codice, certificazione);
        pub.publish(new Message("Account", "sostieniCredito", u, param), eventBusService);
        System.out.println("Utente" + idUtente + " ha sostenuto il credito");
    }

    public ArrayList<String> getCFUSostenuti(int idAutorizzato, int idUtente) throws SQLException {
        Utente u = new UtenteInterno();//FIXME not good
        if (checkSupervisore(idAutorizzato) || checkAvanzato(idAutorizzato)) {
            SubscriberConcr subscriber = new SubscriberConcr("CFUsostenuti"+idUtente, eventBusService);
            pub.publish(new Message("Account", "getCfuSostenuti", null, Arrays.asList(idUtente), "CFUsostenuti"+idUtente), eventBusService);

            CompletableFuture<ArrayList<CreditoFormativo>> getCFU = CompletableFuture
                    .supplyAsync(() -> (ArrayList<CreditoFormativo>) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS))
                    .completeOnTimeout(new ArrayList<>(), 3, TimeUnit.SECONDS);
            ArrayList<CreditoFormativo> cfus = getCFU.join();

            subscriber.unSubscribe("CFUsostenuti", eventBusService);
            ArrayList<String> cfuSostenuti = new ArrayList<>();

            for (CreditoFormativo cfu : cfus) {
                String nomeRischio;
                if (new RischioGenerico((cfu.getIdRischio())).getNome() != null) {
                    nomeRischio = new RischioGenerico((cfu.getIdRischio())).getNome();
                } else {
                    nomeRischio = new RischioSpecifico((cfu.getIdRischio())).getNome();
                }
                cfuSostenuti.add(nomeRischio);
            }

            return cfuSostenuti;
        } else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    /* get richieste luogo e dipartimento*/
    public ArrayList<String> getRichiesteLuogo(int idUtente) throws SQLException {
        if (checkSupervisore(idUtente)) {
            //ArrayList<RichiestaLuogo> richiesteLuogo = new UtenteInterno(idUtente).getRichiesteLuogoUtenti();
            SubscriberConcr subscriber = new SubscriberConcr("richiesteLuogoUtenti", eventBusService);
            pub.publish(new Message("Account", "getRichiesteLuogoUtenti", null, Arrays.asList(idUtente), "richiesteLuogoUtenti"), eventBusService);

            CompletableFuture<ArrayList<RichiestaLuogo>> getRichiesteLuogo = CompletableFuture
                    .supplyAsync(() -> (ArrayList<RichiestaLuogo>) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(6, TimeUnit.SECONDS))
                    .completeOnTimeout(new ArrayList<>(), 7, TimeUnit.SECONDS);

            ArrayList<RichiestaLuogo> richiesteLuogo = getRichiesteLuogo.join();
            ArrayList<String> richiesteLuogoString = new ArrayList<>();

            subscriber.unSubscribe("richiesteLuogoUtenti", eventBusService);
            richiesteLuogo.forEach(rl -> richiesteLuogoString.add(rl.toString()));
            return richiesteLuogoString;
        } else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    public ArrayList<String> getRichiesteDipartimento(int idUtente) throws SQLException {
        if (checkAvanzato(idUtente)) {
            //ArrayList<RichiestaDipartimento> richiesteDipartimento = new UtenteInterno(idUtente).getRichiesteDipartimentoUtenti();
            SubscriberConcr subscriber = new SubscriberConcr("richiesteDipartimentoUtenti", eventBusService);
            pub.publish(new Message("Account", "getRichiesteDipartimentoUtenti", null, Arrays.asList(idUtente), "richiesteDipartimentoUtenti"), eventBusService);

            CompletableFuture<ArrayList<RichiestaDipartimento>> getRichiesteDipartimento = CompletableFuture
                    .supplyAsync(() -> (ArrayList<RichiestaDipartimento>) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(4, TimeUnit.SECONDS))
                    .completeOnTimeout(new ArrayList<>(), 5, TimeUnit.SECONDS);

            ArrayList<RichiestaDipartimento> richiesteDipartimento = getRichiesteDipartimento.join();
            ArrayList<String> richiesteDipartimentoString = new ArrayList<>();

            subscriber.unSubscribe("richiesteDipartimentoUtenti", eventBusService);
            richiesteDipartimento.forEach(rd -> richiesteDipartimentoString.add(rd.toString()));
            return richiesteDipartimentoString;
        } else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    public ArrayList<String> getCFUdaSostenere(int idUtente) throws SQLException {
        ArrayList<Integer> rischi;
        SubscriberConcr subscriber = new SubscriberConcr("CFUdaSostenere"+idUtente, eventBusService);
        pub.publish(new Message("Account", "getCFUdaSostenere", null, Arrays.asList(idUtente), "CFUdaSostenere"+idUtente), eventBusService);
            CompletableFuture<ArrayList<Integer>> getRischi = CompletableFuture
                    .supplyAsync(() -> (ArrayList<Integer>) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS))
                    .completeOnTimeout(new ArrayList<>(), 3, TimeUnit.SECONDS);
            rischi = getRischi.join();
            System.out.println("dimensioni richi: " + rischi.size());
            ArrayList<String> rischiString = new ArrayList<>();

            subscriber.unSubscribe("CFUdaSostenere"+idUtente, eventBusService);
            for (Integer rischio : rischi) {
                String nomeRischio;
                if (new RischioGenerico(rischio).getNome() != null) {
                    nomeRischio = new RischioGenerico(rischio).getNome();
                } else {
                    nomeRischio = new RischioSpecifico(rischio).getNome();
                }
                rischiString.add(nomeRischio);
            }
            return rischiString;
    }

    public ArrayList<String> getDashboard(int idAutorizzato) throws SQLException {
        GatewayVisite vGateway = new GatewayVisite(eventBusService);
        GatewayAccessi aGateway = new GatewayAccessi(eventBusService);

        ArrayList<String> dashboard = new ArrayList<>();

        for (String utente : new UtenteInterno().getUtenti()) {
            String[] utenteSplit = utente.split(" ");
            int idUtente = Integer.parseInt(utenteSplit[0]);

            ArrayList<String> cfuDaSostenere = getCFUdaSostenere(idUtente);
            utente += " CFU da sostenere: ";

            for (String cfu : cfuDaSostenere) {
                utente += cfu + ", ";
            }

            ArrayList<String> cfuSostenuti = getCFUSostenuti(idAutorizzato, idUtente);

            utente += " CFU sostenuti: ";

            for (String cfu : cfuSostenuti) {
                utente += cfu + ", ";
            }

            utente += " Visite da sostenere: ";

            for (Visita v : vGateway.getVisiteDaSostentere(idUtente)) {
                utente += v.getDescrizione() + ",";
            }

            utente += " Visite sostenute: ";

            for (Visita v : vGateway.getVisiteSostenute(idUtente)) {
                utente += v.getDescrizione() + ",";
            }

            utente += "Luoghi frequentati: ";

            for (int i : aGateway.getLuoghiFrequentati(idUtente)) {
                utente += new Luogo(i).getNome() + ",";
            }

            utente += "Dipartimenti frequentati: ";

            for (int i : aGateway.getDipartimentiFrequentati(idUtente)) {
                utente += new Dipartimento(i).getNome() + ",";
            }
            dashboard.add(utente);
        }

        return dashboard;
    }
}
