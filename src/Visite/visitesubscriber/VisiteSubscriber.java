package Visite.visitesubscriber;

import AsyncIPCEventBus.PublishSubscribe.*;
import Visite.SchedaVisita;

import java.lang.reflect.Method;
import java.util.List;

import static java.lang.Thread.sleep;

public class VisiteSubscriber extends Subscriber{
/*
    Object response=null;
    EventBusService service;*/
    public VisiteSubscriber(/*String topic,*/ EventBusService service) {
        this.service=service;
        addSubscriber("Visite", service);
    }
/*
    public void addSubscriber(String topic, EventBusService service) {
        service.registerSubscriber(topic, this);
    }

    public void unSubscribe(String topic, EventBusService service) {
        service.removeSubscriber(topic, this);
    }

    public void getMessagesForTopicSuscriber(String topic, EventBusService service) {
        service.getMessagesForTopicSuscriber(topic, this);
    }

    @Override
    public void setSubscriberMessages(List<Message> subscriberMessages) {
        synchronized (subscriberMessages) {
            super.setSubscriberMessages(subscriberMessages);
            subscriberMessages.notifyAll();
        }
    }
/*
    @Override
    public void addMessage(Message message) {
        synchronized (subscriberMessages) {
            super.addMessage(message);
            subscriberMessages.notifyAll();
        }
    }
*/
    /*
    public Object getResponse(){
        return response;
    }*/
    public void receiveMessage(Message message, EventBusService service) {
        System.out.println("SubscriberVisite received message: " + message.getMessage());
        try {
            switch (message.getMessage()){
            case "sostieniVisita":
                List<Object> param =message.getParameters();
                int idVisita=(int)param.get(0);
                String esito=(String)param.get(1);
                SchedaVisita sv=(SchedaVisita)message.getData();
                sv.sostieniVisita(idVisita,esito);
                break;

            case "getVisiteDaSostenere":
                SchedaVisita sv1=(SchedaVisita)message.getData();
                Publisher pub=new PublisherConcr();
                pub.publish(new Message(message.getReturnAddress(),"response",sv1.getVisiteDaSostenere(),null),service);
                break;

            case "getVisiteSostenute":
                SchedaVisita sv2=(SchedaVisita)message.getData();
                Publisher pub1=new PublisherConcr();
                pub1.publish(new Message(message.getReturnAddress(),"response",sv2.getVisiteEffettuate(),null),service);
                break;

            default:
                System.out.println("Message not recognized");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
