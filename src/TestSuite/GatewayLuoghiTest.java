package TestSuite;

import Account.UtenteInterno;
import GatewayIPC.GatewayLuoghi;
import Luoghi.Luogo;
import LuoghiGatewayDb.LuoghiGatewayDB;
import org.junit.Test;
import org.junit.jupiter.api.Order;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GatewayLuoghiTest {
    GatewayLuoghi gL=new GatewayLuoghi();
    @Test
    @Order(1)
    public void addDipartimento() throws SQLException {
        gL.addDipartimento(1, "nomeDipartimento", 1);
    }

    @Test
    @Order(2)
    public void addLuogo() throws SQLException {
        gL.addLuogo(1,"nome", "aula", 1234567, 1);
    }

    @Test
    @Order(3)
    public void getResponsabileLuogo() throws SQLException {
        Luogo l = new Luogo(1);
        UtenteInterno u =gL.getResponsabileLuogo(l);
        assertEquals(1, u.getCodice());
    }

    @Test
    @Order(4)
    public void delateLuogo() throws SQLException {
        LuoghiGatewayDB lgDB= new LuoghiGatewayDB();
        lgDB.deleteLuogo(1);
    }

    @Test
    @Order(5)
    public void deleteDipartimento() throws SQLException {
        LuoghiGatewayDB lgDB= new LuoghiGatewayDB();
        lgDB.deleteDipartimento(1);
    }
}
