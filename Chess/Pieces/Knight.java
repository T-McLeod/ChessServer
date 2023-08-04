package Chess.Pieces;

import Chess.Board;
import Chess.Piece;
import Chess.Move;
import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(Board board, int x, int y, Boolean isWhite){
        super(board, x, y, isWhite);
    }

    @Override
    public ArrayList<Move> getLegalMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        int[] dx = new int[] {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dy = new int[] {-1, 1, -2, 2, -2, 2, -1, 1};
        int x, y;
        
        for(int i = 0; i < 8; ++i){
            x = xPosition + dx[i];
            y = yPosition + dy[i];
            if(Board.isValid(x, y) && board.getTeamAt(x, y) != (isWhite ? 1 : -1))
                addMove(moves, xPosition + dx[i], yPosition + dy[i]);
        }
        
        return moves;
    }

    @Override
    public Boolean isChecking(){
        int[] dx = new int[] {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dy = new int[] {-1, 1, -2, 2, -2, 2, -1, 1};
        int x, y;
        King king = board.getKing(!isWhite);
        
        for(int i = 0; i < 8; ++i){
            x = xPosition + dx[i];
            y = yPosition + dy[i];
            if(Board.isValid(x, y) && board.getPiece(x, y) == king)
                return true;
        }
        
        return false;
    }

    @Override
    public String toString(){
        return("Knight");
    }

    @Override
    public String notation(){
        return(isWhite ? "N" : "n");
    }
}
