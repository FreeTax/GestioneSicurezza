package GatewayIPC;

import Accessi.Accesso;
import Accessi.AccessoDipartimentoAbilitato;
import Accessi.AccessoLuogoAbilitato;

import AccessiGatewayDb.AccessoLuogoAbilitatoGatewayDb;
import Account.CreditoFormativo;
import AccountGateway.UtenteGatewayDb;
import GatewayIPC.GatewayUtente;
import LuoghiGatewayDb.LuoghiGatewayDB;

import java.sql.SQLException;
import java.util.ArrayList;


public class GatewayAccessi {
    private AccessoLuogoAbilitatoGatewayDb accessoLuogoAbilitatoGatewayDb;

    public GatewayAccessi() throws SQLException {
        accessoLuogoAbilitatoGatewayDb = new AccessoLuogoAbilitatoGatewayDb();
    }

    public boolean insertAccessoDipartimento(int utente, int dipartimento, int authorizerUser) throws RuntimeException{  //TODO: CreditiFromativi check is missing

        try{
            if(!GatewayUtente.checkAvanzato(authorizerUser)) throw new  RuntimeException("la persona che tenta di abilitare l'utente non è un utente avanzato");
            else {
                Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
                ArrayList<Integer> rischiDipartimento = new LuoghiGatewayDB().getRischiDipartimento(dipartimento);
                ArrayList<CreditoFormativo> cfuUtente = new UtenteGatewayDb().GetCFUSostenuti(utente);
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
        }catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
        }/*catch (Error e){
                System.out.println(e.getMessage());
                return false;
            }*/
    }

    public boolean insertAccessoLuogo(int utente, int luogo, int authorizerUser) throws RuntimeException{
        try{
            if(!GatewayUtente.checkSupervisore(authorizerUser)) throw new RuntimeException("la persona che tenta di abilitare l'utente non è un utente avanzato o un supervisore");
            else {
                Accesso a = new AccessoLuogoAbilitato(utente, luogo);
                ArrayList<Integer> rischiLuogo = new LuoghiGatewayDB().getRischiLuogo(luogo);
                ArrayList<CreditoFormativo> cfuUtente = new UtenteGatewayDb().GetCFUSostenuti(utente);
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
            }catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }/*catch (Error e){
                System.out.println(e.getMessage());
                return false;
            }*/
    }

    public boolean updateAccessoDipartimento(int utente, int dipartimento){
        try{
            Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
            a.updateAccesso();
            return true;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateAccessoLuogo(int utente, int dipartimento) throws SQLException{
        try{
            Accesso a = new AccessoLuogoAbilitato(utente, dipartimento);
            a.updateAccesso();
            return true;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        try{
            Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
            a.deleteAccesso();
            return true;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteAccessoLuogo(int utente, int dipartimento) throws SQLException{
        try{
            Accesso a = new AccessoLuogoAbilitato(utente, dipartimento);
            a.deleteAccesso();
            return true;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}
