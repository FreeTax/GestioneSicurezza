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

class TestSuite {

}