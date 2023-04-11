package TestSuite;

import AccountGateway.UtenteGatewayDb;
import AsyncIPCEventBus.GatewayCorsiSicurezza;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import CorsiSicurezza.corsisubscriber.CorsiSicurezzaSubscriber;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class GatewayCorsiSicurezzaTest {
    static GatewayCorsiSicurezza gC;
    UtenteGatewayDb gU = new UtenteGatewayDb();

    public GatewayCorsiSicurezzaTest() throws SQLException {

    }

    @BeforeClass
    public static void initialize() throws SQLException {
        EventBusService eventBusService = new EventBusService();
        gC = new GatewayCorsiSicurezza(eventBusService);
        CompletableFuture.runAsync(() -> eventBusService.run());
        CompletableFuture.runAsync(() -> new CorsiSicurezzaSubscriber(eventBusService).run());
    }

    @Test
    public void _1addNewCorsoType() throws SQLException {
        gC.addCorsoType(1, "nome", "descrizione", 1, 9123456);
    }

    @Test
    public void _2addNewCorso() throws SQLException {
        LocalDate inizio = LocalDate.of(2022, 12, 03);
        LocalDate fine = LocalDate.of(2023, 03, 24);
        gC.addCorso("nomeCorso", "descrizione del corso", 1, inizio, fine, 9123456);
    }
}
