import GatewayIPC.GatewayUtente;
import Rischi.RischioSpecifico;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        GatewayUtente gUtente= new GatewayUtente();
        gUtente.insertUtente(7029563,"marco","trambusti");
        System.out.println(gUtente.nomeUtenti());

        RischioSpecifico r = new RischioSpecifico(1,"nome","descrizione","tipologia");
        r.saveToDB(1);



    }
}