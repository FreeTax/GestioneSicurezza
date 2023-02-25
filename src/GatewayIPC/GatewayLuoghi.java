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
        try{
            Luogo l = new Luogo(codice, nome,  tipo,  referente,  dipartimento);
            l.saveToDB();
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }

    public void addDipartimento(int codice, String nome, int responsabile) throws SQLException {
        try{
            Dipartimento d = new Dipartimento(codice, nome, responsabile);
            d.saveToDB();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
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

    public void insertRischioLuogo(int codiceLuogo, int codiceRischio) throws SQLException {
        try{
            Luogo l = new Luogo(codiceLuogo);
            l.addRischio(codiceRischio);
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void insertRischioDipartimento(int codiceDipartimento, int codiceRischio) throws SQLException {
        try{
            Dipartimento d = new Dipartimento(codiceDipartimento);
            d.addRischio(codiceRischio);
        }catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
