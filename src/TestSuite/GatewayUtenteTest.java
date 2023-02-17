package TestSuite;

import AccountGateway.UtenteGatewayDb;
import GatewayIPC.GatewayUtente;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GatewayUtenteTest {
    GatewayUtente gU;
    UtenteGatewayDb uDb;

    public GatewayUtenteTest() throws SQLException {
        gU = new GatewayUtente();
        uDb=new UtenteGatewayDb()   ;
    }


    @Test
    @Order(1)
    public void insertUtenteInterno() throws SQLException {
        gU.insertUtenteInterno(1234567, "passwordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    @Order(2)
    public void insertUtenteEsterno() throws SQLException {
        gU.insertUtenteEsterno(19029420, "passwordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    @Order(3)
    public void insertCreditoFormativo() throws SQLException {
        gU.insertCreditoFormativo(1, "FE123");
    }

    @Test
    @Order(4)
    public void loginInterno() throws SQLException {
        assertTrue(gU.loginInterno(1234567, "passwordinterno"));
    }

    @Test
    @Order(5)
    public void loginInternoFalse() throws SQLException {
        assertTrue(!gU.loginInterno(1234567, "errata"));
    }

    @Test
    @Order(6)
    public void loginEsterno() throws SQLException {
        assertTrue(gU.loginEsterno(19029420, "passwordesterno"));
    }

    @Test
    @Order(7)
    public void loginEsternoFalse() throws SQLException {
        assertTrue(!gU.loginEsterno(19029420, "errata"));
    }

    @Test
    @Order(8)
    public void insertRichiestaLuogo() throws SQLException {
       int idUtente=uDb.getIdUtente(1234567,true);
       gU.insertRichiestaLuogo(idUtente,1);
    }

    @Test
    @Order(9)
    public void insertRichiestaDipartimento() throws SQLException {
        int idUtente=uDb.getIdUtente(19029420,false);
        gU.insertRichiestaDipartimento(idUtente,2);
    }

    @Test
    @Order(10)
    public void aggiornaUtenteInterno() throws SQLException {
        gU.aggiornaUtenteInterno(1234567, "nuovapasswordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento","base");
    }

    @Test
    @Order(11)
    public void aggiornaUtenteEsterno() throws SQLException {
        gU.aggiornaUtenteEsterno(19029420, "nuovapasswordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    @Order(12)
    public void caricaCertificazione() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        gU.caricaCertificazione(idUtente,1,"http:certificazione");
    }

    @Test
    @Order(13)
    public void getCFUSostenuti() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        ArrayList<String> creditisostenuti=gU.getCFUSostenuti(idUtente);
        assert creditisostenuti.size()==1;
        assertArrayEquals(creditisostenuti.toArray(),new String[]{"codice=1, idRischio=FE123, certificaEsterna=http:certificazione"});
    }

    @Test
    @Order(14)
    public void getRichiesteLuogo() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        ArrayList<String> richiesteluogo=gU.getRichiesteLuogo(idUtente);
        assert richiesteluogo.size()==1;
        assertArrayEquals(richiesteluogo.toArray(),new String[]{"idUtente=1, statoRichiesta=0, idLuogo=1"});
    }

    @Test
    @Order(15)
    public void getRichiesteDipartimento() throws SQLException {
        int idUtente=uDb.getIdUtente(19029420,false);
        ArrayList<String> richiestedipartimento=gU.getRichiesteDipartimento(idUtente);
        assert richiestedipartimento.size()==1;
        assertArrayEquals(richiestedipartimento.toArray(),new String[]{"idUtente=2, statoRichiesta=0, idDipartimento=2"});
    }
}