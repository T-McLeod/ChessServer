package Chess;

import java.util.Arrays;

import Chess.Pieces.King;
import Chess.Pieces.Pawn;
import Chess.Pieces.Rook;

public class Move {
    Piece initialPiece;
    Piece finalPiece;
    int[] initialPosition;
    int[] finalPosition;
    Move nextMove;
    Boolean changedMoveStatus;
    String toString;

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

    public Move(Board board, int[] initialPosition, int[] finalPosition){
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
        initialPiece = board.getPiece(initialPosition[0], initialPosition[1]);
        finalPiece = board.getPiece(finalPosition[0], finalPosition[1]);
        if(initialPiece != null)
            changedMoveStatus = !initialPiece.getHasMoved();
        else
            changedMoveStatus = false;
    }

    public int[] getInitialPosition(){
        return initialPosition;
    }

    public int[] getFinalPosition(){
        return finalPosition;
    }

    public Piece getInitialPiece(){
        return initialPiece;
    }

    public Piece getFinalPiece(){
        return finalPiece;
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
            return (Arrays.equals(initialPosition, move.getInitialPosition()) && Arrays.equals(finalPosition, move.getFinalPosition()));
        }
        return false;
    }

    public Boolean getChangedMoveStatus(){
        return changedMoveStatus;
    }

    @Override
    public String toString(){
        if(toString == null)
            return(String.format("%s to %d, %d", initialPiece, finalPosition[0], finalPosition[1]));
        return toString;
    }
}
