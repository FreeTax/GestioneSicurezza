package GatewayIPC;

import AccountGateway.UtenteGatewayDb;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    void insertUtenteInterno() throws SQLException {
        gU.insertUtenteInterno(1234567, "passwordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    void insertUtenteEsterno() throws SQLException {
        gU.insertUtenteEsterno(19029420, "passwordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    void insertCreditoFormativo() throws SQLException {
        gU.insertCreditoFormativo(1);
    }

    @Test
    void loginInterno() throws SQLException {
        assertTrue(gU.loginInterno(1234567, "passwordinterno"));
    }

    @Test
    void loginInternoFalse() throws SQLException {
        assertTrue(!gU.loginInterno(1234567, "errata"));
    }

    @Test
    void loginEsterno() throws SQLException {
        assertTrue(gU.loginEsterno(19029420, "passwordesterno"));
    }

    @Test
    void loginEsternoFalse() throws SQLException {
        assertTrue(!gU.loginEsterno(19029420, "errata"));
    }

    @Test
    void insertRichiestaLuogo() throws SQLException {
       int idUtente=uDb.getIdUtente(1234567,true);
       gU.insertRichiestaLuogo(idUtente,1);
    }

    @Test
    void insertRichiestaDipartimento() throws SQLException {
        int idUtente=uDb.getIdUtente(19029420,false);
        gU.insertRichiestaDipartimento(idUtente,2);
    }

    @Test
    void aggiornaUtenteInterno() throws SQLException {
        gU.aggiornaUtenteInterno(1234567, "nuovapasswordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento","base");
    }

    @Test
    void aggiornaUtenteEsterno() throws SQLException {
        gU.aggiornaUtenteEsterno(19029420, "nuovapasswordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    void caricaCertificazione() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        gU.caricaCertificazione(idUtente,1,"http:certificazione");
    }

    @Test
    void getCFUSostenuti() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        ArrayList<String> creditisostenuti=gU.getCFUSostenuti(idUtente);
        assert creditisostenuti.size()==1;
    }

    @Test
    void getRichiesteLuogo() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        ArrayList<String> richiesteluogo=gU.getRichiesteLuogo(idUtente);
        assert richiesteluogo.size()==1;
    }

    @Test
    void getRichiesteDipartimento() throws SQLException {
        int idUtente=uDb.getIdUtente(19029420,false);
        ArrayList<String> richiestedipartimento=gU.getRichiesteDipartimento(idUtente);
        assert richiestedipartimento.size()==1;
    }
}