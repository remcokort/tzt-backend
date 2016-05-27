// HBO-ICT - WINDESHEIM - KBS2 - PERIODE 3/4 2016 - HAT03
// RICK HOLTERMAN
// REMCO KORT
// DENNIE DE WILDE
// ELWIN STREEkSTRA
package backofficeapplicatie_TZT;

//Importeren van de benodigde Java library's
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class SchermNieuwPakket extends JFrame implements ActionListener{
    
    private JLabel jlNieuwPakket;
    private JLabel jlNieuwPakket2;
    
    public SchermNieuwPakket() {
        //Laden van de fonts
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.getAllFonts();
        
        //Instellingen scherm
        setTitle("TZT Backend");
        setSize(800, 800);
        setLayout(new GridLayout(0, 3));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Laat het eerste deel van de titel zien
        jlNieuwPakket = new JLabel("Nieuw");
        jlNieuwPakket.setForeground(new Color(237, 127, 35));
        jlNieuwPakket.setFont(new Font("Roboto", Font.PLAIN, 50));
        add(jlNieuwPakket);
        
        //Laat het tweede deel van de titel zien
        jlNieuwPakket2 = new JLabel("pakket");
        jlNieuwPakket2.setForeground(new Color(237, 127, 35));
        jlNieuwPakket2.setFont(new Font("Roboto", Font.PLAIN, 50));
        add(jlNieuwPakket2);
    }

    //Functie die actie onderneemt als er een knop ingedrukt wordt
    public void actionPerformed(ActionEvent e) {
        
    }
}
