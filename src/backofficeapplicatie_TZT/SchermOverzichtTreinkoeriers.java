package backofficeapplicatie_TZT;

import static backofficeapplicatie_TZT.Databaseverbinding.connectionString;
import static backofficeapplicatie_TZT.Databaseverbinding.password;
import static backofficeapplicatie_TZT.Databaseverbinding.username;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import javax.swing.UIManager;

public class SchermOverzichtTreinkoeriers extends JFrame implements ActionListener {
    
    private Koerier koerier;
    
    // Het scherm is opgebouwd uit de volgende panels {
    JPanel panelTitel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
    JPanel panelFilter = new JPanel(new GridLayout(1, 3));
    JPanel panelOverzichtTreinkoeriers = new JPanel(); // Layout is variabel en wordt later geset
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    
    JScrollPane scrollPanePanelOverzichtTreinkoeriers = new JScrollPane();
    // }
    
    // Declaratie onderdelen JFrame {
    private JTextField jtfAchternaam;
    private JButton jbZoeken;
    private JButton jbDetails;
    private JButton jbTerug;
    // }
    
    private int rows; // Aantal rows is varabel
    private int hightScherm;
    private int hightPanelOverzichtKoeriers; // Is afhankelijk van het aantal rows
    private int hightPanePanelOverzichtKoeriers;
    private String ingevoerdeAchternaam;
    private String query;
    
    /* Twee arraylists: een waarin de detailsknoppen opgeslagen zullen worden en een waarin de 
    treinkoerier_ids opgeslagen zullen worden. Deze zijn nodig om na te gaan welke informatie 
    opgehaald moet worden uit de database wanneer er op welke detailsknop gedrukt wordt. */
    ArrayList detailsButtons = new ArrayList();
    ArrayList treinkoerier_ids = new ArrayList();
    
    public void setIngevoerdeAchternaam(String ingevoerdeAchternaam) {
        this.ingevoerdeAchternaam = ingevoerdeAchternaam;
    }

    public SchermOverzichtTreinkoeriers(String ingevoerdeAchternaam) {
        
        
        System.out.println(this.ingevoerdeAchternaam);
        
        // Verbinden met database {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString, username, password); 
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        // }
        
        // Instellingen scherm {
        setTitle("Overzicht treinkoeriers");
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // }
        
        // Wanneer op het kruisje wordt geklikt wordt het volgende dialoog getoond
        UIManager.put("OptionPane.okButtonText", "Ja"); // Verandert de tekst op de OK-button naar 'Ja'
        UIManager.put("OptionPane.cancelButtonText", "Annuleren"); // Verandert de tekst op de Cancel-button naar 'Annuleren'
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                JDialog dialoog = new JDialog();
                if (JOptionPane.showConfirmDialog(
                    dialoog, 
                    "De applicatie afsluiten?", 
                    "Afsluiten", 
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        UIManager.put("OptionPane.okButtonText", "OK"); // Verandert de tekst op de OK-button weer naar OK
        
        // Setten van instellingen van de panels waaruit het scherm is opgebouwd {
        panelTitel.setPreferredSize(new Dimension(700, 90));
        panelFilter.setPreferredSize(new Dimension(680, 40));
        panelOverzichtTreinkoeriers.setLayout(new GridLayout(rows, 4)); // Afmetingen worden later gezet, want zijn variabel
        panelButtons.setPreferredSize(new Dimension(700, 40));
        // }

        // panelTitel {
        JLabel titel = new JLabel("Overzicht Treinkoeriers");
        titel.setForeground(new Color(237, 127, 35));
        titel.setFont(new Font("Roboto", Font.PLAIN, 50));
        panelTitel.add(titel);
        add(panelTitel); // Toevoegen van panel het aan scherm
        // }
        
        // panelFilter {
        panelFilter.add(new JLabel("Zoeken op achternaam:"));
        
        jtfAchternaam = new JTextField(10);
        panelFilter.add(jtfAchternaam);
        
        jbZoeken = new JButton("Zoeken");
        panelFilter.add(jbZoeken);
        jbZoeken.addActionListener(this);
        
        add(panelFilter); // Toevoegen van panel het aan scherm
        // }
        
        // panelOverzichtTreinkoeriers {

        query = // Als het zoekveld voor achternaam leeg is gelaten is dit de query
            "SELECT t.treinkoerier_id, p.voornaam, p.achternaam " + 
            "FROM treinkoerier t " +
            "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id ";
        if (ingevoerdeAchternaam != "") { // Als het zoekveld voor achternaam niet leeg is gelaten, wordt deze invoer verwerkt in de query: 
            query += "WHERE p.achternaam = '" + ingevoerdeAchternaam + "'";
        } 
        
        // Ophalen gegevens uit database voor overzicht treinkoeriers
        try {
            
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); 
            
            rows = 0; // Het aantal rows in de gridLayout is 0: er staan nog geen gegevens in
            
            while (resultSet.next()) { // Voor elke row in de database wordt een row op het scherm aangemaakt
                
                // Elke row bevat een treinkoerier_id, een voornaam, achternaam en detailsbutton
                String treinkoerier_id = resultSet.getString("t.treinkoerier_id");
                panelOverzichtTreinkoeriers.add(new JLabel(treinkoerier_id)); // Treinkoerier-ID voor deze row toevoegen aan het panel
                
                String voornaam = resultSet.getString("p.voornaam");
                panelOverzichtTreinkoeriers.add(new JLabel(voornaam)); // Voornaam voor deze row toevoegen aan het panel
                
                String achternaam = resultSet.getString("p.achternaam");
                panelOverzichtTreinkoeriers.add(new JLabel(achternaam)); // Achternaam voor deze row toevoegen aan het panel
                
                jbDetails = new JButton("details");
                panelOverzichtTreinkoeriers.add(jbDetails); // Detailsknop voor deze row toevoegen aan het panel
                jbDetails.addActionListener(this); 

                rows++; // Een row toevoegen aan de grid-layout, zodat de nieuwe row goed wordt weergegeven
                
                detailsButtons.add(jbDetails); /* Toevoegen van de detailsknop aan een arraylist 
                met daarin alle detailsknoppen, zodat in de actionPerformed de detailsknoppen als verschillende 
                sources onderscheiden kunnen worden */
                
                treinkoerier_ids.add(resultSet.getString("t.treinkoerier_id")); /* Toevoegen van de treinkoerier_id
                aan een arraylist met daarin alle treinkoerier_ids, zodat in de actionPerformed per detailsknop de
                bijbehorende treinkoerier bekend is, en het mogelijk is om vervolgens de gegevens van deze 
                treinkoerier uit de database op te halen. */
                
            }
            
            // Databaseverbinding beëindigen {
            statement.close();
            connection.close();
            // }

            hightPanelOverzichtKoeriers = (rows * 50); // Voor elke extra row wordt het panel 50 pixels hoger
            panelOverzichtTreinkoeriers.setPreferredSize(new Dimension(600, hightPanelOverzichtKoeriers));
            
            
            if (hightPanelOverzichtKoeriers <= 300) {
                hightPanePanelOverzichtKoeriers = hightPanelOverzichtKoeriers + 5;
            } else {
                hightPanePanelOverzichtKoeriers = 300;
            }
            // Setten van de afmeting van de scroll pane waarin het panelOverzichtTreinkoeriers zit
            scrollPanePanelOverzichtTreinkoeriers.setPreferredSize(new Dimension(680, hightPanePanelOverzichtKoeriers));
            
            hightScherm = hightPanePanelOverzichtKoeriers + 265;
            
            setSize(740, hightScherm); // De hoogte van het scherm hangt af van het aantal gereturnde rows en wordt later geset
            
            if (rows != 0) { // Als de query resultaat hebben opgeleverd
                scrollPanePanelOverzichtTreinkoeriers.getViewport().add(panelOverzichtTreinkoeriers); // panelOverzichtTreinkoeriers toevoegen aan de scroll pane
                add(scrollPanePanelOverzichtTreinkoeriers); // De scroll pane met daarin het overzicht van de treinkoeriers toevoegen aan het scherm
            } else {
                JLabel geenResultaten = new JLabel("Geen resultaten");
                geenResultaten.setForeground(Color.RED);
                add(geenResultaten);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        // }
        
        // panelButtons {
        jbTerug = new JButton("Terug naar Beginscherm");
        panelButtons.add(jbTerug);
        jbTerug.addActionListener(this);
        
        add(panelButtons); // Toevoegen van panel het aan scherm
        // }  
       
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == jbZoeken) {
            
            if (jtfAchternaam.getText().length() != 0) { // Als het zoekveld niet leeg is
                
                setIngevoerdeAchternaam(jtfAchternaam.getText());
                
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers(ingevoerdeAchternaam);
                sot.jtfAchternaam.setText(jtfAchternaam.getText());
                sot.setIngevoerdeAchternaam(ingevoerdeAchternaam);
                
                dispose();
                
            } else {
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers("");
                dispose();
            }
        }
        
        int i = 0;
        while (i < detailsButtons.size()) {
        
            if (e.getSource() == detailsButtons.get(i)) {
                
                // Verbinden met database {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(connectionString, username, password); 
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                // }
                
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
        
        if (e.getSource() == jbTerug) { // Als de terugknop wordt aangeklikt
            
            SchermHome scherm = new SchermHome();
            dispose(); 
            
        }

    }

}

