package backofficeapplicatie_TZT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Databaseverbinding {
    
    public static final String username = "sql7115322";
    public static final String password = "AiRKLLIZeZ";
    public static final String connectionString = "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7115322";
    
    public static void genereerDatabaseVerbindingError() {
        JOptionPane.showMessageDialog(new JDialog(),
            "Er kon geen verbinding worden gemaakt met de database,\nuw wijzigingen zijn niet opgeslagen",
            "Fout!",
            JOptionPane.WARNING_MESSAGE);
    }

}
