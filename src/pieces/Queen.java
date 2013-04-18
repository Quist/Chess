/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Queen extends Piece {

    private final String imagePathWhite = "images/wqueen.png";
    private final String imagePathBlack = "images/bqueen.png";

    public Queen(Player color, Position pos, Chessboard board) {
        super(color, pos, board);
    }
    
    public Queen(Chessboard board, Piece other){
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
    public void addAllPossibleMoves() {
        posMoves.clear();

        Bishop bishop = new Bishop(pieceColor, currentPos, board);
        Tower tower = new Tower(pieceColor, currentPos, board);
        ArrayList<Move> bishopMoves = bishop.getMoves();
        ArrayList<Move> towerMoves = tower.getMoves();

        bishopMoves.addAll(towerMoves);
        for (Move m : bishopMoves) {
            addMove(m.getEndPos());
        }

    }
}
