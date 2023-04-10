package CorsiSicurezza.corsisubscriber;

import AsyncIPCEventBus.PublishSubscribe.*;

import java.lang.reflect.Method;
import java.util.List;

import static java.lang.Thread.sleep;

public class CorsiSicurezzaSubscriber extends Subscriber implements Runnable{

    Object response=null;
    EventBusService service;
    public CorsiSicurezzaSubscriber(/*String topic,*/ EventBusService service) {
        this.service=service;
        addSubscriber("CorsiSicurezza", service);
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
        synchronized (subscriberMessages){
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
    public Object getResponse(){
        return response;
    }
    public void receiveMessage(Message message, EventBusService service) {
        System.out.println("SubscriberCorsi received message: " + message.getMessage());
        try {
            Object obj = message.getData();
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
            /*switch (message.getMessage()){
            case "insertUtente":
                data = message.getData();
                Utente u = (Utente) data;

                    u.insertUtente();
                break;

            case "insertRichiestaLuogo":
                data = message.getData();
                RichiestaLuogo rl = (RichiestaLuogo) data;
                rl.insertRichiesta();
                break;

            case "insertRichiestaDipartimento":
                data = message.getData();
                RichiestaDipartimento rd = (RichiestaDipartimento) data;
                rd.insertRichiesta();
                break;

            case "updateUtenteInterno":
                data = message.getData();
                UtenteInterno u1 = (UtenteInterno) data;
                u1.updateUtenteDb();
                break;

            case "updateUtenteEsterno":
                data = message.getData();
                UtenteEsterno u2 = (UtenteEsterno) data;
                u2.updateUtenteDb();
                break;

            case "sostieniCredito":
                List<Object> param = (List<Object>) message.getData();
                int idUtente=(int)param.get(0);
                int codice=(int)param.get(1);
                String certificato=(String)param.get(2);
                Utente ui=(UtenteInterno)param.get(3);
                ui.sostieniCredito(idUtente,codice,certificato);
                break;

            default:
                System.out.println("Message not recognized");
        }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            synchronized (subscriberMessages) {
                while (!Thread.currentThread().isInterrupted()) {
                    //System.out.println("SubscriberConcr is running");
                    while (subscriberMessages.isEmpty()) {
                        subscriberMessages.wait();
                    }
                    receiveMessage(subscriberMessages.remove(0),service);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
