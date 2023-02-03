package GatewayIPC;

import AccountGateway.UtenteGatewayDb;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class GatewayUtenteTest {
    GatewayUtente gU = new GatewayUtente();
    UtenteGatewayDb uDb=new UtenteGatewayDb();

    GatewayUtenteTest() throws SQLException {
    }


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void insertUtenteInterno() throws SQLException {
        gU.insertUtenteInterno(1234567, "passwordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    @Order(2)
    void insertUtenteEsterno() throws SQLException {
        gU.insertUtenteEsterno(19029420, "passwordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    @Order(3)
    void insertCreditoFormativo() throws SQLException {
        gU.insertCreditoFormativo(1);
    }

    @Test
    @Order(4)
    void loginInterno() throws SQLException {
        assertTrue(gU.loginInterno(1234567, "passwordinterno"));
    }

    @Test
    @Order(5)
    void loginInternoFalse() throws SQLException {
        assertTrue(!gU.loginInterno(1234567, "errata"));
    }

    @Test
    @Order(6)
    void loginEsterno() throws SQLException {
        assertTrue(gU.loginEsterno(19029420, "passwordesterno"));
    }

    @Test
    @Order(7)
    void loginEsternoFalse() throws SQLException {
        assertTrue(!gU.loginEsterno(19029420, "errata"));
    }

    @Test
    @Order(8)
    void insertRichiestaLuogo() throws SQLException {
       int idUtente=uDb.getIdUtente(1234567,true);
       gU.insertRichiestaLuogo(idUtente,1);
    }

    @Test
    @Order(9)
    void insertRichiestaDipartimento() throws SQLException {
        int idUtente=uDb.getIdUtente(19029420,false);
        gU.insertRichiestaDipartimento(idUtente,2);
    }

    @Test
    @Order(10)
    void aggiornaUtenteInterno() throws SQLException {
        gU.aggiornaUtenteInterno(1234567, "nuovapasswordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento","base");
    }

    @Test
    @Order(11)
    void aggiornaUtenteEsterno() throws SQLException {
        gU.aggiornaUtenteEsterno(19029420, "nuovapasswordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    @Order(12)
    void caricaCertificazione() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        gU.caricaCertificazione(idUtente,1,"http:certificazione");
    }

    @Test
    @Order(13)
    void getCFUSostenuti() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        ArrayList<String> creditisostenuti=gU.getCFUSostenuti(idUtente);
        assert creditisostenuti.size()==1;
    }

    @Test
    @Order(14)
    void getRichiesteLuogo() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        ArrayList<String> richiesteluogo=gU.getRichiesteLuogo(idUtente);
        assert richiesteluogo.size()==1;
    }

    @Test
    @Order(15)
    void getRichiesteDipartimento() throws SQLException {
        int idUtente=uDb.getIdUtente(19029420,false);
        ArrayList<String> richiestedipartimento=gU.getRichiesteDipartimento(idUtente);
        assert richiestedipartimento.size()==1;
    }
}