
package sjakk;

import enums.Player;

/**
 *
 * @author Joakim
 */
public class Chess {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
    
    public static Player switchPlayer(Player player){
        if(player == Player.WHITE){
            return Player.BLACK;
        } else {
            return Player.WHITE;
        }
    }
    
    
}
