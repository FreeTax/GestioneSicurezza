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
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


public class GatewayUtente {
    private Utente u;
    private CreditoFormativo cf;

    private EventBusService eventBusService;
    private Subscriber sub;
    private Publisher pub;

    public GatewayUtente(EventBusService eventBusService) {
        this.eventBusService = eventBusService;
        this.sub=new SubscriberConcr("Account", eventBusService);
        this.pub=new PublisherConcr();
    }
    public void insertUtenteInterno(int matricola, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento, String tipo) throws SQLException {
        Date date = Date.valueOf(datanascita);
        u = new UtenteInterno(matricola, password, nome, cognome, sesso, Dipartimento, date, matricola, tipo);
        //u.insertUtente();
        pub.publish(new Message("Account", "insertUtente",u), eventBusService);
        System.out.println("Utente"+matricola+"creato");
    }

    public void insertUtenteEsterno(int idEsterno, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento) throws SQLException {
        Date date = Date.valueOf(datanascita);
        u = new UtenteEsterno(1, password, nome, cognome, sesso, Dipartimento, date, idEsterno);
        pub.publish(new Message("Account", "insertUtente",u), eventBusService);
        System.out.println("Utente"+idEsterno+"creato");
        //u.insertUtente();
    }

    public void insertCreditoFormativo(int codice, int idRischio) throws SQLException {
        cf = new CreditoFormativo(codice, idRischio, "");
        pub.publish(new Message("Account", "insertCreditoFormativo",cf), eventBusService);
        System.out.println("Credito formativo creato");
        //cf.insertCreditoFormativo(idRischio);
    }

    /*login utente interno e esterno*/
    public boolean loginInterno(int matricola, String password) throws SQLException {
        UtenteInterno u=new UtenteInterno(matricola);
        return u.loginInterno(matricola, password);
    }

    public boolean loginEsterno(int idEsterno, String password) throws SQLException {
        UtenteEsterno u=new UtenteEsterno(idEsterno);
        return u.loginEsterno(idEsterno, password);
    }

    public static boolean checkSupervisore(int matricola) throws SQLException {
        UtenteInterno u = new UtenteInterno(matricola);
        if(u.getType()==null){
            return false;
        }else{
            return u.getType().equals("supervisore");
        }
    }

    public static boolean checkAvanzato(int matricola) throws SQLException {
        UtenteInterno u = new UtenteInterno(matricola);
        if(u.getType()==null){
            return false;
        }else{
            return u.getType().equals("avanzato");
        }
    }

    /* inserimento richiesta accesso a luogo e dipartimento*/
    public void insertRichiestaLuogo(int idUtente, int idLuogo) throws SQLException {
        RichiestaLuogo rl = new RichiestaLuogo(idUtente, 0, idLuogo);
        pub.publish(new Message("Account", "insertRichiestaLuogo",rl), eventBusService);
        System.out.println("Richiesta utente"+idUtente+"luogo creato");
        //rl.insertRichiesta(idLuogo);
    }

    public void insertRichiestaDipartimento(int idUtente, int idDipartimento) throws SQLException {
        RichiestaDipartimento rd = new RichiestaDipartimento(idUtente, 0, idDipartimento);
        pub.publish(new Message("Account", "insertRichiestaDipartimento",rd), eventBusService);
        System.out.println("Richiesta utente"+idUtente+"dipartimento creato");
        //rd.insertRichiesta(idDipartimento);
    }

    /* aggiorna dati utente interno e esterno*/
    public void aggiornaUtenteInterno(int matricola, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento, String tipo) throws SQLException {
        Date date = Date.valueOf(datanascita);
        UtenteInterno u = new UtenteInterno(1, password, nome, cognome, sesso, Dipartimento, date, matricola, tipo);
        pub.publish(new Message("Account", "updateUtenteInterno",u), eventBusService);
        System.out.println("Utente"+matricola+"aggiornato");
        //u.updateUtenteDb();
    }

    public void aggiornaUtenteEsterno(int idEsterno, String password, String nome, String cognome, String sesso, String datanascita, String Dipartimento) throws SQLException {
        Date date = Date.valueOf(datanascita);
        UtenteEsterno u = new UtenteEsterno(1, password, nome, cognome, sesso, Dipartimento, date, idEsterno);
        pub.publish(new Message("Account", "updateUtenteEsterno",u), eventBusService);
        System.out.println("Utente"+idEsterno+"aggiornato");
        //u.updateUtenteDb();
    }

    /* caricamento certificazione*/
    public void sostieniCredito(int idUtente, int codice, String certificazione) throws SQLException {
        Utente u = new UtenteInterno();//FIXME not good
        List<Object> param =  Arrays.asList(idUtente, codice, certificazione);
        pub.publish(new Message("Account", "sostieniCredito",u,param), eventBusService);
        System.out.println("Utente"+idUtente+" ha sostenuto il credito");

        //u.sostieniCredito(idUtente, codice, certificazione);
    }

    public ArrayList<CreditoFormativo> getCFUSostenuti(int idAutorizzato, int idUtente) throws SQLException {
        Utente u=new UtenteInterno();//FIXME not good
        if (checkSupervisore(idAutorizzato) || checkAvanzato(idAutorizzato)) {
           // ArrayList<CreditoFormativo> cfus = u.getCfuSostenuti(idUtente);
            SubscriberConcr subscriber = new SubscriberConcr("CFUsostenuti", eventBusService);
            pub.publish(new Message("Account", "getCfuSostenuti",null,Arrays.asList(idUtente),"CFUsostenuti"), eventBusService);
            //CompletableFuture.runAsync(()->subscriber.run());
            //ArrayList<String> cfusString = new ArrayList<>();
            //cfus.forEach(cf -> cfusString.add(cf.toString()));
            //ArrayList<CreditoFormativo> cfus;

            Executor deleyed=CompletableFuture.delayedExecutor(4, TimeUnit.SECONDS);
            CompletableFuture<ArrayList<CreditoFormativo>> getCFU = CompletableFuture
                    .supplyAsync(()->(ArrayList<CreditoFormativo>)subscriber.getSubscriberMessages().get(0).getData(),deleyed)
                    .completeOnTimeout(new ArrayList<>(), 5, TimeUnit.SECONDS);
            ArrayList<CreditoFormativo> cfus = getCFU.join();

            return cfus;
        } else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    /* get richieste luogo e dipartimento*/
    public ArrayList<String> getRichiesteLuogo(int idUtente) throws SQLException {
        if (checkSupervisore(idUtente)) {
            ArrayList<RichiestaLuogo> richiesteLuogo = new UtenteInterno(idUtente).getRichiesteLuogoUtenti();
            ArrayList<String> richiesteLuogoString = new ArrayList<>();
            richiesteLuogo.forEach(rl -> richiesteLuogoString.add(rl.toString()));
            return richiesteLuogoString;
        } else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    public ArrayList<String> getRichiesteDipartimento(int idUtente) throws SQLException {
        if (checkAvanzato(idUtente)) {
            ArrayList<RichiestaDipartimento> richiesteDipartimento = new UtenteInterno(idUtente).getRichiesteDipartimentoUtenti();
            ArrayList<String> richiesteDipartimentoString = new ArrayList<>();
            richiesteDipartimento.forEach(rd -> richiesteDipartimentoString.add(rd.toString()));
            return richiesteDipartimentoString;
        } else {
            throw new RuntimeException("Utente non autorizzato");
        }
    }

    public ArrayList<String> getCFUdaSostenere(int idUtente) throws SQLException{
        ArrayList<RichiestaLuogo> richiesteLuogo=null;
        ArrayList<RichiestaDipartimento> richiesteDipartimento=null;
        ArrayList<CreditoFormativo> cfuUtente;

        if(new UtenteInterno(idUtente).getNome()!=null){
            UtenteInterno ui=new UtenteInterno(idUtente);
            richiesteLuogo = ui.getRichiesteLuogo();
            richiesteDipartimento =ui.getRichiesteDipartimento();
            cfuUtente=ui.getCfuSostenuti();
        }
        else {
            UtenteEsterno ue=new UtenteEsterno(idUtente);
            richiesteLuogo =ue.getRichiesteLuogo();
            richiesteDipartimento =ue.getRichiesteDipartimento();
            cfuUtente=ue.getCfuSostenuti();
        }

        ArrayList<Integer> rischi = new ArrayList<>();

        for(RichiestaLuogo rl : richiesteLuogo){
            Luogo l=new Luogo(rl.getIdLuogo());
            ArrayList<Integer> rischiLuogo = l.getRischi();
            for(Integer i : rischiLuogo){
                if(!rischi.contains(i))
                    rischi.add(i);
            }
        }

        for(RichiestaDipartimento rd : richiesteDipartimento){
            Dipartimento d=new Dipartimento(rd.getIdDipartimento());
            ArrayList<Integer> rischiDipartimento = d.getRischi();
            for(Integer i : rischiDipartimento){
                if(!rischi.contains(i))
                    rischi.add(i);
            }
        }

        //ArrayList<CreditoFormativo> cfuUtente = new UtenteGatewayDb().GetCFUSostenuti(idUtente);
        ArrayList<Integer> cfuUtenteId = new ArrayList<>();

        cfuUtente.forEach(cfu ->cfuUtenteId.add(cfu.getIdRischio()));

        rischi.removeAll(cfuUtenteId);
        ArrayList<String> rischiString = new ArrayList<>();

        for (Integer rischio : rischi) {
            String nomeRischio;
            if(new RischioGenerico(rischio).getNome()!=null) {
                nomeRischio = new RischioGenerico(rischio).getNome();
            }
            else{
                nomeRischio= new RischioSpecifico(rischio).getNome();
            }
            rischiString.add(nomeRischio);//TODO fix this: should be rischio
        }

        return rischiString;
    }

    public ArrayList<String> getDashboard(int idAutorizzato) throws SQLException{
        GatewayVisite vGateway = new GatewayVisite();
        GatewayAccessi aGateway = new GatewayAccessi();

        ArrayList<String> dashboard = new ArrayList<>();

        for(String utente : new UtenteInterno().getUtenti()){
            String[] utenteSplit = utente.split(" ");
            int idUtente = Integer.parseInt(utenteSplit[0]);

            ArrayList<String> cfuDaSostenere = getCFUdaSostenere(idUtente);
            utente += " CFU da sostenere: ";

            for(String cfu : cfuDaSostenere){
                    utente += cfu + ", ";
            }

            ArrayList<CreditoFormativo> cfuSostenuti = getCFUSostenuti(idAutorizzato, idUtente);

            utente += " CFU sostenuti: ";

            for(CreditoFormativo cfu : cfuSostenuti){
                String nomeRischio;
                if(new RischioGenerico((cfu.getIdRischio())).getNome()!=null) {
                    nomeRischio = new RischioGenerico((cfu.getIdRischio())).getNome();
                }
                else{
                    nomeRischio= new RischioSpecifico((cfu.getIdRischio())).getNome();
                }
                utente +=nomeRischio+ ", "; //TODO fix this: should be rischio or should be generic
            }

            utente += " Visite da sostenere: ";

            for(Visita v : vGateway.getVisiteDaSostentere(idUtente)){
                utente+=v.getDescrizione()+",";
            }

            utente += " Visite sostenute: ";

            for(Visita v : vGateway.getVisiteSostenute(idUtente)){
                utente+=v.getDescrizione()+",";
            }

            utente +="Luoghi frequentati: ";

            for(int i: aGateway.getLuoghiFrequentati(idUtente)){
                utente+=new Luogo(i).getNome()+",";
            }

            utente +="Dipartimenti frequentati: ";

            for(int i: aGateway.getDipartimentiFrequentati(idUtente)){
                utente+=new Dipartimento(i).getNome()+",";
            }
            dashboard.add(utente);
        }

        return dashboard;
    }
}
