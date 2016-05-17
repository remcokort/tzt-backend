package backofficeapplicatie_TZT;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SchermOverzichtPakketjes extends JFrame implements ActionListener{
    
    private JLabel jlPakketTitel;
    
    public SchermOverzichtPakketjes(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.getAllFonts();
        
        setTitle("TZT Backend");
        setSize(800, 400);
        setLayout(new GridLayout(10, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        jlPakketTitel = new JLabel("Pakketoverzicht", SwingConstants.CENTER);
        jlPakketTitel.setForeground(new Color(237, 127, 35));
        jlPakketTitel.setFont(new Font("Roboto", Font.PLAIN, 50));
        add(jlPakketTitel);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        
    }
}
