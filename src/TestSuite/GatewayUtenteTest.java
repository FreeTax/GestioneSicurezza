package TestSuite;

import AccountGateway.UtenteGatewayDb;
import GatewayIPC.GatewayUtente;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GatewayUtenteTest {
    GatewayUtente gU;
    UtenteGatewayDb uDb;

    public GatewayUtenteTest() throws SQLException {
        gU = new GatewayUtente();
        uDb=new UtenteGatewayDb()   ;
    }
    @Test
    @BeforeAll
    public void _00reset() throws SQLException {
        InitDB.initDB();
    }
    @Test
    public void _01insertUtenteInterno() throws SQLException {
        gU.insertUtenteInterno(1234567, "passwordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento","base");
    }

    @Test
    public void _02insertUtenteEsterno() throws SQLException {
        gU.insertUtenteEsterno(19029420, "passwordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    public void _03insertCreditoFormativo() throws SQLException {
        gU.insertCreditoFormativo(1, 10123);
    }

    @Test
    public void _04loginInterno() throws SQLException {
        assertTrue(gU.loginInterno(1234567, "passwordinterno"));
    }

    @Test
    public void _05loginInternoFalse() throws SQLException {
        assertTrue(!gU.loginInterno(1234567, "errata"));
    }

    @Test
    public void _06loginEsterno() throws SQLException {
        assertTrue(gU.loginEsterno(19029420, "passwordesterno"));
    }

    @Test
    public void _07loginEsternoFalse() throws SQLException {
        assertTrue(!gU.loginEsterno(19029420, "errata"));
    }

    @Test
    public void _08insertRichiestaLuogo() throws SQLException {
       int idUtente=uDb.getIdUtente(1234567,true);
       gU.insertRichiestaLuogo(idUtente,1);
    }

    @Test
    public void _09insertRichiestaDipartimento() throws SQLException {
        int idUtente=uDb.getIdUtente(19029420,false);
        gU.insertRichiestaDipartimento(idUtente,2);
    }

    @Test
    public void _10aggiornaUtenteInterno() throws SQLException {
        gU.aggiornaUtenteInterno(1234567, "nuovapasswordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento","base");
    }

    @Test
    public void _11aggiornaUtenteEsterno() throws SQLException {
        gU.aggiornaUtenteEsterno(19029420, "nuovapasswordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }
    @Test
    public void _12caricaCertificazione() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        gU.sostieniCredito(idUtente,1,"http:certificazione");
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void _13getRichiesteLuogoNonAut() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        thrown.expect(java.lang.RuntimeException.class);
        thrown.expectMessage("Utente non autorizzato");
        ArrayList<String> richiesteluogo=gU.getRichiesteLuogo(idUtente);
        //assert richiesteluogo.size()==1;
        //assertArrayEquals(richiesteluogo.toArray(),new String[]{"idUtente=1, statoRichiesta=0, idLuogo=1"});
    }

    @Test
    public void _14getRichiesteLuogoAut() throws SQLException {
        gU.insertUtenteInterno(8912345, "passwordinterno", "nome", "cognome", "sesso", "2000-11-03", "Dipartimento","supervisore");
        int idUtente=uDb.getIdUtente(8912345,true);

        ArrayList<String> richiesteluogo=gU.getRichiesteLuogo(idUtente);
        assert richiesteluogo.size()==1;
        assertArrayEquals(richiesteluogo.toArray(),new String[]{"idUtente=1, statoRichiesta=0, idLuogo=1"});
    }
    @Test
    public void _15getRichiesteDipartimentoNonAut() throws SQLException {
        thrown.expect(java.lang.RuntimeException.class);
        thrown.expectMessage("Utente non autorizzato");
        int idUtente=uDb.getIdUtente(19029420,false);
        ArrayList<String> richiestedipartimento=gU.getRichiesteDipartimento(idUtente);
        //assert richiestedipartimento.size()==1;
        //assertArrayEquals(richiestedipartimento.toArray(),new String[]{"idUtente=2, statoRichiesta=0, idDipartimento=2"});
    }

    @Test
    public void _16getRichiesteDipartimentoAut() throws SQLException {
        gU.insertUtenteInterno(9123456, "passwordinterno", "nome", "cognome", "sesso", "2000-12-03", "Dipartimento","avanzato");
        int idUtente=uDb.getIdUtente(9123456,true);
        ArrayList<String> richiestedipartimento=gU.getRichiesteDipartimento(idUtente);
        assert richiestedipartimento.size()==1;
        assertArrayEquals(richiestedipartimento.toArray(),new String[]{"idUtente=2, statoRichiesta=0, idDipartimento=2"});
    }

    @Test
    public void _17getCFUSostenuti() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        int idAutorizzato=uDb.getIdUtente(8912345,true);
        ArrayList<String> creditisostenuti=gU.getCFUSostenuti(idAutorizzato, idUtente);
        assert creditisostenuti.size()==1;
        assertArrayEquals(creditisostenuti.toArray(),new String[]{"codice=1, idRischio=10123, certificaEsterna=http:certificazione"});
    }

    @Test
    public void _18getCFUSostenutiNonAut() throws SQLException {
        int idUtente=uDb.getIdUtente(1234567,true);
        int idAutorizzato=uDb.getIdUtente(1234567,true);
        thrown.expect(java.lang.RuntimeException.class);
        thrown.expectMessage("Utente non autorizzato");
        ArrayList<String> creditisostenuti=gU.getCFUSostenuti(idAutorizzato, idUtente);
    }
}