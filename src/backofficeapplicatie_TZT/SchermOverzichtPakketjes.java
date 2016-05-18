// HBO-ICT - WINDESHEIM - KBS2 - PERIODE 3/4 2016 - HAT03
// RICK HOLTERMAN
// REMCO KORT
// DENNIE DE WILDE
// ELWIN STREESTRA

package backofficeapplicatie_TZT;

//Importeren van de inloggegevens van de database
import static backofficeapplicatie_TZT.Databaseverbinding.connectionString;
import static backofficeapplicatie_TZT.Databaseverbinding.password;
import static backofficeapplicatie_TZT.Databaseverbinding.username;

//Importeren van de benodigde Java library's
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class SchermOverzichtPakketjes extends JFrame implements ActionListener{
    
    //Aanmaken van de benodigde variabelen/objecten
    private JLabel jlPakketTitel;
    private JLabel jlPakketTitel2;
    private JButton jbPakketNieuw;
    
    //Arraylists voor het ophalen van de gegevens uit de database
    ArrayList detailsButtons = new ArrayList();
    ArrayList pakket_ids = new ArrayList();
    
    public SchermOverzichtPakketjes(){
        
        // Verbinden met database
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString, username, password); 
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        
        //Laden van de fonts
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.getAllFonts();
        
        //Instellingen scherm
        setTitle("TZT Backend");
        setSize(800, 800);
        setLayout(new GridLayout(0, 3));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Laat het eerste deel van de titel zien
        jlPakketTitel = new JLabel("Pakket");
        jlPakketTitel.setForeground(new Color(237, 127, 35));
        jlPakketTitel.setFont(new Font("Roboto", Font.PLAIN, 50));
        add(jlPakketTitel);
        
        //Laat het 2e deel van de titel zien
        jlPakketTitel2 = new JLabel("overzicht");
        jlPakketTitel2.setForeground(new Color(237, 127, 35));
        jlPakketTitel2.setFont(new Font("Roboto", Font.PLAIN, 50));
        add(jlPakketTitel2);
        
        //Opvullen van regel in de gridlayout
        add(new JLabel(""));
        
        //Knop voor het toevoegen van nieuwe pakketjes
        jbPakketNieuw = new JButton("Nieuw pakket");
        add(jbPakketNieuw);
        jbPakketNieuw.addActionListener(this);
        
        //Opvullen van de regel in de gridlayout
        add(new JLabel(""));
        add(new JLabel(""));
        
        //Ophalen gegevens uit de database voor overzicht pakketjes
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT pakket_id, trackingnr, gewicht"
                    + "FROM pakket");
            
            //Verwerken van de uit de database opgehaalde gegevens
            //Elke row wordt een voor een opgehaald door de loop en wordt vervolgens verwerkt in de loop
            while(resultSet.next()){
                
                //De uit de database opgehaalde gegevens worden in variabelen gestopt
                String pakketId = resultSet.getString("pakket_id");
                String trackingnr = resultSet.getString("trackingnr");
                String gewicht = resultSet.getString("gewicht");
                
                //De uit de database opgehaalde gegevens tonen op het scherm
                add(new JLabel(pakketId));
                add(new JLabel(trackingnr));
                add(new JLabel(gewicht));
            }
            
            // Databaseverbinding beëindigen
            statement.close();
            connection.close();
            
        } catch(SQLException e) {
            System.out.println(e);
        }

        //Maakt het scherm zichtbaar
        setVisible(true);
    }
    
    //Functie die actie onderneemt als er een knop ingedrukt wordt
    public void actionPerformed(ActionEvent e) {
        
    }
}
