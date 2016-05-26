package backofficeapplicatie_TZT;

import static backofficeapplicatie_TZT.Databaseverbinding.connectionString;
import static backofficeapplicatie_TZT.Databaseverbinding.password;
import static backofficeapplicatie_TZT.Databaseverbinding.username;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;

public class SchermOverzichtPakketten extends JFrame implements ActionListener{
    
    private Koerier koerier;
    
    // Het scherm is opgebouwd uit de volgende panels {
    JPanel panelTitel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
    JPanel panelFilter = new JPanel(new GridLayout(1, 3));
    JPanel panelOverzichtPakketten = new JPanel(); // Layout is variabel en wordt later geset
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    
    JScrollPane scrollPanePanelOverzichtPakketten = new JScrollPane();
    // }
    
    // Declaratie onderdelen JFrame {
    private JTextField jtfOntvangstadres;
    private JButton jbZoeken;
    private JButton jbDetails;
    private JButton jbTerug;
    // }
    
    private int rows; // Aantal rows is varabel
    private int hightScherm;
    private int hightPanelOverzichtPakketten; // Is afhankelijk van het aantal rows
    private int hightPanePanelOverzichtPakketten;
    private String ingevoerdOntvangstadres;
    private String query1;
    private String query2;
    
    /* Twee arraylists: een waarin de detailsknoppen opgeslagen zullen worden en een waarin de 
    treinkoerier_ids opgeslagen zullen worden. Deze zijn nodig om na te gaan welke informatie 
    opgehaald moet worden uit de database wanneer er op welke detailsknop gedrukt wordt. */
    ArrayList detailsButtons = new ArrayList();
    ArrayList pakket_ids = new ArrayList();
    
    public void setIngevoerdOntvangstadres(String ingevoerdOntvangstadres) {
        this.ingevoerdOntvangstadres = ingevoerdOntvangstadres;
    }

    public SchermOverzichtPakketten(String ingevoerdOntvangstadres) {
        
        // Verbinden met database {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionString, username, password); 
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        // }
        
        // Instellingen scherm {
        setTitle("Overzicht pakketten");
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
        panelTitel.setPreferredSize(new Dimension(900, 90));
        panelFilter.setPreferredSize(new Dimension(880, 40));
        panelOverzichtPakketten.setLayout(new GridLayout(rows, 5)); // Afmetingen worden later gezet, want zijn variabel
        panelButtons.setPreferredSize(new Dimension(900, 40));
        // }

        // panelTitel {
        JLabel titel = new JLabel("Overzicht Pakketten");
        titel.setForeground(new Color(237, 127, 35));
        titel.setFont(new Font("Roboto", Font.PLAIN, 50));
        panelTitel.add(titel);
        add(panelTitel); // Toevoegen van panel het aan scherm
        // }
        
        // panelFilter {
        panelFilter.add(new JLabel("Zoeken op ontvangstadres:"));
        
        jtfOntvangstadres = new JTextField(10);
        panelFilter.add(jtfOntvangstadres);
        
        jbZoeken = new JButton("Zoeken");
        panelFilter.add(jbZoeken);
        jbZoeken.addActionListener(this);
        
        add(panelFilter); // Toevoegen van panel het aan scherm
        // }
        
        // panelOverzichtTreinkoeriers {
        
        panelOverzichtPakketten.add(new JLabel("Verzendadres"));
        panelOverzichtPakketten.add(new JLabel("Ontvangstadres"));
        panelOverzichtPakketten.add(new JLabel("Datum"));
        panelOverzichtPakketten.add(new JLabel("Tijd"));
        panelOverzichtPakketten.add(new JLabel("")); // Lege cel boven detailsknoppen
        
        query1 = // Als het zoekveld voor achternaam leeg is gelaten is dit de query
            "SELECT p.pakket_id, t.straat AS verzendadresStraat, t.huisnr AS verzendadresHuisnr, t.verwacht_ts " + 
            "FROM tussenstap t " +
            "LEFT JOIN pakket p ON p.pakket_id = t.pakket_id " +
            "WHERE t.tussenstap_type = 1 ";
        query2 =
            "SELECT t.straat AS ontvangstadresStraat, t.huisnr AS ontvangstadresHuisnr " +
            "FROM tussenstap t " +
            "WHERE t.tussenstap_type = 3 ";
        if (ingevoerdOntvangstadres != "") { // Als het zoekveld voor achternaam niet leeg is gelaten, wordt deze invoer verwerkt in de query: 
            query2 += "&& t.straat LIKE '%" + ingevoerdOntvangstadres + "%'";
        } 
        
        // Ophalen gegevens uit database voor overzicht treinkoeriers
        try {
            
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(query1);
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query2);
            
            
            rows = 0; // Het aantal rows in de gridLayout is 0: er staan nog geen gegevens in
            
            while (resultSet1.next() && resultSet2.next()) { // Voor elke row in de database wordt een row op het scherm aangemaakt
                
                String verzendadres = resultSet1.getString("verzendadresStraat");
                verzendadres += " " + resultSet1.getString("verzendadresHuisnr");
                panelOverzichtPakketten.add(new JLabel(verzendadres)); // Voornaam voor deze row toevoegen aan het panel
                
                String ontvangstadres = resultSet2.getString("ontvangstadresStraat");
                ontvangstadres += " " + resultSet2.getString("ontvangstadresHuisnr");
                panelOverzichtPakketten.add(new JLabel(ontvangstadres)); // Achternaam voor deze row toevoegen aan het panel
                
                String datum = resultSet1.getString("t.verwacht_ts");
                datum = datum.substring(0, 10);
                panelOverzichtPakketten.add(new JLabel(datum)); // Achternaam voor deze row toevoegen aan het panel
                
                String tijd = resultSet1.getString("t.verwacht_ts");
                tijd = tijd.substring(10);
                tijd = tijd.substring(0, 6);
                panelOverzichtPakketten.add(new JLabel(tijd)); // Achternaam voor deze row toevoegen aan het panel
                
                jbDetails = new JButton("Details");
                panelOverzichtPakketten.add(jbDetails); // Detailsknop voor deze row toevoegen aan het panel
                jbDetails.addActionListener(this); 

                rows++; // Een row toevoegen aan de grid-layout, zodat de nieuwe row goed wordt weergegeven
                
                detailsButtons.add(jbDetails); /* Toevoegen van de detailsknop aan een arraylist 
                met daarin alle detailsknoppen, zodat in de actionPerformed de detailsknoppen als verschillende 
                sources onderscheiden kunnen worden */
                
                pakket_ids.add(resultSet1.getString("p.pakket_id")); /* Toevoegen van de treinkoerier_id
                aan een arraylist met daarin alle treinkoerier_ids, zodat in de actionPerformed per detailsknop de
                bijbehorende treinkoerier bekend is, en het mogelijk is om vervolgens de gegevens van deze 
                treinkoerier uit de database op te halen. */
                
            }
            
            // Databaseverbinding beëindigen {
            statement1.close();
            statement2.close();
            connection.close();
            // }

            hightPanelOverzichtPakketten = (rows * 50 + 50); // Voor elke extra row wordt het panel 50 pixels hoger
            panelOverzichtPakketten.setPreferredSize(new Dimension(800, hightPanelOverzichtPakketten));
            
            
            if (hightPanelOverzichtPakketten <= 300) {
                hightPanePanelOverzichtPakketten = hightPanelOverzichtPakketten + 5;
            } else {
                hightPanePanelOverzichtPakketten = 300;
            }
            // Setten van de afmeting van de scroll pane waarin het panelOverzichtTreinkoeriers zit
            scrollPanePanelOverzichtPakketten.setPreferredSize(new Dimension(880, hightPanePanelOverzichtPakketten));
            
            hightScherm = hightPanePanelOverzichtPakketten + 265;
            
            setSize(940, hightScherm); // De hoogte van het scherm hangt af van het aantal gereturnde rows en wordt later geset
            
            if (rows != 0) { // Als de query resultaat hebben opgeleverd
                scrollPanePanelOverzichtPakketten.getViewport().add(panelOverzichtPakketten); // panelOverzichtTreinkoeriers toevoegen aan de scroll pane
                add(scrollPanePanelOverzichtPakketten); // De scroll pane met daarin het overzicht van de treinkoeriers toevoegen aan het scherm
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
            
            if (jtfOntvangstadres.getText().length() != 0) { // Als het zoekveld niet leeg is
                
                setIngevoerdOntvangstadres(jtfOntvangstadres.getText());
                
                SchermOverzichtPakketten sop = new SchermOverzichtPakketten(ingevoerdOntvangstadres);
                sop.jtfOntvangstadres.setText(jtfOntvangstadres.getText());
                sop.setIngevoerdOntvangstadres(ingevoerdOntvangstadres);
                
                dispose();
                
            } else {
                SchermOverzichtPakketten sop = new SchermOverzichtPakketten("");
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
                
                pakket_ids.get(i); /* De detailsknop waarop gedrukt wordt, heeft dezelfde plaats (index)
                in de detailsButtonsarraylist, als de bijbehorende treinkoerier_id heeft in 
                de treinkoerier_ids-arraylist */
                
                // Ophalen gegevens uit database
                try {
                    
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(
                        "SELECT * " + 
                        "FROM pakket p " +
                        "LEFT JOIN persoon p ON p.persoon_id = t.persoon_id " +
                        "WHERE t.treinkoerier_id = '" + pakket_ids.get(i) + "'");

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

