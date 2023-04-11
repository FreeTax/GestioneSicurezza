import Accessi.accessisubscriber.AccessiSubscriber;
import Account.UtenteEsterno;
import Account.UtenteInterno;
import Account.accountsubscriber.AccountSubscriber;
import AsyncIPCEventBus.*;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import Luoghi.luoghisubscriber.LuoghiSubscriber;
import Rischi.rischisubscriber.RischiSubscriber;
import TestSuite.InitDB;
import Visite.visitesubscriber.VisiteSubscriber;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class Main {
    //TODO: test cross department and role update, getCrediti da sostenere,
    //TODO: gestire eliminazione nei db e accettazione richieste->eliminazione richiesta (entrambi i branch)
    //TODO: tracciare output colori anche tra vari test
    // dashboard: crediti da soostenre, crediti sostenuti, visite da effettuare, visite effettuate,luoghi frequentati
    public static void initData(GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL, GatewayCorsiSicurezza gCS) {
        InitDB.initDB();
        try {
            gU.insertUtenteInterno(1, "password", "nome", "cognome", "sesso", "1999-01-01", "1", "base");
            gU.insertUtenteInterno(2, "password", "nome2", "cognome2", "sesso", "1999-01-01", "1", "supervisore");
            gU.insertUtenteInterno(3, "password", "nome3", "cognome3", "sesso", "1999-01-01", "2", "avanzato");
            gU.insertUtenteEsterno(4, "password", "nome4", "cognome4", "sesso", "1999-01-01", "2");
            gL.addDipartimento(1, "dipartimento1", 3);
            gL.addDipartimento(2, "dipartimento2", 1);
            gL.addLuogo(1, "luogo1", "laboratorio", 1, 1);
            gL.addLuogo(2, "luogo2", "ufficio", 2, 2);
            gU.insertCreditoFormativo(1, 1);
            gU.insertCreditoFormativo(2, 2);
            gU.insertCreditoFormativo(3, 3);

            gV.addVisitaType(1, "visita oculistica", " ", "2 anno", 2);
            gV.addSchedaVisita(1);
            sleep(800);
            gV.addVisitaUtente(1, 1, "dott.Mario Rossi", "visita oculistica", Timestamp.valueOf(LocalDateTime.now()), "da sostenere", 1);
            gV.addVisitaUtente(1, 2, "dott.Mario Rossi", "visita controllo", Timestamp.valueOf(LocalDateTime.now()), "da sostenere", 1);
            sleep(800);
            gV.sostieniVisita(1, "superata", 1);
            gL.insertRischioLuogo(1, 1);
            gL.insertRischioLuogo(1, 2);
            gL.insertRischioLuogo(1, 3);
            gR.insertRischioSpecifico(1, "chimico", "hhjljhl");
            gR.insertRischioSpecifico(2, "computer", "hhjljhl");
            gR.insertRischioSpecifico(3, "laser", "hhjljhl");
            gR.insertRischioSpecifico(4, "ustione", "hhjljhl");
            //sleep(3000);
            gCS.addCorsoType(1, "sicurezza", "  ", 1, 3);
            gCS.addCorso("corsoSicurezza1", " ", 1, LocalDate.now(), LocalDate.now(), 3);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void test(GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL) throws SQLException {
        UtenteInterno u1 = new UtenteInterno(1);//base
        UtenteInterno u2 = new UtenteInterno(2);//supervisore
        UtenteInterno u3 = new UtenteInterno(3); //avanzato
        UtenteEsterno u4 = new UtenteEsterno(4);//esterno
        System.out.println(u3.getType());
        /*
        try {
            if (gA.insertAccessoDipartimento(4, 2, u3.getMatricola()))
                System.out.println("utente" + u3.getCodice() + " ha accesso al dipartimento 2");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (gA.insertAccessoLuogo(1, 1, u2.getMatricola()))
                System.out.println("utente" + u2.getCodice() + " ha accesso al luogo 1");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (gA.insertAccessoDipartimento(4, 1, u2.getMatricola()))
                System.out.println("utente" + u2.getCodice() + " ha accesso al dipartimento 2");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (gA.insertAccessoLuogo(4, 1, u1.getMatricola())) //FIXME: non viene eseguito perchè il metodo prima lancia eccezione (con le completablefuture viene eseguita ma c'è duplicate entry TOFIX))
                System.out.println("utente" + u1.getCodice() + " ha accesso al luogo 1");
        } catch (Exception e) {
            System.out.println(e);
        }*/
            gA.insertAccessoDipartimento(4, 2, u3.getMatricola()).thenAcceptAsync((accesso) -> {
                if (accesso)
                    System.out.println("utente" + u3.getCodice() + " ha accesso al dipartimento 2");
            }).exceptionally((e) -> {
                System.out.println(e.getMessage());
                return null;
            });

            gA.insertAccessoLuogo(1, 1, u2.getMatricola()).thenAcceptAsync((accesso) -> {
                if (accesso)
                    System.out.println("utente" + u2.getCodice() + " ha accesso al luogo 1");
            }).exceptionally((e) -> {
                System.out.println(e.getMessage());
                return null;
            });

            gA.insertAccessoDipartimento(4, 1, u2.getMatricola()).thenAcceptAsync((accesso) -> {
                if (accesso)
                    System.out.println("utente" + u2.getCodice() + " ha accesso al dipartimento 2");
            }).exceptionally((e) -> {
                System.out.println(e.getMessage());
                return null;
            });

            gA.insertAccessoLuogo(4, 1, u1.getMatricola()).thenAcceptAsync((accesso) -> {
                if (accesso)
                    System.out.println("utente" + u1.getCodice() + " ha accesso al luogo 1");
            }).exceptionally((e) -> {
                System.out.println(e.getMessage());
                return null;
            });

    }

    public static void test2(GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL, EventBusService eventBusService) throws SQLException {
        try {
            if (gU.loginInterno(1, "password").join()) {
                gU.sostieniCredito(1, 1, "");
                gU.sostieniCredito(1, 2, "");
                gU.sostieniCredito(1, 3, "certificazione");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            if (gU.loginEsterno(4, "password").join()) {
                gU.sostieniCredito(4, 1, "");
                gU.sostieniCredito(4, 3, "certificazione");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            if (gU.loginInterno(2, "password").join()) { //login utente supervisore
               /* ArrayList<String> CFUsostenuti=gU.getCFUSostenuti(2, 1);
                System.out.println("l'utente 1 ha sostenuto i seguenti crediti formativi: " + CFUsostenuti);*/
                /*CompletableFuture.supplyAsync(() -> {
                    try {
                        return gU.getCFUSostenuti(2, 1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).thenAcceptAsync((CFUsostenuti) -> {
                    System.out.println("l'utente 1 ha sostenuto i seguenti crediti formativi: " + CFUsostenuti);
                });*/
                gU.getCFUSostenuti(2, 1).thenAcceptAsync((CFUsostenuti) -> {
                    System.out.println("l'utente 1 ha sostenuto i seguenti crediti formativi: " + CFUsostenuti);
                });

                gA.insertAccessoLuogo(1, 1, 2).thenAcceptAsync((accesso) -> {
                    if (accesso) {
                        System.out.println("utente con codice 1 ha accesso al luogo 1");
                    }
                }).exceptionally((e) -> {
                    System.out.println(e);
                    return null;
                });

                gA.insertAccessoLuogo(4, 1, 2).thenAcceptAsync((accesso) -> {
                    if (accesso) {
                        System.out.println("utente con codice 2 ha accesso al luogo 1");
                    }
                }).exceptionally((e) -> {
                    System.out.println(e);
                    return null;
                });
                /*
                if (gA.insertAccessoLuogo(1, 1, 2).join()) {
                    System.out.println("utente con codice 1 ha accesso al luogo 1");
                }
                if (gA.insertAccessoLuogo(4, 1, 2).join()) {
                    System.out.println("utente con codice 2 ha accesso al luogo 1");
                }*/
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void testDashboard(GatewayUtente gU, GatewayLuoghi gL) throws SQLException, InterruptedException {
        gL.insertRischioLuogo(2, 4);
        gU.insertRichiestaLuogo(1, 2);

        gU.getRichiesteLuogo(2).thenAcceptAsync((richiesteLuoghi) -> {
            System.out.println("richieste di accesso ai seguenti luoghi: " + richiesteLuoghi);
        });

        gU.getRichiesteDipartimento(3).thenAcceptAsync((richiesteDipartimenti) -> {
            System.out.println("richieste di accesso ai seguenti dipartimenti: " + richiesteDipartimenti);
        });

        //usando completableFuture il main va avanti mentre la richiesta è eseguita
        /*
        CompletableFuture.supplyAsync(() -> { //TODO: rendere il tutto più pulito, metodo che returna completableFuture nei gateway?
            try {
                return gU.getRichiesteLuogo(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }).thenAcceptAsync((richiesteLuoghi) -> {
            System.out.println("richieste di accesso ai seguenti luoghi: " + richiesteLuoghi);
        });
        /*ArrayList<String> richiesteLuoghi = gU.getRichiesteLuogo(2);
        System.out.println("richieste di accesso ai seguenti luoghi: " + richiesteLuoghi);*/
        /*
        CompletableFuture.supplyAsync(() -> {
            try {
                return gU.getRichiesteDipartimento(3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }).thenAcceptAsync((richiesteDipartimenti) -> {
            System.out.println("richieste di accesso ai seguenti dipartimenti: " + richiesteDipartimenti);
        });
        /*ArrayList<String> richiesteDipartimenti = gU.getRichiesteDipartimento(3);
        System.out.println("richieste di accesso ai seguenti dipartimenti: " + richiesteDipartimenti);*/
         gU.getDashboard(3).thenAcceptAsync((dashboard) -> {
            for (String s : dashboard) {
                System.out.println(s);
            }
        }).join();
        /*for(String s: gU.getDashboard(3)){
            System.out.println(s);
        }*/
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        InitDB.initDB();
        EventBusService eventBusService = new EventBusService();
        AccountSubscriber accountSubscriber = new AccountSubscriber(eventBusService);
        GatewayUtente gU = new GatewayUtente(eventBusService);
        GatewayLuoghi gL = new GatewayLuoghi(eventBusService);
        GatewayRischi gR = new GatewayRischi(eventBusService);
        GatewayAccessi gA = new GatewayAccessi(eventBusService);
        GatewayVisite gV = new GatewayVisite(eventBusService);
        GatewayCorsiSicurezza gCS = new GatewayCorsiSicurezza(eventBusService);
       /*
        CompletableFuture.runAsync(()->eventBusService.run());
        CompletableFuture.runAsync(()->accountSubscriber.run());
        CompletableFuture.runAsync(()->new AccessiSubscriber(eventBusService).run());
        CompletableFuture.runAsync(()->new LuoghiSubscriber(eventBusService).run());
        CompletableFuture.runAsync(()->new RischiSubscriber(eventBusService).run());
        CompletableFuture.runAsync(()->new VisiteSubscriber(eventBusService).run());*/

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

        System.out.println("_______________INIT DB_______________");
        initData(gA, gR, gV, gU, gL, gCS);
        //sleep(7000);
        System.out.println("_______________TEST2_______________");
        test2(gA, gR, gV, gU, gL, eventBusService);
        //sleep(3000);
        System.out.println("_______________TEST1_______________");
        test(gA, gR, gV, gU, gL);
        //sleep(3000);
        System.out.println("_______________TEST DASHBOARD_______________");
        testDashboard(gU, gL);

        try {
            for (Thread t : threads) {
                t.interrupt();
                t.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}