package Chess;

import java.util.ArrayList;

import Chess.Pieces.Pawn;

public abstract class Piece {
    static double mouseIX;
    static double mouseIY;
    static double pieceIX;
    static double pieceIY;
    protected Boolean isWhite;
    protected Board board;
    protected int xPosition;
    protected int yPosition;

    public Piece(Board board, int x, int y, Boolean isWhite){
        this.board = board;
        xPosition = x;
        yPosition = y;
        this.isWhite = isWhite;

    }

    

    public Boolean getIsWhite(){
        return isWhite;
    }

    public int getX(){
        return xPosition;
    }

    public int getY(){
        return yPosition;
    }

    public void setPosition(int x, int y){
        xPosition = x;
        yPosition = y;
    }

    public ArrayList<Move> getLegalMoves(){
        return null;
    }

    public Boolean getHasMoved(){
        return true;
    }

    public void reverseMove(){
        return;
    }

    public void addMove(ArrayList<Move> moves, int x, int y){
    //check for check:
        // switch tiles
        addMove(moves, new Move(this, x, y));
    }

    public void addMove(ArrayList<Move> moves, Move move){
    //check for check:
        // switch tiles
        board.move(move);

        // getMoves() for other color
        if(board.isInCheck(isWhite)){
            board.unmove();
            return;
        }
        // switch tiles back
        board.unmove();

        moves.add(move);
    }

    public Boolean isChecking(){
        return false;
    }

    public void move(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
    }

    public String notation(){
        return("");
    }
}
