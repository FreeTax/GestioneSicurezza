package TestSuite;

import Accessi.accessisubscriber.AccessiSubscriber;
import AsyncIPCEventBus.GatewayAccessi;
import AsyncIPCEventBus.GatewayLuoghi;
import AsyncIPCEventBus.GatewayUtente;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import Luoghi.luoghisubscriber.LuoghiSubscriber;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GatewayAccessiTest {
    GatewayAccessi gatewayAccessi;

    GatewayLuoghi gl;

    public GatewayAccessiTest() throws SQLException {
        EventBusService eventBusService = new EventBusService();
        gatewayAccessi = new GatewayAccessi(eventBusService);
        gl = new GatewayLuoghi(eventBusService);

        CompletableFuture.runAsync(() -> eventBusService.run());
        CompletableFuture.runAsync(() -> new AccessiSubscriber(eventBusService).run());
        CompletableFuture.runAsync(() -> new LuoghiSubscriber(eventBusService).run());
    }
    @Test
    public void _00beforeallmethods() throws SQLException, InterruptedException {
        gl.addDipartimento(3, "nomeDipartimento", 1);
        gl.addDipartimento(4, "nome", 1);
        gl.addLuogo(1,"nome", "aula", 1234567, 1);
        gl.addLuogo(2, "nome", "aula", 1234567, 1);
        gl.insertRischioDipartimento(4,1000);
        sleep(5000);
    }
    @Test
    public void _01insertAccessoDipartimento() throws SQLException {
        //gatewayAccessi = new GatewayAccessi();
       // GatewayUtente gu = new GatewayUtente(eventBusService);
        //GatewayLuoghi gL = new GatewayLuoghi(eventBusService);
        //gu.insertUtenteInterno(1, "password", "nome", "cognome", "maschile", "2000-10-03", "1","base");
        //gu.aggiornaUtenteInterno(2, "password", "nome2", "cognome2", "maschile", "2000-10-03", "1","avanzato");
        assertEquals(true, gatewayAccessi.insertAccessoDipartimento(1, 3, 9123456)); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void _02insertAccessoDipartimentoNonAut() throws SQLException, InterruptedException {
        //gatewayAccessi = new GatewayAccessi(eventBusService);

        thrown.expectMessage("l'utente non ha i crediti formativi necessari per accedere al dipartimento");
        assertEquals(false, gatewayAccessi.insertAccessoDipartimento(1, 4, 9123456)); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }
    @Test
    public void _03insertAccessoLuogo() throws SQLException {
        /*GatewayLuoghi gL = new GatewayLuoghi(eventBusService);
        gL.addLuogo(1,"nome", "aula", 1234567, 1);
        gatewayAccessi = new GatewayAccessi(eventBusService);*/
        assertEquals(true, gatewayAccessi.insertAccessoLuogo(1, 1, 8912345)); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }
    @Test
    public void _04insertAccessoLuogoNonAut() throws SQLException {
        /*gatewayAccessi = new GatewayAccessi();
        GatewayLuoghi gl = new GatewayLuoghi(eventBusService);
        gl.addLuogo(2, "nome", "aula", 1234567, 1);
        gl.insertRischioLuogo(2,1);*/
        thrown.expect(java.lang.RuntimeException.class);
        thrown.expectMessage("l'utente non ha i crediti formativi necessari per accedere al luogo");
        //gatewayAccessi.insertAccessoLuogo(1, 2, 3);
        assertEquals(false, gatewayAccessi.insertAccessoLuogo(1, 2, 8912345)); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }

    @Test
    public void _05updateAccessoDipartimento() throws SQLException {
       // gatewayAccessi = new GatewayAccessi(eventBusService);
        gatewayAccessi.updateAccessoDipartimento(1, 2);
    }

    @Test
    public void _06updateAccessoLuogo() throws SQLException {
       // gatewayAccessi = new GatewayAccessi(eventBusService);
        gatewayAccessi.updateAccessoLuogo(1,  2);
    }

    @Test
    public void _07deleteAccessoDipartimento() throws SQLException {
      //  gatewayAccessi = new GatewayAccessi(eventBusService);
        gatewayAccessi.deleteAccessoDipartimento(1,  2);
    }

    @Test
    public void _08deleteAccessoLuogo() throws SQLException {
        //gatewayAccessi = new GatewayAccessi(eventBusService);
        gatewayAccessi.deleteAccessoLuogo(1,  2);
    }

}
