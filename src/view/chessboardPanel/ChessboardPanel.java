package view.chessboardPanel;

import sjakk.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import sjakk.Move;
import sjakk.Position;
import view.ChessSquare;
import view.MouseClickParser;

/**
 *
 * @author Joakim
 */
public class ChessboardPanel extends JPanel implements MouseListener {

    public final static Color CHESSWHITE = Color.WHITE;
    public final static Color CHESSBLACK = Color.GRAY;
    public final static Color SELECTEDCOLOR = Color.GREEN;
    public final static Color RECENTMOVECOLOR = Color.BLUE;
    
    private ChessSquare squares[][];
    private Controller controller;
    
    //State varibles
    private ChessSquare selectedSquare;
    private ArrayList<ChessSquare> taggedSquares;

    public ChessboardPanel(Controller controller) {
        this.controller = controller;
        int prefSize = 8 * ChessSquare.SQUARE_SIZE;
        setPreferredSize(new Dimension(prefSize, prefSize));

        squares = new ChessSquare[8][8];
        createAndAddSquares();
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares[x][y].draw(g);
            }
        }
    }

    public void refreshUI() {
        paintComponent(this.getGraphics());
    }

    private void createAndAddSquares() {
        Color color = CHESSBLACK;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xPos = x * ChessSquare.SQUARE_SIZE;
                int yPos = y * ChessSquare.SQUARE_SIZE;
                ChessSquare square = new ChessSquare(color, xPos, yPos);
                squares[x][y] = square;
                color = switchBlackWhite(color);
            }
            color = switchBlackWhite(color);
        }
    }

    private Color switchBlackWhite(Color color) {
        if (color == CHESSBLACK) {
            return CHESSWHITE;
        } else {
            return CHESSBLACK;
        }
    }
    
    public void setSquareImgPath(String path, Position pos) {
        squares[pos.getRow()][pos.getColumn()].setPieceIMGPath(path);
    }

    private void tagPossibleMoves(ArrayList<Position> pos) {
        taggedSquares = new ArrayList<ChessSquare>();
        for (Position p : pos) {
            ChessSquare square = squares[p.getRow()][p.getColumn()];
            square.setPosMoveOval();
            taggedSquares.add(square);
        }
    }

    private void untagSquares() {
        for (ChessSquare c : taggedSquares) {
            c.disablePosMoveOval();
        }
        taggedSquares = null;
    }

    private void selectSquare(ChessSquare square) {
        selectedSquare = square;
        selectedSquare.setHighlighted(SELECTEDCOLOR);
        Position pos = new Position(square.getRowID(), square.getColumnID());
        tagPossibleMoves(controller.possibleMovesRequestFromUI(pos));
        refreshUI();
    }

    private void unselectSquare() {
        untagSquares();
        selectedSquare.disableHighlighted();
        selectedSquare = null;
        refreshUI();
    }

    private void clickEvent(int square[]) {
        int x = square[0];
        int y = square[1];
        ChessSquare clickedSquare = squares[x][y];

        //If no square currently selected
        if (selectedSquare == null) {
            selectSquare(clickedSquare);
            return;
        }

        //Tries to process a move
        Move move = squaresToMove(selectedSquare,clickedSquare);
        if (processMove(move)) {
            unselectSquare();
        } else {
            unselectSquare();
            selectSquare(clickedSquare);
        }
    }
    
    private Move squaresToMove(ChessSquare a, ChessSquare b){
            Position startPos = new Position(a.getRowID(),
                a.getColumnID());
        Position endPos = new Position(b.getRowID(), b.getColumnID());
        
        Move move = new Move(startPos,endPos, null);
        return move;
    }

    private boolean processMove(Move move) {
    
        return controller.processMoveFromUI(move);
    }
    
    public void updateMoveFromAI(Move move){
        setSquareImgPath(null,move.getStartPos());
        setSquareImgPath(move.getMovingPiece().getPieceImagePath(),
                move.getEndPos());
        refreshUI();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickEvent(MouseClickParser.parseMouseClick(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
