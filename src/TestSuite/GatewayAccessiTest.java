package TestSuite;

import Accessi.accessisubscriber.AccessiSubscriber;
import AsyncIPCEventBus.GatewayAccessi;
import AsyncIPCEventBus.GatewayLuoghi;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import Luoghi.luoghisubscriber.LuoghiSubscriber;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GatewayAccessiTest {
    static GatewayAccessi gatewayAccessi;

    static Thread subscriberAccessi;
    static Thread subscriberLuoghi;
    static Thread service;

    static GatewayLuoghi gl;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public GatewayAccessiTest() throws SQLException {

    }

    @BeforeClass
    public static void initialize() throws SQLException, InterruptedException {
        //InitDB.initDB();
        sleep(3000);
        EventBusService eventBusService = new EventBusService();
        gatewayAccessi = new GatewayAccessi(eventBusService);
        gl = new GatewayLuoghi(eventBusService);
        System.out.println("GatewayAccessiTest: initialize");
        service = new Thread(() -> eventBusService.run());
        service.start();
        subscriberAccessi = new Thread(() -> new AccessiSubscriber(eventBusService).run());
        subscriberAccessi.start();
        subscriberLuoghi = new Thread(() -> new LuoghiSubscriber(eventBusService).run());
        subscriberLuoghi.start();
        //service= CompletableFuture.runAsync(() -> eventBusService.run());
        //subscriberAccessi=CompletableFuture.runAsync(()->new AccessiSubscriber(eventBusService).run());
        //subscriberLuoghi=CompletableFuture subscriberLuoghi=CompletableFuture.runAsync(() -> new LuoghiSubscriber(eventBusService).run());

        //subscriberLuoghi.complete(null);
    }

    @AfterClass
    public static void close() throws SQLException, InterruptedException {
        //InitDB.dropDB();
        sleep(3000);
        service.interrupt();
        subscriberAccessi.interrupt();
        subscriberLuoghi.interrupt();
    }

    @Test
    public void _00init() throws SQLException, InterruptedException {
        gl.addDipartimento(3, "nomeDipartimento", 1);
        gl.addDipartimento(4, "nome", 1);
        sleep(500);
        gl.insertRischioDipartimento(4, 1);
        gl.addLuogo(3, "nome", "aula", 1234567, 1);
        gl.addLuogo(4, "nome", "aula", 1234567, 1);
        sleep(500);
        gl.insertRischioLuogo(4, 3);
        sleep(6000);
    }

    @Test
    public void _01insertAccessoDipartimento() throws SQLException, InterruptedException {
        //gatewayAccessi = new GatewayAccessi();
        // GatewayUtente gu = new GatewayUtente(eventBusService);
        //GatewayLuoghi gL = new GatewayLuoghi(eventBusService);
        //gu.insertUtenteInterno(1, "password", "nome", "cognome", "maschile", "2000-10-03", "1","base");
        //gu.aggiornaUtenteInterno(2, "password", "nome2", "cognome2", "maschile", "2000-10-03", "1","avanzato");
        //gl.addDipartimento(1, "nomeDipartimento", 1);
        boolean res = gatewayAccessi.insertAccessoDipartimento(1, 3, 9123456).join();
        assertEquals(true, res); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }

    @Test
    public void _02insertAccessoDipartimentoNonAut() throws SQLException, InterruptedException {
        //gatewayAccessi = new GatewayAccessi(eventBusService);
        /*gl.addDipartimento(2, "nome", 1);
        gl.insertRischioDipartimento(2,1);*/
        thrown.expectMessage("l'utente non ha i crediti formativi necessari per accedere al dipartimento");
        boolean res = gatewayAccessi.insertAccessoDipartimento(2, 4, 9123456).join();
        assertEquals(false, res); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }

    @Test
    public void _03insertAccessoLuogo() throws SQLException {
        /*GatewayLuoghi gL = new GatewayLuoghi(eventBusService);*/
        //gl.addLuogo(1,"nome", "aula", 1234567, 1);
        assertEquals(true, gatewayAccessi.insertAccessoLuogo(1, 3, 8912345).join()); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }

    @Test
    public void _04insertAccessoLuogoNonAut() throws SQLException {
        /*gatewayAccessi = new GatewayAccessi();
        GatewayLuoghi gl = new GatewayLuoghi(eventBusService);*/
        //gl.addLuogo(2, "nome", "aula", 1234567, 1);
        //gl.insertRischioLuogo(2,1);
        thrown.expect(java.lang.RuntimeException.class);
        thrown.expectMessage("l'utente 1 non ha i crediti formativi necessari per accedere al luogo");
        //gatewayAccessi.insertAccessoLuogo(1, 2, 3);
        assertEquals(false, gatewayAccessi.insertAccessoLuogo(1, 4, 8912345).join()); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }

    @Test
    public void _05updateAccessoDipartimento() throws SQLException {
        // gatewayAccessi = new GatewayAccessi(eventBusService);
        gatewayAccessi.updateAccessoDipartimento(1, 3);
    }

    @Test
    public void _06updateAccessoLuogo() throws SQLException {
        // gatewayAccessi = new GatewayAccessi(eventBusService);
        gatewayAccessi.updateAccessoLuogo(1, 4);
    }

    @Test
    public void _07deleteAccessoDipartimento() throws SQLException {
        //  gatewayAccessi = new GatewayAccessi(eventBusService);
        gatewayAccessi.deleteAccessoDipartimento(1, 3);
    }

    @Test
    public void _08deleteAccessoLuogo() throws SQLException {
        //gatewayAccessi = new GatewayAccessi(eventBusService);
        gatewayAccessi.deleteAccessoLuogo(1, 4);
    }

}
