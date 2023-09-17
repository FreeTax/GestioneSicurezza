package GatewayIPC;

import Accessi.Accesso;
import Accessi.AccessoDipartimentoAbilitato;
import Accessi.AccessoLuogoAbilitato;
import Account.CreditoFormativo;
import Account.UtenteInterno;
import Luoghi.Dipartimento;
import Luoghi.Luogo;
import Delay.Delay;

import java.sql.SQLException;
import java.util.ArrayList;


public class GatewayAccessi {

    public GatewayAccessi() throws SQLException {
    }

    public boolean insertAccessoDipartimento(int utente, int dipartimento, int authorizerUser) throws RuntimeException, SQLException {
            if(!GatewayUtente.checkAvanzato(authorizerUser)) throw new  RuntimeException("la persona che tenta di abilitare l'utente non è un utente avanzato");
            else {

                Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
                Dipartimento d=new Dipartimento(dipartimento);
                ArrayList<Integer> rischiDipartimento = d.getRischi();
                ArrayList<CreditoFormativo> cfuUtente = new UtenteInterno().getCfuSostenuti(utente);
                ArrayList<Integer> cfuUtenteId = new ArrayList<>();

                cfuUtente.forEach(cfu ->cfuUtenteId.add(cfu.getIdRischio()));

                if(cfuUtenteId.containsAll(rischiDipartimento)) {
                    a.insertAccesso();
                    return true;
                }
                else{
                    throw new RuntimeException("l'utente non ha i crediti formativi necessari per accedere al dipartimento");
                }
            }
    }

    public boolean insertAccessoLuogo(int utente, int luogo, int authorizerUser) throws RuntimeException, SQLException {
            if(!GatewayUtente.checkSupervisore(authorizerUser)) throw new RuntimeException("la persona che tenta di abilitare l'utente non è un utente avanzato o un supervisore");
            else {

                Accesso a = new AccessoLuogoAbilitato(utente, luogo);
                Luogo l=new Luogo(luogo);
                ArrayList<Integer> rischiLuogo = l.getRischi();
                ArrayList<CreditoFormativo> cfuUtente = new UtenteInterno().getCfuSostenuti(utente);
                ArrayList<Integer> cfuUtenteId = new ArrayList<>();

                cfuUtente.forEach(cfu ->cfuUtenteId.add(cfu.getIdRischio()));

                if(cfuUtenteId.containsAll(rischiLuogo)) {
                    a.insertAccesso();
                    return true;
                }
                else{
                    throw new RuntimeException("l'utente non ha i crediti formativi necessari per accedere al luogo");
                }
            }
    }

    public void updateAccessoDipartimento(int utente, int dipartimento) throws SQLException {
            Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
            a.updateAccesso();
    }

    public void updateAccessoLuogo(int utente, int dipartimento) throws SQLException{
            Accesso a = new AccessoLuogoAbilitato(utente, dipartimento);
            a.updateAccesso();
    }

    public void deleteAccessoDipartimento(int utente, int dipartimento) throws SQLException{
            Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
            a.deleteAccesso();
    }

    public void deleteAccessoLuogo(int utente, int dipartimento) throws SQLException{
            Accesso a = new AccessoLuogoAbilitato(utente, dipartimento);
            a.deleteAccesso();
    }

    public ArrayList<Integer> getLuoghiFrequentati(int idUtente) throws SQLException{
         AccessoLuogoAbilitato a = new AccessoLuogoAbilitato(idUtente, 0);
         return a.getLuoghiFrequentati(idUtente);
    }

    public ArrayList<Integer> getDipartimentiFrequentati(int idUtente) throws SQLException{
        AccessoDipartimentoAbilitato a = new AccessoDipartimentoAbilitato(idUtente, 0);
        return a.getDipartimentiFrequentati(idUtente);
    }

}
