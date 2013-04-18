
package ai;

import enums.Player;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Tower;
import Chessboard.Chessboard;

/**
 *
 * @author Joakim
 */
public class BoardRater {
    private static final int pawnValue = 10;
    private static final int rookValue = 50;
    private static final int knightValue = 30;
    private static final int bishopValue = 30;
    private static final int queenValue = 90;
    private static final int kingValue = 5000;
    
    
    static public int rateBoard(Chessboard board, Player playingAs){
        int whitePoints = 0;
        int blackPoints = 0;
        for(Piece p: board.getPieceList()){
            if(p.getPieceColor() == Player.WHITE){
                whitePoints += ratePiece(p);
            } else {
                blackPoints += ratePiece(p);
            }
        }
        
        if(playingAs == Player.WHITE){
            return whitePoints - blackPoints;
        } else {
            return blackPoints - whitePoints;
        }
    }
    
    static private int ratePiece(Piece piece){
        if(piece instanceof Pawn){
            return pawnValue;
        }
        if(piece instanceof Tower){
            return rookValue + piece.getMoves().size();
        }
        if(piece instanceof Knight){
            return knightValue + piece.getMoves().size();
        }
        if(piece instanceof Bishop){
            return bishopValue + piece.getMoves().size();
        }
        if(piece instanceof Queen){
            return queenValue + piece.getMoves().size();
        }
        if(piece instanceof King){
            return kingValue;
        }
        return 0;
    }    
}
