package TestSuite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {
    public static void initAccountDb(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccountDB", "root", "root");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM UtenteInterno");
            stmt.executeUpdate("DELETE FROM UtenteEsterno");
            stmt.executeUpdate("DELETE FROM CreditoFormativoSostenuto");
            stmt.executeUpdate("DELETE FROM CreditoFormativo ");
            stmt.executeUpdate("DELETE FROM Richiesta");
            stmt.executeUpdate("DELETE FROM Utente");
            stmt.executeUpdate("ALTER TABLE CreditoFormativo AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE Utente AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE Richiesta AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initAccessiDB(){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccessiDB", "root", "root");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM AccessoLuogoAbilitato");
            stmt.executeUpdate("DELETE FROM AccessoDipartimentoAbilitato");
            stmt.executeUpdate("ALTER TABLE AccessoLuogoAbilitato AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE AccessoDipartimentoAbilitato AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initCorsiSicurezzaDB(){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CorsiSicurezzaDB", "root", "root");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM Corso");
            stmt.executeUpdate("DELETE FROM CorsoType");
            stmt.executeUpdate("ALTER TABLE CorsoType AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE Corso AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initLuoghiDB(){
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LuoghiDB", "root", "root");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM Dipartimenti");
            stmt.executeUpdate("DELETE FROM Luoghi");
            stmt.executeUpdate("DELETE FROM RischiDipartimento");
            stmt.executeUpdate("DELETE FROM RischiLuogo");
            stmt.executeUpdate("ALTER TABLE Dipartimenti AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE Luoghi AUTO_INCREMENT = 1");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initRischiDB(){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/RischiDB", "root", "root");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM RischioGenerico");
            stmt.executeUpdate("DELETE FROM RischioSpecifico");
            stmt.executeUpdate("ALTER TABLE RischioGenerico AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE RischioSpecifico AUTO_INCREMENT = 1");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void initVisiteDB(){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/VisiteDB", "root", "root");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM Visita");
            stmt.executeUpdate("DELETE FROM VisitaType");
            stmt.executeUpdate("DELETE FROM SchedaVisita");
            stmt.executeUpdate("DELETE FROM SchedaVisita_has_Patologia");
            stmt.executeUpdate("DELETE FROM Patologia");
            stmt.executeUpdate("ALTER TABLE SchedaVisita AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE Visita AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE Patologia AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE VisitaType AUTO_INCREMENT = 1");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void initDB(){
        initAccountDb();
        initAccessiDB();
        initRischiDB();
        initCorsiSicurezzaDB();
        initLuoghiDB();
        initVisiteDB();
    }
}
