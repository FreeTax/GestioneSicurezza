package Luoghi.luoghisubscriber;

import Account.UtenteInterno;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import AsyncIPCEventBus.PublishSubscribe.Message;
import AsyncIPCEventBus.PublishSubscribe.PublisherConcr;
import AsyncIPCEventBus.PublishSubscribe.Subscriber;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

public class LuoghiSubscriber extends Subscriber {
    public LuoghiSubscriber(/*String topic,*/ EventBusService service) {
        this.service = service;
        addSubscriber("Luoghi", service);
    }

    /*
        @Override
        public void addMessage(Message message) {
            synchronized (subscriberMessages) {
                super.addMessage(message);
                subscriberMessages.notifyAll();
            }
        }*/
    public void receiveMessage(Message message, EventBusService service) {
        System.out.println("SubscriberLuoghi received message: " + message.getMessage());
        try {
            switch (message.getMessage()) {
                case"addLuogo":
                    int codice = (int) message.getParameters().get(0);
                    String nome = (String) message.getParameters().get(1);
                    String tipo = (String) message.getParameters().get(2);
                    int referente = (int) message.getParameters().get(3);
                    int dipartimento = (int) message.getParameters().get(4);
                    Luogo luogo = new Luogo(codice, nome, tipo, referente, dipartimento);
                    luogo.saveToDB();
                    break;

                case "addDipartimento":
                    int codiceD = (int) message.getParameters().get(0);
                    String nomeD = (String) message.getParameters().get(1);
                    int responsabile = (int) message.getParameters().get(2);
                    Dipartimento dipartimento1 = new Dipartimento(codiceD, nomeD, responsabile);
                    dipartimento1.saveToDB();
                    break;

                case "insertRischioLuogo":
                    Luogo l = (Luogo) message.getData();
                    int idRischio = (int) message.getParameters().get(0);
                    l.addRischio(idRischio);
                    break;

                case "insertRischioDipartimento":
                    Dipartimento d = (Dipartimento) message.getData();
                    int idRischioD = (int) message.getParameters().get(0);
                    d.addRischio(idRischioD);
                    break;

                case "getRischiLuogo":
                    Luogo l1 = (Luogo) message.getData();
                    PublisherConcr publisher = new PublisherConcr();
                    publisher.publish(new Message(message.getReturnAddress(), "response", l1.getRischi(), null), service);
                    break;

                case "getRischiDipartimento":
                    Dipartimento d1 = (Dipartimento) message.getData();
                    PublisherConcr publisher1 = new PublisherConcr();
                    publisher1.publish(new Message(message.getReturnAddress(), "response", d1.getRischi(), null), service);
                    break;

                case "getResponsabileLuogo":
                    Luogo l2 = (Luogo) message.getData();
                    PublisherConcr publisher2 = new PublisherConcr();
                    publisher2.publish(new Message(message.getReturnAddress(), "response", new UtenteInterno(l2.getReferente()), null), service);
                    break;

                case "getResponsabileDipartimento":
                    Dipartimento d2 = (Dipartimento) message.getData();
                    PublisherConcr publisher3 = new PublisherConcr();
                    publisher3.publish(new Message(message.getReturnAddress(), "response", new UtenteInterno(d2.getResponsabile()), null), service);
                    break;

                default:
                    System.out.println("Message not recognized");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
