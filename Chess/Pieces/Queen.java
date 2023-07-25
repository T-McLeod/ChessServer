package Chess.Pieces;

import java.util.ArrayList;

import Chess.Board;
import Chess.Move;
import Chess.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Queen extends Piece{
    private static final Image whiteIMG = new Image("./Chess/Assets/Pieces/White/Queen.png");
    private static final Image blackIMG = new Image("./Chess/Assets/Pieces/Black/Queen.png");
    Boolean hasMoved;

    public Queen(Board board, int x, int y, Boolean isWhite){
        super(board, x, y, isWhite);
        hasMoved = false;
        if(isWhite)
            graphic.setImage(whiteIMG);
        else
            graphic.setImage(blackIMG);
    }

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

    public Boolean isChecking(){
        int x = xPosition;
        int y = yPosition;
        int team;
        King king = board.getKing(!isWhite);

        //NorthWest
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
        
        //NorthEast
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

        //SouthWest
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

        //SouthEast
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
    public String toString(){
        return("Queen");
    }

    @Override
    public String notation(){
        return(isWhite ? "Q" : "q");
    }
}
