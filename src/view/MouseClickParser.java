package view;

import java.awt.event.MouseEvent;

/**
 *
 * @author Joakim
 */
public class MouseClickParser {
    
    
    public static int[] parseMouseClick(MouseEvent e){
        int result[] = new int[2];
        
        int xPoint = e.getX();
        int yPoint = e.getY();
       
        result[0] = xPoint/ChessSquare.SQUARE_SIZE;
        result[1] = yPoint/ChessSquare.SQUARE_SIZE;
       
        return result;
        
    }
    
}
