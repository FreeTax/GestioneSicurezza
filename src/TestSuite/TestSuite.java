package TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        GatewayUtenteTest.class,
        GatewayLuoghiTest.class,
        GatewayAccessiTest.class,
        GatewayCorsiSicurezzaTest.class,
        GatewayVisiteTest.class
})

//TODO: i test non funzionano bene a un certo punto non comunica più con il subscriber e non inserisce i dati nel db (se eseguiti separatamente funzionano)
//per ora fixato usando thread
class TestSuite {

}