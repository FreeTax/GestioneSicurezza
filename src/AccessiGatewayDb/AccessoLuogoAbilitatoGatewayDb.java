package AccessiGatewayDb;

import java.sql.*;

public class AccessoLuogoAbilitatoGatewayDb {
    private Connection con;

    public AccessoLuogoAbilitatoGatewayDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/AccessiDB", "root", "root");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void inserAccessoDipartimento(int utente, int dipartimento) throws SQLException{
        try{
            String insertSql = "INSERT INTO AccessoDipartimentoAbilitato(utente, dipartimento)"
                    + " VALUES("+utente+", "+dipartimento+")";
            Statement stmt=con.createStatement();
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
