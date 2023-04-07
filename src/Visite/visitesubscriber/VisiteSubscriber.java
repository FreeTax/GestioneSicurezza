package Visite.visitesubscriber;

import AsyncIPCEventBus.PublishSubscribe.*;
import Visite.SchedaVisita;

import java.lang.reflect.Method;
import java.util.List;

import static java.lang.Thread.sleep;

public class VisiteSubscriber extends Subscriber implements Runnable{

    Object response=null;
    EventBusService service;
    public VisiteSubscriber(/*String topic,*/ EventBusService service) {
        this.service=service;
        addSubscriber("Visite", service);
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

    @Override
    public void setSubscriberMessages(List<Message> subscriberMessages) {
        super.setSubscriberMessages(subscriberMessages);
    }

    public Object getResponse(){
        return response;
    }
    public void receiveMessage(Message message, EventBusService service) {
        System.out.println("SubscriberConcr received message: " + message.getMessage());
        try {
            Object obj = message.getData();
            List<Object> parameters = message.getParameters();
            Method method = null;
            Object returnObj;
            /*if (!message.getMessage().equals("response")) {
                if (parameters != null) {
                    Class[] cls = new Class[parameters.size()];
                    int i = 0;
                    for (Object p : parameters) {
                        cls[i] = p.getClass();
                        i++;
                    }
                    method = obj.getClass().getMethod(message.getMessage(), cls);
                    returnObj=method.invoke(obj, parameters.toArray());
                } else {
                    method = obj.getClass().getMethod(message.getMessage());
                    returnObj=method.invoke(obj);
                }
                if(message.getReturnAddress()!=null){
                    Publisher pub=new PublisherConcr();
                    pub.publish(new Message(message.getReturnAddress(),"response",returnObj,null),service);
                }
            }
            else {
                response=message.getData();
            }
            */
            switch (message.getMessage()){
            case "sostieniVisita":
                List<Object> param =message.getParameters();
                int idVisita=(int)param.get(0);
                String esito=(String)param.get(1);
                SchedaVisita sv=(SchedaVisita)message.getData();
                sv.sostieniVisita(idVisita,esito);
                break;

            default:
                System.out.println("Message not recognized");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //System.out.println("SubscriberConcr is running");
            if(getSubscriberMessages().size()!=0) {
                Message m = getSubscriberMessages().remove(0);
                receiveMessage(m, service);
            }
        }
    }
}
