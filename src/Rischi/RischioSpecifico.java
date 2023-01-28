package Rischi;

import CorsiSicurezza.Corso;
import RischiGatewayDb.RischioGatewayDb;

import java.sql.SQLException;

public class RischioSpecifico extends Rischio {
    private RischioGatewayDb rischioGatewayDb;
    public RischioSpecifico(int codice, String nome, String descrizione, String tipologia /*, Corso corso, Visita visita*/) throws SQLException { //wating that Corso and Visita classes are implemented
        super(codice, nome, descrizione, tipologia);
        rischioGatewayDb = new RischioGatewayDb();
    }

    public RischioSpecifico() throws SQLException {
        super();
        rischioGatewayDb = new RischioGatewayDb();
    }

    public RischioSpecifico(int codice) throws SQLException {
        super();
        rischioGatewayDb = new RischioGatewayDb();
        RischioSpecifico r=rischioGatewayDb.getRischioSpecifico(codice);
        setCodice(r.getCodice());
        setNome(r.getNome());
        setDescrizione(r.getDescrizione());
        setTipologia(r.getTipologia());
    }

    public RischioSpecifico(RischioSpecifico r) throws SQLException {
        super();
        rischioGatewayDb = new RischioGatewayDb();
        setCodice(r.getCodice());
        setNome(r.getNome());
        setDescrizione(r.getDescrizione());
        setTipologia(r.getTipologia());
    }

    @Override
    public void saveToDB() throws SQLException {
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

    @Override
    public Boolean removeRischio() throws SQLException {
        return rischioGatewayDb.removeRischioSpecifico(this.getCodice());
    }
}
