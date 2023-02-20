package TestSuite;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.sql.*;


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