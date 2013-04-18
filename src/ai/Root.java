package ai;

import Chessboard.Chessboard;
import Chessboard.ChessboardBuilder;
import enums.Player;
import java.util.ArrayList;
import sjakk.Chess;
import sjakk.Move;

/**
 *
 * @author joakimlindquister
 */
public class Root {
    public Move bestMove;
    private Player playingAs;
    
    public Root(Player playingAs){
        this.playingAs = playingAs;
    }
    
    public Move explore(Chessboard board){
        ArrayList<Move> moves = board.getLegalMoves(playingAs);
        if(moves.isEmpty()){
            throw new IllegalStateException();
        }
        
        int currentBest = Integer.MIN_VALUE;
        for(Move move: moves){
            Chessboard newBoard = ChessboardBuilder.copy(board);
            newBoard.updateMove(move);
            Node node = new Node(playingAs);
            node.explore(BruteChessAI.depth -1, newBoard, Chess.switchPlayer(playingAs));
            if(node.getRating() > currentBest){
                bestMove = move;
                currentBest = node.getRating();
            }
        }
        return bestMove;
    }    
}
