package TestSuite;

import AccountGateway.UtenteGatewayDb;
import GatewayIPC.GatewayCorsiSicurezza;
import GatewayIPC.GatewayUtente;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.time.LocalDate;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class GatewayCorsiSicurezzaTest {
    GatewayCorsiSicurezza gC = new GatewayCorsiSicurezza();
    UtenteGatewayDb gU=new UtenteGatewayDb();

    public GatewayCorsiSicurezzaTest() throws SQLException {
    }

    @Test
    public void _1addNewCorsoType() throws SQLException {
        gC.addCorsoType(1, "nome", "descrizione", 1,9123456);
    }

    @Test
    public void _2addNewCorso() throws SQLException {
        LocalDate inizio = LocalDate.of(2022, 12, 03);
        LocalDate fine = LocalDate.of(2023, 03, 24);
        gC.addCorso("nomeCorso", "descrizione del corso", 1, inizio, fine, 9123456);
    }
}
