package AsyncIPCEventBus.PublishSubscribe;

import java.util.List;

public class Message {
    String returnAddress;
    private String topic;
    private String message;
    private Object data;
    private List<Object> parameters;

    public Message() {
    }

    public Message(String topic, String message, Object data) {
        this.topic = topic;
        this.message = message;
        this.data = data;

    }

    public Message(String topic, String message, Object data, List<Object> parameters) {
        this.topic = topic;
        this.message = message;
        this.data = data;
        this.parameters = parameters;
    }

    public Message(String topic, String message, Object data, List<Object> parameters, String returnAddress) {
        this.topic = topic;
        this.message = message;
        this.data = data;
        this.parameters = parameters;
        this.returnAddress = returnAddress;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public String getReturnAddress() {
        return returnAddress;
    }
}
