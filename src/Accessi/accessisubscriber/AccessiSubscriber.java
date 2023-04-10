package Accessi.accessisubscriber;

import Accessi.AccessoDipartimentoAbilitato;
import Accessi.AccessoLuogoAbilitato;
import AsyncIPCEventBus.PublishSubscribe.*;

import java.lang.reflect.Method;
import java.util.List;

import static java.lang.Thread.sleep;

public class AccessiSubscriber extends Subscriber{
    public AccessiSubscriber(/*String topic,*/ EventBusService service) {
        this.service=service;
        addSubscriber("Accessi", service);
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
        System.out.println("SubscriberAccessi received message: " + message.getMessage());
        try {
            switch (message.getMessage()){
            case "insertAccessoDipartimento":
                AccessoDipartimentoAbilitato ad = (AccessoDipartimentoAbilitato) message.getData();
                ad.insertAccesso();
                break;

            case "insertAccessoLuogo":
                AccessoLuogoAbilitato al = (AccessoLuogoAbilitato) message.getData();
                al.insertAccesso();
                break;

            case "updateAccessoDipartimento":
                AccessoDipartimentoAbilitato ad1 = (AccessoDipartimentoAbilitato) message.getData();
                ad1.updateAccesso();
                break;

            case "updateAccessoLuogo":
                AccessoLuogoAbilitato al1 = (AccessoLuogoAbilitato) message.getData();
                al1.updateAccesso();
                break;

            case "deleteAccessoDipartimento":
                AccessoDipartimentoAbilitato ad2 = (AccessoDipartimentoAbilitato) message.getData();
                ad2.deleteAccesso();
                break;

            case "deleteAccessoLuogo":
                AccessoLuogoAbilitato al2 = (AccessoLuogoAbilitato) message.getData();
                al2.deleteAccesso();
                break;

            case "getLuoghiFrequentati":
                int idUtente = (int) message.getParameters().get(0);
                AccessoLuogoAbilitato al3 = (AccessoLuogoAbilitato) message.getData();
                Publisher publisher = new PublisherConcr();
                publisher.publish(new Message(message.getReturnAddress(),"response", al3.getLuoghiFrequentati(idUtente)), service);
                break;

            case "getDipartimentiFrequentati":
                int idUtente1 = (int) message.getParameters().get(0);
                AccessoDipartimentoAbilitato ad3 = new AccessoDipartimentoAbilitato(idUtente1,0);
                Publisher publisher1 = new PublisherConcr();
                publisher1.publish(new Message(message.getReturnAddress(),"response", ad3.getDipartimentiFrequentati(idUtente1)), service);
                break;

            default:
                System.out.println("Message not recognized");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
