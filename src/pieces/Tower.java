
package pieces;

import enums.Player;
import Chessboard.Chessboard;
import sjakk.Position;

/**
 *
 * @author Joakim
 */
public class Tower extends Piece {

    private final String imagePathWhite = "images/wrook.png";
    private final String imagePathBlack = "images/brook.png";

    private boolean moved;
    
    public Tower(Player color, Position pos, Chessboard board) {
        super(color, pos, board);
    }
    
   public Tower(Chessboard board, Piece other){
        super(board,other);
    }    

    @Override
    public String getPieceImagePath() {
        if (pieceColor == Player.WHITE) {
            return imagePathWhite;
        }

        return imagePathBlack;
    }
    
    @Override
    public void updateCurrentPos(Position pos) {
        this.currentPos = pos;
        moved = true;
    }
    
    public boolean isMoved(){
        return moved;
    }

    @Override
    public void addAllPossibleMoves() {
        posMoves.clear();
        goNorth();
        goSouth();
        goWest();
        goEast();
    }

    private void goNorth() {
        int column = currentPos.getColumn();
        for (int i = currentPos.getRow() - 1; i >= 0; i--) {
            Position pos = new Position(i, column);
            if (board.isFree(pos)) {
                addMove(pos);
            } else {
                checkEncounter(pos);
                return;
            }
        }
    }

    private void goSouth() {
        int column = currentPos.getColumn();
        for (int i = currentPos.getRow() + 1; i < 8; i++) {
            Position pos = new Position(i, column);
            if (board.isFree(pos)) {
                addMove(pos);
            } else {
                checkEncounter(pos);
                return;
            }

        }
    }

    private void goWest() {
        int row = currentPos.getRow();
        for (int i = currentPos.getColumn() - 1; i >= 0; i--) {
            Position pos = new Position(row, i);
            if (board.isFree(pos)) {
                addMove(pos);
            } else {
                checkEncounter(pos);
                return;
            }
        }
    }

    private void goEast() {
        int row = currentPos.getRow();
        for (int i = currentPos.getColumn() + 1; i < 8; i++) {
            Position pos = new Position(row, i);
            if (board.isFree(pos)) {
                addMove(pos);
            } else {
                checkEncounter(pos);
                return;
            }
        }
    }

    private void checkEncounter(Position pos) {
        if (board.containsEnemy(currentPos, pos)) {
            addMove(pos);
        }
    }
}
