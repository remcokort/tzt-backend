package backofficeapplicatie_TZT;

import static backofficeapplicatie_TZT.Databaseverbinding.connectionString;
import static backofficeapplicatie_TZT.Databaseverbinding.password;
import static backofficeapplicatie_TZT.Databaseverbinding.username;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class SchermOverzichtTreinkoeriers extends JFrame implements ActionListener {
    
    private Koerier koerier;
    
    // Het scherm is opgebouwd uit de volgende panels {
    JTabbedPane tabblad = new JTabbedPane(); // Om de verschillende tabbladen in weer te geven
    
    // Panels tabblad 1 {
    JPanel panelTitel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
    JPanel panelFilter = new JPanel(new GridLayout(1, 3));
    JPanel panelGeenResultaten = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 18));
    JPanel panelOverzichtTreinkoeriers = new JPanel(); // Layout is variabel en wordt later geset
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    JScrollPane scrollPanePanelOverzichtTreinkoeriers = new JScrollPane();
    // }
    
    // Panels tabblad 2
    JPanel panelTitel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
    JPanel panelFilter2 = new JPanel(new GridLayout(1, 3));
    JPanel panelGeenResultaten2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 18));
    JPanel panelOverzichtTreinkoeriers2 = new JPanel(); // Layout is variabel en wordt later geset
    JPanel panelButtons2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    JScrollPane scrollPanePanelOverzichtTreinkoeriers2 = new JScrollPane();
    // } 
    
    // Panels tabblad 3
    JPanel panelTitel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
    JPanel panelFilter3 = new JPanel(new GridLayout(1, 3));
    JPanel panelGeenResultaten3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 18));
    JPanel panelOverzichtTreinkoeriers3 = new JPanel(); // Layout is variabel en wordt later geset
    JPanel panelButtons3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    JScrollPane scrollPanePanelOverzichtTreinkoeriers3 = new JScrollPane();
    // } 
    // } Einde panels
    
    // Declaratie onderdelen JFrame {
    // Onderdelen JFrame tabblad 1 {
    private JTextField jtfAchternaam;
    private JButton jbZoeken;
    private JButton jbDetails;
    private JButton jbTerug;
    // }
    
    // Onderdelen JFrame tabblad 2 {
    private JTextField jtfAchternaam2;
    private JButton jbZoeken2;
    private JButton jbDetails2;
    private JButton jbTerug2;
    // }
    
    // Onderdelen JFrame tabblad 3 {
    private JTextField jtfAchternaam3;
    private JButton jbZoeken3;
    private JButton jbDetails3;
    private JButton jbTerug3;
    // }
    // } Einde onderdelen JFrame
    
    // Tabblad 1 {
    private int rows; // Aantal rows is variabel
    private int hightScherm;
    private int hightPanelOverzichtKoeriers; // Is afhankelijk van het aantal rows
    private int hightPanePanelOverzichtKoeriers;
    private String ingevoerdeAchternaam;
    private String query;
    // }
    
    // Tabblad 2 {
    private int rows2; // Aantal rows is varabel
    private int hightScherm2;
    private int hightPanelOverzichtKoeriers2; // Is afhankelijk van het aantal rows
    private int hightPanePanelOverzichtKoeriers2;
    private String ingevoerdeAchternaam2;
    private String query2;
    // }
    
    // Tabblad 3 {
    private int rows3; // Aantal rows is varabel
    private int hightScherm3;
    private int hightPanelOverzichtKoeriers3; // Is afhankelijk van het aantal rows
    private int hightPanePanelOverzichtKoeriers3;
    private String ingevoerdeAchternaam3;
    private String query3;
    // }
    
    /* Twee arraylists per tabblad: een waarin de detailsknoppen opgeslagen zullen worden en een waarin de 
    treinkoerier_ids opgeslagen zullen worden. Deze zijn nodig om na te gaan welke informatie 
    opgehaald moet worden uit de database wanneer er op welke detailsknop gedrukt wordt. { */
    // ArrayLists tabblad 1 {
    ArrayList detailsButtons = new ArrayList();
    ArrayList treinkoerier_ids = new ArrayList();
    // }
    
    // ArayLists tabblad 2 {
    ArrayList detailsButtons2 = new ArrayList();
    ArrayList treinkoerier_ids2 = new ArrayList();
    // }
    
    // ArayLists tabblad 3 {
    ArrayList detailsButtons3 = new ArrayList();
    ArrayList treinkoerier_ids3 = new ArrayList();
    // }
    // } Einde ArrayLists
    
    public void setIngevoerdeAchternaam(String ingevoerdeAchternaam) {
        this.ingevoerdeAchternaam = ingevoerdeAchternaam;
    }
    
    public void setIngevoerdeAchternaam2(String ingevoerdeAchternaam2) {
        this.ingevoerdeAchternaam2 = ingevoerdeAchternaam2;
    }
    
    public void setIngevoerdeAchternaam3(String ingevoerdeAchternaam3) {
        this.ingevoerdeAchternaam3 = ingevoerdeAchternaam3;
    }

    public SchermOverzichtTreinkoeriers(String ingevoerdeAchternaam) {
        
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
        setSize(740, 580);
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
        // Tabblad 1 {
        panelTitel.setPreferredSize(new Dimension(700, 90));
        panelFilter.setPreferredSize(new Dimension(680, 40));
        panelOverzichtTreinkoeriers.setLayout(new GridLayout(rows, 4)); // Afmetingen worden later gezet, want zijn variabel
        panelButtons.setPreferredSize(new Dimension(700, 40));
        // }
        
        // Tabblad 2 {
        panelTitel2.setPreferredSize(new Dimension(700, 90));
        panelFilter2.setPreferredSize(new Dimension(680, 40));
        panelOverzichtTreinkoeriers2.setLayout(new GridLayout(rows, 4)); // Afmetingen worden later gezet, want zijn variabel
        panelButtons2.setPreferredSize(new Dimension(700, 40));
        // }
        
        // Tabblad 3 {
        panelTitel3.setPreferredSize(new Dimension(700, 90));
        panelFilter3.setPreferredSize(new Dimension(680, 40));
        panelOverzichtTreinkoeriers3.setLayout(new GridLayout(rows, 4)); // Afmetingen worden later gezet, want zijn variabel
        panelButtons3.setPreferredSize(new Dimension(700, 40));
        // }
        // } Einde instellingen pannels

        // panelTitel {
        // Tabblad 1 {
        JLabel titel = new JLabel("Actieve Treinkoeriers");
        titel.setForeground(new Color(237, 127, 35));
        titel.setFont(new Font("Roboto", Font.PLAIN, 50));
        panelTitel.add(titel);
        add(panelTitel); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 2 {
        JLabel titel2 = new JLabel("In Afwachting");
        titel2.setForeground(new Color(237, 127, 35));
        titel2.setFont(new Font("Roboto", Font.PLAIN, 50));
        panelTitel2.add(titel2);
        add(panelTitel2); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 3 {
        JLabel titel3 = new JLabel("Geblokkeerde Treinkoeriers");
        titel3.setForeground(new Color(237, 127, 35));
        titel3.setFont(new Font("Roboto", Font.PLAIN, 50));
        panelTitel3.add(titel3);
        add(panelTitel3); // Toevoegen van panel het aan scherm
        // }        
        // } Einde panelTitel
        
        // panelFilter {
        // Tabblad 1 {
        panelFilter.add(new JLabel("Zoeken op achternaam:"));
        
        jtfAchternaam = new JTextField(10);
        panelFilter.add(jtfAchternaam);
        
        jbZoeken = new JButton("Zoeken");
        panelFilter.add(jbZoeken);
        jbZoeken.addActionListener(this);
        
        add(panelFilter); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 2 {        
        panelFilter2.add(new JLabel("Zoeken op achternaam:"));
        
        jtfAchternaam2 = new JTextField(10);
        panelFilter2.add(jtfAchternaam2);
        
        jbZoeken2 = new JButton("Zoeken");
        panelFilter2.add(jbZoeken2);
        jbZoeken2.addActionListener(this);
        
        add(panelFilter2); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 3 {        
        panelFilter3.add(new JLabel("Zoeken op achternaam:"));
        
        jtfAchternaam3 = new JTextField(10);
        panelFilter3.add(jtfAchternaam3);
        
        jbZoeken3 = new JButton("Zoeken");
        panelFilter3.add(jbZoeken3);
        jbZoeken3.addActionListener(this);
        
        add(panelFilter3); // Toevoegen van panel het aan scherm
        // }
        // } Einde panelFilter
        
        // panelOverzichtTreinkoeriers {
        // Tabblad 1 {
        panelOverzichtTreinkoeriers.add(new JLabel("Treinkoerier-ID"));
        panelOverzichtTreinkoeriers.add(new JLabel("Voornaam"));
        panelOverzichtTreinkoeriers.add(new JLabel("Achternaam"));
        panelOverzichtTreinkoeriers.add(new JLabel("")); // Lege cel boven detailsknoppen

        query = // Als het zoekveld voor achternaam leeg is gelaten is dit de query
            "SELECT t.treinkoerier_id AS treinkoerier_id1, p.voornaam AS voornaam1, p.achternaam AS achternaam1 " + 
            "FROM treinkoerier t " +
            "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id " + 
            "WHERE t.actief = 1 ";
        if (ingevoerdeAchternaam != "") { // Als het zoekveld voor achternaam niet leeg is gelaten, wordt deze invoer verwerkt in de query: 
            query += "&& p.achternaam LIKE '%" + ingevoerdeAchternaam + "%'";
        } 
        // }
        
        // Tabblad 2 {
        panelOverzichtTreinkoeriers2.add(new JLabel("Treinkoerier-ID"));
        panelOverzichtTreinkoeriers2.add(new JLabel("Voornaam"));
        panelOverzichtTreinkoeriers2.add(new JLabel("Achternaam"));
        panelOverzichtTreinkoeriers2.add(new JLabel("")); // Lege cel boven detailsknoppen

        query2 = // Als het zoekveld voor achternaam leeg is gelaten is dit de query
            "SELECT t.treinkoerier_id AS treinkoerier_id2, p.voornaam AS voornaam2, p.achternaam AS achternaam2 " + 
            "FROM treinkoerier t " +
            "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id " +
            "WHERE t.actief = 0 ";
        if (ingevoerdeAchternaam2 != "") { // Als het zoekveld voor achternaam niet leeg is gelaten, wordt deze invoer verwerkt in de query: 
            query2 += "&& p.achternaam LIKE '%" + ingevoerdeAchternaam + "%'";
        } 
        // }
        
        // Tabblad 3 {
        panelOverzichtTreinkoeriers3.add(new JLabel("Treinkoerier-ID"));
        panelOverzichtTreinkoeriers3.add(new JLabel("Voornaam"));
        panelOverzichtTreinkoeriers3.add(new JLabel("Achternaam"));
        panelOverzichtTreinkoeriers3.add(new JLabel("")); // Lege cel boven detailsknoppen

        query3 = // Als het zoekveld voor achternaam leeg is gelaten is dit de query
            "SELECT t.treinkoerier_id AS treinkoerier_id3, p.voornaam AS voornaam3, p.achternaam AS achternaam3 " + 
            "FROM treinkoerier t " +
            "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id " +
            "WHERE t.actief = 2 ";
        if (ingevoerdeAchternaam3 != "") { // Als het zoekveld voor achternaam niet leeg is gelaten, wordt deze invoer verwerkt in de query: 
            query2 += "&& p.achternaam LIKE '%" + ingevoerdeAchternaam + "%'";
        } 
        // }
        
        // Ophalen gegevens uit database voor overzicht treinkoeriers
        try {
            // Tabblad 1 {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);             
            
            rows = 0; // Het aantal rows in de gridLayout is 0: er staan nog geen gegevens in
            
            while (resultSet.next()) { // Voor elke row in de database wordt een row op het scherm aangemaakt
                
                // Elke row bevat een treinkoerier_id, een voornaam, achternaam en detailsbutton
                String treinkoerier_id = resultSet.getString("treinkoerier_id1");
                panelOverzichtTreinkoeriers.add(new JLabel(treinkoerier_id)); // Treinkoerier-ID voor deze row toevoegen aan het panel
                
                String voornaam = resultSet.getString("voornaam1");
                panelOverzichtTreinkoeriers.add(new JLabel(voornaam)); // Voornaam voor deze row toevoegen aan het panel
                
                String achternaam = resultSet.getString("achternaam1");
                panelOverzichtTreinkoeriers.add(new JLabel(achternaam)); // Achternaam voor deze row toevoegen aan het panel
                
                jbDetails = new JButton("Details");
                panelOverzichtTreinkoeriers.add(jbDetails); // Detailsknop voor deze row toevoegen aan het panel
                jbDetails.addActionListener(this);

                rows++; // Een row toevoegen aan de grid-layout, zodat de nieuwe row goed wordt weergegeven
                
                detailsButtons.add(jbDetails); /* Toevoegen van de detailsknop aan een arraylist 
                met daarin alle detailsknoppen, zodat in de actionPerformed de detailsknoppen als verschillende 
                sources onderscheiden kunnen worden */
               
                treinkoerier_ids.add(resultSet.getString("treinkoerier_id1")); /* Toevoegen van de treinkoerier_id
                aan een arraylist met daarin alle treinkoerier_ids, zodat in de actionPerformed per detailsknop de
                bijbehorende treinkoerier bekend is, en het mogelijk is om vervolgens de gegevens van deze 
                treinkoerier uit de database op te halen. */
                
            }
            // }
            
            // Tabblad 2 {
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query2); 
            
            rows2 = 0;
            
            while (resultSet2.next()) {
                
                // Elke row bevat een treinkoerier_id, een voornaam, achternaam en detailsbutton
                String treinkoerier_id = resultSet2.getString("treinkoerier_id2");
                panelOverzichtTreinkoeriers2.add(new JLabel(treinkoerier_id)); // Treinkoerier-ID voor deze row toevoegen aan het panel
                
                String voornaam = resultSet2.getString("voornaam2");
                panelOverzichtTreinkoeriers2.add(new JLabel(voornaam)); // Voornaam voor deze row toevoegen aan het panel
                
                String achternaam = resultSet2.getString("achternaam2");
                panelOverzichtTreinkoeriers2.add(new JLabel(achternaam)); // Achternaam voor deze row toevoegen aan het panel
                                
                jbDetails2 = new JButton("Details");
                panelOverzichtTreinkoeriers2.add(jbDetails2); // Detailsknop voor deze row toevoegen aan het panel
                jbDetails2.addActionListener(this); 
                
                rows2++;
                
                detailsButtons2.add(jbDetails2); /* Toevoegen van de detailsknop aan een arraylist 
                met daarin alle detailsknoppen, zodat in de actionPerformed de detailsknoppen als verschillende 
                sources onderscheiden kunnen worden */
                             
                treinkoerier_ids2.add(resultSet2.getString("treinkoerier_id2")); /* Toevoegen van de treinkoerier_id
                aan een arraylist met daarin alle treinkoerier_ids, zodat in de actionPerformed per detailsknop de
                bijbehorende treinkoerier bekend is, en het mogelijk is om vervolgens de gegevens van deze 
                treinkoerier uit de database op te halen. */
            }
            // }
            
            // Tabblad 3 {
            Statement statement3 = connection.createStatement();
            ResultSet resultSet3 = statement3.executeQuery(query3); 
            
            rows3 = 0;
            
            while (resultSet3.next()) {
                
                // Elke row bevat een treinkoerier_id, een voornaam, achternaam en detailsbutton
                String treinkoerier_id = resultSet3.getString("treinkoerier_id3");
                panelOverzichtTreinkoeriers3.add(new JLabel(treinkoerier_id)); // Treinkoerier-ID voor deze row toevoegen aan het panel
                
                String voornaam = resultSet3.getString("voornaam3");
                panelOverzichtTreinkoeriers3.add(new JLabel(voornaam)); // Voornaam voor deze row toevoegen aan het panel
                
                String achternaam = resultSet2.getString("achternaam3");
                panelOverzichtTreinkoeriers3.add(new JLabel(achternaam)); // Achternaam voor deze row toevoegen aan het panel
                                
                jbDetails3 = new JButton("Details");
                panelOverzichtTreinkoeriers3.add(jbDetails3); // Detailsknop voor deze row toevoegen aan het panel
                jbDetails3.addActionListener(this); 
                
                rows3++;
                
                detailsButtons3.add(jbDetails3); /* Toevoegen van de detailsknop aan een arraylist 
                met daarin alle detailsknoppen, zodat in de actionPerformed de detailsknoppen als verschillende 
                sources onderscheiden kunnen worden */
                             
                treinkoerier_ids3.add(resultSet3.getString("treinkoerier_id3")); /* Toevoegen van de treinkoerier_id
                aan een arraylist met daarin alle treinkoerier_ids, zodat in de actionPerformed per detailsknop de
                bijbehorende treinkoerier bekend is, en het mogelijk is om vervolgens de gegevens van deze 
                treinkoerier uit de database op te halen. */
            }
            // }
            
            // Databaseverbinding beëindigen {
            statement.close();
            statement2.close();
            statement3.close();
            connection.close();
            // }
            
            // Tabblad 1 {
            hightPanelOverzichtKoeriers = (rows * 50 + 50); // Voor elke extra row wordt het panel 50 pixels hoger
            panelOverzichtTreinkoeriers.setPreferredSize(new Dimension(600, hightPanelOverzichtKoeriers));

            if (hightPanelOverzichtKoeriers <= 300) {
                hightPanePanelOverzichtKoeriers = hightPanelOverzichtKoeriers + 5;
            } else {
                hightPanePanelOverzichtKoeriers = 300;
            }
            // Setten van de afmeting van de scroll pane waarin het panelOverzichtTreinkoeriers zit
            scrollPanePanelOverzichtTreinkoeriers.setPreferredSize(new Dimension(680, hightPanePanelOverzichtKoeriers));

            if (rows != 0) { // Als de query resultaat hebben opgeleverd
                scrollPanePanelOverzichtTreinkoeriers.getViewport().add(panelOverzichtTreinkoeriers); // panelOverzichtTreinkoeriers toevoegen aan de scroll pane
            } else {
                JLabel geenResultaten = new JLabel("Geen resultaten");
                geenResultaten.setForeground(Color.RED);
                panelGeenResultaten.setPreferredSize(new Dimension(600, 40));
                panelGeenResultaten.add(geenResultaten);
                scrollPanePanelOverzichtTreinkoeriers.getViewport().add(panelGeenResultaten);
            }
            add(scrollPanePanelOverzichtTreinkoeriers); // De scroll pane met daarin het overzicht van de treinkoeriers toevoegen aan het scherm
            // }
            
            // Tabblad 2 {
            hightPanelOverzichtKoeriers2 = (rows2 * 50 + 50); // Voor elke extra row wordt het panel 50 pixels hoger
            panelOverzichtTreinkoeriers2.setPreferredSize(new Dimension(600, hightPanelOverzichtKoeriers2));
            
            if (hightPanelOverzichtKoeriers2 <= 300) {
                hightPanePanelOverzichtKoeriers2 = hightPanelOverzichtKoeriers2 + 5;
            } else {
                hightPanePanelOverzichtKoeriers2 = 300;
            }  
  
            // Setten van de afmetingen van de scroll pane waarin het panelOverzichtTreinkoeriers zit
            scrollPanePanelOverzichtTreinkoeriers2.setPreferredSize(new Dimension(680, hightPanePanelOverzichtKoeriers2));
            
            if (rows2 != 0) { // Als de query resultaat hebben opgeleverd
                scrollPanePanelOverzichtTreinkoeriers2.getViewport().add(panelOverzichtTreinkoeriers2); // panelOverzichtTreinkoeriers toevoegen aan de scroll pane
            } else {
                JLabel geenResultaten2 = new JLabel("Geen resultaten");
                geenResultaten2.setForeground(Color.RED);
                panelGeenResultaten2.setPreferredSize(new Dimension(600, 40));
                panelGeenResultaten2.add(geenResultaten2);
                scrollPanePanelOverzichtTreinkoeriers2.getViewport().add(panelGeenResultaten2);
            }
            add(scrollPanePanelOverzichtTreinkoeriers2); // De scroll pane met daarin het overzicht van de treinkoeriers toevoegen aan het scherm
            // }  
            
            // Tabblad 3 {
            hightPanelOverzichtKoeriers3 = (rows3 * 50 + 50); // Voor elke extra row wordt het panel 50 pixels hoger
            panelOverzichtTreinkoeriers3.setPreferredSize(new Dimension(600, hightPanelOverzichtKoeriers3));
            
            if (hightPanelOverzichtKoeriers3 <= 300) {
                hightPanePanelOverzichtKoeriers3 = hightPanelOverzichtKoeriers3 + 5;
            } else {
                hightPanePanelOverzichtKoeriers3 = 300;
            }  
  
            // Setten van de afmetingen van de scroll pane waarin het panelOverzichtTreinkoeriers zit
            scrollPanePanelOverzichtTreinkoeriers3.setPreferredSize(new Dimension(680, hightPanePanelOverzichtKoeriers2));
            
            if (rows3 != 0) { // Als de query resultaat hebben opgeleverd
                scrollPanePanelOverzichtTreinkoeriers3.getViewport().add(panelOverzichtTreinkoeriers3); // panelOverzichtTreinkoeriers toevoegen aan de scroll pane
            } else {
                JLabel geenResultaten3 = new JLabel("Geen resultaten");
                geenResultaten3.setForeground(Color.RED);
                panelGeenResultaten3.setPreferredSize(new Dimension(600, 40));
                panelGeenResultaten3.add(geenResultaten3);
                scrollPanePanelOverzichtTreinkoeriers3.getViewport().add(panelGeenResultaten3);
            }
            add(scrollPanePanelOverzichtTreinkoeriers3); // De scroll pane met daarin het overzicht van de treinkoeriers toevoegen aan het scherm
            // } 
            
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        // } Einde panelOverzichtTreinkoeriers
        
        // panelButtons {
        // Tabblad 1 {
        jbTerug = new JButton("Terug naar Beginscherm");
        panelButtons.add(jbTerug);
        jbTerug.addActionListener(this);
        
        add(panelButtons); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 2 {
        jbTerug2 = new JButton("Terug naar Beginscherm");
        panelButtons2.add(jbTerug2);
        jbTerug2.addActionListener(this);
        
        add(panelButtons2); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 3 {
        jbTerug3 = new JButton("Terug naar Beginscherm");
        panelButtons3.add(jbTerug3);
        jbTerug3.addActionListener(this);
        
        add(panelButtons2); // Toevoegen van panel het aan scherm
        // }
        // } Einde panelButtons
        
        // De containers die per container alles bevatten wat op één tabblad staat {
        // Tabblad 1 {
        JPanel container = new JPanel();
        container.setPreferredSize(new Dimension(722, 605));
        container.add(panelTitel);
        container.add(panelFilter);
        container.add(scrollPanePanelOverzichtTreinkoeriers);
        container.add(panelButtons);
        // }
        
        // Tabblad 2 {        
        JPanel container2 = new JPanel();
        container2.setPreferredSize(new Dimension(722, 605));
        container2.add(panelTitel2);
        container2.add(panelFilter2);
        container2.add(scrollPanePanelOverzichtTreinkoeriers2);
        container2.add(panelButtons2);
        // }
        
        // Tabblad 3 {        
        JPanel container3 = new JPanel();
        container3.setPreferredSize(new Dimension(722, 605));
        container3.add(panelTitel3);
        container3.add(panelFilter3);
        container3.add(scrollPanePanelOverzichtTreinkoeriers3);
        container3.add(panelButtons3);
        // }
        // } Einde containers
        
        // Toevoegen van de containers aan de drie tabbladen
        tabblad.addTab("Actief", container);
        tabblad.addTab("In Afwachting", container2);
        tabblad.addTab("Geblokkeerd", container3);
        
        add(tabblad);
       
        setVisible(true);
        
    }

    public void actionPerformed(ActionEvent e) {
        
        // Action performed voor tabblad 1 {
        if (e.getSource() == jbZoeken) {
            
            if (jtfAchternaam.getText().length() != 0) { // Als het zoekveld niet leeg is
                
                setIngevoerdeAchternaam(jtfAchternaam.getText());
                
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers(ingevoerdeAchternaam);
                sot.jtfAchternaam.setText(jtfAchternaam.getText());
                sot.jtfAchternaam2.setText(jtfAchternaam.getText());
                sot.setIngevoerdeAchternaam(ingevoerdeAchternaam);
                sot.setIngevoerdeAchternaam2(ingevoerdeAchternaam);
                
                sot.tabblad.setSelectedIndex(tabblad.getSelectedIndex());
                
                dispose();
                
            } else {
                
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers("");
                sot.tabblad.setSelectedIndex(tabblad.getSelectedIndex());
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
                    SchermGegevensTreinkoerier sgt = new SchermGegevensTreinkoerier(koerier);
                    sgt.setTab(tabblad.getSelectedIndex()); 
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
        // }
        
        // Action performed voor tabblad 2
        if (e.getSource() == jbZoeken2) {
            
            if (jtfAchternaam2.getText().length() != 0) {
                
                setIngevoerdeAchternaam2(jtfAchternaam2.getText());
                
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers(ingevoerdeAchternaam2);
                sot.jtfAchternaam2.setText(jtfAchternaam2.getText());
                sot.jtfAchternaam.setText(jtfAchternaam2.getText());
                sot.setIngevoerdeAchternaam2(ingevoerdeAchternaam2);
                sot.setIngevoerdeAchternaam(ingevoerdeAchternaam2);
                
                sot.tabblad.setSelectedIndex(tabblad.getSelectedIndex());
                
                dispose();
                
            } else {
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers("");
                
                sot.tabblad.setSelectedIndex(tabblad.getSelectedIndex());
                
                dispose();
            }
        }
        
        int i2 = 0;
        while (i2 < detailsButtons2.size()) { 
        
            if (e.getSource() == detailsButtons2.get(i2)) {
                
                // Verbinden met database {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(connectionString, username, password); 
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                // }
                
                treinkoerier_ids2.get(i2); /* De detailsknop waarop gedrukt wordt, heeft dezelfde plaats (index)
                in de detailsButtonsarraylist, als de bijbehorende treinkoerier_id heeft in 
                de treinkoerier_ids-arraylist */
                
                // Ophalen gegevens uit database
                try {
                    
                    Statement statement2 = connection.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery(
                        "SELECT * " + 
                        "FROM treinkoerier t " +
                        "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id " +
                        "WHERE t.treinkoerier_id = '" + treinkoerier_ids2.get(i2) + "'");

                    resultSet2.next();
                    
                    String treinkoerier_id = resultSet2.getString("treinkoerier_id"); 
                    String voornaam = resultSet2.getString("voornaam");
                    String achternaam = resultSet2.getString("achternaam");
                    String geslacht = resultSet2.getString("geslacht");
                    String geboortedatum = resultSet2.getString("gebdatum"); // Later naar kijken
                    String straat = resultSet2.getString("straat");
                    String huisnummer = resultSet2.getString("huisnr");
                    String postcode = resultSet2.getString("postcode");
                    String plaats = resultSet2.getString("plaats");
                    String email = resultSet2.getString("email");
                    String telefoon = resultSet2.getString("telefoon");
                    String bsn = resultSet2.getString("bsn");
                    String documentnummer = resultSet2.getString("documentnr");
                       
                    // Aanmaken nieuw koerierobject
                    Koerier koerier = new Koerier(treinkoerier_id, voornaam, achternaam, geslacht, geboortedatum, straat, huisnummer, 
                    postcode, plaats, email, telefoon, bsn, documentnummer, "Hash");
                    
                    // Maak nieuw scherm met de gegevens van de zojuist aangemaakte koerier
                    SchermGegevensTreinkoerier sgt = new SchermGegevensTreinkoerier(koerier);
                    sgt.setTab(tabblad.getSelectedIndex()); 
                    
                    // Databaseverbinding beëindigen
                    statement2.close();
                    connection.close();
                    
                    dispose(); // Sluit het huidige scherm
                    
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                
            }
            
            i2++;
        } 
        
        // Action performed voor tabblad 3
        if (e.getSource() == jbZoeken3) {
            
            if (jtfAchternaam3.getText().length() != 0) {
                
                setIngevoerdeAchternaam2(jtfAchternaam3.getText());
                
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers(ingevoerdeAchternaam3);
                sot.jtfAchternaam3.setText(jtfAchternaam3.getText());
                sot.jtfAchternaam.setText(jtfAchternaam3.getText());
                sot.setIngevoerdeAchternaam3(ingevoerdeAchternaam3);
                sot.setIngevoerdeAchternaam(ingevoerdeAchternaam3);
                
                sot.tabblad.setSelectedIndex(tabblad.getSelectedIndex());
                
                dispose();
                
            } else {
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers("");
                
                sot.tabblad.setSelectedIndex(tabblad.getSelectedIndex());
                
                dispose();
            }
        }
        
        int i3 = 0;
        while (i3 < detailsButtons2.size()) { 
        
            if (e.getSource() == detailsButtons2.get(i2)) {
                
                // Verbinden met database {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(connectionString, username, password); 
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                // }
                
                treinkoerier_ids2.get(i2); /* De detailsknop waarop gedrukt wordt, heeft dezelfde plaats (index)
                in de detailsButtonsarraylist, als de bijbehorende treinkoerier_id heeft in 
                de treinkoerier_ids-arraylist */
                
                // Ophalen gegevens uit database
                try {
                    
                    Statement statement2 = connection.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery(
                        "SELECT * " + 
                        "FROM treinkoerier t " +
                        "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id " +
                        "WHERE t.treinkoerier_id = '" + treinkoerier_ids2.get(i2) + "'");

                    resultSet2.next();
                    
                    String treinkoerier_id = resultSet2.getString("treinkoerier_id"); 
                    String voornaam = resultSet2.getString("voornaam");
                    String achternaam = resultSet2.getString("achternaam");
                    String geslacht = resultSet2.getString("geslacht");
                    String geboortedatum = resultSet2.getString("gebdatum"); // Later naar kijken
                    String straat = resultSet2.getString("straat");
                    String huisnummer = resultSet2.getString("huisnr");
                    String postcode = resultSet2.getString("postcode");
                    String plaats = resultSet2.getString("plaats");
                    String email = resultSet2.getString("email");
                    String telefoon = resultSet2.getString("telefoon");
                    String bsn = resultSet2.getString("bsn");
                    String documentnummer = resultSet2.getString("documentnr");
                       
                    // Aanmaken nieuw koerierobject
                    Koerier koerier = new Koerier(treinkoerier_id, voornaam, achternaam, geslacht, geboortedatum, straat, huisnummer, 
                    postcode, plaats, email, telefoon, bsn, documentnummer, "Hash");
                    
                    // Maak nieuw scherm met de gegevens van de zojuist aangemaakte koerier
                    SchermGegevensTreinkoerier sgt = new SchermGegevensTreinkoerier(koerier);
                    sgt.setTab(tabblad.getSelectedIndex()); 
                    
                    // Databaseverbinding beëindigen
                    statement2.close();
                    connection.close();
                    
                    dispose(); // Sluit het huidige scherm
                    
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                
            }
            
            i2++;
        } 
        
        if (e.getSource() == jbTerug2) { // Als de terugknop wordt aangeklikt
            
            SchermHome scherm = new SchermHome();
            dispose(); 
            
        }

    }

}

