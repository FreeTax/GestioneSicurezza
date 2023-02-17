package Accessi;
import AccessiGatewayDb.AccessoLuogoAbilitatoGatewayDb;

import java.sql.SQLException;

public abstract class Accesso {
    private int idUtente;
    protected AccessoLuogoAbilitatoGatewayDb gatewayDb;

    protected Accesso(int utente) throws SQLException {
        this.idUtente = utente;
        this.gatewayDb = new AccessoLuogoAbilitatoGatewayDb();
    }

    public abstract void insertAccesso() throws SQLException;

    public abstract void updateAccesso() throws SQLException;

    public abstract void deleteAccesso() throws SQLException;


}

