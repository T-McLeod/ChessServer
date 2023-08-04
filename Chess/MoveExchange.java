package Chess;
import java.io.*;
import java.net.*;

public class MoveExchange implements Serializable {
    int initialX;
    int initialY;
    int finalX;
    int finalY;
    MoveExchange nextMove;

    public MoveExchange(Move move){
        initialX = move.getInitialX();
        initialY = move.getInitialY();
        finalX = move.getFinalX();
        finalY = move.getFinalY();
        if(move.getNextMove() != null){
            nextMove = new MoveExchange(move.getNextMove());
        }
    }

    public Move toMove(Board board){
        return new Move(board.getPiece(initialX, initialY), finalX, finalY);
    }
}
