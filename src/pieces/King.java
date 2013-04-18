package pieces;

import enums.Player;
import Chessboard.Chessboard;
import sjakk.Position;

/**
 *
 * @author Joakim
 */
public class King extends Piece {
    private final String imagePathWhite = "images/wking.png";
    private final String imagePathBlack = "images/bking.png";
    
    private boolean moved;
    
    public King(Player color, Position pos, Chessboard board) {
        super(color, pos, board);
    }
    
    public King(Chessboard board, Piece other) {
        super(board, other);
    }
    
    @Override
    public void updateCurrentPos(Position pos) {
        this.currentPos = pos;
        moved = true;
    }
    
    @Override
    public String getPieceImagePath(){
        if(pieceColor == Player.WHITE){
            return imagePathWhite;
        }
        
        return imagePathBlack;
    }

    @Override
    protected void addAllPossibleMoves() {        
        posMoves.clear();
        Position pos = new Position(currentPos.getRow() -1, 
                currentPos.getColumn() - 1);
        
        checkSpace(pos);
       
        pos = new Position(currentPos.getRow() - 1,currentPos.getColumn());
        checkSpace(pos);
        
        pos = new Position(currentPos.getRow() - 1, currentPos.getColumn() +1 );
        checkSpace(pos);
        
        pos = new Position(currentPos.getRow(), currentPos.getColumn() -1);
        checkSpace(pos);

        pos = new Position(currentPos.getRow(), currentPos.getColumn() +1);
        checkSpace(pos);
        
        pos = new Position(currentPos.getRow() +1, currentPos.getColumn() -1);
        checkSpace(pos);
        
        pos = new Position(currentPos.getRow() +1, currentPos.getColumn());
        checkSpace(pos);
        
        pos = new Position(currentPos.getRow() +1, currentPos.getColumn() +1);
        checkSpace(pos);
        
    }
    
    private void checkCastling(){
        queenSideCastling();
        kingSideCastling();
        
    }
    
    private void queenSideCastling() {
        if (moved) {
            return;
        }
        for(int column = 4; column<7; column++){
            Position pos = new Position(currentPos.getRow(),column);
            if(!board.isFree(pos)){
                return;
            }
        }
        
        Position queenSideTower = new Position(currentPos.getRow(), 7);
        if (board.containsEnemy(currentPos, queenSideTower)) {
            return;
        }

        if (!board.isUnmovedTower(queenSideTower)) {
            return;
        }
        addMove(queenSideTower);
    }
    
    private void kingSideCastling(){
        if (moved) {
            return;
        }
        for(int column = 2; column > 0; column--){
            Position pos = new Position(currentPos.getRow(),column);
            if(!board.isFree(pos)){
                return;
            }
        }
        
        Position kingSideTower = new Position(currentPos.getRow(), 0);
        if (board.containsEnemy(currentPos, kingSideTower)) {
            return;
        }

        if (!board.isUnmovedTower(kingSideTower)) {
            return;
        }
        addMove(kingSideTower);
    }
    
    private void checkSpace(Position pos){
        if(board.containsEnemy(currentPos,pos)){
            addMove(pos);
        }else if(board.isFree(pos)){
            addMove(pos);
        }
    }    
}
