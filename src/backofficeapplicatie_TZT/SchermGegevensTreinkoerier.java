package backofficeapplicatie_TZT;

import static backofficeapplicatie_TZT.Databaseverbinding.connectionString;
import static backofficeapplicatie_TZT.Databaseverbinding.password;
import static backofficeapplicatie_TZT.Databaseverbinding.username;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class SchermGegevensTreinkoerier extends JFrame implements ActionListener {
    
    private Koerier koerier;
    
    // Het scherm is opgebouwd uit de volgende vier JPanels {
    JPanel panelTitel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
    JPanel panelGegevensTreinkoerier = new JPanel(new GridLayout(12, 2));
    JPanel panelMelding = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    // }
    
    // Declaratie onderdelen JFrame {
    private JTextField jtfVoornaam;
    private JTextField jtfAchternaam;
    private JTextField jtfGeslacht;
    private JTextField jtfGeboortedatum;
    private JTextField jtfStraat;
    private JTextField jtfHuisnummer;
    private JTextField jtfPostcode;
    private JTextField jtfPlaats;
    private JTextField jtfEmail;
    private JTextField jtfTelefoon;
    private JTextField jtfBsn;
    private JTextField jtfDocumentnummer;
    private JLabel jlMelding;
    private JButton jbWijzigingenDoorvoeren;
    private JButton jbTerug;
    // }
    
    private String melding = ""; /* Bevat de tekst die getoond moet worden op het scherm 
    wanneer op 'wijzigingen doorvoeren' gedrukt wordt. De melding is nog leeg, aangezien er nog niks gemeld 
    hoeft te worden. */
    
    public String zetKommaAlsNodig() { /* Checkt of een doorgevoerde wijziging 
    de eerste wijziging is die in de opsommming in de melding staat. Wanneer er al een andere wijziging in de melding
    staat, wordt een komma toegevoegd aan de melding-string. */
        if (melding != "") { // 
            melding += ", ";
        }
        return melding;
    }

    public SchermGegevensTreinkoerier(Koerier koerier) {
        
        this.koerier = koerier;
        
        setTitle("Gegevens treinkoerier");
        setSize(740, 580);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Doe niets wanneer op het kruisje geklikt wordt
	setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        
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
        
        // Setten van de afmetingen van de panels waaruit het scherm is opgebouwd {
        panelTitel.setPreferredSize(new Dimension(700, 40));
        panelGegevensTreinkoerier.setPreferredSize(new Dimension(690, 400));
        panelMelding.setPreferredSize(new Dimension(700, 20));
        panelButtons.setPreferredSize(new Dimension(700, 40));
        // }
        
        // panelTitel {
        panelTitel.add(new JLabel("Gegevens Treinkoerier"));
        add(panelTitel); // Toevoegen van panel het aan scherm
        // }
        
        // panelGegevensTreinkoerier {
        panelGegevensTreinkoerier.add(new JLabel("Voornaam:"));
        jtfVoornaam = new JTextField(10);
        jtfVoornaam.setText(koerier.getVoornaam());
        panelGegevensTreinkoerier.add(jtfVoornaam);
        
        panelGegevensTreinkoerier.add(new JLabel("Achternaam:"));
        jtfAchternaam = new JTextField(10);
        jtfAchternaam.setText(koerier.getAchternaam());
        panelGegevensTreinkoerier.add(jtfAchternaam);
        
        panelGegevensTreinkoerier.add(new JLabel("Geslacht:"));
        jtfGeslacht = new JTextField(10);
        jtfGeslacht.setText(koerier.getGeslacht());
        panelGegevensTreinkoerier.add(jtfGeslacht);
        
        panelGegevensTreinkoerier.add(new JLabel("Geboortedatum:"));
        jtfGeboortedatum = new JTextField(10);
        jtfGeboortedatum.setText(koerier.getGeboortedatum());
        panelGegevensTreinkoerier.add(jtfGeboortedatum);
        
        panelGegevensTreinkoerier.add(new JLabel("Straat:"));
        jtfStraat = new JTextField(10);
        jtfStraat.setText(koerier.getStraat());
        panelGegevensTreinkoerier.add(jtfStraat);
        
        panelGegevensTreinkoerier.add(new JLabel("Huisnummer:"));
        jtfHuisnummer = new JTextField(10);
        jtfHuisnummer.setText(koerier.getHuisnummer());
        panelGegevensTreinkoerier.add(jtfHuisnummer);
        
        panelGegevensTreinkoerier.add(new JLabel("Postcode:"));
        jtfPostcode = new JTextField(10);
        jtfPostcode.setText(koerier.getPostcode());
        panelGegevensTreinkoerier.add(jtfPostcode);
        
        panelGegevensTreinkoerier.add(new JLabel("Woonplaats:"));
        jtfPlaats = new JTextField(10);
        jtfPlaats.setText(koerier.getPlaats());
        panelGegevensTreinkoerier.add(jtfPlaats);
        
        panelGegevensTreinkoerier.add(new JLabel("E-mailadres:"));
        jtfEmail = new JTextField(10);
        jtfEmail.setText(koerier.getEmail());
        panelGegevensTreinkoerier.add(jtfEmail);
        
        panelGegevensTreinkoerier.add(new JLabel("Telefoonnummer:"));
        jtfTelefoon = new JTextField(10);
        jtfTelefoon.setText(koerier.getTelefoon());
        panelGegevensTreinkoerier.add(jtfTelefoon);
        
        panelGegevensTreinkoerier.add(new JLabel("BSN:"));
        jtfBsn = new JTextField(10);
        jtfBsn.setText(koerier.getBsn());
        panelGegevensTreinkoerier.add(jtfBsn);
        
        panelGegevensTreinkoerier.add(new JLabel("Documentnummer:"));
        jtfDocumentnummer = new JTextField(10);
        jtfDocumentnummer.setText(koerier.getDocumentnummer());
        panelGegevensTreinkoerier.add(jtfDocumentnummer);
        
        add(panelGegevensTreinkoerier); // Toevoegen van panel het aan scherm
        // }
        
        // panelMelding {
        jlMelding = new JLabel(melding);
        panelMelding.add(jlMelding);
        add(panelMelding); // Toevoegen van panel het aan scherm
        // }
        
        // panelButtons {
        jbWijzigingenDoorvoeren = new JButton("Wijzigingen doorvoeren");
        panelButtons.add(jbWijzigingenDoorvoeren);
        jbWijzigingenDoorvoeren.addActionListener(this);
        
        jbTerug = new JButton("Terug naar Overzicht Treinkoeriers");
        panelButtons.add(jbTerug);
        jbTerug.addActionListener(this);
        
        add(panelButtons); // Toevoegen van panel het aan scherm
        // }

        setVisible(true);

    }
    
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == jbWijzigingenDoorvoeren) { // Als er op wijzigingen doorvoeren wordt geklikt
            
            // Verbinden met database
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(connectionString, username, password); 
            } catch (SQLException ex) {
                System.err.println(ex);
            }
            
            // Voornaam
            if (!jtfVoornaam.getText().equals(koerier.getVoornaam()) ) { // Als er een aanpassing is gedaan aan de voornaam
                
                koerier.setVoornaam(jtfVoornaam.getText()); // Verander dan de voornaam naar de nieuwe input
                
                try {
                    Statement statement = connection.createStatement();
                    int gelukt = statement.executeUpdate(
                        "UPDATE persoon p " + 
                        "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                        "SET p.voornaam = '" + koerier.voornaam + "' " +
                        "WHERE t.email = '" + koerier.email + "';");
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
                jtfVoornaam.setText(koerier.getVoornaam()); // En laat de nieuwe voornaam zien in het invoerveld
                melding += "voornaam aangepast"; // Neem in de melding op dat de voornaam aangepast is  
            }
            
            // Achternaam
            if (!jtfAchternaam.getText().equals(koerier.getAchternaam()) ) { // Als er een aanpassing is gedaan aan de achternaam
                koerier.setAchternaam(jtfAchternaam.getText()); // Verander dan de achternaam naar de nieuwe input
                jtfAchternaam.setText(koerier.getAchternaam()); // En laat de nieuwe achternaam zien in het invoerveld
                zetKommaAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                melding += "achternaam aangepast";
            }
            
            if (melding != "") { // Als de melding niet leeg is, zet er dan het volgende voor en achter:
                melding = "Wijzigingen doorgevoerd: " + melding + ".";
            } else {
                melding = "U heeft geen wijzigingen gedaan.";
            }
            jlMelding.setText(melding); /* Zet de inhoud van de melding in de melding-JLabel, 
            zodat de melding op het scherm getoond wordt. */
            melding = ""; // Maak de melding leeg, zodat deze opnieuw gevuld kan worden
            
            // Databaseverbinding beëindigen
            try {
               connection.close(); 
            } catch (SQLException ex) {
                System.out.println(e);
            }
            
                
        } else { // Als de terugknop wordt aangeklikt
            
            SchermOverzichtTreinkoeriers scherm = new SchermOverzichtTreinkoeriers();
            setVisible(false); // Verberg het scherm dan 
        }
        
    }

}
