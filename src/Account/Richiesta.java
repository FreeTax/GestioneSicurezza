package Account;

import AccountGateway.UtenteGatewayDb;

import java.sql.SQLException;

abstract class Richiesta {
    private int idUtente;
    private int statoRichiesta;

    private UtenteGatewayDb uGateway;

    Richiesta(int idUtente, int statoRichiesta) throws SQLException {
        this.idUtente = idUtente;
        this.statoRichiesta = statoRichiesta;
        uGateway = new UtenteGatewayDb();
    }

    public void insertRichiesta(int idRiferimento, String tipo) throws SQLException {
        uGateway.InsertRichiesta(idUtente, idRiferimento, tipo);
    }

    public void insertRichiesta(int idRiferimento) throws SQLException {
    }

    public void setStatoRichiesta(int statoRichiesta) {
        this.statoRichiesta = statoRichiesta;
    }

    public String toString() {
        return "idUtente=" + idUtente + ", statoRichiesta=" + statoRichiesta;
    }
}

