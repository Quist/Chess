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
public class Bishop extends Piece {
    private final String imagePathWhite = "src/images/wbishop.png";
    private final String imagePathBlack = "src/images/bbishop.png";
    
    public Bishop(Player color, Position position,Chessboard board) {
        super(color, position, board);
    }
    
    public Bishop(Chessboard board, Piece other){
        super(board,other);
    }

    @Override
    public void addAllPossibleMoves() {
        posMoves.clear();
        checkNorthWest();
        checkNorthEast();
        checkSouthWest();
        checkSouthEast();
    }

    @Override
    public String getPieceImagePath(){
        if(pieceColor == Player.WHITE){
            return imagePathWhite;
        }
        
        return imagePathBlack;
    }
    
    private void checkNorthWest() {
        int row = currentPos.getRow() +1;
        int column = currentPos.getColumn() -1;
        
        while (row < 8 && column >= 0) {
            Position pos = new Position(row,column);
            
            if (!board.isFree(pos)) {
                checkEncounter(pos);
                return;
            } else {
                addMove(pos);
            }
            row++;
            column--;
        }
    }

    private void checkNorthEast() {
        int row =  currentPos.getRow() + 1;
        int column = currentPos.getColumn() + 1;
        
        while (row < 8 && column < 8) {
            Position pos = new Position(row,column);
            if (!board.isFree(pos)) {
                checkEncounter(pos);
                return;
            } else {
                addMove(pos);
            }
            row++;
            column++;
        }
    }

    private void checkSouthWest() {
        int row = currentPos.getRow() - 1;
        int column = currentPos.getColumn() - 1;
        while (row >= 0 && column >= 0) {
            Position pos = new Position(row, column);
            if (!board.isFree(pos)) {
                checkEncounter(pos);
                return;
            } else {
                addMove(pos);
            }
            row--;
            column--;
        }
    }

    private void checkSouthEast() {
        int row = currentPos.getRow() - 1;
        int column = currentPos.getColumn() + 1;
        while (row >= 0 && column < 8) {
            Position pos = new Position(row,column);
            if (!board.isFree(pos)) {
                checkEncounter(pos);
                return;
            } else {
                addMove(pos);
            }
            row--;
            column++;
        }
    }

    private void checkEncounter(Position pos) {
        if (board.containsEnemy(currentPos,pos)) {
            addMove(pos);
        }
    }

    
    
}
