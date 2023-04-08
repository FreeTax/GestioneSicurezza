import Accessi.accessisubscriber.AccessiSubscriber;
import Account.CreditoFormativo;
import Account.Utente;
import Account.UtenteEsterno;
import Account.UtenteInterno;
import Account.accountsubscriber.AccountSubscriber;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import AsyncIPCEventBus.*;
import AsyncIPCEventBus.PublishSubscribe.SubscriberConcr;
import Luoghi.luoghisubscriber.LuoghiSubscriber;
import Rischi.rischisubscriber.RischiSubscriber;
import TestSuite.InitDB;
import Visite.visitesubscriber.VisiteSubscriber;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class Main {
    //TODO: test cross department and role update, getCrediti da sostenere,
    // dashboard: crediti da soostenre, crediti sostenuti, visite da effettuare, visite effettuate,luoghi frequentati
    //FIXME: UserIPC access directly to DBGateway, <-metodi che usano costruttori per id di Rischio e Utente  vanno resi generici
    public static void initData(GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL, GatewayCorsiSicurezza gCS){
        InitDB.initDB();
        try{
            gU.insertUtenteInterno(1,"password","nome","cognome","sesso","1999-01-01","1","base");
            gU.insertUtenteInterno(2,"password","nome2","cognome2","sesso","1999-01-01","1","supervisore");
            gU.insertUtenteInterno(3,"password","nome3","cognome3","sesso","1999-01-01","2","avanzato");
            gU.insertUtenteEsterno(4,"password","nome4","cognome4","sesso","1999-01-01","2");
            gL.addDipartimento(1,"dipartimento1", 3);
            gL.addDipartimento(2,"dipartimento2", 1);
            gL.addLuogo(1,"luogo1", "laboratorio", 1,1);
            gL.addLuogo(2,"luogo2", "ufficio", 2,2);
            gU.insertCreditoFormativo(1,1);
            gU.insertCreditoFormativo(2,2);
            gU.insertCreditoFormativo(3,3);

            gV.addVisitaType(1,"visita oculistica", " ", "2 anno",2);
            gV.addSchedaVisita(1);

            gV.addVisitaUtente(1,1,"dott.Mario Rossi","visita oculistica",Timestamp.valueOf(LocalDateTime.now()),"da sostenere",1);
            gV.addVisitaUtente(1,2,"dott.Mario Rossi","visita controllo",Timestamp.valueOf(LocalDateTime.now()),"da sostenere",1);
            gV.sostieniVisita(1, "superata",1);
            gL.insertRischioLuogo(1,1);
            gL.insertRischioLuogo(1,2);
            gL.insertRischioLuogo(1,3);
            gR.insertRischioSpecifico(1,"chimico","hhjljhl");
            gR.insertRischioSpecifico(2,"computer","hhjljhl");
            gR.insertRischioSpecifico(3,"laser","hhjljhl");
            gR.insertRischioSpecifico(4,"ustione","hhjljhl");
            //sleep(3000);
            gCS.addCorsoType(1,"sicurezza","  ",1,3);
            gCS.addCorso("corsoSicurezza1"," ",1, LocalDate.now(),LocalDate.now(),3);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void test( GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL) throws SQLException {
        UtenteInterno u1= new UtenteInterno(1);//base
        UtenteInterno u2= new UtenteInterno(2);//supervisore
        UtenteInterno u3= new UtenteInterno(3); //avanzato
        UtenteEsterno u4= new UtenteEsterno(4);//esterno
        System.out.println(u3.getType());
        try {
            if(gA.insertAccessoDipartimento(4, 2, u3.getMatricola()))
                System.out.println("utente" + u3.getCodice() + " ha accesso al dipartimento 2");
        } catch (Exception e) {
            System.out.println(e);
        }
        try{
            if(gA.insertAccessoLuogo(1,1,u2.getMatricola()))
                System.out.println("utente" + u2.getCodice() + " ha accesso al luogo 1");
        } catch (Exception e) {
            System.out.println(e);
        }
        try{
            if(gA.insertAccessoDipartimento(4,1,u2.getMatricola()))
                System.out.println("utente" + u2.getCodice() + " ha accesso al dipartimento 2");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (gA.insertAccessoLuogo(4, 1, u1.getMatricola())) //FIXME: non viene eseguito perchè il metodo prima lancia eccezione
                System.out.println("utente" + u1.getCodice() + " ha accesso al luogo 1");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void test2(GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL, EventBusService eventBusService) throws SQLException {
        try {
            if (gU.loginInterno(1, "password")) {
                gU.sostieniCredito(1, 1, "");
                gU.sostieniCredito(1, 2, "");
                gU.sostieniCredito(1, 3, "certificazione");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (gU.loginEsterno(4, "password")) {
                gU.sostieniCredito(4, 1, "");
                gU.sostieniCredito(4, 3, "certificazione");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (gU.loginInterno(2, "password")) { //login utente supervisore
                ArrayList<CreditoFormativo> CFUsostenuti=gU.getCFUSostenuti(2, 1);
                /*SubscriberConcr subscriber = new SubscriberConcr("CFUsostenuti", eventBusService);
                synchronized (subscriber.getSubscriberMessages()) {
                    while (subscriber.getSubscriberMessages().isEmpty()) {
                        subscriber.getSubscriberMessages().wait();
                    }
                    subscriber.receiveMessage(subscriber.getSubscriberMessages().remove(0),eventBusService);
                }
                ArrayList<CreditoFormativo> CFUsostenuti = (ArrayList<CreditoFormativo>) subscriber.getResponse();
                /*subscriber.receiveMessage(subscriber.getSubscriberMessages().remove(0), eventBusService);
                ArrayList<CreditoFormativo> CFUsostenuti = (ArrayList<CreditoFormativo>) subscriber.getResponse();*/
                ArrayList<String> cfusString = new ArrayList<>();
                CFUsostenuti.forEach(cf -> cfusString.add(cf.toString()));
                System.out.println("l'utente 1 ha sostenuto i seguenti crediti formativi: " + cfusString);
                if (gA.insertAccessoLuogo(1, 1, 2)) {
                    System.out.println("utente con codice 1 ha accesso al luogo 1");
                }
                if (gA.insertAccessoLuogo(4, 1, 2)) {
                    System.out.println("utente con codice 2 ha accesso al luogo 1");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void testDashboard(GatewayUtente gU, GatewayLuoghi gL) throws SQLException {
        gL.insertRischioLuogo(2,4);
        gU.insertRichiestaLuogo(1,2);
        for(String s: gU.getDashboard(3)){
            System.out.println(s);
        }
    }
    public static void main(String[] args) throws SQLException, InterruptedException {
        InitDB.initDB();
        EventBusService eventBusService = new EventBusService();
        AccountSubscriber accountSubscriber = new AccountSubscriber(eventBusService);
        //Publisher publisher = new PublisherConcr();
        GatewayUtente gU = new GatewayUtente(eventBusService);
        GatewayLuoghi gL = new GatewayLuoghi(eventBusService);
        GatewayRischi gR = new GatewayRischi(eventBusService);
        GatewayAccessi gA = new GatewayAccessi(eventBusService);
        GatewayVisite gV = new GatewayVisite(eventBusService);
        GatewayCorsiSicurezza gCS= new GatewayCorsiSicurezza(eventBusService);

        CompletableFuture.runAsync(()->eventBusService.run());
        CompletableFuture.runAsync(()->accountSubscriber.run());
        CompletableFuture.runAsync(()->new AccessiSubscriber(eventBusService).run());
        CompletableFuture.runAsync(()->new LuoghiSubscriber(eventBusService).run());
        CompletableFuture.runAsync(()->new RischiSubscriber(eventBusService).run());
        CompletableFuture.runAsync(()->new VisiteSubscriber(eventBusService).run());

/*
        ArrayList<Thread> threads = new ArrayList<Thread>();
        threads.add(new Thread(eventBusService));
        threads.add(new Thread(accountSubscriber));
        threads.add(new Thread(new AccessiSubscriber(eventBusService)));
        threads.add(new Thread(new LuoghiSubscriber(eventBusService)));
        threads.add(new Thread(new RischiSubscriber(eventBusService)));
        threads.add(new Thread(new VisiteSubscriber(eventBusService)));

        for (Thread t : threads) {
            t.start();
        }
*/
        System.out.println("_______________INIT DB_______________");
        initData(gA, gR, gV, gU, gL,gCS);
        //sleep(7000);
        System.out.println("_______________TEST2_______________");
        test2(gA,gR,gV,gU,gL,eventBusService);
        //sleep(3000);
        System.out.println("_______________TEST1_______________");
        test(gA, gR, gV, gU, gL);
        //sleep(3000);
        System.out.println("_______________TEST DASHBOARD_______________");
        testDashboard(gU, gL);

        //publisher.publish(new Message("GatewayUtente", "createUtente"), eventBusService);
        /*System.out.println("Utente creato");
        publisher.publish(new Message("Luoghi", "create"), eventBusService);
        System.out.println("Luoghi creato");
        publisher.publish(new Message("Rischi", "create"), eventBusService);
        System.out.println("Rischi creato");
        publisher.publish(new Message("Accessi", "create"), eventBusService);
        System.out.println("Accessi creato");
        publisher.publish(new Message("Visite", "create"), eventBusService);
        System.out.println("Visite creato");
        publisher.publish(new Message("CorsiSicurezza", "create"), eventBusService);*/
/*
        gU.insertUtenteInterno(1,"password", "Mario", "Rossi", "M", "1999-01-01", "1", "base");
        gU.insertUtenteInterno(2,"password", "Mario", "Bianchi", "M", "1999-01-01", "1", "base");
        gU.insertUtenteInterno(3,"password", "Mario", "Verdi", "M", "1999-01-01", "1", "base");
        gU.insertUtenteInterno(4,"password", "Mario", "blu", "M", "1999-01-01", "1", "base");

        gU.insertRichiestaLuogo(1,1);
        gU.insertRichiestaDipartimento(2,1);

        gU.aggiornaUtenteInterno(1,"nuovapassword","Mario", "Rossi", "M", "1999-01-01", "1", "supervisore");
        gU.aggiornaUtenteInterno(4,"nuovapassword","Mario", "Bianchi", "M", "1999-01-01", "1", "avanzato");
        gU.insertCreditoFormativo(1,1);
        gU.insertCreditoFormativo(1,2);
        gU.sostieniCredito(1,1,"certificato");
        gU.sostieniCredito(2,1,"");
        gU.sostieniCredito(2,2,"");

        ArrayList<CreditoFormativo> cfus = gU.getCFUSostenuti(1,2);
        System.out.println(cfus.toString());

        //eventBusService.broadcast();

        gV.addVisitaType(1,"visita oculistica", " ", "2 anno",2);
        gV.addSchedaVisita(1);
        gV.addVisitaUtente(1,1,"smsm","dd",Timestamp.valueOf(LocalDateTime.now()),"da sostenere", 1);
        gV.sostieniVisita(1,"ok",1);

        gR.insertRischioGenerico(1,"rischio generico", "descrizione");
        gR.insertRischioSpecifico(2,"rischio specifico", "descrizione");

        gL.addDipartimento(1,"dipartimentoX",1);
        gL.addLuogo(1,"laboratorioX","fisica",1,1);
        gL.insertRischioLuogo(1,2);
        gL.insertRischioDipartimento(1,1);

        //sleep(27000);

        gA.insertAccessoDipartimento(2,1,4);
        gA.insertAccessoLuogo(2,1,1);
        gA.updateAccessoLuogo(2,1);
        gA.updateAccessoDipartimento(2,1);
        gA.deleteAccessoDipartimento(2,1);
        gA.deleteAccessoLuogo(2,1);

        SubscriberConcr subscriber = new SubscriberConcr("CFUsostenuti", eventBusService);
        ArrayList<CreditoFormativo> cf=gU.getCFUSostenuti(1,2);
        System.out.println(cf.toString());

        //eventBusService.broadcast();
        //sleep(27000);
        /*
        synchronized (subscriber.getSubscriberMessages()) {
                //System.out.println("SubscriberConcr is running");
                while (subscriber.getSubscriberMessages().isEmpty()) {
                    //System.out.println(subscriber.getSubscriberMessages().toString());
                    subscriber.getSubscriberMessages().wait();
                }
                subscriber.receiveMessage(subscriber.getSubscriberMessages().remove(0),eventBusService);
            ArrayList<CreditoFormativo> cf = (ArrayList<CreditoFormativo>) subscriber.getResponse();
            System.out.println(cf.toString());
        }*
        //subscriber.receiveMessage(subscriber.getSubscriberMessages().remove(0), eventBusService);

        try {
            for (Thread t : threads) {
                t.interrupt();
                t.join();
            }
        }catch (InterruptedException e){
            System.out.println(e);
        }
*/
        /*
        initData(gA, gR, gV, gU, gL,gCS);
        test2(gA,gR,gV,gU,gL);
        test(gA, gR, gV, gU, gL);
        testDashboard(gU, gL);*/
       //sleep(10000);
    }
}