package AsyncIPCEventBus;

import AsyncIPCEventBus.PublishSubscribe.*;
import Rischi.Rischio;
import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

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

    public RischioGenerico getRischioGenerico(int codice) throws SQLException {
        r = new RischioGenerico(codice);
        return (RischioGenerico) r;
    }

    public RischioSpecifico getRischioSpecifico(int codice) throws SQLException {
        r = new RischioSpecifico(codice);
        return (RischioSpecifico) r;
    }
}
