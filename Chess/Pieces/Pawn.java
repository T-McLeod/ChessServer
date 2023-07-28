package Chess.Pieces;

import Chess.Board;
import Chess.Piece;
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

        if(Board.isValid(xPosition-1, yPosition + yDirection) && board.getTeamAt(xPosition-1, yPosition + yDirection) == yDirection)
            addMove(moves, xPosition-1, yPosition + yDirection);

        if(Board.isValid(xPosition+1, yPosition + yDirection) && board.getTeamAt(xPosition+1, yPosition + yDirection) == yDirection)
            addMove(moves, xPosition+1, yPosition + yDirection);    

        //Add En Passant
        Move lastMove = board.getLastMove();
        Move testCase = new Move(board, new int[] {xPosition-1, (int) (yDirection * 2.5 + 3.5)}, new int[] {xPosition-1, (int) (yDirection * .5 + 3.5)});
        if(board.getTargetSquare() == ){
            Move newMove = new Move(board, getPosition(), new int[] {xPosition-1, yPosition + yDirection});
            newMove.setNextMove(new Move(board, lastMove.getInitialPosition(), lastMove.getFinalPosition()));
            addMove(moves, newMove);
        }
        
        testCase = new Move(board, new int[] {xPosition+1, (int) (yDirection * 2.5 + 3.5)}, new int[] {xPosition+1, (int) (yDirection * .5 + 3.5)});
        if(yPosition == (int) (yDirection * .5 + 3.5) && lastMove.getInitialPiece() instanceof Pawn && Arrays.equals(board.getLastMove().getFinalPosition(), new int[] {xPosition+1, (int) (yDirection * .5 + 3.5)})){
            System.out.println("en Passant");
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
