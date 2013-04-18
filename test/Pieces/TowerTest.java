
package Pieces;

import pieces.Tower;
import enums.Player;
import org.junit.*;
import static org.junit.Assert.*;
import Chessboard.Chessboard;
import sjakk.Position;

/**
 *
 * @author Joakim
 */
public class TowerTest {
    Tower tower;
    
    @Before
    public void setUp() {
        Chessboard board = new Chessboard();
        Position pos = new Position(0,0);
        tower = new Tower(Player.WHITE, pos, board);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAllMovesFirstPlay() {
        assertEquals(0,tower.getMoves().size());
    }    
}
