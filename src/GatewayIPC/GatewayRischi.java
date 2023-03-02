package GatewayIPC;
import Rischi.Rischio;
import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;

import java.sql.SQLException;

public class GatewayRischi {
    private Rischio r;
    public void insertRischioGenerico(int codice, String nome, String descrizione, String tipologia) throws SQLException {
            r=new RischioGenerico(codice, nome, descrizione, tipologia);
            r.saveToDB();
    }

    public void insertRischioSpecifico(int codice, String nome, String descrizione, String tipologia) throws SQLException {
            r=new RischioSpecifico(codice, nome, descrizione, tipologia);
            r.saveToDB();
    }

    public RischioGenerico getRischioGenerico(int codice) throws SQLException {
            r = new RischioGenerico(codice);
            return (RischioGenerico) r;
    }

    public RischioSpecifico getRischioSpecifico(int codice) throws SQLException {
            r = new RischioSpecifico(codice);
            return (RischioSpecifico) r;
    }
}
