package pieces;

import enums.Player;
import Chessboard.Chessboard;
import sjakk.Position;


/**
 *
 * @author Joakim
 */
public class Pawn extends Piece {

    private final String imagePathWhite = "src/images/wpawn.png";
    private final String imagePathBlack = "src/images/bpawn.png";

    public Pawn(Player color, Position pos, Chessboard board) {
        super(color, pos, board);
    }
    
   public Pawn(Chessboard board, Piece other){
        super(board,other);
    }    
    
    @Override
    public String getPieceImagePath(){
        if(pieceColor == Player.WHITE){
            return imagePathWhite;
        }
        
        return imagePathBlack;
    }

    @Override
    public void addAllPossibleMoves() {
        posMoves.clear();
        if (pieceColor == Player.BLACK) {
            moveBlackForward();
            attackBlack();
        } else {
            moveWhiteForward();
            attackWhite();
        }
    }

    private void moveBlackForward() {
        Position pos = new Position(currentPos.getRow()-1, currentPos.getColumn());
        if(board.isFree(pos)){
            addMove(pos);
        } else {
            return;
        }
        
        if (isInStartPos()) {
            pos = new Position(currentPos.getRow()-2, currentPos.getColumn());
            if (board.isFree(pos)) {
                addMove(pos);
            }
        }
    }

    private void attackBlack() {
        //Check to the left
        Position pos = new Position(currentPos.getRow()-1, currentPos.getColumn()-1);
        if (board.containsEnemy(currentPos,pos)) {
            addMove(pos);
        }

        //Check to the right:
        pos = new Position(currentPos.getRow()-1, currentPos.getColumn()+1);
        if (board.containsEnemy(currentPos,pos)) {
            addMove(pos);
        }
    }

    private void moveWhiteForward() {
        Position pos = new Position(currentPos.getRow() +1, currentPos.getColumn());
        if (board.isFree(pos)) {
             addMove(pos);
        } else {
            return;
        } 

        //If in startpos
        if (isInStartPos()) {
            pos = new Position(currentPos.getRow()+2, currentPos.getColumn());
            if (board.isFree(pos)) {
                addMove(pos);
            }
        }
    }

    private void attackWhite() {
        //Check to the left
        Position pos = new Position(currentPos.getRow() +1, currentPos.getColumn() -1);
        if (board.containsEnemy(currentPos,pos)) {
            addMove(pos);
        }

        //Check to the right:
        pos = new Position(currentPos.getRow() + 1, currentPos.getColumn() +1);
        if (board.containsEnemy(currentPos,pos)) {
            addMove(pos);
        }
    }

    private boolean isInStartPos() {
        if (pieceColor == Player.BLACK) {
            return currentPos.getRow() == 6;
        } else {
            return currentPos.getRow() == 1;
        }
    }

}
