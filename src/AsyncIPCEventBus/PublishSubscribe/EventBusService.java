package AsyncIPCEventBus.PublishSubscribe;

import java.sql.SQLException;
import java.util.*;

import static java.lang.Thread.sleep;

public class EventBusService implements Runnable{
    private Map<String, Set<Subscriber>> TopicSubscribers;
    private static EventBusService eventBusService;
    private Queue<Message> MessageQueue;

    public EventBusService() {
        TopicSubscribers= new HashMap<String, Set<Subscriber>>();
        MessageQueue=new LinkedList<>();
    }

    public static EventBusService getIstance(){
        if(eventBusService==null){
            eventBusService=new EventBusService();
        }
        return eventBusService;
    }
    public void addMessage(Message message) throws SQLException {
        MessageQueue.add(message);
        //broadcast();
    }

    public void registerSubscriber(String topic, Subscriber subscriber) {
        if (TopicSubscribers.containsKey(topic)) {
            TopicSubscribers.get(topic).add(subscriber);
        } else {
            Set<Subscriber> subscribers=new HashSet<>();
            subscribers.add(subscriber);
            TopicSubscribers.put(topic, subscribers);
        }
    }

    public void removeSubscriber(String topic, Subscriber subscriber) {
        if (TopicSubscribers.containsKey(topic)) {
            TopicSubscribers.get(topic).remove(subscriber);
        }
    }

    public void sendMessage(Message m) throws SQLException {
        Set<Subscriber> subs=TopicSubscribers.get(m.getTopic());
        for(Subscriber s: subs){
            s.receiveMessage(m, this);
        }
    }

    public void broadcast() throws SQLException {
        while (!MessageQueue.isEmpty()) {
            Message message=MessageQueue.remove();
            if (TopicSubscribers.containsKey(message.getTopic())) {
                for (Subscriber subscriber : TopicSubscribers.get(message.getTopic())) {
                    List<Message> subscriberMessages = subscriber.getSubscriberMessages();
                    subscriberMessages.add(message);
                    //subscriber.setSubscriberMessages(subscriberMessages);
                    //subscriber.receiveMessage(message, this);
                }
            }
        }
    }

    public void getMessagesForTopicSuscriber(String topic, Subscriber subscriber) {
        while(!MessageQueue.isEmpty()){
            Message message=MessageQueue.remove();

            if(message.getTopic().equals(topic)){
                Set<Subscriber> topicSubscribers=TopicSubscribers.get(topic);
                for(Subscriber sub: topicSubscribers){
                    if(sub.equals(subscriber)){
                        List<Message> subscriberMessages = subscriber.getSubscriberMessages();
                        subscriberMessages.add(message);
                        subscriber.setSubscriberMessages(subscriberMessages);
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //System.out.println("Broadcasting...");
            try {
                broadcast();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
