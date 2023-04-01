package TestSuite;

import GatewayIPC.GatewayVisite;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Timestamp;

import VisiteGateway.VisiteGatewayDb;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GatewayVisiteTest {
    GatewayVisite gV = new GatewayVisite();
    VisiteGatewayDb vG;
    public GatewayVisiteTest() throws SQLException {
        vG = new VisiteGatewayDb();
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





