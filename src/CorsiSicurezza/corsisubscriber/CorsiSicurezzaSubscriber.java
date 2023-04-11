package CorsiSicurezza.corsisubscriber;

import AsyncIPCEventBus.PublishSubscribe.*;
import CorsiSicurezza.Corso;
import CorsiSicurezza.CorsoType;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;

public class CorsiSicurezzaSubscriber extends Subscriber implements Runnable {

    Object response = null;
    EventBusService service;

    public CorsiSicurezzaSubscriber(/*String topic,*/ EventBusService service) {
        this.service = service;
        addSubscriber("CorsiSicurezza", service);
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
        System.out.println("SubscriberCorsi received message: " + message.getMessage());
        try {
        switch (message.getMessage()){
            case "addCorsoType":
                int id = (int) message.getParameters().get(0);
                String nome = (String) message.getParameters().get(1);
                String descrizione = (String) message.getParameters().get(2);
                int rischioAssociato = (int) message.getParameters().get(3);

                CorsoType type = new CorsoType(id, nome, descrizione, rischioAssociato);
                type.saveToDB();
                break;

            case "addCorso":
                String nomeCorso = (String) message.getParameters().get(0);
                String descrizioneCorso = (String) message.getParameters().get(1);
                int typeCorso = (int) message.getParameters().get(2);
                LocalDate inizioCorso = (LocalDate) message.getParameters().get(3);
                LocalDate fineCorso = (LocalDate) message.getParameters().get(4);

                Corso corso = new Corso(nomeCorso, descrizioneCorso, typeCorso, inizioCorso, fineCorso);
                corso.saveToDB();
                break;

            default:
                System.out.println("Message not recognized");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
