package Chess;

import java.util.Arrays;

import Chess.Pieces.King;
import Chess.Pieces.Pawn;
import Chess.Pieces.Rook;

public class Move {
    Piece initialPiece;
    //Piece finalPiece; // X
    int[] initialPosition; // X?
    int finalX;
    int finalY;
    Move nextMove;
    //Boolean changedMoveStatus; //X
    String toString; // X ?

    //Add En Passant, Castling, Pins
    /*public Move(Piece piece, int[] initialPosition, int[] finalPosition){
        this.piece = piece;
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
        changedMoveStatus = false;
        if(piece instanceof Rook){
            Rook temp = (Rook) piece;
            changedMoveStatus = !temp.getHasMoved();
        }
        if(piece instanceof Pawn){
            Pawn temp = (Pawn) piece;
            changedMoveStatus = !temp.getHasMoved();
        }
        if(piece instanceof King){
            King temp = (King) piece;
            changedMoveStatus = !temp.getHasMoved();
        }
    }*/

    public Move(Board board, int[] initialPosition, int fx, int fy){
        this.initialPosition = initialPosition;
        finalX = fx;
        finalY = fy;
        initialPiece = board.getPiece(initialPosition[0], initialPosition[1]);
    }

    public int[] getInitialPosition(){
        return initialPosition;
    }

    public int getFinalX(){
        return finalX;
    }

    public int getFinalY(){
        return finalY;
    }

    public Piece getInitialPiece(){
        return initialPiece;
    }

    public Move getNextMove(){
        return nextMove;
    }

    public void setNextMove(Move move){
        nextMove = move;
    }

    public void setString(String str){
        toString = str;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Move){
            Move move = (Move) obj;
            return (Arrays.equals(initialPosition, move.getInitialPosition()) && finalX == move.getFinalX() && finalY == move.getFinalY());
        }
        return false;
    }

    @Override
    public String toString(){
        if(toString == null)
            return(String.format("%s to %d, %d", initialPiece, finalX, finalY));
        return toString;
    }
}
