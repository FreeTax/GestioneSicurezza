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
            /*Object obj = message.getData();
            List<Object> parameters = message.getParameters();
            Method method = null;
            Object returnObj;
            if (!message.getMessage().equals("response")) {
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

            default:
                System.out.println("Message not recognized");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
