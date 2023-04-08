package Accessi.accessisubscriber;

import Accessi.AccessoDipartimentoAbilitato;
import Accessi.AccessoLuogoAbilitato;
import AsyncIPCEventBus.PublishSubscribe.*;

import java.lang.reflect.Method;
import java.util.List;

import static java.lang.Thread.sleep;

public class AccessiSubscriber extends Subscriber{
    public AccessiSubscriber(/*String topic,*/ EventBusService service) {
        this.service=service;
        addSubscriber("Accessi", service);
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
            }*/
            switch (message.getMessage()){
            case "insertAccessoDipartimento":
                AccessoDipartimentoAbilitato ad = (AccessoDipartimentoAbilitato) message.getData();
                ad.insertAccesso();
                break;

            case "insertAccessoLuogo":
                AccessoLuogoAbilitato al = (AccessoLuogoAbilitato) message.getData();
                al.insertAccesso();
                break;

            case "updateAccessoDipartimento":
                AccessoDipartimentoAbilitato ad1 = (AccessoDipartimentoAbilitato) message.getData();
                ad1.updateAccesso();
                break;

            case "updateAccessoLuogo":
                AccessoLuogoAbilitato al1 = (AccessoLuogoAbilitato) message.getData();
                al1.updateAccesso();
                break;

            case "deleteAccessoDipartimento":
                AccessoDipartimentoAbilitato ad2 = (AccessoDipartimentoAbilitato) message.getData();
                ad2.deleteAccesso();
                break;

            case "deleteAccessoLuogo":
                AccessoLuogoAbilitato al2 = (AccessoLuogoAbilitato) message.getData();
                al2.deleteAccesso();
                break;

            default:
                System.out.println("Message not recognized");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
