
package pieces;

import enums.Player;
import java.util.ArrayList;
import Chessboard.Chessboard;
import sjakk.Move;
import sjakk.Position;

/**
 *
 * @author Joakim
 */
public abstract class Piece{
    protected ArrayList<Move> posMoves = new ArrayList<Move>();
    protected Player pieceColor;
    protected Position currentPos;
    protected Chessboard board;

    public Piece(Player color, Position position, Chessboard board) {
        currentPos = position;
        pieceColor = color;
        this.board = board;
    }
    
    //Cloning
     public Piece(Chessboard board, Piece other) {
        pieceColor = other.getPieceColor();
        this.board = board;
        currentPos = new Position(other.getPosition());
    }

    abstract protected void addAllPossibleMoves();
    
    abstract public String getPieceImagePath();
    
    
    public Player getPieceColor(){
        return pieceColor;
    }
    
    
    
    public void updateCurrentPos(Position pos){
        this.currentPos = pos;
    }
    
    public Position getPosition(){
        return currentPos;
    }
    
    protected void addMove(Position destination) {
        Move move = new Move(currentPos, destination, this);
        posMoves.add(move);
    }
    
    public ArrayList<Move> getMoves(){
        addAllPossibleMoves();
        return posMoves;
    }
   
}
