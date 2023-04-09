package Account.accountsubscriber;

import Account.*;
import AsyncIPCEventBus.PublishSubscribe.*;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class AccountSubscriber extends Subscriber {
    public AccountSubscriber(/*String topic,*/ EventBusService service) {
        this.service = service;
        addSubscriber("Account", service);
    }

    /*@Override
    public void addMessage(Message message) {
        synchronized (subscriberMessages) {
            super.addMessage(message);
            subscriberMessages.notifyAll();
        }
    }*/
/*
    public synchronized void executeMessage(){

        Message message=getSubscriberMessages().remove(0);
        receiveMessage(message,service);
    }*/
    public Object getResponse() {
        return response;
    }

    public synchronized void receiveMessage(Message message, EventBusService service) {
        // Message message=getSubscriberMessages().remove(0);
        /*while (getSubscriberMessages().isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message message=getSubscriberMessages().remove(0);*/
        System.out.println("SubscriberConcr received message: " + message.getMessage());
        try {
            switch (message.getMessage()) {
                case "insertUtente":
                    Utente u = (Utente) message.getData();
                    u.insertUtente();
                    break;

                case "insertCreditoFormativo":
                    CreditoFormativo cf = (CreditoFormativo) message.getData();
                    cf.insertCreditoFormativo();
                    break;

                case "insertRichiestaLuogo":
                    RichiestaLuogo rl = (RichiestaLuogo) message.getData();
                    rl.insertRichiesta();
                    break;

                case "insertRichiestaDipartimento":
                    RichiestaDipartimento rd = (RichiestaDipartimento) message.getData();
                    rd.insertRichiesta();
                    break;

                case "updateUtenteInterno":
                    UtenteInterno u1 = (UtenteInterno) message.getData();
                    u1.updateUtenteDb();
                    break;

                case "updateUtenteEsterno":
                    UtenteEsterno u2 = (UtenteEsterno) message.getData();
                    u2.updateUtenteDb();
                    break;

                case "sostieniCredito":
                    List<Object> param = message.getParameters();
                    int idUtente = (int) param.get(0);
                    int codice = (int) param.get(1);
                    String certificato = (String) param.get(2);
                    Utente ui = (UtenteInterno) message.getData();
                    ui.sostieniCredito(idUtente, codice, certificato);
                    break;

                case "getCfuSostenuti":
                    List<Object> param1 = message.getParameters();
                    int idUtente1 = (int) param1.get(0);
                    //boolean interno=(boolean)param1.get(1);
                    UtenteInterno ui1 = new UtenteInterno();
                    Publisher pub = new PublisherConcr();
                    pub.publish(new Message(message.getReturnAddress(), "response", ui1.getCfuSostenuti(idUtente1), null), service);
                    break;

                case "getCFUdaSostenere":
                    int idUtente2 = (int) message.getParameters().get(0);
                    ArrayList<RichiestaLuogo> richiesteLuogo=null;
                    ArrayList<RichiestaDipartimento> richiesteDipartimento=null;
                    ArrayList<CreditoFormativo> cfuUtente;

                    if(new UtenteInterno(idUtente2).getNome()!=null){
                        UtenteInterno ui2=new UtenteInterno(idUtente2);
                        richiesteLuogo = ui2.getRichiesteLuogo();
                        richiesteDipartimento =ui2.getRichiesteDipartimento();
                        cfuUtente=ui2.getCfuSostenuti();
                    }
                    else {
                        UtenteEsterno ue=new UtenteEsterno(idUtente2);
                        richiesteLuogo =ue.getRichiesteLuogo();
                        richiesteDipartimento =ue.getRichiesteDipartimento();
                        cfuUtente=ue.getCfuSostenuti();
                    }

                    ArrayList<Integer> rischi = new ArrayList<>();

                    for(RichiestaLuogo rl2 : richiesteLuogo){
                        Luogo l=new Luogo(rl2.getIdLuogo());
                        ArrayList<Integer> rischiLuogo = l.getRischi();
                        for(Integer i : rischiLuogo){
                            if(!rischi.contains(i))
                                rischi.add(i);
                        }
                    }

                    for(RichiestaDipartimento rd2 : richiesteDipartimento){
                        Dipartimento d=new Dipartimento(rd2.getIdDipartimento());
                        ArrayList<Integer> rischiDipartimento = d.getRischi();
                        for(Integer i : rischiDipartimento){
                            if(!rischi.contains(i))
                                rischi.add(i);
                        }
                    }

                    ArrayList<Integer> cfuUtenteId = new ArrayList<>();

                    cfuUtente.forEach(cfu ->cfuUtenteId.add(cfu.getIdRischio()));

                    rischi.removeAll(cfuUtenteId);

                    Publisher pub11 = new PublisherConcr();
                    pub11.publish(new Message(message.getReturnAddress(), "response", rischi, null), service);

                case "getRichiesteLuogoUtenti":
                    int idUtente3 = (int) message.getParameters().get(0);
                    Publisher pub1 = new PublisherConcr();
                    pub1.publish(new Message(message.getReturnAddress(), "response", new UtenteInterno(idUtente3).getRichiesteLuogoUtenti(), null), service);
                    break;

                case "getRichiesteDipartimentoUtenti":
                    int idUtente4 = (int) message.getParameters().get(0);
                    Publisher pub2 = new PublisherConcr();
                    pub2.publish(new Message(message.getReturnAddress(), "response", new UtenteInterno(idUtente4).getRichiesteDipartimentoUtenti(), null), service);
                    break;

                default:
                    System.out.println("Message not recognized");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
