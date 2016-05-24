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
    JPanel panelGegevensTreinkoerier = new JPanel(new GridLayout(13, 2));
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    // }
    
    // Declaratie onderdelen JFrame {
    public JTextField jtfVoornaam;
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
    private JButton jbWijzigingenDoorvoeren;
    private JButton jbTerug;
    // }
    
    private String melding = ""; /* Bevat de tekst die getoond moet worden op het dialoogvenster
    wanneer op 'wijzigingen doorvoeren' gedrukt wordt en alle ingevoerde gegevens voldoen. 
    De melding is nog leeg, aangezien er nog niks gemeld hoeft te worden. */
    private String foutmelding = ""; /* Bevat de tekst die getoond moet worden op het dialoogvenster
    wanneer op 'wijzigingen doorvoeren' gedrukt wordt en als er minimaal een veld is waarin de invoer onjuist is. 
    De melding is nog leeg, aangezien er nog niks gemeld hoeft te worden. */
    
    private String voornaamOud;
    private String achternaamOud;
    private String geslachtOud;
    private String geboortedatumOud;
    private String straatOud;
    private String huisnummerOud;
    private String postcodeOud;
    private String plaatsOud;
    private String emailOud;
    private String telefoonOud;
    private String bsnOud;
    private String documentnummerOud;
    
    private String zetKommaInMeldingAlsNodig() { /* Checkt of een doorgevoerde wijziging 
    de eerste wijziging is die in de opsommming in de melding staat. Wanneer er al een andere wijziging in de melding
    staat, wordt een komma toegevoegd aan de melding-string. */
        if (melding != "") { // 
            melding += ", ";
        }
        return melding;
    }
    
    private String zetKommaInFoutmeldingAlsNodig() { /* Checkt of een doorgevoerde wijziging 
    de eerste wijziging is die in de opsommming in de melding staat. Wanneer er al een andere wijziging in de melding
    staat, wordt een komma toegevoegd aan de melding-string. */
        if (foutmelding != "") { // 
            foutmelding += ", ";
        }
        return foutmelding;
    }
    
    public SchermGegevensTreinkoerier(Koerier koerier) {
        
        this.koerier = koerier;
        
        setTitle("Gegevens treinkoerier " + koerier.getTreinkoerier_id());
        setSize(740, 590);
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
        UIManager.put("OptionPane.okButtonText", "OK"); // Verandert de tekst op de OK-button weer naar OK
        
        // Setten van de afmetingen van de panels waaruit het scherm is opgebouwd {
        panelTitel.setPreferredSize(new Dimension(700, 80));
        panelGegevensTreinkoerier.setPreferredSize(new Dimension(690, 400));
        panelButtons.setPreferredSize(new Dimension(700, 40));
        // }
        
        // panelTitel {
        JLabel titel = new JLabel("Gegevens Treinkoerier ");
        titel.setForeground(new Color(237, 127, 35));
        titel.setFont(new Font("Roboto", Font.PLAIN, 50));
        panelTitel.add(titel);
        add(panelTitel); // Toevoegen van panel het aan scherm
        // }
        
        // panelGegevensTreinkoerier {
        panelGegevensTreinkoerier.add(new JLabel("Treinkoerier-ID:"));
        panelGegevensTreinkoerier.add(new JLabel(koerier.getTreinkoerier_id()));
        
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
        
        // Opslaan van de gegevens van de treinkoerier, deze worden gebruikt wanneer geen verbinding mogelijk is met de database
        voornaamOud = jtfVoornaam.getText();
        achternaamOud = jtfAchternaam.getText();
        geslachtOud = jtfGeslacht.getText();
        geboortedatumOud = jtfGeboortedatum.getText();
        straatOud = jtfStraat.getText();
        huisnummerOud = jtfHuisnummer.getText();
        postcodeOud = jtfPostcode.getText();
        plaatsOud = jtfPlaats.getText();
        emailOud = jtfEmail.getText();
        telefoonOud = jtfTelefoon.getText();
        bsnOud = jtfBsn.getText();
        documentnummerOud = jtfDocumentnummer.getText();
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
            
                // Voornaam
                if (!jtfVoornaam.getText().equals(koerier.getVoornaam()) ) { // Als er een aanpassing is gedaan aan de voornaam

                    koerier.setVoornaam(jtfVoornaam.getText()); // Verander dan de voornaam naar de nieuwe input

                    if (jtfVoornaam.getText().equals(koerier.getVoornaam())) { // Als de voornaam voldoet (dit wordt gecheckt in de setter)

                        koerier.setVoornaam(koerier.getVoornaam().substring(0, 1).toUpperCase() + koerier.getVoornaam().substring(1)); // Hoofdletter

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET p.voornaam = '" + koerier.getVoornaam() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {

                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "voornaam aangepast"; // Neem in de melding op dat de voornaam aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "voornaam";

                    }

                    jtfVoornaam.setText(koerier.getVoornaam()); // En laat de nieuwe voornaam zien in het invoerveld

                }

                // Achternaam
                if (!jtfAchternaam.getText().equals(koerier.getAchternaam()) ) { // Als er een aanpassing is gedaan aan de achternaam

                    koerier.setAchternaam(jtfAchternaam.getText()); // Verander dan de achternaam naar de nieuwe input

                    if (jtfAchternaam.getText().equals(koerier.getAchternaam())) { // Als de achternaam voldoet (dit wordt gecheckt in de setter)

                        koerier.setAchternaam(koerier.getAchternaam().substring(0, 1).toUpperCase() + koerier.getAchternaam().substring(1)); // Hoofdletter

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET p.achternaam = '" + koerier.getAchternaam() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "achternaam aangepast"; // Neem in de melding op dat de achternaam aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "achternaam";

                    }

                    jtfAchternaam.setText(koerier.getAchternaam()); // En laat de nieuwe achternaam zien in het invoerveld

                }

                // Geslacht
                if (!jtfGeslacht.getText().equals(koerier.getGeslacht()) ) { // Als er een aanpassing is gedaan aan het geslacht

                    koerier.setGeslacht(jtfGeslacht.getText()); // Verander dan het geslacht naar de nieuwe input

                    if (jtfGeslacht.getText().equals(koerier.getGeslacht())) { // Als het geslacht voldoet (dit wordt gecheckt in de setter)

                        koerier.setGeslacht(koerier.getGeslacht().substring(0, 1).toUpperCase() + koerier.getGeslacht().substring(1)); // Hoofdletter

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.geslacht = '" + koerier.getGeslacht() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "geslacht aangepast"; // Neem in de melding op dat het geslacht aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "geslacht";

                    }

                    jtfGeslacht.setText(koerier.getGeslacht()); // En laat het nieuwe geslacht zien in het invoerveld

                }

                // Geboortedatum
                if (!jtfGeboortedatum.getText().equals(koerier.getGeboortedatum()) ) { // Als er een aanpassing is gedaan aan de geboortedatum

                    koerier.setGeboortedatum(jtfGeboortedatum.getText()); // Verander dan de geboortedatum naar de nieuwe input

                    if (jtfGeboortedatum.getText().equals(koerier.getGeboortedatum())) { // Als de geboortedatum voldoet (dit wordt gecheckt in de setter)

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.gebdatum = '" + koerier.getGeboortedatum() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "geboortedatum aangepast"; // Neem in de melding op dat de geboortedatum aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "geboortedatum";

                    }

                    jtfGeboortedatum.setText(koerier.getGeboortedatum()); // En laat de nieuwe geboortedatum zien in het invoerveld

                }

                // Straat
                if (!jtfStraat.getText().equals(koerier.getStraat()) ) { // Als er een aanpassing is gedaan aan de straat

                    koerier.setStraat(jtfStraat.getText()); // Verander dan de straat naar de nieuwe input

                    if (jtfStraat.getText().equals(koerier.getStraat())) { // Als de straat voldoet (dit wordt gecheckt in de setter)

                        koerier.setStraat(koerier.getStraat().substring(0, 1).toUpperCase() + koerier.getStraat().substring(1)); // Hoofdletter

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.straat = '" + koerier.getStraat() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "straat aangepast"; // Neem in de melding op dat de straat aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "straat";

                    }

                    jtfStraat.setText(koerier.getStraat()); // En laat de nieuwe straat zien in het invoerveld

                }

                // Huisnummer
                if (!jtfHuisnummer.getText().equals(koerier.getHuisnummer()) ) { // Als er een aanpassing is gedaan aan het huisnummer

                    koerier.setHuisnummer(jtfHuisnummer.getText()); // Verander dan het huisnummer naar de nieuwe input

                    if (jtfHuisnummer.getText().equals(koerier.getHuisnummer())) { // Als het huisnummer voldoet (dit wordt gecheckt in de setter)

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.huisnr = '" + koerier.getHuisnummer() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "huisnummer aangepast"; // Neem in de melding op dat het huisnummer aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "huisnummer";

                    }

                    jtfHuisnummer.setText(koerier.getHuisnummer()); // En laat het nieuwe huisnummer zien in het invoerveld

                }

                // Postcode
                if (!jtfPostcode.getText().equals(koerier.getPostcode()) ) { // Als er een aanpassing is gedaan aan de postcode

                    koerier.setPostcode(jtfPostcode.getText()); // Verander dan de postcode naar de nieuwe input

                    if (jtfPostcode.getText().equals(koerier.getPostcode())) { // Als de postcode voldoet (dit wordt gecheckt in de setter)

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.postcode = '" + koerier.getPostcode() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "postcode aangepast"; // Neem in de melding op dat de postcode aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "postcode";

                    }

                    jtfPostcode.setText(koerier.getPostcode()); // En laat de nieuwe plaats zien in het invoerveld

                }

                // Plaats
                if (!jtfPlaats.getText().equals(koerier.getPlaats()) ) { // Als er een aanpassing is gedaan aan de plaats

                    koerier.setPlaats(jtfPlaats.getText()); // Verander dan de plaats naar de nieuwe input

                    if (jtfPlaats.getText().equals(koerier.getPlaats())) { // Als de plaats voldoet (dit wordt gecheckt in de setter)

                        koerier.setPlaats(koerier.getPlaats().substring(0, 1).toUpperCase() + koerier.getPlaats().substring(1)); // Hoofdletter

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.plaats = '" + koerier.getPlaats() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "plaats aangepast"; // Neem in de melding op dat de plaats aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "plaats";

                    }

                    jtfPlaats.setText(koerier.getPlaats()); // En laat de nieuwe plaats zien in het invoerveld

                }

                // E-mailadres
                if (!jtfEmail.getText().equals(koerier.getEmail()) ) { // Als er een aanpassing is gedaan aan de email

                    koerier.setEmail(jtfEmail.getText()); // Verander dan het e-mailadres naar de nieuwe input 

                    if (jtfEmail.getText().equals(koerier.getEmail())) { // Als het e-mailadres voldoet (dit wordt gecheckt in de setter)

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.email = '" + koerier.getEmail() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                            zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                            melding += "e-mail aangepast"; // Neem in de melding op dat het e-mailadres aangepast is 
                        } catch (SQLException ex) {
                            
                                koerier.setEmail(emailOud);
                                SchermGegevensTreinkoerier scherm = new SchermGegevensTreinkoerier(koerier);
                                
                                System.out.println(emailOud);
                                dispose();
                                
                                JOptionPane.showMessageDialog(new JDialog(),
                                "Het ingevoerde e-mailadres is al in gebruik.",
                                "Fout!",
                                JOptionPane.ERROR_MESSAGE);
                                
                        }

                        

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "e-mailadres";

                    }

                    jtfEmail.setText(koerier.getEmail()); // En laat het nieuwe e-mailadres zien in het invoerveld

                }

                // Telefoonnummer
                if (!jtfTelefoon.getText().equals(koerier.getTelefoon()) ) { // Als er een aanpassing is gedaan aan het telefoonnummer

                    koerier.setTelefoon(jtfTelefoon.getText()); // Verander dan het telefoonnummer naar de nieuwe input 

                    if (jtfTelefoon.getText().equals(koerier.getTelefoon())) { // Als het telefoonnummer voldoet (dit wordt gecheckt in de setter)

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.telefoon = '" + koerier.getTelefoon() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "telefoonnummer aangepast"; // Neem in de melding op dat het telefoonnummer aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "telefoonnummer";

                    }

                    jtfTelefoon.setText(koerier.getTelefoon()); // En laat het nieuwe telefoonnummer zien in het invoerveld

                }

                // BSN
                if (!jtfBsn.getText().equals(koerier.getBsn()) ) { // Als er een aanpassing is gedaan aan het BSN

                    koerier.setBsn(jtfBsn.getText()); // Verander dan het BSN naar de nieuwe input 

                    if (jtfBsn.getText().equals(koerier.getBsn())) { // Als het BSN voldoet (dit wordt gecheckt in de setter)

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.bsn = '" + koerier.getBsn() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "BSN aangepast"; // Neem in de melding op dat het BSN aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "BSN";

                    }

                    jtfBsn.setText(koerier.getBsn()); // En laat het nieuwe BSN zien in het invoerveld

                }

                // Documentnummer
                if (!jtfDocumentnummer.getText().equals(koerier.getDocumentnummer()) ) { // Als er een aanpassing is gedaan aan het documentnummer

                    koerier.setDocumentnummer(jtfDocumentnummer.getText()); // Verander dan het documentnummer naar de nieuwe input 

                    if (jtfDocumentnummer.getText().equals(koerier.getDocumentnummer())) { // Als het documentnummer voldoet (dit wordt gecheckt in de setter)

                        try {
                            Statement statement = connection.createStatement();
                            int gelukt = statement.executeUpdate(
                                "UPDATE persoon p " + 
                                "LEFT JOIN treinkoerier t ON p.persoon_id = t.persoon_id " +
                                "SET t.documentnr = '" + koerier.getDocumentnummer() + "' " +
                                "WHERE t.treinkoerier_id = '" + koerier.getTreinkoerier_id() + "';");
                            statement.close();
                        } catch (SQLException ex) {
                            Databaseverbinding.genereerDatabaseverbindingError(koerier);
                            dispose();
                        }

                        zetKommaInMeldingAlsNodig(); // Voegt een komma toe aan de melding-string als dit nodig is
                        melding += "documentnummer aangepast"; // Neem in de melding op dat het documentnummer aangepast is 

                    } else {

                        zetKommaInFoutmeldingAlsNodig(); 
                        foutmelding += "documentnummer";

                    }

                    jtfDocumentnummer.setText(koerier.getDocumentnummer()); // En laat het nieuwe documentnummer zien in het invoerveld

                }
            
            } catch (SQLException ex) { // Wanneer er geen databaseverbinding mogelijk is
                
                // Dan worden alle gegevens gereset naar de oorspronkelijke waarden
                koerier.setVoornaam(voornaamOud);
                koerier.setAchternaam(achternaamOud);
                koerier.setGeslacht(geslachtOud);
                koerier.setGeboortedatum(geboortedatumOud);
                koerier.setStraat(straatOud);
                koerier.setHuisnummer(huisnummerOud);
                koerier.setPostcode(postcodeOud);
                koerier.setPlaats(plaatsOud);
                koerier.setEmail(emailOud);
                koerier.setTelefoon(telefoonOud);
                koerier.setBsn(bsnOud);
                koerier.setDocumentnummer(documentnummerOud); 
                
                // Maar de nieuw ingevoerde waarden komen in de desbetreffende velden te staan in een nieuw scherm dat aangemaakt wordt
                SchermGegevensTreinkoerier scherm = new SchermGegevensTreinkoerier(koerier);
                scherm.jtfVoornaam.setText(jtfVoornaam.getText());
                scherm.jtfAchternaam.setText(jtfAchternaam.getText());
                scherm.jtfGeslacht.setText(jtfGeslacht.getText());
                scherm.jtfGeboortedatum.setText(jtfGeboortedatum.getText());
                scherm.jtfStraat.setText(jtfStraat.getText());
                scherm.jtfHuisnummer.setText(jtfHuisnummer.getText());
                scherm.jtfPostcode.setText(jtfPostcode.getText());
                scherm.jtfPlaats.setText(jtfPlaats.getText());
                scherm.jtfEmail.setText(jtfEmail.getText());
                scherm.jtfTelefoon.setText(jtfTelefoon.getText());
                scherm.jtfBsn.setText(jtfBsn.getText());
                scherm.jtfDocumentnummer.setText(jtfDocumentnummer.getText());
                
                /* Op deze manier zullen de nieuw ingevoerde waarden alsnog doorgevoerd worden wanneer op 'Wijzigingen doorvoeren'
                gedrukt wordt, wanneer er wel weer verbinding met de database mogelijk is.
                */
                
                dispose();
                
                // Weergeven foutmelding
                JOptionPane.showMessageDialog(new JDialog(),
                "Er kon geen verbinding worden gemaakt met de database.\nUw wijzigingen zijn niet opgeslagen.",
                "Fout!",
                JOptionPane.ERROR_MESSAGE);
                
                
            }

            if (melding != "") { // Als de melding niet leeg is, zet er dan het volgende voor en achter:
                melding = "Wijzigingen doorgevoerd: " + melding + ".\n";
            } 

            if (foutmelding != "") { // Als de foutmelding niet leeg is, zet er dan het volgende voor en achter:
                foutmelding = "Onjuiste invoer: " + foutmelding + ".";
            }
            
            if (melding == "" && foutmelding == "") {
                melding = "U heeft geen wijzigingen aangebracht.";
            }
            
            // Databaseverbinding beëindigen {
            try {
               connection.close(); 
            } catch (SQLException ex) {

                Databaseverbinding.genereerDatabaseverbindingError(koerier);
                dispose();

            }
            // }
            
            // Alleen wanneer verbinding met de database mogelijk is mogen de overige meldingen weergegeven worden {
            connection = null;
            try {
                connection = DriverManager.getConnection(connectionString, username, password); 
                
                // Het dialoog met daarop de melding(en) wordt geopend
                JOptionPane.showMessageDialog(new JDialog(),
                melding + foutmelding,
                "Melding",
                JOptionPane.WARNING_MESSAGE);
                connection.close(); 
                
            } catch (SQLException ex) {
                
                Databaseverbinding.genereerDatabaseverbindingError(koerier);
                dispose();
                
            }
            // }
            
            melding = ""; // Maak de melding leeg, zodat deze opnieuw gevuld kan worden
            foutmelding = "";
       
        } else { // Als de terugknop wordt aangeklikt
            
            SchermOverzichtTreinkoeriers scherm = new SchermOverzichtTreinkoeriers("");
            dispose(); // Sluit dit scherm dan
            
        }
        
    }

}
