package AsyncIPCEventBus;

import AsyncIPCEventBus.PublishSubscribe.*;
import Rischi.Rischio;
import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class GatewayRischi {
    private Rischio r;
    private EventBusService eventBusService;
    private Subscriber sub;
    private Publisher pub;

    public GatewayRischi(EventBusService service) {
        eventBusService = service;
        //sub = new SubscriberConcr("Rischi", service);
        pub = new PublisherConcr();
    }

    public void insertRischioGenerico(int codice, String nome, String descrizione/*, String tipologia*/) throws SQLException {
        CompletableFuture.runAsync(() -> {
            try {
                r = new RischioGenerico(codice, nome, descrizione/*, tipologia*/);
                pub.publish(new Message("Rischi", "insertRischioGenerico", r, null), eventBusService);
                System.out.println("Rischio generico creato");
                //r.saveToDB();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).join();
    }

    public void insertRischioSpecifico(int codice, String nome, String descrizione/*, String tipologia*/) throws SQLException {
        CompletableFuture.runAsync(() -> {
            try {
                r = new RischioSpecifico(codice, nome, descrizione/*, tipologia*/);
                pub.publish(new Message("Rischi", "insertRischioSpecifico", r, null), eventBusService);
                System.out.println("Rischio specifico creato");
                //r.saveToDB();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).join();
    }

    public CompletableFuture<RischioGenerico> getRischioGenerico(int codice) throws SQLException {
        return CompletableFuture.supplyAsync(() -> {
            try {
               // r = new RischioGenerico(codice);
                //return (RischioGenerico) r;
                SubscriberConcr subscriber = new SubscriberConcr("RischioGenerico" + codice, eventBusService);
                pub.publish(new Message("Rischi", "getRischioGenerico", null, Arrays.asList(codice), "RischioGenerico" + codice), eventBusService);

                CompletableFuture<RischioGenerico> getRischioGenerico = CompletableFuture
                        .supplyAsync(() -> (RischioGenerico) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                        .completeOnTimeout(null, 3, TimeUnit.SECONDS);
                RischioGenerico res = getRischioGenerico.join();
                return res;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<RischioSpecifico> getRischioSpecifico(int codice) throws SQLException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                //r = new RischioSpecifico(codice);
                //return (RischioSpecifico) r;
                SubscriberConcr subscriber = new SubscriberConcr("RischioSpecifico" + codice, eventBusService);
                pub.publish(new Message("Rischi", "getRischioSpecifico", null, Arrays.asList(codice), "RischioSpecifico" + codice), eventBusService);

                CompletableFuture<RischioSpecifico> getRischioSpecifico = CompletableFuture
                        .supplyAsync(() -> (RischioSpecifico) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                        .completeOnTimeout(null, 3, TimeUnit.SECONDS);
                RischioSpecifico res = getRischioSpecifico.join();
                return res;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
