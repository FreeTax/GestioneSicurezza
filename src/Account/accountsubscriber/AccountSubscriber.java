package Account.accountsubscriber;

import Account.*;
import AsyncIPCEventBus.PublishSubscribe.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class AccountSubscriber extends Subscriber {
    public AccountSubscriber(/*String topic,*/ EventBusService service) {
        this.service=service;
        addSubscriber("Account", service);
    }

    /*@Override
    public void addMessage(Message message) {
        synchronized (subscriberMessages) {
            super.addMessage(message);
            subscriberMessages.notifyAll();
        }
    }*/
/*
    public synchronized void executeMessage(){

        Message message=getSubscriberMessages().remove(0);
        receiveMessage(message,service);
    }*/
    public Object getResponse(){
        return response;
    }
    public synchronized void receiveMessage(Message message, EventBusService service) {
       // Message message=getSubscriberMessages().remove(0);
        /*while (getSubscriberMessages().isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message message=getSubscriberMessages().remove(0);*/
        System.out.println("SubscriberConcr received message: " + message.getMessage());
        try {
            /*//Object obj = message.getData();
            Object data;
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
            }*/
            switch (message.getMessage()){
            case "insertUtente":
                Utente u = (Utente) message.getData();
                u.insertUtente();
                break;

            case "insertCreditoFormativo":
                CreditoFormativo cf = (CreditoFormativo) message.getData();
                cf.insertCreditoFormativo();
                break;

            case "insertRichiestaLuogo":
                RichiestaLuogo rl = (RichiestaLuogo) message.getData();
                rl.insertRichiesta();
                break;

            case "insertRichiestaDipartimento":
                RichiestaDipartimento rd = (RichiestaDipartimento) message.getData();
                rd.insertRichiesta();
                break;

            case "updateUtenteInterno":
                UtenteInterno u1 = (UtenteInterno) message.getData();
                u1.updateUtenteDb();
                break;

            case "updateUtenteEsterno":
                UtenteEsterno u2 = (UtenteEsterno) message.getData();
                u2.updateUtenteDb();
                break;

            case "sostieniCredito":
                List<Object> param = message.getParameters();
                int idUtente=(int)param.get(0);
                int codice=(int)param.get(1);
                String certificato=(String)param.get(2);
                Utente ui=(UtenteInterno)message.getData();
                ui.sostieniCredito(idUtente,codice,certificato);
                break;

            case "getCfuSostenuti":
                List<Object> param1 = message.getParameters();
                int idUtente1=(int)param1.get(0);
                //boolean interno=(boolean)param1.get(1);
                UtenteInterno ui1=new UtenteInterno();
                Publisher pub=new PublisherConcr();
                pub.publish(new Message(message.getReturnAddress(),"response",ui1.getCfuSostenuti(idUtente1),null),service);
                break;


            default:
                System.out.println("Message not recognized");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
