package AsyncIPCEventBus;
import AsyncIPCEventBus.PublishSubscribe.*;
import Rischi.Rischio;
import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;

import java.sql.SQLException;

public class GatewayRischi {
    private Rischio r;
    private EventBusService eventBusService;
    private Subscriber sub;
    private Publisher pub;

    public GatewayRischi(EventBusService service) {
        eventBusService = service;
        sub = new SubscriberConcr("Rischi", service );
        pub = new PublisherConcr();
    }
    public void insertRischioGenerico(int codice, String nome, String descrizione/*, String tipologia*/) throws SQLException {
        r=new RischioGenerico(codice, nome, descrizione/*, tipologia*/);
        pub.publish(new Message("Rischi","insertRischioGenerico", r, null), eventBusService);
        System.out.println("Rischio generico creato");
        //r.saveToDB();
    }

    public void insertRischioSpecifico(int codice, String nome, String descrizione/*, String tipologia*/) throws SQLException {
        r=new RischioSpecifico(codice, nome, descrizione/*, tipologia*/);
        pub.publish(new Message("Rischi","insertRischioSpecifico", r, null), eventBusService);
        System.out.println("Rischio specifico creato");
        //r.saveToDB();
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
