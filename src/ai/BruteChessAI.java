package ai;

import Chessboard.Chessboard;
import enums.Player;
import sjakk.Move;

/**
 *
 * @author joakimlindquister
 */
public class BruteChessAI {
    public static int depth = 4;
    private Player playingAs;
    
    
    public BruteChessAI(Player playingAs){
        this.playingAs = playingAs;
    }
    
    public Move getChessMove(Chessboard board){
        Root root = new Root(playingAs);
        root.explore(board);
        return root.bestMove;
    }
}
