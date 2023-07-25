package Chess.Pieces;

import Chess.Board;
import Chess.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import Chess.Move;

public class Bishop extends Piece{
    private static final Image whiteIMG = new Image("./Chess/Assets/Pieces/White/Bishop.png");
    private static final Image blackIMG = new Image("./Chess/Assets/Pieces/Black/Bishop.png");

    public Bishop(Board board, int x, int y, Boolean isWhite){
        super(board, x, y, isWhite);
    }

    @Override
    public ArrayList<Move> getLegalMoves(){
        int x = xPosition;
        int y = yPosition;
        int team;
        ArrayList<Move> moves = new ArrayList<>();

        //NorthWest
        x = xPosition;
        y = yPosition;
        while(--x >= 0 && ++y < Board.heightSquares){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(team != (isWhite ? 1 : -1))
                    addMove(moves, x, y);
                break;
            }
            addMove(moves, x, y);
        }
        
        //NorthEast
        x = xPosition;
        y = yPosition;
        while(++x < Board.widthSquares && ++y < Board.heightSquares){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(team != (isWhite ? 1 : -1))
                    addMove(moves, x, y);
                break;
            }
            addMove(moves, x, y);
        }

        //SouthWest
        x = xPosition;
        y = yPosition;
        while(--x >= 0 && --y >= 0){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(team != (isWhite ? 1 : -1))
                    addMove(moves, x, y);
                break;
            }
            addMove(moves, x, y);
        }

        //SouthEast
        x = xPosition;
        y = yPosition;
        while(++x < Board.widthSquares && --y >= 0){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(team != (isWhite ? 1 : -1))
                    addMove(moves, x, y);
                break;
            }
            addMove(moves, x, y);
        }

        return moves;
    }

    @Override
    public Boolean isChecking(){
        int x = xPosition;
        int y = yPosition;
        int team;
        King king = board.getKing(!isWhite);

        //SouthWest
        x = xPosition;
        y = yPosition;
        while(--x >= 0 && ++y < Board.heightSquares){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(board.getPiece(x, y) == king)
                    return true;
                break;
            }
        }
        
        //SouthEast
        x = xPosition;
        y = yPosition;
        while(++x < Board.widthSquares && ++y < Board.heightSquares){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(board.getPiece(x, y) == king)
                    return true;
                break;
            }
        }

        //NorthWest
        x = xPosition;
        y = yPosition;
        while(--x >= 0 && --y >= 0){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(board.getPiece(x, y) == king)
                    return true;
                break;
            }
        }

        //NorthEast
        x = xPosition;
        y = yPosition;
        while(++x < Board.widthSquares && --y >= 0){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(board.getPiece(x, y) == king)
                    return true;
                break;
            }
        }
        return false;
    }

    @Override
    public Image getImage(){
        return isWhite ? whiteIMG : blackIMG;
    }

    @Override
    public String toString(){
        return("Bishop");
    }

    @Override
    public String notation(){
        return(isWhite ? "B" : "b");
    }
}
