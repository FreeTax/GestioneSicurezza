import Account.CreditoFormativo;
import Account.Utente;
import Account.UtenteEsterno;
import Account.UtenteInterno;
import GatewayIPC.*;
import TestSuite.InitDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    //TODO: test cross department and role update, getCrediti da sostenere,
    // dashboard: crediti da soostenre, crediti sostenuti, visite da effettuare, visite effettuate,luoghi frequentati
    public static void initData(GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL, GatewayCorsiSicurezza gCS){
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
            gU.insertCreditoFormativo(1,1);
            gU.insertCreditoFormativo(2,2);
            gU.insertCreditoFormativo(3,3);
            gCS.addCorsoType(1,"sicurezza","  ",1,3);
            gCS.addCorso("corsoSicurezza1"," ",1, LocalDate.now(),LocalDate.now(),3);
            gV.addVisitaType(1,"visita oculistica", " ", "2 anno",2);
            gV.addSchedaVisita(1);

            gV.addVisitaUtente(1,1,"dott.Mario Rossi","visita oculistica",Timestamp.valueOf(LocalDateTime.now()),"da sostenere",1);
            gV.addVisitaUtente(1,2,"dott.Mario Rossi","visita controllo",Timestamp.valueOf(LocalDateTime.now()),"da sostenere",1);
            gV.sostieniVisita(1, "superata",1);
            gL.insertRischioLuogo(1,1);
            gL.insertRischioLuogo(1,2);
            gL.insertRischioLuogo(1,3);
            gR.insertRischioSpecifico(1,"chimico","hhjljhl");
            gR.insertRischioSpecifico(2,"computer","hhjljhl");
            gR.insertRischioSpecifico(3,"laser","hhjljhl");
            gR.insertRischioSpecifico(4,"ustione","hhjljhl");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void test( GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL) throws SQLException {
        Utente u1= new UtenteInterno(1);//base
        Utente u2= new UtenteInterno(2);//supervisore
        Utente u3= new UtenteInterno(3); //avanzato
        Utente u4= new UtenteEsterno(4);//esterno
        System.out.println(u3.getType());
        try {
            if(gA.insertAccessoDipartimento(4, 2, u3.getCodice()))
                System.out.println("utente" + u3.getCodice() + " ha accesso al dipartimento 2");
        } catch (Exception e) {
            System.out.println(e);
        }
        try{
            if(gA.insertAccessoLuogo(1,1,u2.getCodice()))
                System.out.println("utente" + u2.getCodice() + " ha accesso al luogo 1");
        } catch (Exception e) {
            System.out.println(e);
        }
        try{
            if(gA.insertAccessoDipartimento(4,1,u2.getCodice()))
                System.out.println("utente" + u2.getCodice() + " ha accesso al dipartimento 2");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (gA.insertAccessoLuogo(4, 1, u1.getCodice())) //FIXME: non viene eseguito perchè il metodo prima lancia eccezione
                System.out.println("utente" + u1.getCodice() + " ha accesso al luogo 1");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void test2(GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL) throws SQLException {
        try {
            if (gU.loginInterno(1, "password")) {
                gU.sostieniCredito(1, 1, "");
                gU.sostieniCredito(1, 2, "");
                gU.sostieniCredito(1, 3, "certificazione");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (gU.loginEsterno(4, "password")) {
                gU.sostieniCredito(4, 1, "");
                gU.sostieniCredito(4, 3, "certificazione");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            if (gU.loginInterno(2, "password")) { //login utente supervisore
                ArrayList<CreditoFormativo> CFUsostenuti = gU.getCFUSostenuti(2, 1);
                ArrayList<String> cfusString = new ArrayList<>();
                CFUsostenuti.forEach(cf -> cfusString.add(cf.toString()));
                System.out.println("l'utente 1 ha sostenuto i seguenti crediti formativi: " + cfusString);
                if (gA.insertAccessoLuogo(1, 1, 2)) {
                    System.out.println("utente con codice 1 ha accesso al luogo 1");
                }
                if (gA.insertAccessoLuogo(4, 1, 2)) {
                    System.out.println("utente con codice 2 ha accesso al luogo 1");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void testDashboard(GatewayUtente gU, GatewayLuoghi gL) throws SQLException {
        gL.insertRischioLuogo(2,4);
        gU.insertRichiestaLuogo(1,2);
        for(String s: gU.getDashboard(3)){
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws SQLException {
        GatewayUtente gU = new GatewayUtente();
        GatewayLuoghi gL = new GatewayLuoghi();
        GatewayRischi gR = new GatewayRischi();
        GatewayAccessi gA = new GatewayAccessi();
        GatewayVisite gV = new GatewayVisite();
        GatewayCorsiSicurezza gCS= new GatewayCorsiSicurezza();
        initData(gA, gR, gV, gU, gL,gCS);
        test2(gA,gR,gV,gU,gL);
        test(gA, gR, gV, gU, gL);
        testDashboard(gU, gL);
    }
}