package backofficeapplicatie_TZT;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Valideer {
    
    public static boolean voornaam(String voornaam) { // Valideren voornaam
        if(voornaam.matches("[a-zA-Z]*")){
           return true;  // Het ingevoerde veld gebruikt alleen letters
        } else {
            System.out.println("De ingevoerde voornaam is onjuist.");
            
        }
        return false;
    }
    
    public static boolean achternaam(String achternaam) { // Valideren achternaam
        if(achternaam.matches("[a-zA-Z][a-zA-Z ]*")){
           return true;  // Het ingevoerde veld gebruikt alleen letters en spatie
        } else {
            System.out.println("De ingevoerde achternaam is onjuist.");
            
        }
        return false;
    }
    
     public static boolean geslacht(String geslacht) { // Valideren achternaam
        if(geslacht.matches("[vV]*") || geslacht.matches("[mM]*")){
           return true;  // Het ingevoerde veld is een m of een v
        } else {
            System.out.println("Het ingevoerde geslacht is geen geldig geslacht");
            
        }
        return false;
    }
    
     public static boolean geboortedatum(String geboortedatum){ //valideren geboortedatum
	String dateFormat = "yyyy-MM-dd";
	SimpleDateFormat sdf = new SimpleDateFormat(dateFormat); // De in te voeren datum krijft het format yyyy/MM/dd (jaar/Maand van het jaar/ dag van de maand)
	sdf.setLenient(false);	
	try {	
		//Als de datum niet goed is, vangt hij de exception op
		Date date = sdf.parse(geboortedatum);
                return true;
	} catch (ParseException e) {
            System.out.println("De ingevoerde geboortedatum is onjuist");
		return false;
	}	
	
	}

    public static boolean straat(String straatnaam) { // straatnaam
        if(straatnaam.matches("[a-zA-Z][a-zA-Z ]*")){
           return true;  // Het ingevoerde veld gebruikt alleen letters en spaties
        } else {
            System.out.println("De ingevoerde straatnaam is onjuist.");
            
        }
        return false;
    }
    
    public static boolean huisnummer(String huisnummer) { // valideren huisnummer
        
        if(huisnummer.matches("([0-9]){1,}([A-Z]){0,3}/*")){
           return true;  // Het ingevoerde veld gebruikt gebruikt eerst cijfers en dan een mogelijke letter
        } else {
            System.out.println("De ingevoerde straatnaam is onjuist.");
            
        }
        return false;
    }
    
    public static boolean postcode(String postcode) { // valideren postcode
        if(postcode.matches("([0-9]{4}[ ]+[A-Z]{2})*")){
           return true;  // Het ingevoerde veld bestaat uit 4 nummers een spatie en dan twee hoofdletters
        } else {
            System.out.println("De ingevoerde postcode is onjuist.");
            
        }
        return false;
    }
     
    public static boolean plaats(String plaats) { // valideren plaats
        if(plaats.matches("[a-zA-Z]*")){
           return true;  // Het ingevoerde veld bestaat uit alleen maar letters
        } else {
            System.out.println("De ingevoerde straatnaam is onjuist.");
            
        }
        return false;
    }
    
    public static boolean email(String email) { // Valideren e-mailadres
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true; // Het ingevoerde e-mailadres is een geldig e-mailadres
        } catch (AddressException ex) {
            System.out.println("Het ingevoerde e-mailadres is onjuist.");
            // Acties die ondernomen moeten worden bij fout e-mailadres
            return false;
        }
    }
        
    public static boolean telefoon(String telefoon) { // valideren telefoonnummer
        if(telefoon.matches("(06-[0-9]{8})*")){
           return true;  // Het ingevoerde veld gebruikt alleen letters en spaties
        }
        return false;
    }
    
    public static boolean bsn(String bsn) {
        if (!bsn.matches("[0-9]{9}")){
            return false;
        } else {
            int bsnnummer = Integer.parseInt(bsn);

            if (bsnnummer <= 9999999 || bsnnummer > 999999999) {
                return false;
            }

            int sum = -1 * bsnnummer % 10;

            for (int multiplier = 2; bsnnummer > 0; multiplier++) {
                int val = (bsnnummer /= 10) % 10;
                sum += multiplier * val;
            }

            return sum != 0 && sum % 11 == 0;
        }
    }
    
    


    

        
    public static boolean documentnummer(String documentnummer) { // documentnummer
        
        if(documentnummer.matches("[A-Z]{2}[A-Z0-9]{7}")){
           return true;  // Het ingevoerde veld gebruikt alleen letters en spaties
        } else {
            System.out.println("De ingevoerde straatnaam is onjuist.");
            
        }
        return false;
    }
        
}
    
   
    

    

