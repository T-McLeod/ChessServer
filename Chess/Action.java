package Chess;

public class Action {
    Move move;
    boolean isCapture;
    Piece takenPiece;
    Tile targetSquare;
    Tile initialSquare;
    int halfMoveClock;
    boolean changedCastleStatus;

    public Action(Board board, Move move){
        this.move = move;
        //takenPiece = board.getPiece(move.getFinalX(), move.getFinalY());
        isCapture = !(takenPiece == null);
        targetSquare = board.getTargetSquare();
        Piece piece = move.getInitialPiece();
        //halfMoveClock
        //ChangedCastleStatus
    }

    public Move getMove(){
        return move;
    }

    public Tile getInitialSquare(){
        return initialSquare;
    }

    public Piece getTakenPiece(){
        return takenPiece;
    }

    public void setTakenPiece(Piece piece){
        takenPiece = piece;
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
