/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pieces;

import enums.Player;
import Chessboard.Chessboard;
import sjakk.Position;

/**
 *
 * @author Joakim
 */
public class Knight extends Piece {
    private final String imagePathWhite = "images/wknight.png";
    private final String imagePathBlack = "images/bknight.png";

    public Knight(Player color, Position pos, Chessboard board) {
        super(color, pos, board);
    }
    
     public Knight(Chessboard board, Piece other) {
        super(board, other);
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
        
         //1 north, 2 west:
        Position pos = new Position(currentPos.getRow() +1, currentPos.getColumn() -2);
        checkEncounter(pos);

        //1 north, 2 east:
        pos = new Position(currentPos.getRow() + 1, currentPos.getColumn() +2);
        checkEncounter(pos);

        //2 north, 1 west:
        pos = new Position(currentPos.getRow()+2, currentPos.getColumn() -1);
        checkEncounter(pos);

        //2 north, 1 east:
        pos = new Position(currentPos.getRow()+2, currentPos.getColumn()+1);
        checkEncounter(pos);

        //2 south, 1 west:
        pos = new Position(currentPos.getRow()-2, currentPos.getColumn() -1);
        checkEncounter(pos);
        
        //2 south, 1 east:
        pos = new Position(currentPos.getRow()-2, currentPos.getColumn()+1);
        checkEncounter(pos);
        
        //1 south, 2 east:
        pos = new Position(currentPos.getRow()-1, currentPos.getColumn()+2);
        checkEncounter(pos);
        
        //1 south, 2 west:
        pos = new Position(currentPos.getRow()-1, currentPos.getColumn()-2);
        checkEncounter(pos);
    }

    private void checkEncounter(Position pos) {
        if (board.containsEnemy(currentPos,pos)) {
            addMove(pos);
            return;
        }
        if(board.isFree(pos)){
            addMove(pos);
        }
    }
}
