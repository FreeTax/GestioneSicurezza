package GatewayIPC;

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
    @Order(1)
    public void insertSchedaVisita() throws SQLException {
        gV.aggiungiNuovaSchedaVisita(1234567);
    }

    @Test
    @Order(2)
    public void insertVisitaType() throws SQLException{
        vG.addVisitaType("visita1", "descrizione1", "tipo1");
    }
    @Test
    @Order(3)
    public void insertVisita() throws SQLException{
        gV.aggiungiVisistaUtente(1234567, 1, "dottore", "descrizione", Timestamp.valueOf("2020-10-03 10:10:10"), "effettuata", 1);
    }

    @Test
    @Order(4)
    public void getVisiteSostenute() throws SQLException{
        gV.getVisiteSostenute(1234567);
    }
}




