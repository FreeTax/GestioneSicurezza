package TestSuite;
import GatewayIPC.GatewayAccessi;
import GatewayIPC.GatewayLuoghi;
import GatewayIPC.GatewayUtente;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GatewayAccessiTest {
    GatewayAccessi gatewayAccessi;

    @Test
    public void _01insertAccessoDipartimento() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        GatewayUtente gu = new GatewayUtente();
        GatewayLuoghi gL = new GatewayLuoghi();
        gL.addDipartimento(1, "nomeDipartimento", 1);
        //gu.insertUtenteInterno(1, "password", "nome", "cognome", "maschile", "2000-10-03", "1","base");
        //gu.aggiornaUtenteInterno(2, "password", "nome2", "cognome2", "maschile", "2000-10-03", "1","avanzato");
        assertTrue(gatewayAccessi.insertAccessoDipartimento(1, 1, 9123456)); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void _02insertAccessoDipartimentoNonAut() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        GatewayLuoghi gl = new GatewayLuoghi();
        gl.addDipartimento(2, "nome", 1);
        gl.insertRischioDipartimento(2,1);
        thrown.expectMessage("l'utente non ha i crediti formativi necessari per accedere al dipartimento");
        assertFalse(gatewayAccessi.insertAccessoDipartimento(1, 2, 9123456)); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }
    @Test
    public void _03insertAccessoLuogo() throws SQLException {
        GatewayLuoghi gL = new GatewayLuoghi();
        gL.addLuogo(1,"nome", "aula", 1234567, 1);
        gatewayAccessi = new GatewayAccessi();
        assertTrue(gatewayAccessi.insertAccessoLuogo(1, 1, 8912345)); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }
    @Test
    public void _04insertAccessoLuogoNonAut() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        GatewayLuoghi gl = new GatewayLuoghi();
        gl.addLuogo(2, "nome", "aula", 1234567, 1);
        gl.insertRischioLuogo(2,1);
        thrown.expect(java.lang.RuntimeException.class);
        thrown.expectMessage("l'utente non ha i crediti formativi necessari per accedere al luogo");
        //gatewayAccessi.insertAccessoLuogo(1, 2, 3);
        assertFalse(gatewayAccessi.insertAccessoLuogo(1, 2, 8912345)); //in a real implementation, the user would be logged in and the type would be taken from the User's object
    }

    @Test
    public void _05updateAccessoDipartimento() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.updateAccessoDipartimento(1, 2);
    }

    @Test
    public void _06updateAccessoLuogo() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.updateAccessoLuogo(1,  2);
    }

    @Test
    public void _07deleteAccessoDipartimento() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.deleteAccessoDipartimento(1,  2);
    }

    @Test
    public void _08deleteAccessoLuogo() throws SQLException {
        gatewayAccessi = new GatewayAccessi();
        gatewayAccessi.deleteAccessoLuogo(1,  2);
    }

}
