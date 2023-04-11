package Rischi.rischisubscriber;

import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import AsyncIPCEventBus.PublishSubscribe.Message;
import AsyncIPCEventBus.PublishSubscribe.Subscriber;
import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;

public class RischiSubscriber extends Subscriber {
    /*
        Object response=null;
        EventBusService service;*/
    public RischiSubscriber(/*String topic,*/ EventBusService service) {
        this.service = service;
        addSubscriber("Rischi", service);
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
        }*/
    /*
    public Object getResponse(){
        return response;
    }*/
    public void receiveMessage(Message message, EventBusService service) {
        System.out.println("SubscriberRischi received message: " + message.getMessage());
        try {
            switch (message.getMessage()) {
                case "insertRischioGenerico":
                    RischioGenerico rg = (RischioGenerico) message.getData();
                    rg.saveToDB();
                    break;

                case "insertRischioSpecifico":
                    RischioSpecifico rs = (RischioSpecifico) message.getData();
                    rs.saveToDB();
                    break;

                default:
                    System.out.println("Message not recognized");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
