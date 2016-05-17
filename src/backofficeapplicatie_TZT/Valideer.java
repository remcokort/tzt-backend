package backofficeapplicatie_TZT;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Valideer {

    public static boolean email(String email) { // Valideren e-mailadres
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true; // Het ingevoerde e-mailadres is een geldig e-mailadres
        } catch (AddressException ex) {
            System.out.println("Het ingevoerde e-mailadres is onjuist.");
            // Acties die ondernomen moeten worden bij fout e-mailadres
        }
        return false;
    }
    
    public static boolean voornaam(String voornaam) { // Valideren voornaam
        if(voornaam.matches("[a-zA-Z]+")){
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
    
     public static boolean geboortedatum(String geboortedatum){
	String dateFormat = "dd/MM/yyyy";
	SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	sdf.setLenient(false);	
	try {	
		//if not valid, it will throw ParseException
		Date date = sdf.parse(geboortedatum);
                System.out.println("gg");
                return true;
	} catch (ParseException e) {
            System.out.println("bg");
		return false;
	}	
	
	}


	

    public static boolean straatnaam(String straatnaam) { // straatnaam
        if(straatnaam.matches("[a-zA-Z][a-zA-Z ]*")){
           return true;  // Het ingevoerde veld gebruikt alleen letters en spatie
        } else {
            System.out.println("De ingevoerde straatnaam is onjuist.");
            
        }
        return false;
    }
    
    
   
    

    
}
