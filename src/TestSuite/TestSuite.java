package TestSuite;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import TestSuite.InitDB;

import java.sql.*;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        GatewayUtenteTest.class,
        GatewayLuoghiTest.class,
        GatewayAccessiTest.class,
        GatewayCorsiSicurezzaTest.class,
        GatewayVisiteTest.class
})

//TODO: i test non funzionano bene a un certo punto non comunica più con il subscriber e non inserisce i dati nel db (se eseguiti separatamente funzionano)

class TestSuite {

}