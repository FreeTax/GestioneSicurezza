package Rischi;

import CorsiSicurezza.Corso;
import RischiGatewayDb.RischioGatewayDb;
import Visite.Visita;

import java.sql.SQLException;

public class RischioGenerico extends Rischio {
    private RischioGatewayDb rischioGatewayDb;
    public RischioGenerico(int codice, String nome, String descrizione, String tipologia /*, Corso corso, Visita visita*/) throws SQLException { //wating that Corso and Visita classes are implemented
        super();
        rischioGatewayDb = new RischioGatewayDb();
    }
    public RischioGenerico() throws SQLException {
        super();
        rischioGatewayDb = new RischioGatewayDb();
    }

    @Override
    public void saveToDB(int codice) throws SQLException {
        rischioGatewayDb.insertRischioGenerico(this);
    }

    @Override
    public String toString() {
        return "RischioGenerico{" +
                "codice=" + getCodice() +
                ", nome='" + getNome() + '\'' +
                ", descrizione='" + getDescrizione() + '\'' +
                ", tipologia='" + getTipologia() + '\'' +
/*                ", corso=" + getCorso() +
                ", visita=" + getVisita() +*/
                '}';
    }


}
