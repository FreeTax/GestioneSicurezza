package AsyncIPCEventBus;

import AsyncIPCEventBus.PublishSubscribe.*;
import CorsiSicurezza.Corso;
import CorsiSicurezza.CorsoType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class GatewayCorsiSicurezza {
    private EventBusService eventBusService;
    private PublisherConcr pub;
    public GatewayCorsiSicurezza(EventBusService service) {
        //Subscriber subscriber = new SubscriberConcr("CorsiSicurezza", service);
        eventBusService = service;
        pub = new PublisherConcr();
    }

    public void addCorsoType(int id, String nome, String descrizione, int rischioAssociato, int authorizerUser) throws SQLException {
        CompletableFuture.runAsync(() -> {
            try {
                if (!GatewayUtente.checkAvanzato(authorizerUser))
                    throw new RuntimeException("l'utente che tenta di inserire il corsotype non è avanzato");
                else {
                    //CorsoType type = new CorsoType(id, nome, descrizione, rischioAssociato);
                    //type.saveToDB();
                    pub.publish(new Message("CorsiSicurezza", "addCorsoType", null, Arrays.asList(id,nome,descrizione,rischioAssociato)), eventBusService);
                    System.out.println("CorsoType creato");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).join();
    }

    public void addCorso(String nome, String descrizione, int type, LocalDate inizio, LocalDate fine, int authorizerUser) throws SQLException {
        CompletableFuture.runAsync(() -> {
            try {
                if (!GatewayUtente.checkAvanzato(authorizerUser))
                    throw new RuntimeException("l'utente che tenta di inserire il corso non è avanzato");
                else {
                    //Corso corso = new Corso(nome, descrizione, type, inizio, fine);
                    //corso.saveToDB();
                    pub.publish(new Message("CorsiSicurezza", "addCorso", null, Arrays.asList(nome,descrizione,type,inizio,fine)), eventBusService);
                    System.out.println("Corso creato");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).join();
    }
}
