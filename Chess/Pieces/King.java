package Chess.Pieces;

import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat.Type;

import Chess.Board;
import Chess.Move;
import Chess.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class King extends Piece{
    private static final Image whiteIMG = new Image("./Chess/Assets/Pieces/White/King.png");
    private static final Image blackIMG = new Image("./Chess/Assets/Pieces/Black/King.png");
    Boolean hasMoved;
    Rook kingSide; //unused
    Rook queenSide; //unused

    public King(Board board, int x, int y, Boolean isWhite){
        super(board, x, y, isWhite);
        hasMoved = false;
    }

    @Override
    public Boolean getHasMoved(){
        return hasMoved;
    }

    public Rook getKingRook(){
        return kingSide;
    }

    public Rook getQueenRook(){
        return queenSide;
    }

    public void setKingRook(Rook rook){
        kingSide = rook;
    }

    public void setQueenRook(Rook rook){
        queenSide = rook;
    }

    @Override
    public void reverseMove(){
        hasMoved = false;
    }

    @Override
    public ArrayList<Move> getLegalMoves(){
        ArrayList<Move> moves = new ArrayList<>();
        for(int x = -1; x < 2; x++){
            for(int y = -1; y < 2; y++){
                if(Board.isValid(xPosition + x, yPosition + y) && board.getTeamAt(xPosition + x, yPosition + y) != (isWhite ? 1 : -1))
                    addMove(moves, xPosition + x, yPosition + y);
            }
        }

        Boolean legal = true;
        Move move;
        if(kingSide != null && !kingSide.getHasMoved() && !board.isInCheck(isWhite)){
            for(int i = 5; i < 7; ++i){
                if(board.getTeamAt(i, yPosition) == 0){
                    move = new Move(this, i, yPosition);
                    board.move(move);
                    if(board.isInCheck(isWhite)){
                        legal = false;
                        board.unmove();
                        break;
                    }
                    board.unmove();
                } else{
                    legal = false;
                    break;
                }
            }
            if(legal){
                move = new Move(this, 6, yPosition);
                move.setNextMove(new Move(kingSide, 5, yPosition));
                moves.add(move);
            }
        }

        legal = true;
        if(queenSide != null && !queenSide.getHasMoved()){
            for(int i = 5; i > 1; --i){
                if(board.getTeamAt(i, yPosition) == 0){
                    move = new Move(this, i, yPosition);
                    board.move(move);
                    if(board.isInCheck(isWhite)){
                        legal = false;
                        board.unmove();
                        break;
                    }
                    board.unmove();
                } else{
                    legal = false;
                    break;
                }
            }
            if(board.getTeamAt(1, yPosition) != 0){
                legal = false;
            }
            if(legal){
                move = new Move(this, 2, yPosition);
                move.setNextMove(new Move(queenSide, 3, yPosition));
                moves.add(move);
            }
        }
        
        return moves;
    }

    @Override
    public void move(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
        hasMoved = true;
    }

    /*public Boolean inCheck(){
        int x = xPosition;
        int y = yPosition;
        int team;
        ArrayList<Move> moves = new ArrayList<>();
        Piece piece;

        //NorthWest
        x = xPosition;
        y = yPosition;
        while(--x >= 0 && ++y < Board.heightSquares){
            team = board.getTeamAt(x, y);
            if(team != 0){
                piece = board.getPiece(x, y);
                if(team != (isWhite ? 1 : -1) && (piece instanceof Queen || piece instanceof Bishop))
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
                piece = board.getPiece(x, y);
                if(team != (isWhite ? 1 : -1) && (piece instanceof Queen || piece instanceof Bishop))
                    addMove(moves, x, y);
                break;
            }
            addMove(moves, x, y);
        }

        //SouthWest
        x = xPosition;
        y = yPosition;
        while(--x > 0 && --y >= 0){
            team = board.getTeamAt(x, y);
            if(team != 0){
                piece = board.getPiece(x, y);
                if(team != (isWhite ? 1 : -1) && (piece instanceof Queen || piece instanceof Bishop))
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
                piece = board.getPiece(x, y);
                if(team != (isWhite ? 1 : -1) && (piece instanceof Queen || piece instanceof Bishop))
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
                piece = board.getPiece(x, y);
                if(team != (isWhite ? 1 : -1) && (piece instanceof Queen || piece instanceof Rook))
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
    }*/

    @Override
    public Image getImage(){
        return isWhite ? whiteIMG : blackIMG;
    }

    @Override
    public String toString(){
        return("King");
    }

    @Override
    public String notation(){
        return(isWhite ? "K" : "k");
    }
}
