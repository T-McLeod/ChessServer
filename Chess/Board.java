package Chess;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import Chess.Pieces.*;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebHistory;
import java.lang.*;

public class Board {
    final public static int widthSquares = 8;
    final public static int heightSquares = 8;
    private int initialX;
    private int initialY;
    private int width;
    private int height;
    private Tile[][] board;
    private Boolean isWhiteMove;
    private GridPane buttonGrid;
    private King whiteKing;
    private King blackKing;
    private Deque<Action> stack;
    private int halfMoveClock; //unused
    private int fullMoveCounter; //unused
    private Tile targetSquare;

    public Board(){
        this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public Board(String FEN){
        long startTime, endTime;
        buttonGrid = new GridPane();

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
                    piece = new Bishop(this, x, y, isWhite);
                    break;
                case 'K':
                    piece = new King(this, x, y, isWhite);
                    if(isWhite)
                        whiteKing = (King) piece;
                    else
                        blackKing = (King) piece;
                    break;
                case 'N':
                    piece = new Knight(this, x, y, isWhite);
                    break;
                case 'P':
                    piece = new Pawn(this, x, y, isWhite);
                    break;
                case 'Q':
                    piece = new Queen(this, x, y, isWhite);
                    break;
                case 'R':
                    piece = new Rook(this, x, y, isWhite);
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
            
            if(piece != null)
                board[x][y].addPiece(piece);
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
        if(x > 96){
            //stack.push(new Move(this, new int[] {1,1}, new int[]{x-97, 8 - Character.getNumericValue(FEN.charAt(++i))}));
        } else{
            //stack.push(new Move(this, new int[] {1,1}, new int[]{1,1}));
        }

        i += 2;
        halfMoveClock = FEN.charAt(i);
        fullMoveCounter = FEN.charAt(i+2);
    }

    public GridPane display(int initialX, int initialY, int width, int height){
        this.initialX = initialX;
        this.initialY = initialY;
        this.width = width;
        this.height = height;

        buttonGrid = new GridPane();
        buttonGrid.setLayoutX(initialX);
        buttonGrid.setLayoutY(initialY);
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setPrefSize(8*20, 8*20);

        Tile tile;
        for(int x = 0; x < widthSquares; ++x){
            for(int y = 0; y< heightSquares; ++y){
                tile = board[x][y];
                tile.display(width/widthSquares, height/heightSquares);
                buttonGrid.add(tile.getStackPane(), x, y);
            }
        }

        return buttonGrid;
    }

    public GridPane getButtons(){
        return buttonGrid;
    }

    public Boolean isWhiteMove(){
        return isWhiteMove;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Tile getTargetSquare() {
        return targetSquare;
    }

    public static Boolean isValid(int x, int y){
        return(x >= 0 && x < widthSquares && y >= 0 && y < heightSquares);
    }

    public ArrayList<Move> getMoves(Boolean forWhite){
        ArrayList<Move> moves = new ArrayList<>();
        Piece piece;
        for(int x = 0; x < widthSquares; ++x)
            for(int y = 0; y < heightSquares; ++y){
                piece = getPiece(x, y);
                if(piece != null && piece.getIsWhite() == forWhite)
                    moves.addAll(piece.getLegalMoves());
            }
        
        return moves;
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

    public Deque<Action> getStack(){
        return stack;
    }

    public Action getLastAction(){
        return stack.peek();
    }

    public int[] pixelToTileCoords(double pixelX, double pixelY){
        int tileX = (int) (pixelX - initialX) / (width/widthSquares);
        int tileY = (int) (pixelY - initialY) / (height/heightSquares);
        return new int[] {tileX, tileY};
    }

    public void addPiece(Piece piece){
        board[piece.getX()][piece.getY()].addPiece(piece);
    }

    public Action move(Move move){
        Action action = new Action(this, move);
        Piece initialPiece = null;
        int fx = 0, fy = 0;
        while(move != null){
            initialPiece = move.getInitialPiece();
            fx = move.getFinalX();
            fy = move.getFinalY();

            board[fx][fy].removePiece();
            if(initialPiece != null){
                board[initialPiece.getX()][initialPiece.getY()].removePiece();
                board[fx][fy].addPiece(initialPiece);
                initialPiece.move(fx, fy);
            }
            move = move.getNextMove();
        }

        targetSquare = null;
        int iy = action.getInitialSquare().getY();
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
        
        Tile initialSquare = action.getInitialSquare();
        int ix = initialSquare.getX();
        int iy = initialSquare.getY();
        int fx = move.getFinalX();
        int fy = move.getFinalY();
        Piece initialPiece = move.getInitialPiece();
        Piece takenPiece = action.getTakenPiece();

        if(move.getNextMove() != null){
            unmove(action);
            takenPiece = null;
        };

        board[fx][fy].removePiece();
        if(initialPiece != null){
            board[ix][iy].addPiece(initialPiece);
            initialPiece.move(ix, iy);
        }
        if(takenPiece != null)
            board[fx][fy].addPiece(takenPiece);

        targetSquare = action.getTargetSquare();
        halfMoveClock = action.getHalfMoveClock();

        if(action.isChangedCastleStatus()){
            initialPiece.reverseMove();
        }
        
        isWhiteMove = !isWhiteMove;
    }

    public void unmove(Action action){
        Move move = action.getMove().getNextMove();
        Piece takenPiece = action.getTakenPiece();
        if(move.getNextMove() != null){
            unmove(action);
            takenPiece = null;
        }
            
        Tile initialSquare = action.getInitialSquare();
        int ix = initialSquare.getX();
        int iy = initialSquare.getY();
        int fx = move.getFinalX();
        int fy = move.getFinalY();
        Piece initialPiece = move.getInitialPiece();

        board[fx][fy].removePiece();
        if(initialPiece != null){
            board[ix][iy].addPiece(initialPiece);
            initialPiece.move(ix, iy);
        }
        if(takenPiece != null)
            board[fx][fy].addPiece(takenPiece);
        
        //isWhiteMove = !isWhiteMove;
    }

    public void showAction(Action action){
        Move move = action.getMove();
        Tile initialSquare = action.getInitialSquare();
        while(move != null){

            board[initialSquare.getX()][initialSquare.getY()].updateDisplay();
            board[move.getFinalX()][move.getFinalY()].updateDisplay();
            move = move.getNextMove();
        }
        System.out.println(targetSquare);
    }

    public int getInitialX(){
        return initialX;
    }

    public int getInitialY(){
        return initialY;
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

        str = str.substring(0, str.length()-1) + " " + (isWhiteMove ? "w" : "b");
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
