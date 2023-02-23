import Account.Utente;
import Account.UtenteEsterno;
import Account.UtenteInterno;
import GatewayIPC.*;
import TestSuite.InitDB;

import java.sql.*;

public class Main {

    public static void initData(GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL){
        InitDB.initDB();
        try{
            gU.insertUtenteInterno(1,"password","nome","cognome","sesso","1999-01-01","1","base");
            gU.insertUtenteInterno(2,"password","nome2","cognome2","sesso","1999-01-01","1","supervisore");
            gU.insertUtenteInterno(3,"password","nome3","cognome3","sesso","1999-01-01","2","avanzato");
            gU.insertUtenteEsterno(4,"password","nome4","cognome4","sesso","1999-01-01","2");
            gL.addDipartimento(1,"dipartimento1", 3);
            gL.addDipartimento(2,"dipartimento2", 1);
            gL.addLuogo(1,"luogo1", "laboratorio", 1,1);
            gL.addLuogo(2,"luogo2", "ufficio", 2,2);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void test( GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL) throws SQLException {
        Utente u1= new UtenteInterno(1);//base
        Utente u2= new UtenteInterno(2);//supervisore
        Utente u3= new UtenteInterno(3); //avanzato
        Utente u4= new UtenteEsterno(4);//esterno
        System.out.println(u3.getType());
        if(gA.inserAccessoDipartimento(4, 2, u3.getType()))
            System.out.println("utente" + u3.getCodice() + " ha accesso al dipartimento 2");
        if(gA.insertAccessoLuogo(1,1,u2.getType()))
            System.out.println("utente" + u2.getCodice() + " ha accesso al luogo 1");

        if(gA.inserAccessoDipartimento(4,1,u2.getType()))
            System.out.println("utente" + u2.getCodice() + " ha accesso al dipartimento 2");
        if(gA.insertAccessoLuogo(4,1,u1.getType()))
            System.out.println("utente" + u1.getCodice() + " ha accesso al luogo 1");


    }
    public static void main(String[] args) throws SQLException {
        GatewayUtente gU = new GatewayUtente();
        GatewayLuoghi gL = new GatewayLuoghi();
        GatewayRischi gR = new GatewayRischi();
        GatewayAccessi gA = new GatewayAccessi();
        GatewayVisite gV = new GatewayVisite();
        initData(gA, gR, gV, gU, gL);
        test(gA, gR, gV, gU, gL);

    }
}