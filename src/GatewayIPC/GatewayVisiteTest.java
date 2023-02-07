package GatewayIPC;

import org.junit.Test;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Timestamp;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GatewayVisiteTest {
    GatewayVisite gV = new GatewayVisite();
    public GatewayVisiteTest() {
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
    @Order(2)
    public void insertVisita() throws SQLException{
        gV.aggiungiVisistaUtente(1234567, 1, "dottore", "descrizione", Timestamp.valueOf("2020-10-03 10:10:10"), "stato", 1);
    }
}





