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
        if(isWhite)
            graphic.setImage(whiteIMG);
        else
            graphic.setImage(blackIMG);
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

        /*//Does not account for variants where king does not start on back rank
        King king = board.getKing(isWhite);
        if(!hasMoved && !king.hasMoved){
            int curX = 4;
            int kingY = king.getPosition()[1];
            Boolean condition = true;
            if((xPosition == 7) ^ isWhite){ //queenside castle
                //Check to make sure no pieces between start and end x
                int endX = 6;
                while(curX <= endX){
                    if(board.getTeamAt(curX, kingY) != 0){
                        condition = false;
                        curX--;
                        break;
                    }
                    board.getTile(new int[] {curX, kingY}).setPiece(king);
                    curX++;
                }

                condition = condition && !board.isInCheck(isWhite);

                while(curX >= king.getPosition()[0]){
                    board.getTile(new int[] {curX, kingY}).setPiece(null);
                    curX--;
                }

                //Create new move for king
                Move move = new Move(king, king.getPosition(), new int[] {6, kingY});
                move.setNextMove(new Move(this, new int[] {xPosition, yPosition}, new int[] {5, kingY}));
                //Add extra move
                moves.add(move);
            } else { //kingside castle
                int endX = 2;
                while(curX >= endX){
                    if(board.getTeamAt(curX, kingY) != 0){
                        condition = false;
                        curX++;
                        break;
                    }
                    board.getTile(new int[] {curX, kingY}).setPiece(king);
                    curX--;
                }

                condition = condition && !board.isInCheck(isWhite);

                while(curX <= king.getPosition()[0]){
                    board.getTile(new int[] {curX, kingY}).setPiece(null);
                    curX++;
                }

                Move move = new Move(king, king.getPosition(), new int[] {endX, kingY});
                move.setNextMove(new Move(this, new int[] {xPosition, yPosition}, new int[] {3, kingY}));
                moves.add(move);
            }

        }*/

        return moves;
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
    public String toString(){
        return("Rook");
    }

    @Override
    public String notation(){
        return(isWhite ? "R" : "r");
    }
}
