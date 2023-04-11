package TestSuite;

import Account.UtenteInterno;
import AsyncIPCEventBus.GatewayLuoghi;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import Luoghi.Luogo;
import Luoghi.luoghisubscriber.LuoghiSubscriber;
import LuoghiGatewayDb.LuoghiGatewayDB;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GatewayLuoghiTest {
    static GatewayLuoghi gL;

    static Thread subscriberLuoghi;
    static Thread service;

    public GatewayLuoghiTest() throws SQLException {

    }

    @BeforeClass
    public static void initialize() throws SQLException {
        System.out.println("GatewayLuoghiTest: initialize");
        EventBusService eventBusService = new EventBusService();
        gL = new GatewayLuoghi(eventBusService);
        //service=CompletableFuture.runAsync(() -> eventBusService.run());
        //subscriberLuoghi=CompletableFuture.runAsync(() -> new LuoghiSubscriber(eventBusService).run());
        service = new Thread(() -> eventBusService.run());
        service.start();
        subscriberLuoghi = new Thread(() -> new LuoghiSubscriber(eventBusService).run());
        subscriberLuoghi.start();
    }

    @AfterClass
    public static void close() throws SQLException, InterruptedException {
        System.out.println("GatewayLuoghiTest: close");
        //subscriberLuoghi.complete(null);
        //service.complete(null);
        sleep(1000);
        subscriberLuoghi.interrupt();
        service.interrupt();
    }

    @Test
    public void _01addDipartimento() throws SQLException, InterruptedException {
        gL.addDipartimento(1, "nomeDipartimento", 1);
        sleep(500);
    }

    @Test
    public void _02addLuogo() throws SQLException, InterruptedException {
        gL.addLuogo(1, "nome", "aula", 1234567, 1);
        sleep(500);
    }

    @Test
    public void _03getResponsabileLuogo() throws SQLException {
        UtenteInterno u = gL.getResponsabileLuogo(new Luogo(1)).join();
        assertEquals(1, u.getCodice());
    }

    @Test
    public void _04insertRischioLuogo() throws SQLException {
        gL.insertRischioLuogo(1, 10123);
    }

    @Test
    public void _05insertRischioDipartimento() throws SQLException {
        gL.insertRischioDipartimento(1, 10123);
    }

    @Test
    public void _06deleteLuogo() throws SQLException {
        LuoghiGatewayDB lgDB = new LuoghiGatewayDB();
        lgDB.deleteLuogo(1);
    }

    @Test
    public void _07deleteDipartimento() throws SQLException {
        LuoghiGatewayDB lgDB = new LuoghiGatewayDB();
        lgDB.deleteDipartimento(1);
    }
}
