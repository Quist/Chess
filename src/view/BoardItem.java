
package view;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Joakim
 */
public abstract class BoardItem {
    protected Color color;
    
    public BoardItem(Color color){
        this.color = color;
    }
    
    public abstract void draw(Graphics g);
}
