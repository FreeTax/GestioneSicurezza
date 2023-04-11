package AsyncIPCEventBus;

import Account.UtenteEsterno;
import Account.UtenteInterno;
import AsyncIPCEventBus.PublishSubscribe.*;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.sql.SQLException;
import java.util.Collections;

public class GatewayLuoghi {
    private EventBusService eventBusService;
    private Subscriber sub;
    private Publisher pub;

    public GatewayLuoghi(EventBusService service) {
        eventBusService = service;
        sub = new SubscriberConcr("Luoghi", service);
        pub = new PublisherConcr();
    }

    public void addLuogo(int codice, String nome, String tipo, int referente, int dipartimento) throws SQLException {
        Luogo l = new Luogo(codice, nome, tipo, referente, dipartimento);
        l.saveToDB();
    }

    public void addDipartimento(int codice, String nome, int responsabile) throws SQLException {
        Dipartimento d = new Dipartimento(codice, nome, responsabile);
        d.saveToDB();
    }

    public UtenteInterno getResponsabileLuogo(Luogo l) throws SQLException {
        UtenteInterno u = new UtenteInterno(l.getReferente());
        return u;
    }

    public UtenteEsterno getResponsabileDipartimento(Dipartimento d) throws SQLException {
        UtenteEsterno u = new UtenteEsterno(d.getResponsabile());
        return u;
    }

    public void insertRischioLuogo(int codiceLuogo, int codiceRischio) throws SQLException {
        Luogo l = new Luogo(codiceLuogo);
        pub.publish(new Message("Luoghi", "insertRischioLuogo", l, Collections.singletonList(codiceRischio)), eventBusService);
        System.out.println("Rischio luogo creato");
        //l.addRischio(codiceRischio);
    }

    public void insertRischioDipartimento(int codiceDipartimento, int codiceRischio) throws SQLException {
        Dipartimento d = new Dipartimento(codiceDipartimento);
        pub.publish(new Message("Luoghi", "insertRischioDipartimento", d, Collections.singletonList(codiceRischio)), eventBusService);
        System.out.println("Rischio dipartimento creato");
        //d.addRischio(codiceRischio);
    }


}
