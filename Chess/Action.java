package Chess;

public class Action {
    Move move;
    boolean isCapture;
    Piece takenPiece;
    Tile targetSquare;
    int halfMoveClock;
    boolean changedCastleStatus;

    public Action(Board board, Move move){
        this.move = move;
        takenPiece = board.getPiece(move.getFinalX(), move.getFinalY());
        isCapture = takenPiece == null;
        targetSquare = board.getTargetSquare();
        //halfMoveClock
        //ChangedCastleStatus
    }

    public Move getMove(){
        return move;
    }

    public Piece getTakenPiece(){
        return takenPiece;
    }

    public Tile getTargetSquare(){
        return targetSquare;
    }

    public Boolean isCapture(){
        return isCapture;
    }

    public int getHalfMoveClock() {
        return halfMoveClock;
    }
    
    public boolean isChangedCastleStatus() {
        return changedCastleStatus;
    }
}
