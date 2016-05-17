package backofficeapplicatie_TZT;

public abstract class Persoon {
    
    protected String voornaam;
    protected String achternaam;
    protected String email;
    
    protected abstract String getVoornaam();
    protected abstract void setVoornaam(String voornaam);
    protected abstract String getAchternaam();
    protected abstract void setAchternaam(String achternaam);
    protected abstract String getEmail();
    protected abstract void setEmail(String email);
    

}
