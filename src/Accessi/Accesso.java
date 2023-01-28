package Accessi;
import AccessiGatewayDb.AccessoLuogoAbilitatoGatewayDb;
import Account.Utente;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.sql.SQLException;

public abstract class Accesso {
    private Utente utente;
    protected AccessoLuogoAbilitatoGatewayDb gatewayDb;

    protected Accesso(Utente utente) throws SQLException {
        this.utente = utente;
        this.gatewayDb = new AccessoLuogoAbilitatoGatewayDb();
    }

    public abstract void insertAccesso() throws SQLException;

    public abstract void updateAccesso() throws SQLException;

    public abstract void deleteAccesso() throws SQLException;


}

