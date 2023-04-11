package AsyncIPCEventBus;

import Account.UtenteInterno;
import AsyncIPCEventBus.PublishSubscribe.*;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class GatewayLuoghi {
    private EventBusService eventBusService;
    private Subscriber sub;
    private Publisher pub;

    public GatewayLuoghi(EventBusService service) {
        eventBusService = service;
        //sub = new SubscriberConcr("Luoghi", service);
        pub = new PublisherConcr();
    }

    public void addLuogo(int codice, String nome, String tipo, int referente, int dipartimento) throws SQLException {
        CompletableFuture.runAsync(() -> {
            try {
                //Luogo l = new Luogo(codice, nome, tipo, referente, dipartimento);
                pub.publish(new Message("Luoghi", "addLuogo", null, Arrays.asList(codice,nome,tipo,referente,dipartimento)), eventBusService);
                System.out.println("Luogo creato");
                //l.saveToDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).join();
    }

    public void addDipartimento(int codice, String nome, int responsabile) throws SQLException {
        CompletableFuture.runAsync(() -> {
            try {
                //Dipartimento d = new Dipartimento(codice, nome, responsabile);
                pub.publish(new Message("Luoghi", "addDipartimento", null, Arrays.asList(codice,nome,responsabile)), eventBusService);
                System.out.println("Dipartimento creato");
                //d.saveToDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).join();
    }

    public CompletableFuture<UtenteInterno> getResponsabileLuogo(Luogo l) throws SQLException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                SubscriberConcr subscriber = new SubscriberConcr("ResponsabileLuogo" + l.getNome(), eventBusService);
                pub.publish(new Message("Luoghi", "getResponsabileLuogo", l, null, "ResponsabileLuogo" + l.getNome()), eventBusService);

                CompletableFuture<UtenteInterno> getResponsabileLuogo = CompletableFuture
                        .supplyAsync(() -> (UtenteInterno) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                        .completeOnTimeout(null, 3, TimeUnit.SECONDS);
                UtenteInterno res = getResponsabileLuogo.join();

                subscriber.unSubscribe("ResponsabileLuogo" + l.getNome(), eventBusService);
                return res;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<UtenteInterno> getResponsabileDipartimento(Dipartimento d) throws SQLException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                SubscriberConcr subscriber = new SubscriberConcr("ResponsabileDipartimento" + d.getNome(), eventBusService);
                pub.publish(new Message("Luoghi", "getResponsabileDipartimento", d, null, "ResponsabileDipartimento" + d.getNome()), eventBusService);

                CompletableFuture<UtenteInterno> getResponsabileDipartimento = CompletableFuture
                        .supplyAsync(() -> (UtenteInterno) subscriber.getSubscriberMessages().get(0).getData(), CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                        .completeOnTimeout(null, 3, TimeUnit.SECONDS);
                UtenteInterno res = getResponsabileDipartimento.join();

                subscriber.unSubscribe("ResponsabileDipartimento" + d.getNome(), eventBusService);
                return res;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void insertRischioLuogo(int codiceLuogo, int codiceRischio) throws SQLException {
        CompletableFuture.runAsync(() -> {
            try {
                Luogo l = new Luogo(codiceLuogo);
                pub.publish(new Message("Luoghi", "insertRischioLuogo", l, Collections.singletonList(codiceRischio)), eventBusService);
                System.out.println("Rischio luogo creato");
                //l.addRischio(codiceRischio);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).join();
    }

    public void insertRischioDipartimento(int codiceDipartimento, int codiceRischio) throws SQLException {
        CompletableFuture.runAsync(() -> {
            try {
                Dipartimento d = new Dipartimento(codiceDipartimento);
                pub.publish(new Message("Luoghi", "insertRischioDipartimento", d, Collections.singletonList(codiceRischio)), eventBusService);
                System.out.println("Rischio dipartimento creato");
                //d.addRischio(codiceRischio);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).join();
    }
}
