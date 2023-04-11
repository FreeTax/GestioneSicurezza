package AsyncIPCEventBus.PublishSubscribe;

import java.sql.SQLException;
import java.util.*;

public class EventBusService implements Runnable {
    private static EventBusService eventBusService;
    private Map<String, Set<Subscriber>> TopicSubscribers;
    private Queue<Message> MessageQueue;

    public EventBusService() {
        TopicSubscribers = new HashMap<String, Set<Subscriber>>();
        MessageQueue = new LinkedList<>();
    }

    public static EventBusService getIstance() {
        if (eventBusService == null) {
            eventBusService = new EventBusService();
        }
        return eventBusService;
    }

    public void addMessage(Message message) throws SQLException {
        synchronized (MessageQueue) {
            MessageQueue.add(message);
            MessageQueue.notifyAll();
            System.out.println("addMessage");
        }
        //MessageQueue.add(message);
        //sendMessage(message);
        //broadcast();
    }

    public void registerSubscriber(String topic, Subscriber subscriber) {
        if (TopicSubscribers.containsKey(topic)) {
            TopicSubscribers.get(topic).add(subscriber);
        } else {
            Set<Subscriber> subscribers = new HashSet<>();
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
        Set<Subscriber> subs = TopicSubscribers.get(m.getTopic());
        for (Subscriber s : subs) {
            s.receiveMessage(m, this);
        }
    }

    public void broadcast() throws SQLException {
        synchronized (MessageQueue) {
            System.out.println("i'm inside brodcast");
            while (!MessageQueue.isEmpty()) {
                Message message = MessageQueue.remove();
                if (TopicSubscribers.containsKey(message.getTopic())) {
                    for (Subscriber subscriber : TopicSubscribers.get(message.getTopic())) {
                        List<Message> subscriberMessages = subscriber.getSubscriberMessages();
                        subscriberMessages.add(message);
                        //subscriber.addMessage(message);
                        //notifyAll();
                        subscriber.setSubscriberMessages(subscriberMessages);
                        //subscriber.receiveMessage(message,this);
                    }
                }
            }
        }
    }

    public void getMessagesForTopicSuscriber(String topic, Subscriber subscriber) {
        while (!MessageQueue.isEmpty()) {
            Message message = MessageQueue.remove();
            if (message.getTopic().equals(topic)) {
                Set<Subscriber> topicSubscribers = TopicSubscribers.get(topic);
                for (Subscriber sub : topicSubscribers) {
                    if (sub.equals(subscriber)) {
                        List<Message> subscriberMessages = subscriber.getSubscriberMessages();
                        subscriberMessages.add(message);
                        subscriber.setSubscriberMessages(subscriberMessages);
                    }
                }
            }
        }
    }

    public void interrupt() {
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        synchronized (MessageQueue) {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        // Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
                        while (MessageQueue.isEmpty()) {
                            MessageQueue.wait();
                        }
                        broadcast();
                        //System.out.println("Broadcasting...");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("service interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}
