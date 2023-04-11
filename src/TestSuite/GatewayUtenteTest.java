package TestSuite;

import Account.accountsubscriber.AccountSubscriber;
import AccountGateway.UtenteGatewayDb;
import AsyncIPCEventBus.GatewayRischi;
import AsyncIPCEventBus.GatewayUtente;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import Rischi.rischisubscriber.RischiSubscriber;
import org.junit.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GatewayUtenteTest {
    static GatewayUtente gU;
    static UtenteGatewayDb uDb;

    static Thread subscriberAccount;
    static Thread service;
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    EventBusService eventBusService;

    public GatewayUtenteTest() throws SQLException {
        System.out.println("GatewayUtenteTest: constructor");
    }

    @BeforeClass
    public static void initialize() throws SQLException {
        InitDB.initDB();
        EventBusService eventBusService = new EventBusService();
        gU = new GatewayUtente(eventBusService);
        uDb = new UtenteGatewayDb();
        GatewayRischi gR = new GatewayRischi(eventBusService);

        //service=CompletableFuture.runAsync(()->eventBusService.run());
        // subscriberAccount=CompletableFuture.runAsync(()->new AccountSubscriber(eventBusService).run());
        service = new Thread(() -> eventBusService.run());
        service.start();
        subscriberAccount = new Thread(() -> new AccountSubscriber(eventBusService).run());
        subscriberAccount.start();
        CompletableFuture rischiSubscriber = CompletableFuture.runAsync(() -> new RischiSubscriber(eventBusService).run());
        System.out.println("GatewayUtenteTest: initialize");
        gR.insertRischioSpecifico(10123, "Rischio1", "Descrizione1");
        rischiSubscriber.complete(null);

    }

    @AfterClass
    public static void close() throws SQLException, InterruptedException {
        System.out.println("GatewayUtenteTest: close");
        //service.complete(null);
        //subscriberAccount.cancel(true);
        sleep(1000);
        service.interrupt();
        subscriberAccount.interrupt();
    }

    @Test
    @BeforeAll
    public void _00reset() throws SQLException {
    }

    @Test
    public void _01insertUtenteInterno() throws SQLException, InterruptedException {
        gU.insertUtenteInterno(1234567, "passwordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento", "base");
    }

    @Test
    public void _02insertUtenteEsterno() throws SQLException, InterruptedException {
        gU.insertUtenteEsterno(19029420, "passwordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    public void _03insertCreditoFormativo() throws SQLException, InterruptedException {
        eventBusService = new EventBusService();


        gU.insertCreditoFormativo(1, 10123);
        sleep(1000);
    }

    @Test
    public void _04loginInterno() throws SQLException, InterruptedException {
        assertTrue(gU.loginInterno(1234567, "passwordinterno"));
    }

    @Test
    public void _05loginInternoFalse() throws SQLException {
        assertTrue(!gU.loginInterno(1234567, "errata"));
    }

    @Test
    public void _06loginEsterno() throws SQLException, InterruptedException {
        assertTrue(gU.loginEsterno(19029420, "passwordesterno"));
    }

    @Test
    public void _07loginEsternoFalse() throws SQLException {
        assertTrue(!gU.loginEsterno(19029420, "errata"));
    }

    @Test
    public void _08insertRichiestaLuogo() throws SQLException {
        int idUtente = uDb.getIdUtente(1234567, true);
        gU.insertRichiestaLuogo(idUtente, 1);
    }

    @Test
    public void _09insertRichiestaDipartimento() throws SQLException {
        int idUtente = uDb.getIdUtente(19029420, false);
        gU.insertRichiestaDipartimento(idUtente, 2);
    }

    @Test
    public void _10aggiornaUtenteInterno() throws SQLException, InterruptedException {
        gU.aggiornaUtenteInterno(1234567, "nuovapasswordinterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento", "base");
    }

    @Test
    public void _11aggiornaUtenteEsterno() throws SQLException, InterruptedException {
        gU.aggiornaUtenteEsterno(19029420, "nuovapasswordesterno", "nome", "cognome", "sesso", "2000-10-03", "Dipartimento");
    }

    @Test
    public void _12caricaCertificazione() throws SQLException {
        int idUtente = uDb.getIdUtente(1234567, true);
        gU.sostieniCredito(idUtente, 1, "http:certificazione");
    }

    @Test
    public void _13getRichiesteLuogoNonAut() throws SQLException {
        thrown.expect(java.lang.RuntimeException.class);
        thrown.expectMessage("Utente non autorizzato");
        ArrayList<String> richiesteluogo = gU.getRichiesteLuogo(1234567);
        //assert richiesteluogo.size()==1;
        //assertArrayEquals(richiesteluogo.toArray(),new String[]{"idUtente=1, statoRichiesta=0, idLuogo=1"});
    }

    @Test
    public void _14getRichiesteLuogoAut() throws SQLException, InterruptedException {
        gU.insertUtenteInterno(8912345, "passwordinterno", "nome", "cognome", "sesso", "2000-11-03", "Dipartimento", "supervisore");
        //int idUtente=uDb.getIdUtente(8912345,true);
        sleep(1000);
        ArrayList<String> richiesteluogo = gU.getRichiesteLuogo(8912345);
        assert richiesteluogo.size() == 1;
        assertArrayEquals(richiesteluogo.toArray(), new String[]{"idUtente=1, statoRichiesta=0, idLuogo=1"});
    }

    @Test
    public void _15getRichiesteDipartimentoNonAut() throws SQLException {
        thrown.expect(java.lang.RuntimeException.class);
        thrown.expectMessage("Utente non autorizzato");
        //int idUtente=uDb.getIdUtente(19029420,false);
        ArrayList<String> richiestedipartimento = gU.getRichiesteDipartimento(19029420);
        //assert richiestedipartimento.size()==1;
        //assertArrayEquals(richiestedipartimento.toArray(),new String[]{"idUtente=2, statoRichiesta=0, idDipartimento=2"});
    }

    @Test
    public void _16getRichiesteDipartimentoAut() throws SQLException, InterruptedException {
        gU.insertUtenteInterno(9123456, "passwordinterno", "nome", "cognome", "sesso", "2000-12-03", "Dipartimento", "avanzato");
        //int idUtente=uDb.getIdUtente(9123456,true);
        sleep(1000);
        ArrayList<String> richiestedipartimento = gU.getRichiesteDipartimento(9123456);
        assert richiestedipartimento.size() == 1;
        assertArrayEquals(richiestedipartimento.toArray(), new String[]{"idUtente=2, statoRichiesta=0, idDipartimento=2"});
    }

    @Test
    public void _17getCFUSostenuti() throws SQLException {
        int idUtente = uDb.getIdUtente(1234567, true);
        //int idAutorizzato=uDb.getIdUtente(8912345,true);
        ArrayList<String> creditisostenuti = gU.getCFUSostenuti(8912345, idUtente);
        assert creditisostenuti.size() == 1;
        assertEquals(creditisostenuti.remove(0), "Rischio1");
    }

    @Test
    public void _18getCFUSostenutiNonAut() throws SQLException {
        int idUtente = uDb.getIdUtente(1234567, true);
        //int idAutorizzato=uDb.getIdUtente(1234567,true);
        thrown.expect(java.lang.RuntimeException.class);
        thrown.expectMessage("Utente non autorizzato");
        ArrayList<String> creditisostenuti = gU.getCFUSostenuti(1234567, idUtente);

    }
}