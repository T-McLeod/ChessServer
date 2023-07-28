package Chess.Pieces;

import Chess.Board;
import Chess.Piece;
import Chess.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Chess.Move;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.Position;

public class Pawn extends Piece{
    private static final Image whiteIMG = new Image("./Chess/Assets/Pieces/White/Pawn.png");
    private static final Image blackIMG = new Image("./Chess/Assets/Pieces/Black/Pawn.png");

    public Pawn(Board board, int x, int y, Boolean isWhite){
        super(board, x, y, isWhite);
    }

    @Override
    public Image getImage(){
        return isWhite ? whiteIMG : blackIMG;
    }

    @Override
    public ArrayList<Move> getLegalMoves(){
        ArrayList<Move> moves = new ArrayList<>();

        int yDirection = isWhite ? -1 : 1;

        if(Board.isValid(xPosition, yPosition + yDirection) && board.getTeamAt(xPosition, yPosition + yDirection) == 0){
            addMove(moves, xPosition, yPosition + yDirection);
            if(yPosition == (isWhite ? 6 : 1) && board.getTeamAt(xPosition, yPosition + yDirection*2) == 0){
                addMove(moves, xPosition, yPosition + yDirection * 2);
            }
        }

        if(Board.isValid(xPosition-1, yPosition + yDirection)){
            Tile tile = board.getTile(xPosition-1, yPosition + yDirection);
            if(tile.getIsOccupied() && tile.getPiece().getIsWhite() != isWhite){
                addMove(moves, xPosition-1, yPosition + yDirection);
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
    public String toString(){
        return("("+ xPosition + ", " + yPosition + ")");
    }

    @Override
    public String notation(){
        return(isWhite ? "P" : "p");
    }
}
