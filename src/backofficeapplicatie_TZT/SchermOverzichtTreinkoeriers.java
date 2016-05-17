package backofficeapplicatie_TZT;

import static backofficeapplicatie_TZT.Databaseverbinding.connectionString;
import static backofficeapplicatie_TZT.Databaseverbinding.password;
import static backofficeapplicatie_TZT.Databaseverbinding.username;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SchermOverzichtTreinkoeriers extends JFrame implements ActionListener {
    
    private Koerier koerier;
    
    private JButton jbDetails;
    
    /* Twee arraylists: een waarin de detailsknoppen opgeslagen zullen worden en een waarin de 
    treinkoerier_ids opgeslagen zullen worden. Deze zijn nodig om na te gaan welke informatie 
    opgehaald moet worden uit de database wanneer er op welke detailsknop gedrukt wordt. */
    ArrayList detailsButtons = new ArrayList();
    ArrayList treinkoerier_ids = new ArrayList();

    public SchermOverzichtTreinkoeriers() {
        
        // Verbinden met database
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString, username, password); 
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        
        // Instellingen scherm
        setTitle("Overzicht treinkoeriers");
        setSize(700, 500);
        int rows = 0; // Aantal rows is varabel
	setLayout(new GridLayout(rows, 4));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Ophalen gegevens uit database voor overzicht treinkoeriers
        try {
            
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
		"SELECT t.treinkoerier_id, p.voornaam, p.achternaam " + 
                "FROM treinkoerier t " +
                "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id"); 
            
            while (resultSet.next()) { // Voor elke row in de database wordt een row op het scherm aangemaakt
                
                // Elke row bevat een treinkoerier_id, een voornaam en achternaam
                String treinkoerier_id = resultSet.getString("t.treinkoerier_id");
                String voornaam = resultSet.getString("p.voornaam");
                String achternaam = resultSet.getString("p.achternaam");
                
                // Toevoegen aan scherm
                add(new JLabel(treinkoerier_id));
                add(new JLabel(voornaam));
                add(new JLabel(achternaam));
                
                // Toevoegen van een detailsknop voor deze row
                jbDetails = new JButton("details");
                add(jbDetails);
                jbDetails.addActionListener(this);
                
                rows++; // Een row toevoegen aan de grid-layout, zodat nieuwe row goed wordt weergegeven
                
                detailsButtons.add(jbDetails); /* Toevoegen van de detailsknop aan een arraylist 
                met daarin alle detailsknoppen, zodat in de actionPerformed de detailsknoppen als verschillende 
                sources onderscheiden kunnen worden */
                treinkoerier_ids.add(resultSet.getString("t.treinkoerier_id")); /* Toevoegen van de treinkoerier_id
                aan een arraylist met daarin alle treinkoerier_ids, zodat in de actionPerformed per detailsknop de
                bijbehorende treinkoerier bekend is, en het mogelijk is om vervolgens de gegevens van deze 
                treinkoerier uit de database op te halen. */
                
            }
            
            // Databaseverbinding beëindigen
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
       
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent e) {
        
        // Verbinden met database
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString, username, password); 
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        
        int i = 0;
        while (i < detailsButtons.size()) {
        
            if (e.getSource() == detailsButtons.get(i)) {
                
                treinkoerier_ids.get(i); /* De detailsknop waarop gedrukt wordt, heeft dezelfde plaats (index)
                in de detailsButtonsarraylist, als de bijbehorende treinkoerier_id heeft in 
                de treinkoerier_ids-arraylist */
                
                // Ophalen gegevens uit database
                try {
                    
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(
                        "SELECT * " + 
                        "FROM treinkoerier t " +
                        "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id " +
                        "WHERE t.treinkoerier_id = '" + treinkoerier_ids.get(i) + "'");

                    resultSet.next();
                    
                    String treinkoerier_id = resultSet.getString("treinkoerier_id"); 
                    String voornaam = resultSet.getString("voornaam");
                    String achternaam = resultSet.getString("achternaam");
                    String geslacht = resultSet.getString("geslacht");
                    String geboortedatum = resultSet.getString("gebdatum"); // Later naar kijken
                    String straat = resultSet.getString("straat");
                    String huisnummer = resultSet.getString("huisnr");
                    String postcode = resultSet.getString("postcode");
                    String plaats = resultSet.getString("plaats");
                    String email = resultSet.getString("email");
                    String telefoon = resultSet.getString("telefoon");
                    String bsn = resultSet.getString("bsn");
                    String documentnummer = resultSet.getString("documentnr");
                       
                    // Aanmaken nieuw koerierobject
                    Koerier koerier = new Koerier(treinkoerier_id, voornaam, achternaam, geslacht, geboortedatum, straat, huisnummer, 
                    postcode, plaats, email, telefoon, bsn, documentnummer, "Hash");
                    
                    // Maak nieuw scherm met de gegevens van de zojuist aangemaakte koerier
                    SchermGegevensTreinkoerier scherm = new SchermGegevensTreinkoerier(koerier);
                    
                    // Databaseverbinding beëindigen
                    statement.close();
                    connection.close();
                    
                    dispose(); // Sluit het huidige scherm
                    
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                
            }
            
            i++;
        }     

    }

}

