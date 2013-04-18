package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Joakim
 */
public class ChessSquare extends BoardItem {

    public final static int SQUARE_SIZE = 60;
    
    private Color highlightColor = Color.GREEN;
    private Color posMoveColor = Color.BLUE;
    private int posMoveOvalWidth = 10;
    private int posMoveOvalHeight = 10;
    
    
    private String pieceIMGPath;
    private int x;
    private int y;
    
    private boolean highlighted;
    private boolean posMoveOval;
    

    public ChessSquare(Color color, int x, int y) {
        super(color);
        this.x = x;
        this.y = y;
    }

    public void setPieceIMGPath(String path) {
        this.pieceIMGPath = path;
    }

    public void setHighlighted(Color color) {
        highlighted = true;
        highlightColor = color;
    }
    
    public void disableHighlighted(){
        highlighted = false;
    }
    
    public void setPosMoveOval(){
        posMoveOval = true;
    }
    public void disablePosMoveOval(){
        posMoveOval = false;
    }
    
    public int getRowID(){
        return x/SQUARE_SIZE;
    }
    
    public int getColumnID(){
        return y/SQUARE_SIZE;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Rectangle2D r = new Rectangle2D.Double(x, y, SQUARE_SIZE, SQUARE_SIZE);
        g2.setColor(color);
        g2.fill(r);


        //Draw piece
        if (pieceIMGPath != null) {
            Image img = readImage();
            g.drawImage(img, x, y, null);

        }

        if (highlighted) {
            Rectangle2D outline = new Rectangle2D.Double(x-1, y-1, SQUARE_SIZE, SQUARE_SIZE);
            g2.setColor(highlightColor);
            BasicStroke stroke = new BasicStroke(2);
            
            g2.setStroke(stroke);
            g2.draw(outline);
        }
        
        if(posMoveOval){
            drawPosibleMoveOval(g2);
        }
        

    }

    private void drawPosibleMoveOval(Graphics2D g2){
        Ellipse2D e = new Ellipse2D.Double(x,y,posMoveOvalWidth,posMoveOvalHeight);
        g2.setColor(posMoveColor);
        g2.fill(e);
        g2.draw(e);
    }
    
    private Image readImage() {
        Image image = Toolkit.getDefaultToolkit().getImage(pieceIMGPath);
        return image;
    }
}
