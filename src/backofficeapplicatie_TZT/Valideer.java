package backofficeapplicatie_TZT;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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
    
}
