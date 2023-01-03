package Rischi;

import CorsiSicurezza.Corso;
import RischiGatewayDb.RischioGatewayDb;

import java.sql.SQLException;

public class RischioSpecifico extends Rischio {
    private RischioGatewayDb rischioGatewayDb;
    public RischioSpecifico(int codice, String nome, String descrizione, String tipologia /*, Corso corso, Visita visita*/) throws SQLException { //wating that Corso and Visita classes are implemented
        super();
        rischioGatewayDb = new RischioGatewayDb();
    }
    public RischioSpecifico() throws SQLException {
        super();
        rischioGatewayDb = new RischioGatewayDb();
    }

    @Override
    public void saveToDB(int codice) throws SQLException {
        rischioGatewayDb.insertRischioSpecifico(this);
    }

    @Override
    public String toString() {
        return "RischioSpecifico{" +
                "codice=" + getCodice() +
                ", nome='" + getNome() + '\'' +
                ", descrizione='" + getDescrizione() + '\'' +
                ", tipologia='" + getTipologia() + '\'' +
/*                ", corso=" + getCorso() +
                ", visita=" + getVisita() +*/
                '}';
    }
}
