import GatewayIPC.GatewayUtente;

import java.sql.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println(Date.valueOf(LocalDate.of(2000, 3, 10)));
        GatewayUtente gUtente= new GatewayUtente();
        gUtente.insertUtenteInterno(7029563,"marco","trambusti","M","2000-10-03","ingegneria informatica");
        gUtente.insertUtenteEsterno(14235,"prova","prova","F","2000-10-12","fisica");

        gUtente.insertCreditoFormativo(1);
        gUtente.sostieniCredito(1,1);
        gUtente.insertRichiestaLuogo(1);
        System.out.println(gUtente.nomeUtenti());
    }
}