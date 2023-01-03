package Account;

import AccountGateway.UtenteGatewayDb;
import Rischi.Rischio;

import java.sql.SQLException;

public class CreditoFormativo {
    private int codice;
    private Rischio rischio;
    private String certificaEsterna;

    private UtenteGatewayDb uGateway;
    public CreditoFormativo(int codice, /*Rischio rischio,*/ String certificaEsterna) throws SQLException {
        this.codice = codice;
        this.rischio = rischio;
        this.certificaEsterna = certificaEsterna;
        uGateway=new UtenteGatewayDb();
    }

    public void insertCreditoFormativo() throws SQLException {
        String idRischio="FE123"; //rischio.getCodice()
        uGateway.insertCreditoFormativo(idRischio);
    }

}
