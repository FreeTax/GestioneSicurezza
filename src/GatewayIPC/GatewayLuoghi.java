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
        Luogo l = new Luogo(codice, nome, tipo, referente, dipartimento);
        l.saveToDB();
    }

    public void addDipartimento(int codice, String nome, int responsabile) throws SQLException {
        Dipartimento d = new Dipartimento(codice, nome, responsabile);
        d.saveToDB();
    }

    public UtenteInterno getResponsabileLuogo(Luogo l) throws SQLException {
        UtenteInterno u = new UtenteInterno(l.getReferente());
        return u;
    }

    public UtenteEsterno getResponsabileDipartimento(Dipartimento d) throws SQLException {
        UtenteEsterno u = new UtenteEsterno(d.getResponsabile());
        return u;
    }

    public void insertRischioLuogo(int codiceLuogo, int codiceRischio) throws SQLException {
        Luogo l = new Luogo(codiceLuogo);
        l.addRischio(codiceRischio);
    }

    public void insertRischioDipartimento(int codiceDipartimento, int codiceRischio) throws SQLException {
        Dipartimento d = new Dipartimento(codiceDipartimento);
        d.addRischio(codiceRischio);
    }
}
