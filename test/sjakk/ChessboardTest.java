/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sjakk;

import Chessboard.Chessboard;
import enums.Player;
import pieces.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Joakim
 */
public class ChessboardTest {

    private Chessboard chessboard;

    public ChessboardTest() {
    }

    @Before
    public void setUp() {
        chessboard = new Chessboard();
    }

    @After
    public void tearDown() {
        chessboard = null;
    }

    @Test
    public void testBoardDimensions() {
        Piece board[][] = chessboard.getBoard();
        assertEquals("Dimension is not 8x8", 8, board.length);
        assertEquals("Dimension is not 8x8", 8, board[0].length);
    }

    @Test
    public void testPrepareBoardPieceColors() {
        Piece board[][] = chessboard.getBoard();
        //Check colors
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 8; y++) {
                assertEquals("Wrong piece color", Player.WHITE,
                        board[x][y].getPieceColor());
            }
        }

        for (int x = 7; x < 5; x--) {
            for (int y = 0; y < 8; y++) {
                assertEquals("Wrong piece color", Player.BLACK,
                        board[x][y].getPieceColor());
            }
        }
    }

    @Test
    public void testPrepareBoardPieceType() {
        Piece board[][] = chessboard.getBoard();
        assertTrue(board[0][0] instanceof Tower);
        assertTrue(board[0][1] instanceof Knight);
        assertTrue(board[0][2] instanceof Bishop);
        assertTrue(board[0][3] instanceof Queen);
        assertTrue(board[0][4] instanceof King);
        assertTrue(board[0][5] instanceof Bishop);
        assertTrue(board[0][6] instanceof Knight);
        assertTrue(board[0][7] instanceof Tower);

        assertTrue(board[7][0] instanceof Tower);
        assertTrue(board[7][1] instanceof Knight);
        assertTrue(board[7][2] instanceof Bishop);
        assertTrue(board[7][3] instanceof Queen);
        assertTrue(board[7][4] instanceof King);
        assertTrue(board[7][5] instanceof Bishop);
        assertTrue(board[7][6] instanceof Knight);
        assertTrue(board[7][7] instanceof Tower);

        //Test pawns
        for (int y = 0; y < 8; y++) {
            assertTrue(board[1][y] instanceof Pawn);
            assertTrue(board[6][y] instanceof Pawn);
        }
    }

    @Test
    public void testPrepareBoardRightNumberOfPieces() {
        Piece[][] board = chessboard.getBoard();
        //Checks middleground for pieces
        for (int x = 2; x < 6; x++) {
            for (int y = 0; y < 8; y++) {
                assertNull(board[x][y]);
            }
        }
    }

    @Test
    public void testUpdateMoveSimple() {
        Piece oldBoard[][] = chessboard.getBoard();
        //Moves pawn at e2 to e 4.
        Position pos = new Position(1, 4);
        Position endPos = new Position(3, 4);
        Move move = new Move(pos, endPos);
        chessboard.updateMove(move);
        Piece newBoard[][] = chessboard.getBoard();

        //Assert board not changes apart from move
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if ((x != 1 && y != 4) || (x != 3 && y != 4)) {
                    assertEquals(oldBoard[x][y], newBoard[x][y]);
                }
            }
        }

        //Check that move has been updated.
        assertNull(newBoard[1][4]);
        assertTrue(newBoard[3][4] instanceof Pawn);
    }

    @Test
    public void testUpdateAttackingMove() {
        Piece oldBoard[][] = chessboard.getBoard();

        //Attacks A7 with pawn at a2.. Illegal moves works here

        Position pos = new Position(1, 0);
        Position endPos = new Position(6, 0);
        Move move = new Move(pos, endPos);
        chessboard.updateMove(move);
        Piece newBoard[][] = chessboard.getBoard();

        //Assert board not changes apart from move
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if ((x != 1 && y != 0) || (x != 6 && y != 0)) {
                    assertEquals(oldBoard[x][y], newBoard[x][y]);
                }
            }
        }

        //Check that move has been updated.
        assertNull(newBoard[1][0]);
        assertTrue(newBoard[6][0] instanceof Pawn);
    }

    @Test
    public void testContainsEnemyOnEnemyPosition() {
        Position white = new Position(0, 0);
        Position black = new Position(6, 0);
        assertTrue(chessboard.containsEnemy(white, black));
    }

    @Test
    public void testContainsEnemyOnFriendlyPosition() {
        Position white = new Position(0, 0);
        Position friendly = new Position(0, 5);
        assertFalse(chessboard.containsEnemy(white, friendly));
    }

    @Test
    public void testContainsEnemyOnEmptyPosition() {
        Position white = new Position(0, 0);
        Position empty = new Position(5, 5);
        assertFalse(chessboard.containsEnemy(white, empty));

    }

    @Test
    public void testContainsEnemyOnOwnPosition() {
        Position white = new Position(0, 0);
        Position self = new Position(0, 0);
        assertFalse(chessboard.containsEnemy(white, self));
    }

    @Test
    public void testIsFreeOnFreeSquare() {
        Position pos = new Position(4,4);
        assertTrue(chessboard.isFree(pos));
    }
    
    @Test
    public void testIsFreeOnOccupiedSquare() {
        Position pos = new Position(0,0);
        assertFalse(chessboard.isFree(pos));    
    }
    
    @Test
    public void testIsOutOfBounds() {
        Position pos = new Position(5,5); //Mid point
        assertFalse(chessboard.isOutOfBounds(pos));
        
        pos = new Position(-1,-1);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(-1,0);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(-1,7);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(-1,8);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(0,-1);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(0,0);
        assertFalse(chessboard.isOutOfBounds(pos));
        
        pos = new Position(0,7);
        assertFalse(chessboard.isOutOfBounds(pos));
        
        pos = new Position(0,8);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(7,-1);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(7,0);
        assertFalse(chessboard.isOutOfBounds(pos));
        
        pos = new Position(7,7);
        assertFalse(chessboard.isOutOfBounds(pos));
        
        pos = new Position(7,8);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(8,-1);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(8,0);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(8,7);
        assertTrue(chessboard.isOutOfBounds(pos));
        
        pos = new Position(8,8);
        assertTrue(chessboard.isOutOfBounds(pos));
    }

    @Test
    public void testIsInCheck() {
        fail("Not implemented");
    }
}
