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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GatewayLuoghiTest {
    static GatewayLuoghi gL;

    static CompletableFuture subscriberLuoghi;
    static CompletableFuture service;
    public GatewayLuoghiTest() throws SQLException {

    }

    @BeforeClass
    public static void initialize() throws SQLException {
        System.out.println("GatewayLuoghiTest: initialize");
        EventBusService eventBusService = new EventBusService();
        gL = new GatewayLuoghi(eventBusService);
        service=CompletableFuture.runAsync(() -> eventBusService.run());
        subscriberLuoghi=CompletableFuture.runAsync(() -> new LuoghiSubscriber(eventBusService).run());
    }

    @AfterClass
    public static void close() throws SQLException {
        System.out.println("GatewayLuoghiTest: close");
        subscriberLuoghi.complete(null);
        service.complete(null);
    }
    @Test
    public void _01addDipartimento() throws SQLException {
        gL.addDipartimento(1, "nomeDipartimento", 1);
    }

    @Test
    public void _02addLuogo() throws SQLException {
        gL.addLuogo(1,"nome", "aula", 1234567, 1);
    }

    @Test
    public void _03getResponsabileLuogo() throws SQLException {
        Luogo l = new Luogo(1);
        UtenteInterno u =gL.getResponsabileLuogo(l);
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
        LuoghiGatewayDB lgDB= new LuoghiGatewayDB();
        lgDB.deleteLuogo(1);
    }

    @Test
    public void _07deleteDipartimento() throws SQLException {
        LuoghiGatewayDB lgDB= new LuoghiGatewayDB();
        lgDB.deleteDipartimento(1);
    }
}
