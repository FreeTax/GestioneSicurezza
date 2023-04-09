package AsyncIPCEventBus;

import Accessi.Accesso;
import Accessi.AccessoDipartimentoAbilitato;
import Accessi.AccessoLuogoAbilitato;
import Account.CreditoFormativo;
import Account.UtenteInterno;
import AsyncIPCEventBus.PublishSubscribe.*;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


public class GatewayAccessi {
    private EventBusService eventBusService;
    private Subscriber sub;
    private Publisher pub;

    private Executor deleyed=CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS);

    public GatewayAccessi() throws SQLException {
    }

    public GatewayAccessi(EventBusService service) {
        eventBusService = service;
        sub = new SubscriberConcr("Accessi", service );
        pub = new PublisherConcr();
    }

    public boolean insertAccessoDipartimento(int utente, int dipartimento, int authorizerUser) throws RuntimeException, SQLException {
            if(!GatewayUtente.checkAvanzato(authorizerUser)) throw new  RuntimeException("la persona che tenta di abilitare l'utente non è un utente avanzato");
            else {
                AccessoDipartimentoAbilitato a = new AccessoDipartimentoAbilitato(utente, dipartimento);
                Dipartimento d=new Dipartimento(dipartimento);

                SubscriberConcr sub = new SubscriberConcr("rischiDipartimento", eventBusService);
                pub.publish(new Message("Luoghi", "getRischiDipartimento", d, null,"rischiDipartimento"), eventBusService);
                CompletableFuture<ArrayList<Integer>> getRischiLuogo = CompletableFuture
                        .supplyAsync(()->(ArrayList<Integer>)sub.getSubscriberMessages().get(0).getData(),deleyed)
                        .completeOnTimeout(new ArrayList<>(), 4, TimeUnit.SECONDS);
                ArrayList<Integer> rischiDipartimento = getRischiLuogo.join();

                SubscriberConcr subscriber2 = new SubscriberConcr("CFUsostenuti", eventBusService);
                pub.publish(new Message("Account", "getCfuSostenuti",null,Arrays.asList(utente),"CFUsostenuti"), eventBusService);

                CompletableFuture<ArrayList<CreditoFormativo>> getCFU = CompletableFuture
                        .supplyAsync(()->(ArrayList<CreditoFormativo>)subscriber2.getSubscriberMessages().get(0).getData(),deleyed)
                        .completeOnTimeout(new ArrayList<>(), 4, TimeUnit.SECONDS)
                        .exceptionally(e -> new ArrayList<>());

                ArrayList<CreditoFormativo> cfuUtente = getCFU.join();

                ArrayList<Integer> cfuUtenteId = new ArrayList<>();

                cfuUtente.forEach(cfu ->cfuUtenteId.add(cfu.getIdRischio()));

                if(cfuUtenteId.containsAll(rischiDipartimento)) {
                    pub.publish(new Message("Accessi", "insertAccessoDipartimento", a, null), eventBusService);
                    System.out.println("insertAccessoDipartimento creato");
                    //a.insertAccesso();
                    return true;
                }
                else{
                    throw new RuntimeException("l'utente non ha i crediti formativi necessari per accedere al dipartimento");
                }
            }
    }

    public boolean insertAccessoLuogo(int utente, int luogo, int authorizerUser) throws RuntimeException, SQLException {
            if(!GatewayUtente.checkSupervisore(authorizerUser)) throw new RuntimeException("la persona che tenta di abilitare l'utente non è un utente avanzato o un supervisore");
            else {
                AccessoLuogoAbilitato a = new AccessoLuogoAbilitato(utente, luogo);
                Luogo l=new Luogo(luogo);

                SubscriberConcr subscriber = new SubscriberConcr("RischiLuogo", eventBusService);
                pub.publish(new Message("Luoghi", "getRischiLuogo",l,null,"RischiLuogo"), eventBusService);
                CompletableFuture<ArrayList<Integer>> getRischiLuogo = CompletableFuture
                        .supplyAsync(()->(ArrayList<Integer>)subscriber.getSubscriberMessages().get(0).getData(),deleyed)
                        .completeOnTimeout(new ArrayList<>(), 4, TimeUnit.SECONDS);

                ArrayList<Integer> rischiLuogo = getRischiLuogo.join();

                SubscriberConcr subscriber2 = new SubscriberConcr("CFUsostenuti", eventBusService);
                pub.publish(new Message("Account", "getCfuSostenuti",null,Arrays.asList(utente),"CFUsostenuti"), eventBusService);

                CompletableFuture<ArrayList<CreditoFormativo>> getCFU = CompletableFuture
                        .supplyAsync(()->(ArrayList<CreditoFormativo>)subscriber2.getSubscriberMessages().get(0).getData(),deleyed)
                        .completeOnTimeout(new ArrayList<>(), 4, TimeUnit.SECONDS)
                        .exceptionally(e -> new ArrayList<>());

                ArrayList<CreditoFormativo> cfuUtente = getCFU.join();
                ArrayList<Integer> cfuUtenteId = new ArrayList<>();

                cfuUtente.forEach(cfu ->cfuUtenteId.add(cfu.getIdRischio()));

                if(cfuUtenteId.containsAll(rischiLuogo)) {
                    pub.publish(new Message("Accessi","insertAccessoLuogo", a, null), eventBusService);
                    System.out.println("insertAccessoLuogo creato");
                    //a.insertAccesso();
                    return true;
                }
                else{
                    throw new RuntimeException("l'utente non ha i crediti formativi necessari per accedere al luogo");
                }
            }
    }

    public void updateAccessoDipartimento(int utente, int dipartimento) throws SQLException {
        AccessoDipartimentoAbilitato a = new AccessoDipartimentoAbilitato(utente, dipartimento);
        pub.publish(new Message("Accessi","updateAccessoDipartimento", a, null), eventBusService);
        System.out.println("updateAccessoDipartimento creato");
        //a.updateAccesso();
    }

    public void updateAccessoLuogo(int utente, int dipartimento) throws SQLException{
        AccessoLuogoAbilitato a = new AccessoLuogoAbilitato(utente, dipartimento);
        pub.publish(new Message("Accessi","updateAccessoLuogo", a, null), eventBusService);
        System.out.println("updateAccessoLuogo creato");
        //a.updateAccesso();
    }

    public void deleteAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        AccessoDipartimentoAbilitato a = new AccessoDipartimentoAbilitato(utente, dipartimento);
        pub.publish(new Message("Accessi","deleteAccessoDipartimento", a, null), eventBusService);
        System.out.println("deleteAccessoDipartimento creato");
        //a.deleteAccesso();
    }

    public void deleteAccessoLuogo(int utente, int dipartimento) throws SQLException{
        AccessoLuogoAbilitato a = new AccessoLuogoAbilitato(utente, dipartimento);
        pub.publish(new Message("Accessi","deleteAccessoLuogo", a, null), eventBusService);
        System.out.println("deleteAccessoLuogo creato");
        //a.deleteAccesso();
    }

    public ArrayList<Integer> getLuoghiFrequentati(int idUtente) throws SQLException{
         SubscriberConcr subscriber=new SubscriberConcr("LuoghiFrequentati"+idUtente, eventBusService);
         AccessoLuogoAbilitato a = new AccessoLuogoAbilitato(idUtente, 0);
         pub.publish(new Message("Accessi","getLuoghiFrequentati", a, Arrays.asList(idUtente),"LuoghiFrequentati"+idUtente), eventBusService);

        CompletableFuture<ArrayList<Integer>> getLuoghiFreq = CompletableFuture
                .supplyAsync(()->(ArrayList<Integer>)subscriber.getSubscriberMessages().get(0).getData(), deleyed)
                .completeOnTimeout(new ArrayList<>(), 4, TimeUnit.SECONDS);

         return getLuoghiFreq.join();
    }

    public ArrayList<Integer> getDipartimentiFrequentati(int idUtente) throws SQLException{
        SubscriberConcr subscriber=new SubscriberConcr("DipartimentiFrequentati", eventBusService);
        pub.publish(new Message("Accessi","getDipartimentiFrequentati", null, Arrays.asList(idUtente),"DipartimentiFrequentati"), eventBusService);

        CompletableFuture<ArrayList<Integer>> getDipartimentiFreq = CompletableFuture
                .supplyAsync(()->(ArrayList<Integer>)subscriber.getSubscriberMessages().get(0).getData(), deleyed)
                .completeOnTimeout(new ArrayList<>(), 4, TimeUnit.SECONDS);

        return getDipartimentiFreq.join();
    }

}
