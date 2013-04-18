package Chessboard;

import enums.Player;
import pieces.*;
import java.util.ArrayList;
import sjakk.Chess;
import sjakk.Move;
import sjakk.Position;

/**
 *
 * @author Joakim
 */
public class Chessboard {

    private Piece pieces[][];
    
    private Position whiteKingPosition;
    private Position blackKingPosition;
    
    private ArrayList<Piece> blackPieces;
    private ArrayList<Piece> whitePieces;
    public Chessboard(){
        
    }

    protected void setPieces(Piece pieces[][]){
        this.pieces = pieces;
    }
    
    protected void setBlackPieces(ArrayList<Piece> bPieces){
        this.blackPieces = bPieces;
    }
    
    protected void setWhitePieces(ArrayList<Piece> wPieces){
        this.blackPieces = wPieces;
    } 
    
    protected void setKingPosition(Position white, Position black){
        whiteKingPosition = white;
        blackKingPosition = black;
    }
    
    protected Piece[][] getPieces(){
        return pieces;
    }
    
    public ArrayList<Piece> getPieceList() {
        ArrayList<Piece> all = getPieceList(Player.BLACK);
        all.addAll(getPieceList(Player.WHITE));
        return all;
    }

    public ArrayList<Piece> getPieceList(Player player) {
        ArrayList<Piece> pieceList = new ArrayList<Piece>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (pieces[x][y] != null && pieces[x][y].getPieceColor()
                        == player) {
                    pieceList.add(pieces[x][y]);
                }
            }
        }
        return pieceList;
    }

    public Piece getPiece(Position pos) {
        return pieces[pos.getRow()][pos.getColumn()];
    }

    public ArrayList<Move> getLegalMovesFrom(Position pos){
        ArrayList<Move> moves = new ArrayList<Move>();
        if(isOutOfBounds(pos)){
            return moves;
        }
        Piece piece = pieces[pos.getRow()][pos.getColumn()];
        if(piece == null){
            return moves;
        }
        
        for(Move move: piece.getMoves()){
            if(!movesPlayerIntoCheck(move)){
                moves.add(move);
            }
        }
        return moves;
    }
    
    private ArrayList<Move> getLegalMoves(Piece piece){
        ArrayList<Move> moves = new ArrayList<Move>();

        if(piece == null){
            return moves;
        }
        
        for(Move move: piece.getMoves()){
            if(!movesPlayerIntoCheck(move)){
                moves.add(move);
            }
        }
        return moves;
    }    
    
    
    public ArrayList<Move> getLegalMoves(Player player){
        ArrayList<Move> moves = new ArrayList<Move>();
        for(Piece piece: getPieceList(player)){
            moves.addAll(getLegalMoves(piece));
        }
        return moves;
    }
    
    /**
     * Updates a move on the chessboard piece array. Does not enforce any rules!
     *
     * @param move
     */
    public void updateMove(Move move) {
        Piece movedPiece = pieces[move.getStartPos().getRow()][move.getStartPos().getColumn()];      
        movedPiece.updateCurrentPos(move.getEndPos());

        pieces[move.getStartPos().getRow()][move.getStartPos().getColumn()] = null;

        pieces[move.getEndPos().getRow()][move.getEndPos().getColumn()] = movedPiece;
        if(movedPiece instanceof King){
            updateKingPosition(move);
        }
        if(movedPiece instanceof Pawn){
            checkPawnPromotion(move);
        }        
    }
    
    private void updateCastlingMove(Move move){
        int kingX = move.getStartPos().getRow();
        int kingY = move.getEndPos().getColumn();
        Piece king = pieces[kingX][kingY];
        int towerX = move.getEndPos().getRow();
        int towerY = move.getEndPos().getColumn();
        Piece tower = pieces[towerX][towerY];
        
        pieces[kingX][kingY] = null;
         pieces[towerX][towerY] = null;
        
        if(towerY > kingY){ //Queenside
            Position kPos = new Position(kingX,kingY-2 );
            king.updateCurrentPos(kPos);
        }
        
    }
    
    private void checkPawnPromotion(Move move) {
        int row = move.getEndPos().getRow();

        if (row == 7 || row == 0) {
            
            
            Player pieceColor = move.getMovingPiece().getPieceColor();
            Queen queen = new Queen(pieceColor, move.getEndPos(), this);
            int column = move.getEndPos().getColumn();
            pieces[row][column] = queen;
            move.setMovingPiece(queen);
        }
    }
    
    private void updateKingPosition(Move move){
        Piece piece = move.getMovingPiece();
        if(piece.getPieceColor() == Player.WHITE){
            whiteKingPosition = move.getEndPos();
        } else {
            blackKingPosition = move.getEndPos();
        }
    }
    
    public boolean containsEnemy(Position startPos, Position endPos) {
        if (isOutOfBounds(endPos)) {
            return false;
        }
        if (isFree(endPos)) {
            return false;
        }
        Player movingPieceColor = pieces[startPos.getRow()][startPos.getColumn()].getPieceColor();

        Player recivingPieceColor = pieces[endPos.getRow()][endPos.getColumn()].getPieceColor();

        return (movingPieceColor != recivingPieceColor);

    }

    public boolean isFree(Position endPos) {
        if (isOutOfBounds(endPos)) {
            return false;
        }
        return (pieces[endPos.getRow()][endPos.getColumn()] == null);
    }

    public boolean isOutOfBounds(Position endPos) {
        return endPos.getRow() >= 8 || endPos.getRow() < 0
                || endPos.getColumn() >= 8 || endPos.getColumn() < 0;
    }

    /**
     * Retrives the other players possible moves and checks if he can hit the king
     * @param color
     * @return 
     */
    public boolean isInCheck(Player player) {
        Position kingPos = kingPosition(player);

        Player opponent = Chess.switchPlayer(player);
        ArrayList<Piece> pieceList = getPieceList(opponent);
        for (Piece p : pieceList) {
            for (Move m : p.getMoves()) {
                if (m.getEndPos().equals(kingPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Position kingPosition(Player player) {
        if(player == Player.WHITE){
            return whiteKingPosition;
        } else {
            return blackKingPosition;
        }
    }
    
    public boolean isUnmovedTower(Position pos){
        Piece piece = pieces[pos.getRow()][pos.getColumn()];
        if(!(piece instanceof Tower)){
            return false;
        }
        Tower tower = (Tower) piece;
       
        
        if(tower.isMoved()){
            return false;
        }
        
        return true;
    }

    public boolean movesPlayerIntoCheck(Move move) {
        Player player = move.getMovingPiece().getPieceColor();

        Chessboard newBoard = ChessboardBuilder.copy(this);
        newBoard.updateMove(move);
        if (newBoard.isInCheck(player)) {
            return true;
        }

        return false;

    }

    public boolean isValidMove(Move move) {

        if (!isIsolatedValidMove(move)) {
            return false;
        }
        
        if (movesPlayerIntoCheck(move)) {
            return false;
        }
        return true;
    }

    private boolean isIsolatedValidMove(Move move) {
        Piece piece = move.getMovingPiece();
        Position candidate = move.getEndPos();
        //Check that the move is in the possible moves list
        for (Move m : piece.getMoves()) {
            Position endPos = m.getEndPos();
            if (candidate.equals(endPos)) {
                return true;
            }
        }
        return false;
    }


}
