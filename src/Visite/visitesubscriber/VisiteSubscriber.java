package Visite.visitesubscriber;

import AsyncIPCEventBus.PublishSubscribe.*;
import Visite.SchedaVisita;
import Visite.Visita;
import Visite.VisitaType;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class VisiteSubscriber extends Subscriber {
    /*
        Object response=null;
        EventBusService service;*/
    public VisiteSubscriber(/*String topic,*/ EventBusService service) {
        this.service = service;
        addSubscriber("Visite", service);
    }

    /*
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
            synchronized (subscriberMessages) {
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
        }
    */
    /*
    public Object getResponse(){
        return response;
    }*/
    public void receiveMessage(Message message, EventBusService service) {
        System.out.println("SubscriberVisite received message: " + message.getMessage());
        try {
            switch (message.getMessage()) {
                case "addVisitaType":
                    int id = (int) message.getParameters().get(0);
                    String nome = (String) message.getParameters().get(1);
                    String descrizione = (String) message.getParameters().get(2);
                    String frequenza = (String) message.getParameters().get(3);
                    int rischioAssociato = (int) message.getParameters().get(4);

                    VisitaType type = new VisitaType(id, nome, descrizione, frequenza,rischioAssociato);
                    type.saveToDb();
                    break;

                case "addVisitaUtente":
                    int idUtente = (int) message.getParameters().get(0);
                    int codiceVisita = (int) message.getParameters().get(1);
                    String dottore = (String) message.getParameters().get(2);
                    String descrizione1 = (String) message.getParameters().get(3);
                    Timestamp data = (Timestamp) message.getParameters().get(4);
                    String stato = (String) message.getParameters().get(5);
                    int idType = (int) message.getParameters().get(6);


                    SchedaVisita sv = new SchedaVisita(idUtente);
                    Visita v = new Visita(codiceVisita, dottore, descrizione1, data, stato, "", sv.getId(), idType);
                    sv.insertVisitaDaSostentere(v);
                    break;

                case "addSchedaVisita":
                    int idUtente2 = (int) message.getParameters().get(0);
                    SchedaVisita sv2 = new SchedaVisita(idUtente2);
                    sv2.saveNewSchedaVisita();
                    break;

                case "addVisita":
                    int id2 = (int) message.getParameters().get(0);
                    String dottore2 = (String) message.getParameters().get(1);
                    String descrizione2 = (String) message.getParameters().get(2);
                    Timestamp data2 = (Timestamp) message.getParameters().get(3);
                    String stato2 = (String) message.getParameters().get(4);
                    String esito = (String) message.getParameters().get(5);
                    int schedavisite = (int) message.getParameters().get(6);
                    int idType2 = (int) message.getParameters().get(7);

                    Visita v2 = new Visita(id2, dottore2, descrizione2, data2, stato2, esito, schedavisite, idType2);
                    v2.saveToDB();
                    break;

                case "sostieniVisita":
                    List<Object> param = message.getParameters();
                    int idVisita = (int) param.get(0);
                    String esito3 = (String) param.get(1);
                    SchedaVisita sv3 = (SchedaVisita) message.getData();
                    sv3.sostieniVisita(idVisita, esito3);
                    break;

                case "getVisiteDaSostenere":
                    SchedaVisita sv1 = (SchedaVisita) message.getData();
                    Publisher pub = new PublisherConcr();
                    pub.publish(new Message(message.getReturnAddress(), "response", sv1.getVisiteDaSostenere(), null), service);
                    break;

                case "getVisiteSostenute":
                    SchedaVisita sv4 = (SchedaVisita) message.getData();
                    Publisher pub1 = new PublisherConcr();
                    pub1.publish(new Message(message.getReturnAddress(), "response", sv4.getVisiteEffettuate(), null), service);
                    break;

                default:
                    System.out.println("Message not recognized");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
