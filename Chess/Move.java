package Chess;

import java.util.Arrays;

import Chess.Pieces.King;
import Chess.Pieces.Pawn;
import Chess.Pieces.Rook;

public class Move {
    Piece initialPiece;
    //Piece finalPiece; // X
    int initialX;
    int initialY;
    int finalX;
    int finalY;
    Move nextMove;

    public Move(Piece piece, int fx, int fy){
        finalX = fx;
        finalY = fy;
        initialPiece = piece;
        initialX = piece.getX();
        initialY = piece.getY();
    }

    public int getFinalX(){
        return finalX;
    }

    public int getFinalY(){
        return finalY;
    }

    public int getInitialX(){
        return initialX;
    }

    public int getInitialY(){
        return initialY;
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

    public Move copy(){
        return new Move(initialPiece, finalX, finalY);
    }

    /*public void setString(String str){
        toString = str;
    }*/

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Move){
            Move move = (Move) obj;
            return (initialPiece == move.initialPiece && finalX == move.getFinalX() && finalY == move.getFinalY());
        }
        return false;
    }

    @Override
    public String toString(){
        String str = "";
        Move move = this;
        while(move != null){
            str += String.format("%s to %d, %d; ", move.initialPiece, move.finalX, move.finalY);
            move = move.getNextMove();
        }
        return str;
    }
}
