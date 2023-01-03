import GatewayIPC.GatewayUtente;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //DbAction db=new DbAction();
            /*try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("drivewrok");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Ciao!");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Prova", "root", "root")) {
                System.out.println("connesso!");
                Statement stmt=con.createStatement();
                db.createTable(stmt);
                db.InsertSql(stmt);
                db.SelectSql(stmt);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
     */
        GatewayUtente gUtente= new GatewayUtente();
        gUtente.insertUtente(7029563,"marco","trambusti");
        System.out.println(gUtente.nomeUtenti());
    }
}