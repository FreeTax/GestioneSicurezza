package Luoghi.luoghisubscriber;

import AsyncIPCEventBus.PublishSubscribe.*;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.lang.reflect.Method;
import java.util.List;

import static java.lang.Thread.sleep;

public class LuoghiSubscriber extends Subscriber{
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
        System.out.println("SubscriberConcr received message: " + message.getMessage());
        try {
            switch (message.getMessage()){
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

            default:
                System.out.println("Message not recognized");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
