package GatewayIPC;

import Accessi.Accesso;
import Accessi.AccessoDipartimentoAbilitato;
import Accessi.AccessoLuogoAbilitato;

import AccessiGatewayDb.AccessoLuogoAbilitatoGatewayDb;

import Account.Utente;
import Account.UtenteEsterno;
import Account.UtenteInterno;
import Luoghi.Dipartimento;
import Luoghi.Luogo;

import java.sql.SQLException;


public class GatewayAccessi {
    private AccessoLuogoAbilitatoGatewayDb accessoLuogoAbilitatoGatewayDb;

    public GatewayAccessi() throws SQLException {
        accessoLuogoAbilitatoGatewayDb = new AccessoLuogoAbilitatoGatewayDb();
    }

    public boolean inserAccessoDipartimento(int utente, int dipartimento, String authorizerType) throws SQLException{  //TODO: CreditiFromativi check is missing

        try{
            if(!authorizerType.equals("avanzato")) throw new Error ("la persona che tenta di abilitare l'utente non è un utente avanzato");
            Accesso a = new AccessoDipartimentoAbilitato(utente, dipartimento);
            a.insertAccesso();
            return true;
            }catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }catch (Error e){
                System.out.println(e.getMessage());
                return false;
            }
    }

    public boolean insertAccessoLuogo(int utente, int luogo, String authorizerType){

        try{
            if(!authorizerType.equals("avanzato")&&!authorizerType.equals("supervisore")) throw new Error ("ela persona che tenta di abilitare l'utente non è un utente avanzato o un supervisore");
            Accesso a = new AccessoLuogoAbilitato(utente, luogo);
            a.insertAccesso();
            return true;
            }catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }catch (Error e){
                System.out.println(e.getMessage());
                return false;
            }
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
