
package ai;

import enums.Player;
import pieces.Piece;
import java.util.ArrayList;
import java.util.Random;
import Chessboard.Chessboard;
import sjakk.Move;

/**
 *
 * @author Joakim
 */
public class RandomAI {
    private Player playingAs;
   
    public RandomAI(Player playingAs){
        this.playingAs = playingAs;
   
    }
    
    public Move getChessMove(Chessboard board){
        ArrayList<Move> posMoves = getPosMoves(board);
        return getRandomMove(posMoves);           
    }
   

    
    private ArrayList<Move> getPosMoves(Chessboard board) {

        ArrayList<Move> possibleMoves = new ArrayList<Move>();
                
        for (Piece piece : board.getPieceList(playingAs)) {
            possibleMoves.addAll(board.getLegalMovesFrom(piece.getPosition()));
        }
        return possibleMoves;

    }

    private Move getRandomMove(ArrayList<Move> possibleMoves) {
        System.out.println("Number of pos moves: " + possibleMoves.size());
        Random rgen = new Random();
        int randomMove = rgen.nextInt(possibleMoves.size());
        return possibleMoves.get(randomMove);
    }

}
