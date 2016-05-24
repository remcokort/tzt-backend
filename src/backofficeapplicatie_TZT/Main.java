package backofficeapplicatie_TZT;

import java.sql.*;

public class Main {
    
    private static final String USERNAME = "sql7115322";
    private static final String PASSWORD = "AiRKLLIZeZ";
    private static final String CONN_STRING = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7115322";
    
    public static void main(String[] args) {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            System.out.println("Yes");
        } catch (SQLException e) {
            System.err.println(e);
        }
        
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(
		"SELECT * FROM bedrijf");
            while( rs.next() ) {
                String id = rs.getString(1); 	         // 1e kolom
                String naam = rs.getString("naam");  // kolom ‘Naam’
                String ww = rs.getString(3); 
                System.out.println(naam);
            }
            System.out.println(rs.next());
        } catch (SQLException e) {
            System.err.println(e);
        }
        
        

        
        Klant klant1 = new Klant("Rick", "Holterman", "rick.holterman.rh@gmail.com");
        System.out.println(klant1);
        
        Klant klant2 = new Klant("Rick", "Holterman", "rick.holterman.rh@gmail.com", "06-39814911", "3892021");
        System.out.println(klant2);
        
        Koerier koerier1 = new Koerier("T0000001","Rick", "Holterman", "M", "11/02/1997", "Tiendschuurstraat", "161", 
                "8043 XZ", "Zwolle", "rick.holterman.rhgmail.com", "06-39814911", "238908890", "IY868HJ87", "Hash");
        // koerier1.setActief(true); 
        System.out.println(koerier1);
        
        Koerier koerier2 = new Koerier("Pietersen Transport BV", "info@pietertje.nl");
        System.out.println(koerier2);
        
        // SchermGegevensTreinkoerier s = new SchermGegevensTreinkoerier(koerier1);
        //SchermOverzichtTreinkoeriers s2 = new SchermOverzichtTreinkoeriers();
        SchermHome s = new SchermHome();
        
      
        
       
    }
    
}
