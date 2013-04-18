
package view;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author quist
 */
public class NavigationPanel extends JPanel {
    
    public NavigationPanel(){
        init();
    }
    
    private void init(){
        JButton newGameBtn = new JButton("New game");
        JButton regretBtn = new JButton("Regret move");
        add(newGameBtn);
        add(regretBtn);
    }
}
