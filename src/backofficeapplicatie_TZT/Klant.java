package backofficeapplicatie_TZT;

import java.sql.*;

public class Klant extends Persoon {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "usbw";
    private static final String CONN_STRING = "jdbc:mysql://localhost:8080/sakila";
    
    Connection conn = null;
   
    public void connect() {
        try {
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            System.out.println("Yes");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    
    
    
    
    // Attribuut algemeen
    private boolean isParticulier; /* Geeft aan of de klant een particulier 
        of een bedrijf is. Als de waarde true is, dan is de klant een particulier. 
        Als de waarde false is, dan is de klant een bedrijf. */
    
    // Attributen bedrijf
    private String telefoon;
    private String kvknummer;
    
    // Getters en setters algemeen
    public String getVoornaam() {
        return voornaam;
    }
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }
    public String getAchternaam() {
        return achternaam;
    }   
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }
    public String getEmail() {
        return email;
    } 
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Getters en setters bedrijf
    public String getTelefoon() {
        return telefoon;
    }
    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }
    public String getKvknummer() {
        return kvknummer;
    }
    public void setKvknummer(String kvknummer) {
        this.kvknummer = kvknummer;
    }
    
    // Constructor particulier
    public Klant(String voornaam, String achternaam, String email) { 
        setVoornaam(voornaam);
        setAchternaam(achternaam);
        setEmail(email);
        isParticulier = true; // De klant is een particulier
    }
    
    // Constructor bedrijf
    public Klant(String voornaam, String achternaam, String email, String telefoon, String kvknummer) {
        this(voornaam, achternaam, email); // Maak gebruik van de constructor voor particulier
        setTelefoon(telefoon);
        setKvknummer(kvknummer);
        isParticulier = false; // De klant is een bedrijf
    }
    
    // toString-methode
    public String toString() {
        String output = new String();
        output =
            "KLANT\n" +
            "Voornaam: " + getVoornaam() + "\n" +
            "Achternaam: " + getAchternaam() + "\n" +
            "E-mailadres: " + getEmail() + "\n";
        if (isParticulier == false) {
            output += // De volgende output wordt extra getoond wanneer de klant een bedrijf is
                "Telefoonnummer: " + getTelefoon() + "\n" +
                "KvK-nummer: " + getKvknummer() + "\n";
        }
        return (output);
    }
    
}
