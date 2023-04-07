package AsyncIPCEventBus.PublishSubscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Subscriber {
    private List<Message> subscriberMessages=new ArrayList <Message>();

    public List<Message> getSubscriberMessages() {
        return subscriberMessages;
    }

    public void setSubscriberMessages(List<Message> subscriberMessages) {
        this.subscriberMessages = subscriberMessages;
    }

    public abstract void addSubscriber(String topic, EventBusService service);

    public abstract void unSubscribe(String topic, EventBusService service);

    public abstract void getMessagesForTopicSuscriber(String topic, EventBusService service);

    public void receiveMessage(Message m, EventBusService s) throws SQLException {
    }
}
