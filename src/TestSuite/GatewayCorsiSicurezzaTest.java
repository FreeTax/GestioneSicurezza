package TestSuite;

import GatewayIPC.GatewayCorsiSicurezza;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.SQLException;
import java.time.LocalDate;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class GatewayCorsiSicurezzaTest {
    GatewayCorsiSicurezza gC = new GatewayCorsiSicurezza();
    @Test
    @Order(1)
    public void addNewCorsoType() throws SQLException {
        gC.addCorsoType(1, "nome", "descrizione", null);
    }

    @Test
    @Order(2)
    public void addNewCorso() throws SQLException {
        LocalDate inizio = LocalDate.of(2022, 12, 03);
        LocalDate fine = LocalDate.of(2023, 03, 24);
        gC.addCorso("nomeCorso", "descrizione del corso", 1, inizio, fine);

    }
}
