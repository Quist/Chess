/*
 * Represents a piece position on the chessboard
 */
package sjakk;

/**
 *
 * @author Joakim
 */
public class Position {
    private int row;
    private int column;
    
    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }
    
    public Position(Position other){
        this.row = other.row;
        this.column = other.column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
    
    public boolean equals(Position other){
        return (this.row == other.row) && (this.column == other.column);
    }
    
}
