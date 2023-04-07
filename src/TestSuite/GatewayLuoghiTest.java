package TestSuite;

import Account.UtenteInterno;
import AsyncIPCEventBus.GatewayLuoghi;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import Luoghi.Luogo;
import LuoghiGatewayDb.LuoghiGatewayDB;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GatewayLuoghiTest {
    GatewayLuoghi gL=new GatewayLuoghi(EventBusService.getIstance());
    @Test
    public void _01addDipartimento() throws SQLException {
        gL.addDipartimento(1, "nomeDipartimento", 1);
    }

    @Test
    public void _02addLuogo() throws SQLException {
        gL.addLuogo(1,"nome", "aula", 1234567, 1);
    }

    @Test
    public void _03getResponsabileLuogo() throws SQLException {
        Luogo l = new Luogo(1);
        UtenteInterno u =gL.getResponsabileLuogo(l);
        assertEquals(1, u.getCodice());
    }

    @Test
    public void _04insertRischioLuogo() throws SQLException {
        gL.insertRischioLuogo(1, 10123);
    }

    @Test
    public void _05insertRischioDipartimento() throws SQLException {
        gL.insertRischioDipartimento(1, 10123);
    }

    @Test
    public void _06deleteLuogo() throws SQLException {
        LuoghiGatewayDB lgDB= new LuoghiGatewayDB();
        lgDB.deleteLuogo(1);
    }

    @Test
    public void _07deleteDipartimento() throws SQLException {
        LuoghiGatewayDB lgDB= new LuoghiGatewayDB();
        lgDB.deleteDipartimento(1);
    }
}
