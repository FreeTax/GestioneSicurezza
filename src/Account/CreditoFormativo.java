package Account;

import AccountGateway.UtenteGatewayDb;
import Rischi.Rischio;

import java.sql.SQLException;
import java.util.ArrayList;

public class CreditoFormativo {
    private int codice;
    private int idRischio;
    private String certificaEsterna;

    private UtenteGatewayDb uGateway;
    public CreditoFormativo(int codice, int idRischio, String certificaEsterna) throws SQLException {
        this.codice = codice;
        this.idRischio = idRischio;
        this.certificaEsterna = certificaEsterna;
        uGateway=new UtenteGatewayDb();
    }

    public void insertCreditoFormativo(int idRischio) throws SQLException {
        //String idRischio="FE123"; //rischio.getCodice()
        uGateway.insertCreditoFormativo(idRischio);
    }

    public String toString() {
        return "codice=" + codice + ", idRischio=" + idRischio + ", certificaEsterna=" + certificaEsterna ;
    }

    public Integer getIdRischio() {
        return idRischio;
    }

}
