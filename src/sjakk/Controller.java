package sjakk;

import enums.Player;
import pieces.Piece;
import java.util.ArrayList;
import Chessboard.Chessboard;
import view.ChessFrame;
import view.chessboardPanel.ChessboardPanel;
import view.NavigationPanel;

/**
 *
 * @author Joakim
 */
public class Controller {

    private ChessFrame frame;
    private ChessboardPanel boardPanel;

    private Game game;
    private Chessboard chessboard;
    
    private Player playerTurn = Player.WHITE;
    private final Player playingAs = Player.BLACK;
    
    
    public void prepareUI(Game game) {
        this.game = game;
        chessboard = game.getChessboard();
        frame = new ChessFrame();
        
        NavigationPanel navPanel = new NavigationPanel(); 
        boardPanel = new ChessboardPanel(this);
        
        frame.addPanelCenter(boardPanel);
        frame.addPanelNorth(navPanel);
    }

    public void showStartupBoard() {
        
        for (Piece piece : chessboard.getPieceList()) {
            Position p = mapToUI(piece.getPosition());

            boardPanel.setSquareImgPath(piece.getPieceImagePath(), p);
        }
        try {
            //Waits for ui to load images
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            System.out.println("Error waiting for pictures to load");
        }
       
        
        boardPanel.refreshUI();
    }

    public void startGame() {
        if (playerTurn != playingAs) {
            Move move = game.getAIMove(playerTurn);
            updateMoveToUI(move);
            playerTurn = playingAs;
            boardPanel.refreshUI();
        }        
    }
    
    public void updateMoveToUI(Move move) {
        Move mappedMove = mapToUI(move);
                
        boardPanel.updateMoveFromAI(mappedMove);
    }
    
    public ArrayList<Position> possibleMovesRequestFromUI(Position pos){
        
        Position mappedPos = mapFromUI(pos);     
        return mapMovesToUI(chessboard.getLegalMovesFrom(mappedPos));      
    }

    private Move requestMoveFromAI() {
        return game.getAIMove(playerTurn);
    }
   
    public boolean processMoveFromUI(Move move) {
        Move mappedMove = mapFromUI(move);
        Position startPos = mappedMove.getStartPos();
        Piece piece = chessboard.getPiece(startPos);
        mappedMove.setMovingPiece(piece);

        if (isValidMove(mappedMove)) {
            chessboard.updateMove(mappedMove);
            updateMoveToUI(mappedMove);
            playerTurn = Chess.switchPlayer(playerTurn);
            updateMoveToUI(requestMoveFromAI());
            playerTurn = Chess.switchPlayer(playerTurn);

            return true;
        }
        return false;
    }

    private boolean isValidMove(Move move) {
        Piece piece = move.getMovingPiece();
        if (piece == null || piece.getPieceColor() != playerTurn) {
            return false;
        }
        return chessboard.isValidMove(move);
    }

 
    
    /**
     * Converts a position in the datastructure to the corrosponding square in
     * the user interface
     *
     * @param x The representation of a position in the datastructure
     * @return The corrosponding position for the UI
     */
    private Position mapToUI(Position pos){
        Position mappedPos = new Position(pos.getColumn(), pos.getRow());
        return mappedPos;  
    }
    
    private Move mapToUI(Move move){
        Position startPos = mapToUI(move.getStartPos());
        Position endPos = mapToUI(move.getEndPos());
        Move mappedMove = new Move(startPos, endPos, move.getMovingPiece());
        return mappedMove;
    }
   
    
    private ArrayList<Position> mapMovesToUI(ArrayList<Move> moves){
        ArrayList<Position> positions = new ArrayList<Position>();
        
        for(Move move: moves ){
            Position pos = move.getEndPos();
            Position mappedPos = new Position(pos.getColumn(),pos.getRow());
            positions.add(mappedPos);
        }
        return positions;        
    }
    
    private int[] mapFromUI(int x, int y){
        int result[] = new int[2];
        result[0] = y;
        result[1] = x;
        return result;
    }
    
    private Move mapFromUI(Move move){
        Position startPos = mapFromUI(move.getStartPos());
        Position endPos = mapFromUI(move.getEndPos());
        Move mappedMove = new Move(startPos,endPos, move.getMovingPiece());
        return mappedMove;
    }
    
    private Position mapFromUI(Position p){
       Position mappedPos = new Position(p.getColumn(),p.getRow());
       return mappedPos;
    }
}
