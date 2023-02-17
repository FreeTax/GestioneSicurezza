package GatewayIPC;
import org.junit.Test;
import org.junit.jupiter.api.Order;

import java.sql.SQLException;


public class GatewayAccessiTest {
    GatewayAccessi gatewayAccessi;

    @Test
    @Order(1)
    public void insertAccessoDipartimento() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        GatewayUtente gu = new GatewayUtente();
        gu.insertUtenteInterno(1, "password", "nome", "cognome", "maschile", "1", "1");
        gatewayAccessi.inserAccessoDipartimento(1, 1);
    }

    @Test
    @Order(2)
    public void insertAccessoLuogo() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.insertAccessoLuogo(1,  1);
    }

    @Test
    @Order(3)
    public void updateAccessoDipartimento() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.updateAccessoDipartimento(1, 2);
    }

    @Test
    @Order(4)
    public void updateAccessoLuogo() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.updateAccessoLuogo(1,  2);
    }

    @Test
    @Order(5)
    public void deleteAccessoDipartimento() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.deleteAccessoDipartimento(1,  2);
    }

    @Test
    @Order(6)
    public void deleteAccessoLuogo() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.deleteAccessoLuogo(1,  2);
    }

}
