package TestSuite;

import AsyncIPCEventBus.GatewayVisite;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import Visite.visitesubscriber.VisiteSubscriber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;

import VisiteGateway.VisiteGatewayDb;

import static java.lang.Thread.sleep;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GatewayVisiteTest {
    static GatewayVisite gV;
    static VisiteGatewayDb vG;

    static Thread service;
    static Thread subscriberVisite;
    public GatewayVisiteTest() throws SQLException {

    }

    @BeforeClass
    public static void initialize() throws SQLException {
        EventBusService eventBusService = new EventBusService();
        gV=new GatewayVisite(eventBusService);
        vG = new VisiteGatewayDb();
        System.out.println("GatewayVisiteTest: initialize");
        //CompletableFuture.runAsync(()->eventBusService.run());
        //CompletableFuture.runAsync(()->new VisiteSubscriber(eventBusService).run());
        service=new Thread(() -> eventBusService.run());
        service.start();
        subscriberVisite=new Thread(() -> new VisiteSubscriber(eventBusService).run());
        subscriberVisite.start();
    }

    @AfterClass
    public static void close() throws SQLException, InterruptedException {
        sleep(1000);
        System.out.println("GatewayVisiteTest: close");
        subscriberVisite.interrupt();
        service.interrupt();
    }

    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearDown() {
    }
    @Test
    public void insertSchedaVisita() throws SQLException {
        gV.addSchedaVisita(1234567);
    }

    @Test
    public void insertVisitaType() throws SQLException{
        vG.addVisitaType(1,"visita1", "descrizione1", "tipo1", 1);
    }
    @Test
    public void insertVisita() throws SQLException{
        gV.addVisitaUtente(1234567, 1, "dottore", "descrizione", Timestamp.valueOf("2020-10-03 10:10:10"), "da sostenere", 1);
    }

    @Test
    public void getVisiteSostenute() throws SQLException{
        gV.getVisiteSostenute(1234567);
    }
}





