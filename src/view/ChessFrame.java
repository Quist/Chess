package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Joakim
 */
public class ChessFrame extends JFrame {
    public ChessFrame(){
        setTitle("King Chess");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
    }
    
    public void addPanelNorth(JPanel panel) {
        add(panel, BorderLayout.NORTH);
        pack();
    }

    public void addPanelCenter(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
    }
}
