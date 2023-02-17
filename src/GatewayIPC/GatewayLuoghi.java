package GatewayIPC;

import Account.UtenteEsterno;
import Account.UtenteInterno;
import Luoghi.Dipartimento;
import Luoghi.Luogo;
import Rischi.Rischio;

import java.sql.SQLException;
import java.util.ArrayList;

public class GatewayLuoghi {

    public void addLuogo(int codice, String nome, String tipo, int referente, int dipartimento) throws SQLException {
        Luogo l = new Luogo(codice, nome,  tipo,  referente,  dipartimento);
        l.saveToDB();
    }

    public void addDipartimento(int codice, String nome, int responsabile) throws SQLException {
        Dipartimento d=new Dipartimento(codice, nome, responsabile);
        d.saveToDB();
    }

    public UtenteInterno getResponsabileLuogo(Luogo l) throws SQLException {
        try{
            UtenteInterno u= new UtenteInterno(l.getReferente());
            return u;
        }
        catch (SQLException e){
            throw new SQLException("Responsabile non trovato");
        }
    }

    public UtenteEsterno getResponsabileDipartimento(Dipartimento d) throws SQLException {
        try{
            UtenteEsterno u= new UtenteEsterno(d.getResponsabile());
            return u;
        }
        catch (SQLException e){
            throw new SQLException("Responsabile non trovato");
        }
    }

    public void getResponsabileDipartimento() {

    }
}
