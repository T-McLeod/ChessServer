package Chess;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import Chess.Pieces.*;

import java.lang.*;

public class Board {
    final public static int widthSquares = 8;
    final public static int heightSquares = 8;
    private Tile[][] board;
    private Boolean isWhiteMove;
    private King whiteKing;
    private King blackKing;
    private Deque<Action> stack;
    private int halfMoveClock; //unused
    private int fullMoveCounter; //unused
    private Tile targetSquare;
    private Set<Piece> whitePieces;
    private Set<Piece> blackPieces;

    public Board(){
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public Board(String FEN){
        long startTime, endTime;
        whitePieces = new HashSet<>();
        blackPieces = new HashSet<>();

        isWhiteMove = true;
        stack = new ArrayDeque<>();

        board = new Tile[widthSquares][heightSquares];

        for(int x = 0; x < widthSquares; ++x){
            for(int y = 0; y< heightSquares; ++y){
                Tile tile = new Tile(this, x, y);
                board[x][y] = tile;
            }
        }

        int slashes = 0;
        int p = 0;
        int i;
        for(i = 0; p < heightSquares * widthSquares + slashes; ++i){
            Piece piece = null;
            int x = (p-slashes) % 8;
            int y = (p-slashes) / 8;

            char character = FEN.charAt(i);
            Boolean isWhite = Character.isUpperCase(character);
            character = Character.toUpperCase(character);
            switch (character) {
                case 'B':
                    addPiece(new Bishop(this, x, y, isWhite));
                    break;
                case 'K':
                    King king = new King(this, x, y, isWhite);
                    addPiece(king);
                    if(isWhite)
                        whiteKing = king;
                    else
                        blackKing = king;
                    break;
                case 'N':
                    addPiece(new Knight(this, x, y, isWhite));
                    break;
                case 'P':
                    addPiece(new Pawn(this, x, y, isWhite));
                    break;
                case 'Q':
                    addPiece(new Queen(this, x, y, isWhite));
                    break;
                case 'R':
                    addPiece(new Rook(this, x, y, isWhite));
                    break;
                case '/':
                    slashes++;
                    break;
                default:
                    try {
                        p += Character.getNumericValue(character) - 1;
                    } catch (Exception e) {
                        System.out.println("Bad Character: " + character);
                    }
                    break;
            }
            p++;
        }
        i++;
        isWhiteMove = Character.toUpperCase(FEN.charAt(i)) == 'W';

        ++i;
        Boolean cycle = true;
        while(cycle){
            ++i;
            switch (FEN.charAt(i)) {
                case 'K':
                    whiteKing.setKingRook((Rook) getPiece(7, 7));
                    whiteKing.getKingRook().hasMoved  = false;
                    break;
                case 'Q':
                    whiteKing.setQueenRook((Rook) getPiece(0, 7));
                    whiteKing.getQueenRook().hasMoved  = false;
                    break;
                case 'k':
                    blackKing.setKingRook((Rook) getPiece(7, 0));
                    blackKing.getKingRook().hasMoved  = false;
                    break;
                case 'q':
                    blackKing.setQueenRook((Rook) getPiece(0,0));
                    blackKing.getQueenRook().hasMoved  = false;
                    break;
                case ' ':
                    break;
                default:
                    cycle = false;
                    break;
            }
        }

        int x = (int) FEN.charAt(i);
        targetSquare = null;
        if(x > 96){
            targetSquare = getTile(FEN.substring(i, i+2));
            i++;
        }

        i += 2;
        halfMoveClock = Character.getNumericValue(FEN.charAt(i));
        fullMoveCounter = Character.getNumericValue(FEN.charAt(i+2));
    }

    public Boolean isWhiteMove(){
        return isWhiteMove;
    }

    public Tile getTargetSquare() {
        return targetSquare;
    }

    public static Boolean isValid(int x, int y){
        return(x >= 0 && x < widthSquares && y >= 0 && y < heightSquares);
    }

    /*public ArrayList<Move> getMoves(Boolean forWhite){

        ArrayList<Move> moves = new ArrayList<>();
        Piece piece;
        for(int x = 0; x < widthSquares; ++x)
            for(int y = 0; y < heightSquares; ++y){
                piece = getPiece(x, y);
                if(piece != null && piece.getIsWhite() == forWhite)
                    moves.addAll(piece.getLegalMoves());
            }
        return moves;
    }*/

    public ArrayList<Move> getMoves(Boolean forWhite){
        long startTime, endTime;
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece piece1 : forWhite ? whitePieces : blackPieces) {
            //startTime = System.nanoTime();
            moves.addAll(piece1.getLegalMoves());
            //endTime = System.nanoTime();
            //System.out.printf("%s took %d microseconds\n", piece1.toString(), (endTime - startTime)/1000);
        }
        return moves;
    }

    public ArrayList<Move> getMoves(){
        return getMoves(isWhiteMove);
    }

    public int getTeamAt(int x, int y){
        if(!isValid(x, y))
            throw new IndexOutOfBoundsException(String.format("Invalid Position: (%d, %d)", x, y));
        Piece piece = board[x][y].getPiece();
        if(piece == null)
            return 0;
        else
            return piece.getIsWhite() ? 1 : -1;
    }

    public Piece getPiece(int x, int y){
        if(!isValid(x, y))
            throw new IndexOutOfBoundsException(String.format("Invalid Position: (%d, %d)", x, y));
        return board[x][y].getPiece();
    }

    public Tile getTile(int x, int y){
        return board[x][y];
    }

    public Tile getTile(String str){
        return board[(int) str.charAt(0)-97][8 - Character.getNumericValue(str.charAt(1)) - 1];
    }

    public Deque<Action> getStack(){
        return stack;
    }

    public Action getLastAction(){
        return stack.peek();
    }

    public void addPiece(Piece piece){
        board[piece.getX()][piece.getY()].addPiece(piece);
        (piece.getIsWhite() ? whitePieces : blackPieces).add(piece);
    }

    public Action move(Move move){
        Action action = new Action(this, move);
        Piece initialPiece = null;
        int fx = 0, fy = 0, iy = 0;
        while(move != null){
            initialPiece = move.getInitialPiece();
            fx = move.getFinalX();
            fy = move.getFinalY();

            if(action.getTakenPiece() == null)
                action.setTakenPiece(board[fx][fy].removePiece());
            if(initialPiece != null){
                board[initialPiece.getX()][initialPiece.getY()].removePiece();
                board[fx][fy].addPiece(initialPiece);
                initialPiece.move(fx, fy);
            } else{
                board[fx][fy].removePiece();
            }
            iy = move.getInitialY();
            move = move.getNextMove();
        }

        targetSquare = null;
        if((initialPiece instanceof Pawn) && (Math.abs(fy - iy) == 2)){
            targetSquare = getTile(fx, Math.min(fy, iy) + 1);
        }

        stack.push(action);
        isWhiteMove = !isWhiteMove;
        return action;
    }

    public void unmove(){
        Action action = stack.pop();
        Move move = action.getMove();

        Piece initialPiece = move.getInitialPiece();

        unmove(move);
        Piece takenPiece = action.getTakenPiece();
        if(takenPiece != null)
            board[takenPiece.getX()][takenPiece.getY()].addPiece(takenPiece);

        targetSquare = action.getTargetSquare();
        halfMoveClock = action.getHalfMoveClock();

        if(action.isChangedCastleStatus()){
            initialPiece.reverseMove();
        }
        
        isWhiteMove = !isWhiteMove;
    }

    public void unmove(Move move){
        int ix = move.getInitialX();
        int iy = move.getInitialY();
        int fx = move.getFinalX();
        int fy = move.getFinalY();
        Piece initialPiece = move.getInitialPiece();

        if(move.getNextMove() != null){
            unmove(move.getNextMove());
        }
        board[fx][fy].removePiece();
        board[ix][iy].addPiece(initialPiece);
    }

    public King getKing(Boolean isWhite){
        return isWhite ? whiteKing : blackKing;
    }

    public Boolean isInCheck(Boolean isWhite){
        Piece piece;
        for(int x = 0; x < widthSquares; ++x)
            for(int y = 0; y < heightSquares; ++y){
                piece = getPiece(x, y);
                if(piece != null && piece.getIsWhite() != isWhite)
                    if(piece.isChecking()){
                        return true;
                    }
            }

        return false;
    }

    @Override
    public String toString(){
        Piece piece;
        int emptyCounter = 0;
        String str = "";
        for(int y = 0; y < heightSquares; ++y){
            for(int x = 0; x < widthSquares; ++x){
                piece = getPiece(x, y);
                if(piece == null){
                    emptyCounter++;
                } else {
                    if(emptyCounter > 0)
                        str += emptyCounter;
                    emptyCounter = 0;
                    str += piece.notation();
                }
            }
            if(emptyCounter > 0)
                str += emptyCounter;
            emptyCounter = 0;
            str += "/";
        }

        str = str.substring(0, str.length()-1) + " " + (isWhiteMove ? "w" : "b") + " ";

        boolean castle = false;
        if(!whiteKing.getHasMoved()){
            if(!whiteKing.getKingRook().getHasMoved()){
                castle = true;
                str += "K";
            } if(!whiteKing.getQueenRook().getHasMoved()){
                castle = true;
                str += "Q";
            }
        } if(!blackKing.getHasMoved()){
            if(!blackKing.getKingRook().getHasMoved()){
                castle = true;
                str += "k";
            } if(!blackKing.getQueenRook().getHasMoved()){
                castle = true;
                str += "q";
            }
        }

        if(castle)
            str += " ";

        str += targetSquare == null ? "-" : targetSquare.toString();
        str += " ";

        str += halfMoveClock + " " + fullMoveCounter;

        return str;
    }

    /*public void validateMoves(ArrayList<Move> moves){
        for(int i = 0; i < moves.size(); ++i){
            Move move = moves.get(i);
            int[] finalPosition = move.getFinalPosition();
            int[] initialPosition = move.getInitialPosition();


            board[initialPosition[0]][initialPosition[1]].setPiece(null);
            board[finalPosition[0]][finalPosition[1]].setPiece(move.getPiece());

            ArrayList<Move> oppMoves = getMoves(!move.getPiece().getIsWhite());
            Piece king = move.getPiece().getIsWhite() ? blackKing : whiteKing;
            for(int j = 0; j < oppMoves.size(); ++j){
                if(oppMoves.get(j).getFinalPosition().equals(king.getPosition()))

            }
        }
    }*/
}
