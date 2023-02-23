package TestSuite;
import GatewayIPC.GatewayAccessi;
import GatewayIPC.GatewayUtente;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GatewayAccessiTest {
    GatewayAccessi gatewayAccessi;

    @Test
    public void insertAccessoDipartimento() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        GatewayUtente gu = new GatewayUtente();
        gu.insertUtenteInterno(1, "password", "nome", "cognome", "maschile", "2000-10-03", "1","base");
        gatewayAccessi.inserAccessoDipartimento(1, 1, "avanzato"); //in a real implementation, the user would be logged in and the type would be taken from the User's object

    }

    @Test
    public void insertAccessoLuogo() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.insertAccessoLuogo(1,  1, "avanzato");
    }

    @Test
    public void updateAccessoDipartimento() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.updateAccessoDipartimento(1, 2);
    }

    @Test
    public void updateAccessoLuogo() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.updateAccessoLuogo(1,  2);
    }

    @Test
    public void deleteAccessoDipartimento() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.deleteAccessoDipartimento(1,  2);
    }

    @Test
    public void deleteAccessoLuogo() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.deleteAccessoLuogo(1,  2);
    }

}
