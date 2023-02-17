package TestSuite;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
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