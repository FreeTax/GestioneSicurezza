import Account.Utente;
import Account.UtenteEsterno;
import Account.UtenteInterno;
import Delay.Delay;
import GatewayIPC.*;
import TestSuite.InitDB;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;



public class PerformanceEvaluation {

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

            gV.addVisitaUtente(1,1,"dott.Mario Rossi","visita oculistica", Timestamp.valueOf(LocalDateTime.now()),"da sostenere",1);
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

    public static long evaluateInsertAccessoDipartimento(Utente u, GatewayAccessi gA, Utente u2){
        long startTime = System.nanoTime();
        try{
            gA.insertAccessoDipartimento(u.getCodice(),1, u2.getCodice());
            return (System.nanoTime() - startTime);
        }
        catch (Exception e){
            System.out.println(e);
            return (System.nanoTime() - startTime);
        }

    }

    public static long evaluateInsertAccessoLuoogo(Utente u, GatewayAccessi gA, Utente u2){
        long startTime = System.nanoTime();
        try{
            gA.insertAccessoLuogo(u.getCodice(),1, u2.getCodice());
            return (System.nanoTime() - startTime);
        }
        catch (Exception e){
            System.out.println(e);
            return (System.nanoTime() - startTime);
        }

    }

    public static long evaluateSostineiCreditoUtenteInterno(Utente u, GatewayUtente gU){
        long startTime = System.nanoTime();
        try{
            gU.sostieniCredito(u.getCodice(),1, "");
            return (System.nanoTime() - startTime);
        }
        catch (Exception e){
            System.out.println(e);
            return (System.nanoTime() - startTime);
        }
    }

    public static long evaluateSostineiCreditoUtenteEsterno(Utente u, GatewayUtente gU) throws SQLException {
        long startTime = System.nanoTime();
            try{
            gU.sostieniCredito(u.getCodice(),1, "");
                return (System.nanoTime() - startTime);
        }
        catch (Exception e){
            System.out.println(e);
            return (System.nanoTime() - startTime);
        }
    }

    public static void insertToExcel(Object[][] newData, String filePath){
        try {
            Workbook workbook;
            Sheet sheet;

            File file = new File(filePath);

            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                workbook = WorkbookFactory.create(inputStream); // Carica il file Excel esistente
                sheet = workbook.getSheet("Dati");
                inputStream.close();
            } else {
                workbook = new XSSFWorkbook(); // Crea un nuovo file Excel se non esiste
                sheet = workbook.createSheet("Dati");
            }

            int rowNum = sheet.getLastRowNum(); // Ottiene l'ultima riga utile

            // Se la riga utile è vuota o non esiste, inizia dalla prima riga (0)
            if (rowNum == -1) {
                rowNum = 0;
            } else {
                rowNum++;
            }

            for (Object[] rowData : newData) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (Object field : rowData) {
                    Cell cell = row.createCell(colNum++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    } else if (field instanceof Long) {
                        cell.setCellValue((Long) field);
                    }
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }

            System.out.println("Dati scritti con successo in " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveData(ArrayList<ArrayList<String>> input){
        for(ArrayList<String> element:input){
            Object[][] data = new Object[1][element.size()];
            for( int i=0; i<element.size();i++){
                data[0][i]=element.get(i);
            }
            insertToExcel(data,"data.xlsx");
        }
    }

    public static void test1( Utente u1, Utente u2, Utente u3, Utente u4, GatewayAccessi gA, GatewayUtente gU, GatewayLuoghi gL, GatewayRischi gR, GatewayVisite gV, GatewayCorsiSicurezza gCS, int probability, int delay, int cycles, int probIncrease, int delayIncrease, int iterations) throws SQLException {
        Object[][] data = new Object[1][2];
        data[0][0]=" ";
        data[0][1]=" ";
        insertToExcel(data,"data.xlsx");
        data[0][0]="New Test " + new Date();
        data[0][1]="";
        insertToExcel(data,"data.xlsx");
        long InsertAccessoDipartimento=0;
        long InsertAccessoLuoogo=0;

        Delay.setProbability(probability);
        Delay.setDelay(delay);
        int count=0;


        Delay.addName("UtenteInterno");
        ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
        ArrayList<String> element = new ArrayList<String>();
        element.add("Delay");
        element.add("Probability");
        element.add("Dipartimenti");
        element.add("Luoghi");
        input.add(element);
        saveData(input);
        element.clear();
        input.clear();
        while(count<=iterations){
            for(int i=0;i<cycles;i++){
                InsertAccessoDipartimento+=evaluateInsertAccessoDipartimento(u1,gA,u3);
                InsertAccessoLuoogo+=evaluateInsertAccessoLuoogo(u1, gA, u2);
                initData(gA, gR, gV, gU, gL,gCS);
            }
            InsertAccessoDipartimento=InsertAccessoDipartimento/cycles;
            InsertAccessoLuoogo=InsertAccessoLuoogo/cycles;
            element.add(String.valueOf(delay));
            element.add(String.valueOf(Delay.getProbability()));
            element.add(String.valueOf(InsertAccessoDipartimento));
            element.add(String.valueOf(InsertAccessoLuoogo));
            input.add(element);
            saveData(input);
            Delay.increaseDelay(delayIncrease);
            Delay.increaseProbability(probIncrease);
            element.add(String.valueOf( delay));
            input.clear();
            element.clear();
            count++;
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
        Utente u1= new UtenteInterno(1);//base
        Utente u2= new UtenteInterno(2);//supervisore
        Utente u3= new UtenteInterno(3); //avanzato
        Utente u4= new UtenteEsterno(4);//esterno

        test1(u1,u2,u3,u4,gA,gU,gL,gR,gV,gCS, 5, 500, 5, 5, 100, 5);
    }
}
