package GatewayIPC;
import Rischi.Rischio;
import Rischi.RischioGenerico;
import Rischi.RischioSpecifico;

import java.sql.SQLException;

public class GatewayRischi {
    private Rischio r;
    public void insertRischioGenerico(int codice, String nome, String descrizione, String tipologia) throws SQLException {
        try{
            r=new RischioGenerico(codice, nome, descrizione, tipologia);
            r.saveToDB();
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public void insertRischioSpecifico(int codice, String nome, String descrizione, String tipologia) throws SQLException {
        try{
            r=new RischioSpecifico(codice, nome, descrizione, tipologia);
            r.saveToDB();
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public RischioGenerico getRischioGenerico(int codice) throws SQLException {
        try {
            r = new RischioGenerico(codice);
            return (RischioGenerico) r;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public RischioSpecifico getRischioSpecifico(int codice) throws SQLException {
        try{
            r = new RischioSpecifico(codice);
            return (RischioSpecifico) r;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
