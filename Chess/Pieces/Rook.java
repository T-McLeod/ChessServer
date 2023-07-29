package Chess.Pieces;

import Chess.Board;
import Chess.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Chess.Move;
import java.util.ArrayList;

public class Rook extends Piece{
    private static final Image whiteIMG = new Image("./Chess/Assets/Pieces/White/Rook.png");
    private static final Image blackIMG = new Image("./Chess/Assets/Pieces/Black/Rook.png");
    public Boolean hasMoved;

    public Rook(Board board, int x, int y, Boolean isWhite){
        super(board, x, y, isWhite);
        hasMoved = false;
    }

    @Override
    public Boolean getHasMoved(){
        return hasMoved;
    }

    @Override
    public void reverseMove(){
        hasMoved = false;
    }

    public ArrayList<Move> getLegalMoves(){
        int x, y, team;
        ArrayList<Move> moves = new ArrayList<>();

        //West
        x = xPosition;
        y = yPosition;
        while(--x >= 0){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(team != (isWhite ? 1 : -1))
                    addMove(moves, x, y);
                break;
            }
            addMove(moves, x, y);
        }
        
        //North
        x = xPosition;
        y = yPosition;
        while(++y < Board.heightSquares){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(team != (isWhite ? 1 : -1))
                    addMove(moves, x, y);
                break;
            }
            addMove(moves, x, y);
        }

        //East
        x = xPosition;
        y = yPosition;
        while(++x < Board.widthSquares){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(team != (isWhite ? 1 : -1))
                    addMove(moves, x, y);
                break;
            }
            addMove(moves, x, y);
        }

        //South
        x = xPosition;
        y = yPosition;
        while(--y >= 0){
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

    public static Image getImage(boolean isWhite){
        return isWhite ? whiteIMG : blackIMG;
    }

    @Override
    public void move(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
        hasMoved = true;
    }

    @Override
    public Boolean isChecking(){
        int x, y, team;
        King king = board.getKing(!isWhite);

        //West
        x = xPosition;
        y = yPosition;
        while(--x >= 0){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(board.getPiece(x, y) == king)
                    return true;
                break;
            }
        }
        
        //North
        x = xPosition;
        y = yPosition;
        while(++y < Board.heightSquares){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(board.getPiece(x, y) == king)
                    return true;
                break;
            }
        }

        //East
        x = xPosition;
        y = yPosition;
        while(++x < Board.widthSquares){
            team = board.getTeamAt(x, y);
            if(team != 0){
                if(board.getPiece(x, y) == king)
                    return true;
                break;
            }
        }

        //South
        x = xPosition;
        y = yPosition;
        while(--y >= 0){
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
        return("Rook");
    }

    @Override
    public String notation(){
        return(isWhite ? "R" : "r");
    }
}
