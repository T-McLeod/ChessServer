package Chess.Pieces;

import Chess.Board;
import Chess.Piece;
import Chess.Tile;
import Chess.Move;
import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece{

    public Pawn(Board board, int x, int y, Boolean isWhite){
        super(board, x, y, isWhite);
    }

    @Override
    public ArrayList<Move> getLegalMoves(){
        ArrayList<Move> moves = new ArrayList<>();

        int yDirection = isWhite ? -1 : 1;


        if(board.getTeamAt(xPosition, yPosition + yDirection) == 0){
            addMove(moves, xPosition, yPosition + yDirection);
            if(yPosition == (isWhite ? 6 : 1) && board.getTeamAt(xPosition, yPosition + yDirection*2) == 0){
                addMove(moves, xPosition, yPosition + yDirection * 2);
            }
        }

        if(Board.isValid(xPosition-1, yPosition + yDirection)){
            Tile tile = board.getTile(xPosition-1, yPosition + yDirection);
            if(tile.getIsOccupied() && tile.getPiece().getIsWhite() != isWhite){
                this.addMove(moves, xPosition-1, yPosition + yDirection);
            }
            if(board.getTargetSquare() == tile){
                Move newMove = new Move(this, xPosition-1, yPosition + yDirection);
                newMove.setNextMove(new Move(null, xPosition-1, yPosition));
                addMove(moves, newMove);
            }
        }

        if(Board.isValid(xPosition+1, yPosition + yDirection)){
            Tile tile = board.getTile(xPosition+1, yPosition + yDirection);
            if(tile.getIsOccupied() && tile.getPiece().getIsWhite() != isWhite){
                addMove(moves, xPosition+1, yPosition + yDirection);
            }
            if(board.getTargetSquare() == tile){
                Move newMove = new Move(this, xPosition+1, yPosition + yDirection);
                newMove.setNextMove(new Move(null, xPosition+1, yPosition));
                addMove(moves, newMove);
            }
        }

        return moves;

    }

    @Override
    public void move(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
    }

    @Override
    public Boolean isChecking(){
        ArrayList<Move> moves = new ArrayList<>();

        int yDirection = isWhite ? -1 : 1;
        King king = board.getKing(!isWhite);

        if(Board.isValid(xPosition-1, yPosition + yDirection) && board.getPiece(xPosition-1, yPosition + yDirection) == king)
            return true;

        if(Board.isValid(xPosition+1, yPosition + yDirection) && board.getPiece(xPosition+1, yPosition + yDirection) == king)
            return true;

        return false;
    }

    @Override
    public void addMove(ArrayList<Move> moves, int fx, int fy){
        Move move = new Move(this, fx, fy);
        if(fy == 7 || fy == 0){
            Move copy = move.copy();
            copy.setNextMove(new Move(new Queen(board, fx, fy, isWhite), fx, fy));
            super.addMove(moves, copy);

            copy = move.copy();
            move.copy().setNextMove(new Move(new Knight(board, fx, fy, isWhite), fx, fy));
            super.addMove(moves, copy);

            copy = move.copy();
            move.copy().setNextMove(new Move(new Rook(board, fx, fy, isWhite), fx, fy));
            super.addMove(moves, copy);

            move.setNextMove(new Move(new Bishop(board, fx, fy, isWhite), fx, fy));
        }
        super.addMove(moves, move);
    }

    @Override
    public String toString(){
        return("("+ xPosition + ", " + yPosition + ")");
    }

    @Override
    public String notation(){
        return(isWhite ? "P" : "p");
    }
}
