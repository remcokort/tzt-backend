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
import static javafx.scene.text.Font.font;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.Font.font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SchermOverzichtTreinkoeriers extends JFrame implements ActionListener {
    
    private Koerier koerier;
    
    private Font font = new Font("Roboto", Font.PLAIN, 12);
    
    // Het scherm is opgebouwd uit de volgende panels {
    JTabbedPane tabblad = new JTabbedPane(); // Om de verschillende tabbladen in weer te geven
    
    // Panels tabblad 1 {
    JPanel panelTitel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
    JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    JPanel panelGeenResultaten = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 18));
    JPanel panelOverzichtTreinkoeriers = new JPanel(); // Layout is variabel en wordt later geset
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 15));
    JScrollPane scrollPanePanelOverzichtTreinkoeriers = new JScrollPane();
    // }
    
    // Panels tabblad 2
    JPanel panelTitel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
    JPanel panelFilter2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    JPanel panelGeenResultaten2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 18));
    JPanel panelOverzichtTreinkoeriers2 = new JPanel(); // Layout is variabel en wordt later geset
    JPanel panelButtons2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 15));
    JScrollPane scrollPanePanelOverzichtTreinkoeriers2 = new JScrollPane();
    // } 
    
    // Panels tabblad 3
    JPanel panelTitel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
    JPanel panelFilter3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    JPanel panelGeenResultaten3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 18));
    JPanel panelOverzichtTreinkoeriers3 = new JPanel(); // Layout is variabel en wordt later geset
    JPanel panelButtons3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 15));
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
        setSize(730, 600);
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
        
        tabblad.setPreferredSize(new Dimension(718, 600));
        
        // Setten van instellingen van de panels waaruit het scherm is opgebouwd {
        // Tabblad 1 {
        panelTitel.setPreferredSize(new Dimension(700, 90));
        panelFilter.setPreferredSize(new Dimension(680, 55));
        panelOverzichtTreinkoeriers.setLayout(new GridLayout(rows, 4)); // Afmetingen worden later gezet, want zijn variabel
        panelButtons.setPreferredSize(new Dimension(680, 64));
        // }
        
        // Tabblad 2 {
        panelTitel2.setPreferredSize(new Dimension(700, 90));
        panelFilter2.setPreferredSize(new Dimension(680, 55));
        panelOverzichtTreinkoeriers2.setLayout(new GridLayout(rows, 4)); // Afmetingen worden later gezet, want zijn variabel
        panelButtons2.setPreferredSize(new Dimension(680, 64));
        // }
        
        // Tabblad 3 {
        panelTitel3.setPreferredSize(new Dimension(700, 90));
        panelFilter3.setPreferredSize(new Dimension(680, 55));
        panelOverzichtTreinkoeriers3.setLayout(new GridLayout(rows, 4)); // Afmetingen worden later gezet, want zijn variabel
        panelButtons3.setPreferredSize(new Dimension(680, 64));
        // }
        // } Einde instellingen pannels

        // panelTitel {
        // Tabblad 1 {
        JLabel titel = new JLabel("Actieve Treinkoeriers");
        titel.setForeground(new Color(237, 127, 35));
        titel.setFont(new Font("Roboto", Font.PLAIN, 50));
        panelTitel.add(titel);
        panelTitel.setBackground(Color.WHITE);
        add(panelTitel); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 2 {
        JLabel titel2 = new JLabel("In Afwachting");
        titel2.setForeground(new Color(237, 127, 35));
        titel2.setFont(new Font("Roboto", Font.PLAIN, 50));
        panelTitel2.add(titel2);
        panelTitel2.setBackground(Color.WHITE);
        add(panelTitel2); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 3 {
        JLabel titel3 = new JLabel("Geblokkeerde Treinkoeriers");
        titel3.setForeground(new Color(237, 127, 35));
        titel3.setFont(new Font("Roboto", Font.PLAIN, 50));
        panelTitel3.add(titel3);
        panelTitel3.setBackground(Color.WHITE);
        add(panelTitel3); // Toevoegen van panel het aan scherm
        // }        
        // } Einde panelTitel
        
        // panelFilter {
        // Tabblad 1 {
        JLabel jlZoeken = new JLabel("Zoeken op achternaam:");
        jlZoeken.setPreferredSize(new Dimension(332, 40));
        jlZoeken.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        panelFilter.add(jlZoeken);
        
        jtfAchternaam = new JTextField();
        jtfAchternaam.setBorder(null);
        jtfAchternaam.setBackground(Color.LIGHT_GRAY);
        jtfAchternaam.setPreferredSize(new Dimension(174, 48));
        jtfAchternaam.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 0, Color.LIGHT_GRAY));
        panelFilter.add(jtfAchternaam);
        
        jbZoeken = new JButton("Zoeken");
        jbZoeken.setForeground(Color.WHITE);
        jbZoeken.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jbZoeken.setBackground(new Color(237, 127, 35));
        jbZoeken.setPreferredSize(new Dimension(173, 48));
        jbZoeken.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.WHITE));
        panelFilter.add(jbZoeken);
        jbZoeken.addActionListener(this);
        
        panelFilter.setBackground(Color.WHITE);
        panelFilter.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        add(panelFilter); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 2 {        
        JLabel jlZoeken2 = new JLabel("Zoeken op achternaam:");
        jlZoeken2.setPreferredSize(new Dimension(332, 40));
        jlZoeken2.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        panelFilter2.add(jlZoeken2);
        
        jtfAchternaam2 = new JTextField();
        jtfAchternaam2.setBorder(null);
        jtfAchternaam2.setBackground(Color.LIGHT_GRAY);
        jtfAchternaam2.setPreferredSize(new Dimension(174, 48));
        jtfAchternaam2.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 0, Color.LIGHT_GRAY));
        panelFilter2.add(jtfAchternaam2);
        
        jbZoeken2 = new JButton("Zoeken");
        jbZoeken2.setForeground(Color.WHITE);
        jbZoeken2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jbZoeken2.setBackground(new Color(237, 127, 35));
        jbZoeken2.setPreferredSize(new Dimension(173, 48));
        jbZoeken2.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.WHITE));
        panelFilter2.add(jbZoeken2);
        jbZoeken2.addActionListener(this);
        
        panelFilter2.setBackground(Color.WHITE);
        panelFilter2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
        add(panelFilter2); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 3 {        
        JLabel jlZoeken3 = new JLabel("Zoeken op achternaam:");
        jlZoeken3.setPreferredSize(new Dimension(332, 40));
        jlZoeken3.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        panelFilter3.add(jlZoeken3);
        
        jtfAchternaam3 = new JTextField();
        jtfAchternaam3.setBorder(null);
        jtfAchternaam3.setBackground(Color.LIGHT_GRAY);
        jtfAchternaam3.setPreferredSize(new Dimension(174, 48));
        jtfAchternaam3.setBorder(BorderFactory.createMatteBorder(0, 8, 0, 0, Color.LIGHT_GRAY));
        panelFilter3.add(jtfAchternaam3);
        
        jbZoeken3 = new JButton("Zoeken");
        jbZoeken3.setForeground(Color.WHITE);
        jbZoeken3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jbZoeken3.setBackground(new Color(237, 127, 35));
        jbZoeken3.setPreferredSize(new Dimension(173, 48));
        jbZoeken3.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, Color.WHITE));
        panelFilter3.add(jbZoeken3);
        jbZoeken3.addActionListener(this);
        
        panelFilter3.setBackground(Color.WHITE);
        panelFilter3.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        
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
            query3 += "&& p.achternaam LIKE '%" + ingevoerdeAchternaam + "%'";
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
                JLabel jlTreinkoerier_id1 = new JLabel(treinkoerier_id);
                jlTreinkoerier_id1.setFont(font);
                panelOverzichtTreinkoeriers.add(jlTreinkoerier_id1); // Treinkoerier-ID voor deze row toevoegen aan het panel
                
                String voornaam = resultSet.getString("voornaam1");
                JLabel jlVoornaam = new JLabel(voornaam);
                jlVoornaam.setFont(font);
                panelOverzichtTreinkoeriers.add(jlVoornaam); // Voornaam voor deze row toevoegen aan het panel
                
                String achternaam = resultSet.getString("achternaam1");
                JLabel jlAchternaam = new JLabel(achternaam);
                jlAchternaam.setFont(font);
                panelOverzichtTreinkoeriers.add(jlAchternaam); // Achternaam voor deze row toevoegen aan het panel
                
                jbDetails = new JButton("Details");
                jbDetails.setForeground(Color.WHITE);
                jbDetails.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                jbDetails.setBackground(new Color(237, 127, 35));
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
                String treinkoerier_id2 = resultSet2.getString("treinkoerier_id2");
                JLabel jlTreinkoerier_id2 = new JLabel(treinkoerier_id2);
                jlTreinkoerier_id2.setFont(font);
                panelOverzichtTreinkoeriers2.add(jlTreinkoerier_id2); // Treinkoerier-ID voor deze row toevoegen aan het panel
                
                String voornaam2 = resultSet2.getString("voornaam2");
                JLabel jlVoornaam2 = new JLabel(voornaam2);
                jlVoornaam2.setFont(font);
                panelOverzichtTreinkoeriers2.add(jlVoornaam2); // Voornaam voor deze row toevoegen aan het panel
                
                String achternaam2 = resultSet2.getString("achternaam2");
                JLabel jlAchternaam2 = new JLabel(achternaam2);
                jlAchternaam2.setFont(font);
                panelOverzichtTreinkoeriers2.add(jlAchternaam2); // Achternaam voor deze row toevoegen aan het panel
                
                jbDetails2 = new JButton("Details");
                jbDetails2.setForeground(Color.WHITE);
                jbDetails2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                jbDetails2.setBackground(new Color(237, 127, 35));
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
                String treinkoerier_id3 = resultSet3.getString("treinkoerier_id3");
                JLabel jlTreinkoerier_id3 = new JLabel(treinkoerier_id3);
                jlTreinkoerier_id3.setFont(font);
                panelOverzichtTreinkoeriers3.add(jlTreinkoerier_id3); // Treinkoerier-ID voor deze row toevoegen aan het panel
                
                String voornaam3 = resultSet3.getString("voornaam3");
                JLabel jlVoornaam3 = new JLabel(voornaam3);
                jlVoornaam3.setFont(font);
                panelOverzichtTreinkoeriers3.add(jlVoornaam3); // Voornaam voor deze row toevoegen aan het panel
                
                String achternaam3 = resultSet3.getString("achternaam3");
                JLabel jlAchternaam3 = new JLabel(achternaam3);
                jlAchternaam3.setFont(font);
                panelOverzichtTreinkoeriers3.add(jlAchternaam3); // Achternaam voor deze row toevoegen aan het panel
                
                jbDetails3 = new JButton("Details");
                jbDetails3.setForeground(Color.WHITE);
                jbDetails3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                jbDetails3.setBackground(new Color(237, 127, 35));
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
            hightPanelOverzichtKoeriers = (rows * 54 + 50); // Voor elke extra row wordt het panel 50 pixels hoger
            panelOverzichtTreinkoeriers.setPreferredSize(new Dimension(600, hightPanelOverzichtKoeriers));
            int widthScrollPanePanelOverzichtKoeriers;
            if (hightPanelOverzichtKoeriers <= 300) {
                widthScrollPanePanelOverzichtKoeriers = 680;
                hightPanePanelOverzichtKoeriers = hightPanelOverzichtKoeriers + 5;
                panelOverzichtTreinkoeriers.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
            } else {
                hightPanePanelOverzichtKoeriers = 300;
                widthScrollPanePanelOverzichtKoeriers = 715;
                panelOverzichtTreinkoeriers.setBorder(BorderFactory.createMatteBorder(0, 16, 0, 0, Color.WHITE));
            }
            
            // Setten van de afmeting van de scroll pane waarin het panelOverzichtTreinkoeriers zit
            scrollPanePanelOverzichtTreinkoeriers.setPreferredSize(new Dimension(widthScrollPanePanelOverzichtKoeriers, hightPanePanelOverzichtKoeriers));
            
            scrollPanePanelOverzichtTreinkoeriers.setBorder(null);
            
            if (rows != 0) { // Als de query resultaat hebben opgeleverd
                scrollPanePanelOverzichtTreinkoeriers.getViewport().add(panelOverzichtTreinkoeriers); // panelOverzichtTreinkoeriers toevoegen aan de scroll pane
            } else {
                JLabel geenResultaten = new JLabel("Geen resultaten");
                geenResultaten.setForeground(Color.RED);
                panelGeenResultaten.setPreferredSize(new Dimension(600, 40));
                panelGeenResultaten.add(geenResultaten);
                panelGeenResultaten.setBackground(Color.WHITE);
                scrollPanePanelOverzichtTreinkoeriers.getViewport().add(panelGeenResultaten);
            }
            panelOverzichtTreinkoeriers.setBackground(Color.WHITE);
            scrollPanePanelOverzichtTreinkoeriers.getVerticalScrollBar().setBackground(Color.WHITE);
            add(scrollPanePanelOverzichtTreinkoeriers); // De scroll pane met daarin het overzicht van de treinkoeriers toevoegen aan het scherm
            // }
            
            // Tabblad 2 {
            hightPanelOverzichtKoeriers2 = (rows2 * 54 + 50); // Voor elke extra row wordt het panel 50 pixels hoger
            panelOverzichtTreinkoeriers2.setPreferredSize(new Dimension(600, hightPanelOverzichtKoeriers2));
            int widthScrollPanePanelOverzichtKoeriers2;
            if (hightPanelOverzichtKoeriers2 <= 300) {
                hightPanePanelOverzichtKoeriers2 = hightPanelOverzichtKoeriers2 + 5;
                panelOverzichtTreinkoeriers2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
            } else {
                hightPanePanelOverzichtKoeriers2 = 300;
                widthScrollPanePanelOverzichtKoeriers2 = 715;
                panelOverzichtTreinkoeriers2.setBorder(BorderFactory.createMatteBorder(0, 16, 0, 0, Color.WHITE));
            }  
  
            // Setten van de afmetingen van de scroll pane waarin het panelOverzichtTreinkoeriers zit
            scrollPanePanelOverzichtTreinkoeriers2.setPreferredSize(new Dimension(680, hightPanePanelOverzichtKoeriers2));
            
            scrollPanePanelOverzichtTreinkoeriers2.setBorder(null);
            
            if (rows2 != 0) { // Als de query resultaat hebben opgeleverd
                scrollPanePanelOverzichtTreinkoeriers2.getViewport().add(panelOverzichtTreinkoeriers2); // panelOverzichtTreinkoeriers toevoegen aan de scroll pane
            } else {
                JLabel geenResultaten2 = new JLabel("Geen resultaten");
                geenResultaten2.setForeground(Color.RED);
                panelGeenResultaten2.setPreferredSize(new Dimension(600, 40));
                panelGeenResultaten2.add(geenResultaten2);
                panelGeenResultaten2.setBackground(Color.WHITE);
                scrollPanePanelOverzichtTreinkoeriers2.getViewport().add(panelGeenResultaten2);
            }
            panelOverzichtTreinkoeriers2.setBackground(Color.WHITE);
            scrollPanePanelOverzichtTreinkoeriers2.getVerticalScrollBar().setBackground(Color.WHITE);
            add(scrollPanePanelOverzichtTreinkoeriers2); // De scroll pane met daarin het overzicht van de treinkoeriers toevoegen aan het scherm
            // }  
            
            // Tabblad 3 {
            hightPanelOverzichtKoeriers3 = (rows3 * 54 + 50); // Voor elke extra row wordt het panel 50 pixels hoger
            panelOverzichtTreinkoeriers3.setPreferredSize(new Dimension(600, hightPanelOverzichtKoeriers3));
            int widthScrollPanePanelOverzichtKoeriers3;
            if (hightPanelOverzichtKoeriers3 <= 300) {
                hightPanePanelOverzichtKoeriers3 = hightPanelOverzichtKoeriers3 + 5;
                panelOverzichtTreinkoeriers3.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
            } else {
                hightPanePanelOverzichtKoeriers3 = 300;
                widthScrollPanePanelOverzichtKoeriers3 = 715;
                panelOverzichtTreinkoeriers3.setBorder(BorderFactory.createMatteBorder(0, 16, 0, 0, Color.WHITE));
            }  
  
            // Setten van de afmetingen van de scroll pane waarin het panelOverzichtTreinkoeriers zit
            scrollPanePanelOverzichtTreinkoeriers3.setPreferredSize(new Dimension(680, hightPanePanelOverzichtKoeriers3));
            
            scrollPanePanelOverzichtTreinkoeriers3.setBorder(null);
            
            if (rows3 != 0) { // Als de query resultaat hebben opgeleverd
                scrollPanePanelOverzichtTreinkoeriers3.getViewport().add(panelOverzichtTreinkoeriers3); // panelOverzichtTreinkoeriers toevoegen aan de scroll pane
            } else {
                JLabel geenResultaten3 = new JLabel("Geen resultaten");
                geenResultaten3.setForeground(Color.RED);
                panelGeenResultaten3.setPreferredSize(new Dimension(600, 40));
                panelGeenResultaten3.add(geenResultaten3);
                panelGeenResultaten3.setBackground(Color.WHITE);
                scrollPanePanelOverzichtTreinkoeriers3.getViewport().add(panelGeenResultaten3);
            }
            panelOverzichtTreinkoeriers3.setBackground(Color.WHITE);
            scrollPanePanelOverzichtTreinkoeriers3.getVerticalScrollBar().setBackground(Color.WHITE);
            add(scrollPanePanelOverzichtTreinkoeriers3); // De scroll pane met daarin het overzicht van de treinkoeriers toevoegen aan het scherm
            // } 
            
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        // } Einde panelOverzichtTreinkoeriers
        
        // panelButtons {
        // Tabblad 1 {
        jbTerug = new JButton("Terug naar Beginscherm");
        jbTerug.setForeground(Color.WHITE);
        jbTerug.setBorder(null);
        jbTerug.setBackground(new Color(237, 127, 35));
        jbTerug.setPreferredSize(new Dimension(200, 48));
        panelButtons.add(jbTerug);
        jbTerug.addActionListener(this);
        
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        
        add(panelButtons); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 2 {
        jbTerug2 = new JButton("Terug naar Beginscherm");
        jbTerug2.setForeground(Color.WHITE);
        jbTerug2.setBorder(null);
        jbTerug2.setBackground(new Color(237, 127, 35));
        jbTerug2.setPreferredSize(new Dimension(200, 48));
        panelButtons2.add(jbTerug2);
        jbTerug2.addActionListener(this);
        
        panelButtons2.setBackground(Color.WHITE);
        panelButtons2.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        
        add(panelButtons2); // Toevoegen van panel het aan scherm
        // }
        
        // Tabblad 3 {
        jbTerug3 = new JButton("Terug naar Beginscherm");
        jbTerug3.setForeground(Color.WHITE);
        jbTerug3.setBorder(null);
        jbTerug3.setBackground(new Color(237, 127, 35));
        jbTerug3.setPreferredSize(new Dimension(200, 48));
        panelButtons3.add(jbTerug3);
        jbTerug3.addActionListener(this);
        
        panelButtons3.setBackground(Color.WHITE);
        panelButtons3.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        
        add(panelButtons2); // Toevoegen van panel het aan scherm
        // }
        // } Einde panelButtons
        
        // De containers die per container alles bevatten wat op één tabblad staat {
        // Tabblad 1 {
        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        container.setPreferredSize(new Dimension(722, 605));
        container.add(panelTitel);
        container.add(panelFilter);
        container.add(scrollPanePanelOverzichtTreinkoeriers);
        container.add(panelButtons);
        container.setBackground(Color.WHITE);
        // }
        
        // Tabblad 2 {        
        JPanel container2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        container2.setPreferredSize(new Dimension(722, 605));
        container2.add(panelTitel2);
        container2.add(panelFilter2);
        container2.add(scrollPanePanelOverzichtTreinkoeriers2);
        container2.add(panelButtons2);
        container2.setBackground(Color.WHITE);
        // }
        
        // Tabblad 3 {        
        JPanel container3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        container3.setPreferredSize(new Dimension(722, 605));
        container3.add(panelTitel3);
        container3.add(panelFilter3);
        container3.add(scrollPanePanelOverzichtTreinkoeriers3);
        container3.add(panelButtons3);
        container3.setBackground(Color.WHITE);
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
                setIngevoerdeAchternaam2(jtfAchternaam.getText());
                setIngevoerdeAchternaam3(jtfAchternaam.getText());
                
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers(ingevoerdeAchternaam);
                sot.jtfAchternaam.setText(jtfAchternaam.getText());
                sot.jtfAchternaam2.setText(jtfAchternaam.getText());
                sot.jtfAchternaam3.setText(jtfAchternaam.getText());
                sot.setIngevoerdeAchternaam(ingevoerdeAchternaam);
                sot.setIngevoerdeAchternaam2(ingevoerdeAchternaam);
                sot.setIngevoerdeAchternaam3(ingevoerdeAchternaam);
                
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
                    int actief = resultSet.getInt("actief");
                       
                    // Aanmaken nieuw koerierobject
                    Koerier koerier = new Koerier(treinkoerier_id, voornaam, achternaam, geslacht, geboortedatum, straat, huisnummer, 
                    postcode, plaats, email, telefoon, bsn, documentnummer, "Hash", actief);
                    
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
        // } Einde action performed tabblad 1
        
        // Action performed voor tabblad 2
        if (e.getSource() == jbZoeken2) {
            
            if (jtfAchternaam2.getText().length() != 0) {
                
                setIngevoerdeAchternaam(jtfAchternaam2.getText());
                setIngevoerdeAchternaam2(jtfAchternaam2.getText());
                setIngevoerdeAchternaam3(jtfAchternaam2.getText());
                
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers(ingevoerdeAchternaam2);
                sot.jtfAchternaam.setText(jtfAchternaam2.getText());
                sot.jtfAchternaam2.setText(jtfAchternaam2.getText());
                sot.jtfAchternaam3.setText(jtfAchternaam2.getText());
                sot.setIngevoerdeAchternaam(ingevoerdeAchternaam2);
                sot.setIngevoerdeAchternaam2(ingevoerdeAchternaam2);
                sot.setIngevoerdeAchternaam3(ingevoerdeAchternaam2);
                
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
        
        if (e.getSource() == jbTerug) { // Als de terugknop wordt aangeklikt
            
            SchermHome scherm = new SchermHome();
            dispose(); 
            
        }
        // } Einde action performed tabblad 2
        
        // Action performed voor tabblad 3
        if (e.getSource() == jbZoeken3) {
            
            if (jtfAchternaam3.getText().length() != 0) {
                
                setIngevoerdeAchternaam(jtfAchternaam3.getText());
                setIngevoerdeAchternaam2(jtfAchternaam3.getText());
                setIngevoerdeAchternaam3(jtfAchternaam3.getText());
                
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers(ingevoerdeAchternaam3);
                sot.jtfAchternaam.setText(jtfAchternaam3.getText());
                sot.jtfAchternaam2.setText(jtfAchternaam3.getText());
                sot.jtfAchternaam3.setText(jtfAchternaam3.getText());
                sot.setIngevoerdeAchternaam(ingevoerdeAchternaam3);
                sot.setIngevoerdeAchternaam2(ingevoerdeAchternaam3);
                sot.setIngevoerdeAchternaam3(ingevoerdeAchternaam3);

                sot.tabblad.setSelectedIndex(tabblad.getSelectedIndex());
                
                dispose();
                
            } else {
                SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers("");
                
                sot.tabblad.setSelectedIndex(tabblad.getSelectedIndex());
                
                dispose();
            }
        }
        
        int i3 = 0;
        while (i3 < detailsButtons3.size()) { 
        
            if (e.getSource() == detailsButtons3.get(i3)) {
                
                // Verbinden met database {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(connectionString, username, password); 
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                // }
                
                treinkoerier_ids3.get(i3); /* De detailsknop waarop gedrukt wordt, heeft dezelfde plaats (index)
                in de detailsButtonsarraylist, als de bijbehorende treinkoerier_id heeft in 
                de treinkoerier_ids-arraylist */
                
                // Ophalen gegevens uit database
                try {
                    
                    Statement statement3 = connection.createStatement();
                    ResultSet resultSet3 = statement3.executeQuery(
                        "SELECT * " + 
                        "FROM treinkoerier t " +
                        "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id " +
                        "WHERE t.treinkoerier_id = '" + treinkoerier_ids3.get(i3) + "'");

                    resultSet3.next();
                    
                    String treinkoerier_id = resultSet3.getString("treinkoerier_id"); 
                    String voornaam = resultSet3.getString("voornaam");
                    String achternaam = resultSet3.getString("achternaam");
                    String geslacht = resultSet3.getString("geslacht");
                    String geboortedatum = resultSet3.getString("gebdatum"); // Later naar kijken
                    String straat = resultSet3.getString("straat");
                    String huisnummer = resultSet3.getString("huisnr");
                    String postcode = resultSet3.getString("postcode");
                    String plaats = resultSet3.getString("plaats");
                    String email = resultSet3.getString("email");
                    String telefoon = resultSet3.getString("telefoon");
                    String bsn = resultSet3.getString("bsn");
                    String documentnummer = resultSet3.getString("documentnr");
                       
                    // Aanmaken nieuw koerierobject
                    Koerier koerier = new Koerier(treinkoerier_id, voornaam, achternaam, geslacht, geboortedatum, straat, huisnummer, 
                    postcode, plaats, email, telefoon, bsn, documentnummer, "Hash");
                    
                    // Maak nieuw scherm met de gegevens van de zojuist aangemaakte koerier
                    SchermGegevensTreinkoerier sgt = new SchermGegevensTreinkoerier(koerier);
                    sgt.setTab(tabblad.getSelectedIndex()); 
                    
                    // Databaseverbinding beëindigen
                    statement3.close();
                    connection.close();
                    
                    dispose(); // Sluit het huidige scherm
                    
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                
            }
            
            i3++;
        } 
        
        if (e.getSource() == jbTerug3) { // Als de terugknop wordt aangeklikt
            
            SchermHome scherm = new SchermHome();
            dispose(); 
            
        }
        // Einde action performed tabblad 3

    }

}

