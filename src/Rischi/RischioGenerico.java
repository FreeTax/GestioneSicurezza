package Rischi;

import CorsiSicurezza.Corso;
import RischiGatewayDb.RischioGatewayDb;
import Visite.Visita;

import java.sql.SQLException;

public class RischioGenerico extends Rischio {
    private RischioGatewayDb rischioGatewayDb;
    public RischioGenerico(int codice, String nome, String descrizione, String tipologia /*, Corso corso, Visita visita*/) throws SQLException { //wating that Corso and Visita classes are implemented
        super(codice, nome, descrizione, tipologia);
        rischioGatewayDb = new RischioGatewayDb();
    }
    public RischioGenerico() throws SQLException {
        super();
        rischioGatewayDb = new RischioGatewayDb();
    }
    public RischioGenerico(int codice) throws SQLException {
        super();
        rischioGatewayDb = new RischioGatewayDb();
        RischioGenerico r= rischioGatewayDb.getRischioGenerico(codice);
        setCodice(r.getCodice());
        setNome(r.getNome());
        setDescrizione(r.getDescrizione());
        setTipologia(r.getTipologia());
    }

    public RischioGenerico(RischioGenerico r) throws SQLException {
        super();
        rischioGatewayDb = new RischioGatewayDb();
        setCodice(r.getCodice());
        setNome(r.getNome());
        setDescrizione(r.getDescrizione());
        setTipologia(r.getTipologia());
    }

    @Override
    public void saveToDB() throws SQLException {
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


    @Override
    public Boolean removeRischio() throws SQLException {
        return rischioGatewayDb.removeRischioGenerico(this.getCodice());
    }
}
