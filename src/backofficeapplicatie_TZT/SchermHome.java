// HBO-ICT - WINDESHEIM - KBS2 - PERIODE 3/4 2016 - HAT03
// RICK HOLTERMAN
// REMCO KORT
// DENNIE DE WILDE
// ELWIN STREESTRA

package backofficeapplicatie_TZT;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SchermHome extends JFrame implements ActionListener{
    
    private JLabel jlTztNaam;
    private JButton jbOverzichtTreinkoerier;
    private JButton jbOverzichtPakket;
    
    public SchermHome(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.getAllFonts();
        
        setTitle("TZT Backend");
        setSize(800, 400);
        setLayout(new GridLayout(10, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        jlTztNaam = new JLabel("TZT-backend", SwingConstants.CENTER);
        jlTztNaam.setForeground(new Color(237, 127, 35));
        jlTztNaam.setFont(new Font("Roboto", Font.PLAIN, 50));
        add(jlTztNaam);
        add(new JLabel(""));
        
        jbOverzichtTreinkoerier = new JButton("Overzicht treinkoeriers");
        add(jbOverzichtTreinkoerier);
        jbOverzichtTreinkoerier.setFont(new Font("Roboto", Font.PLAIN, 14));
        jbOverzichtTreinkoerier.addActionListener(this);
        
        jbOverzichtPakket = new JButton("Overzicht pakketjes");
        add(jbOverzichtPakket);
        jbOverzichtPakket.setFont(new Font("Roboto", Font.PLAIN, 14));
        jbOverzichtPakket.addActionListener(this);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbOverzichtTreinkoerier) {
            SchermOverzichtTreinkoeriers sot = new SchermOverzichtTreinkoeriers();
            
            dispose();
        }
        
        if (e.getSource() == jbOverzichtPakket) {
            SchermOverzichtPakketjes sop = new SchermOverzichtPakketjes();
            
            dispose();
        }
    }
    
}
