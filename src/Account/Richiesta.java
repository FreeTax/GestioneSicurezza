package Account;

import AccountGateway.UtenteGatewayDb;
import Luoghi.Luogo;

import java.sql.SQLException;

abstract class Richiesta {
    private Utente utente;
    private int statoRichiesta;

    private UtenteGatewayDb uGateway;
    Richiesta(Utente utente, int statoRichiesta) throws SQLException {
        this.utente = utente;
        this.statoRichiesta = statoRichiesta;
        uGateway=new UtenteGatewayDb();
    }

    public void insertRichiesta(int idRiferimento, String tipo) throws SQLException {
        uGateway.InsertRichiesta(utente.codice,idRiferimento, tipo);
    }
    public void insertRichiesta(int idRiferimento) throws SQLException {
    }
}

