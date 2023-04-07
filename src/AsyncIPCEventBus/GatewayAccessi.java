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


public class GatewayAccessi {
    private EventBusService eventBusService;
    private Subscriber sub;
    private Publisher pub;
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
                ArrayList<Integer> rischiDipartimento = d.getRischi();
                ArrayList<CreditoFormativo> cfuUtente = new UtenteInterno().getCfuSostenuti(utente);
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
                ArrayList<Integer> rischiLuogo = l.getRischi();
                ArrayList<CreditoFormativo> cfuUtente = new UtenteInterno().getCfuSostenuti(utente);
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
         AccessoLuogoAbilitato a = new AccessoLuogoAbilitato(idUtente, 0);
         return a.getLuoghiFrequentati(idUtente);
    }

    public ArrayList<Integer> getDipartimentiFrequentati(int idUtente) throws SQLException{
        AccessoDipartimentoAbilitato a = new AccessoDipartimentoAbilitato(idUtente, 0);
        return a.getDipartimentiFrequentati(idUtente);
    }

}
