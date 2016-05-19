package backofficeapplicatie_TZT;

import java.util.Date;

public class Koerier extends Persoon {
    
    // Attribuut algemeen
    private boolean isTreinkoerier; /* Geeft aan of de koerier een treinkoerier
    of een reguliere koerier is. Als de waarde true is, dan is de koerier een treinkoerier. 
    Als de waarde false is, dan is de koerier een reguliere koerier. */
    
    // Attributen treinkoerier {
    private String treinkoerier_id;
    private String geslacht;
    private String geboortedatum; // Dit moet een Date worden i.p.v. een String
    private String straat;
    private String huisnummer;
    private String postcode;
    private String plaats;
    private String telefoon;
    private String bsn;
    private String documentnummer;
    private String password;
    private String salt;
    private boolean actief; /* Wanneer op de waarde "false" is, 
    heeft de treinkoerier geen toegang tot zijn/haar account. */ 
    // }
    
    // Attribuut reguliere koerier
    private String bedrijfsnaam;
    
    // Getters en setters algemeen {
    public String getEmail() {
        return email;
    } 
    public void setEmail(String email) {
        if (Valideer.email(email)) {
            this.email = email;
        }
    }
    // }
    
    // Getters en setters treinkoerier {

    public String getTreinkoerier_id() {
        return treinkoerier_id;
    }

    public void setTreinkoerier_id(String treinkoerier_id) {
        this.treinkoerier_id = treinkoerier_id;
    }
    
    
    public String getGeslacht() {
        return geslacht;
    }
    
    public void setGeslacht(String geslacht) {
        geslacht = geslacht.substring(0,1).toUpperCase() + geslacht.substring(1);
        if (Valideer.geslacht(geslacht)){
            this.geslacht = geslacht;
        }
    }
    
    public String getVoornaam() {
        return voornaam;
    }
    
    public void setVoornaam(String voornaam) {
        voornaam = voornaam.substring(0, 1).toUpperCase() + voornaam.substring(1); // Hoofdletter
        if (Valideer.voornaam(voornaam)){
           this.voornaam = voornaam; 
        }
    }
    
    public String getAchternaam() {
        return achternaam;
    }  
    
    public void setAchternaam(String achternaam) {
        achternaam = achternaam.substring(0, 1).toUpperCase() + achternaam.substring(1); // Hoofdletter
        if (Valideer.achternaam(achternaam)){
        this.achternaam = achternaam;
        }
    }
        
    
    public String getGeboortedatum() { // Dit moet een Date worden i.p.v. een String
        return geboortedatum;
    }
    
    public void setGeboortedatum(String geboortedatum) {
        if (Valideer.geboortedatum(geboortedatum)){
        this.geboortedatum = geboortedatum; // Dit moet een Date worden i.p.v. een String
        }
    }
    
    public String getStraat() {
        return straat;
    }
    
    public void setStraat(String straat) {
        straat = straat.substring(0,1).toUpperCase() + straat.substring(1);
        if( Valideer.straat(straat)){
            this.straat = straat;
        }
    }
    
    public String getHuisnummer() {
        return huisnummer;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
    
    public String getPostcode() {
        return postcode;
    }
    
    public void setPostcode(String postcode) {
        if(Valideer.postcode(postcode)){
        this.postcode = postcode;
        }
    }
    public String getPlaats() {
        return plaats;
    }
    
    public void setPlaats(String plaats) {
        //plaats = plaats.substring(0,1).toUpperCase() + plaats.substring(1);
        if(Valideer.plaats(plaats)){
            this.plaats = plaats;
        }
    }
    
    public String getTelefoon() {
        return telefoon;
    }
    
    public void setTelefoon(String telefoon) {
        if(Valideer.telefoon(telefoon)){
            this.telefoon = telefoon;
        }
    }
    public String getBsn() {
        return bsn;
    }
    public void setBsn(String bsn) {
        if(Valideer.bsn(bsn)){
            this.bsn = bsn;
        }
    }
    
    public String getDocumentnummer() {
        return documentnummer;
    }
    
    public void setDocumentnummer(String documentnummer) {
        if(Valideer.documentnummer(documentnummer)){
            this.documentnummer = documentnummer;
        }
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public boolean isActief() {
        return actief;
    }
    
    public void setActief(boolean actief) {
        this.actief = actief;
    }
    // }
    
    // Getters en setters reguliere koerier {
    public String getBedrijfsnaam() {
        return bedrijfsnaam;
    }
    public void setBedrijfsnaam(String bedrijfsnaam) {
        this.bedrijfsnaam = bedrijfsnaam;
    }
    // }
    
    // Methodes {
    public String printIsActief() {
        if (isActief() == true) {
            return ("Ja");
        } else {
            return ("Nee");
        }
    }
    // }
    
    // Constructor treinkoerier
    public Koerier(String treinkoerier_id, String voornaam, String achternaam, String geslacht, 
    String geboortedatum, String straat, String huisnummer, String postcode, 
    String plaats, String email, String telefoon, String bsn, String documentnummer,
    String password) { // "geboortedatum" Moet een Date worden i.p.v. een String
        
        setTreinkoerier_id(treinkoerier_id);
        setVoornaam(voornaam);
        setAchternaam(achternaam);
        setGeslacht(geslacht);
        setGeboortedatum(geboortedatum);
        setStraat(straat);
        setHuisnummer(huisnummer);
        setPostcode(postcode);
        setPlaats(plaats);
        setEmail(email);
        setTelefoon(telefoon);
        setBsn(bsn);
        setDocumentnummer(documentnummer);
        setPassword(password);
        setSalt(""); // Wat is hier het plan?
        setActief(false); /* De ingevoerde informatie zal door een medewerker goedgekeurd moeten worden, 
            deze zal het account vervolgens op actief zetten */
        isTreinkoerier = true; // De koerier is een treinkoerier
        
    }
    
    // Constructor reguliere koerier
    public Koerier(String bedrijfsnaam, String email) {
        
        setBedrijfsnaam(bedrijfsnaam);
        setEmail(email);
        isTreinkoerier = false; // De koerier is een reguliere koerier 
        
    }
    
    // toString-methode
    public String toString() {
        String output = new String();
        output = "KOERIER\n";
        if (isTreinkoerier == true) {
            output += // De volgende output wordt getoond wanneer de koerier een treinkoerier is
                "Treinkoerier-ID: " + getTreinkoerier_id() + "\n" +
                "Voornaam: " + getVoornaam() + "\n" +
                "Achternaam: " + getAchternaam() + "\n" +
                "Geslacht: " + getGeslacht() + "\n" +
                "Geboortedatum: " + getGeboortedatum() + "\n" +
                "Straat: " + getStraat() + "\n" +
                "Huisnummer: " + getHuisnummer() + "\n" +
                "Postcode: " + getPostcode() + "\n" +
                "Plaats: " + getPlaats() + "\n" +
                "E-mailadres: " + getEmail() + "\n" +
                "Telefoonnummer: " + getTelefoon() + "\n" +
                "BSN: " + getBsn() + "\n" +
                "Documentnummer: " + getDocumentnummer() + "\n" +
                "Wachtwoord: " + getPassword() + "\n" +
                "Account actief: " + printIsActief() + "\n";
        } else {
            output += // De volgende output wordt getoond wanneer de koerier een reguliere koerier is
                "Koeriersbedrijf: " + getBedrijfsnaam() + "\n" +
                "E-mailadres: " + getEmail() + "\n";
        }
        return (output);
    }
    
}
