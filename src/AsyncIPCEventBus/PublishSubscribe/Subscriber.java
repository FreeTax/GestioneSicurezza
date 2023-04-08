package AsyncIPCEventBus.PublishSubscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Subscriber implements Runnable {
    protected List<Message> subscriberMessages=new ArrayList <Message>();

    protected EventBusService service;

    protected Object response;
    public List<Message> getSubscriberMessages() {
        return subscriberMessages;
    }

    public void setSubscriberMessages(List<Message> subscriberMessages) {
        synchronized (subscriberMessages) {
            this.subscriberMessages = subscriberMessages;
            subscriberMessages.notifyAll();
        }
    }

    public void addSubscriber(String topic, EventBusService service) {
        service.registerSubscriber(topic, this);
    }

    public void unSubscribe(String topic, EventBusService service) {
        service.removeSubscriber(topic, this);
    }

    public void getMessagesForTopicSuscriber(String topic, EventBusService service) {
        service.getMessagesForTopicSuscriber(topic, this);
    }

    public void receiveMessage(Message m, EventBusService s)  {
    }

    public void addMessage(Message message) {
        subscriberMessages.add(message);
    }

    public Object getResponse(){
        return response;
    }

    @Override
    public void run() {
        try {
            synchronized (subscriberMessages) {
                while (!Thread.currentThread().isInterrupted()) {
                    //Thread.sleep(100);
                    //System.out.println("SubscriberConcr is running");
                    while (subscriberMessages.isEmpty()) {
                        subscriberMessages.wait();
                    }
                    receiveMessage(subscriberMessages.remove(0),service);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("SubscriberConcr interrupted");
        }
    }
}
