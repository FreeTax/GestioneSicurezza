import Accessi.accessisubscriber.AccessiSubscriber;
import Account.Utente;
import Account.UtenteEsterno;
import Account.UtenteInterno;
import Account.accountsubscriber.AccountSubscriber;
import AsyncIPCEventBus.*;
import AsyncIPCEventBus.PublishSubscribe.EventBusService;
import Delay.Delay;
import Luoghi.luoghisubscriber.LuoghiSubscriber;
import Rischi.rischisubscriber.RischiSubscriber;
import TestSuite.InitDB;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import Visite.visitesubscriber.VisiteSubscriber;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;


public class PerformanceEvaluation {



    public static void initData(GatewayAccessi gA, GatewayRischi gR, GatewayVisite gV, GatewayUtente gU, GatewayLuoghi gL, GatewayCorsiSicurezza gCS){
        InitDB.initDB();
        try {
            gU.insertUtenteInterno(1,"password","nome","cognome","sesso","1999-01-01","1","base");
            gU.insertUtenteInterno(2,"password","nome2","cognome2","sesso","1999-01-01","1","supervisore");
            gU.insertUtenteInterno(3,"password","nome3","cognome3","sesso","1999-01-01","2","avanzato");
            gL.addDipartimento(1,"dipartimento1", 2);
            gL.addLuogo(1,"luogo1", "laboratorio", 1,1);

            sleep(400);
            gU.insertCreditoFormativo(1,1);
            gU.sostieniCredito(1,1, "");

            gV.addVisitaType(1,"visita oculistica", " ", "2 anno",2);
            gV.addSchedaVisita(1);

            sleep(200);

            gV.addVisitaUtente(1,1,"dott.Mario Rossi","visita oculistica", Timestamp.valueOf(LocalDateTime.now()),"da sostenere",1);

            sleep(200);
            gV.sostieniVisita(1, "superata",1);
            gL.insertRischioLuogo(1,1);
            gR.insertRischioSpecifico(1,"chimico","hhjljhl");



        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public static long evaluateInsertAccessoDipartimento(Utente u, GatewayAccessi gA, Utente u3){
        long startTime = System.nanoTime();
        try{
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(gA.insertAccessoDipartimento(u.getCodice(),1, u3.getCodice()));
            combinedFuture.get();
                return (System.nanoTime() - startTime);
        }
        catch (Exception e){
            System.out.println(e);
            return (System.nanoTime() - startTime);
        }
    }

    public static long evaluateInsertAccessoDipartimentoAndCreditoFormativo(Utente u, GatewayAccessi gA, Utente u3, GatewayUtente gU){
        long startTime = System.nanoTime();
        try{
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf( gA.insertAccessoDipartimento(u.getCodice(),1, u3.getCodice()), gU.getRichiesteDipartimento(u3.getCodice()) );
            combinedFuture.get();
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

    public static void test1( Utente u1, Utente u2, Utente u3, GatewayAccessi gA, GatewayUtente gU, GatewayLuoghi gL, GatewayRischi gR, GatewayVisite gV, GatewayCorsiSicurezza gCS, int probability, int delay, int cycles, int probIncrease, int delayIncrease, int iterations, String testType) throws SQLException {
        //Test di inserimento accesso luogo e dipartimento
        System.out.println(testType);
        Object[][] data = new Object[1][2];
        data[0][0]=" ";
        data[0][1]=" ";
        insertToExcel(data,"data.xlsx");
        data[0][0]=testType+ " " + new Date();
        data[0][1]="";
        insertToExcel(data,"data.xlsx");
        long InsertAccessoDipartimento=0;

        Delay.setProbability(probability);
        Delay.setDelay(delay);
        int count=0;

        ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
        ArrayList<String> element = new ArrayList<String>();
        element.add("Delay");
        element.add("Probability");
        element.add("Dipartimenti");
        input.add(element);
        saveData(input);
        element.clear();
        input.clear();
        while(count<=iterations){
            for(int i=0;i<cycles;i++){
                InsertAccessoDipartimento+=evaluateInsertAccessoDipartimento(u1,gA,u3);

                initData(gA, gR, gV, gU, gL,gCS);
            }
            InsertAccessoDipartimento=InsertAccessoDipartimento/cycles;
            element.add(String.valueOf(Delay.getDelay()));
            element.add(String.valueOf(Delay.getProbability()));
            element.add(String.valueOf(InsertAccessoDipartimento));
            input.add(element);
            saveData(input);
            Delay.setProbability(Delay.getProbability()+probIncrease);
            //Delay.setDelay(Delay.getDelay()+delayIncrease);
            System.out.println("Delay: "+Delay.getDelay()+" Probability: "+Delay.getProbability());
            element.add(String.valueOf( delay));
            input.clear();
            element.clear();
            count++;
        }
    }

    public static void test2( Utente u1, Utente u2, Utente u3, GatewayAccessi gA, GatewayUtente gU, GatewayLuoghi gL, GatewayRischi gR, GatewayVisite gV, GatewayCorsiSicurezza gCS, int probability, int delay, int cycles, int probIncrease, int delayIncrease, int iterations, String testType) throws SQLException {
        //Test di inserimento accesso luogo e dipartimento
        System.out.println(testType);
        Object[][] data = new Object[1][2];
        data[0][0]=" ";
        data[0][1]=" ";
        insertToExcel(data,"data.xlsx");
        data[0][0]=testType+ " " + new Date();
        data[0][1]="";
        insertToExcel(data,"data.xlsx");
        long InsertAccessoDipartimentoAndLuogo=0;

        Delay.setProbability(probability);
        Delay.setDelay(delay);
        int count=0;

        ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
        ArrayList<String> element = new ArrayList<String>();
        element.add("Delay");
        element.add("Probability");
        element.add("DipartimentiAndLuoghi");
        input.add(element);
        saveData(input);
        element.clear();
        input.clear();
        while(count<=iterations){
            for(int i=0;i<cycles;i++){
                InsertAccessoDipartimentoAndLuogo+=evaluateInsertAccessoDipartimentoAndCreditoFormativo(u1,gA,u3, gU);

                initData(gA, gR, gV, gU, gL,gCS);
            }
            InsertAccessoDipartimentoAndLuogo=InsertAccessoDipartimentoAndLuogo/cycles;
            element.add(String.valueOf(Delay.getDelay()));
            element.add(String.valueOf(Delay.getProbability()));
            element.add(String.valueOf(InsertAccessoDipartimentoAndLuogo));
            input.add(element);
            saveData(input);
            System.out.println("Delay: "+Delay.getDelay()+" Probability: "+Delay.getProbability()+" InsertAccessoDipartimentoAndLuogo: "+InsertAccessoDipartimentoAndLuogo);
            Delay.setDelay(Delay.getDelay()+delayIncrease);
            //Delay.setProbability(Delay.getProbability()+probIncrease);
            element.add(String.valueOf( delay));
            input.clear();
            element.clear();
            count++;
        }
    }


    public static void main(String[] args) throws SQLException, InterruptedException {

        EventBusService eventBusService = new EventBusService();
        AccountSubscriber accountSubscriber = new AccountSubscriber(eventBusService);
        GatewayUtente gU = new GatewayUtente(eventBusService);
        GatewayLuoghi gL = new GatewayLuoghi(eventBusService);
        GatewayRischi gR = new GatewayRischi(eventBusService);
        GatewayAccessi gA = new GatewayAccessi(eventBusService);
        GatewayVisite gV = new GatewayVisite(eventBusService);
        GatewayCorsiSicurezza gCS = new GatewayCorsiSicurezza(eventBusService);
        initData(gA, gR, gV, gU, gL,gCS);
        ArrayList<Thread> threads = new ArrayList<Thread>();
        threads.add(new Thread(eventBusService));
        threads.add(new Thread(accountSubscriber));
        threads.add(new Thread(new AccessiSubscriber(eventBusService)));
        threads.add(new Thread(new LuoghiSubscriber(eventBusService)));
        threads.add(new Thread(new RischiSubscriber(eventBusService)));
        threads.add(new Thread(new VisiteSubscriber(eventBusService)));
        for (Thread t : threads) {
            t.start();
        }
        sleep(1000);
        Utente u1= new UtenteInterno(1);//base
        Utente u2= new UtenteInterno(2);//supervisore
        Utente u3= new UtenteInterno(3); //avanzato
        Utente u4= new UtenteEsterno(4);//esterno


        Object[][] data = new Object[1][2];
        data[0][0]="Test Asincrono ";
        data[0][1]= new Date();
        insertToExcel(data,"data.xlsx");
        int probability=100; //initial probability
        int delay=800; //initial delay
        int cycles=5; //number of cycles for each delay
        int probIncrease=0;
        int delayIncrease=100;
        int delayIterations=5;

        Delay.addName("UtenteInterno");
        test1(u1,u2,u3,gA,gU,gL,gR,gV,gCS,probability,delay,cycles,probIncrease,delayIncrease,delayIterations,"Test inseirmento accesso luogo con ritardo in utente interno e senza lancio di eccezioni");

        Delay.celanNames();
        Delay.addName("Dipartimento");
        test1(u1,u2,u3,gA,gU,gL,gR,gV,gCS,probability,delay,cycles,probIncrease,delayIncrease,delayIterations,"Test inseirmento accesso luogo con ritardo in accesso e senza lancio di eccezioni");

        Delay.celanNames();
        Delay.addName("UtenteInterno");
        test2(u1,u2,u3,gA,gU,gL,gR,gV,gCS,probability,delay,cycles,probIncrease,delayIncrease,delayIterations,"Test inseirmento accesso luogo e dipartimento con ritardo in utente interno e senza lancio di eccezioni");

        Delay.celanNames();
        Delay.addName("Dipartimento");
        test2(u1,u2,u3,gA,gU,gL,gR,gV,gCS,probability,delay,cycles,probIncrease,delayIncrease,delayIterations,"Test inseirmento accesso luogo e dipartimento con ritardo in accesso e senza lancio di eccezioni");

        Delay.celanNames();

        try {
            for (Thread t : threads) {
                t.interrupt();
                t.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
