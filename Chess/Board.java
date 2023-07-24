package Chess;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import Chess.Pieces.*;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebHistory;

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
    private Deque<Move> stack;

    public Board(int initialX, int initialY, int width, int height){
        this.initialX = initialX;
        this.initialY = initialY;
        this.width = width;
        this.height = height;
        isWhiteMove = true;
        stack = new ArrayDeque<>();

        board = new Tile[widthSquares][heightSquares];
        buttonGrid = new GridPane();
        buttonGrid.setLayoutX(initialX);
        buttonGrid.setLayoutY(initialY);
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setPrefSize(8*20, 8*20);

        for(int x = 0; x < widthSquares; ++x){
            for(int y = 0; y< heightSquares; ++y){
                Tile tile = new Tile(this, width/widthSquares, height/heightSquares, x, y);
                buttonGrid.add(tile.getStackPane(), x, y);
                board[x][y] = tile;
            }
        }

        //Setting up a traditional board
        for(int i = 0; i < widthSquares; ++i){
            Piece piece = new Pawn(this, i, 6, true);
            board[i][6].addPiece(piece);
        }

        for(int i = 0; i < widthSquares; ++i){
            Piece piece = new Pawn(this, i, 1, false);
            board[i][1].addPiece(piece);
        }

        board[0][7].addPiece(new Rook(this, 0, 7, true));
        board[7][7].addPiece(new Rook(this, 7, 7, true));
        board[7][0].addPiece(new Rook(this, 7, 0, false));
        board[0][0].addPiece(new Rook(this, 0, 0, false));

        board[1][7].addPiece(new Knight(this, 1, 7, true));
        board[6][7].addPiece(new Knight(this, 6, 7, true));
        board[1][0].addPiece(new Knight(this, 1, 0, false));
        board[6][0].addPiece(new Knight(this, 6, 0, false));

        board[2][7].addPiece(new Bishop(this, 2, 7, true));
        board[5][7].addPiece(new Bishop(this, 5, 7, true));
        board[2][0].addPiece(new Bishop(this, 2, 0, false));
        board[5][0].addPiece(new Bishop(this, 5, 0, false));

        board[3][7].addPiece(new Queen(this, 3, 7, true));
        board[3][0].addPiece(new Queen(this, 3, 0, false));

        board[4][7].addPiece(new King(this, 4, 7, true));
        whiteKing = (King) getPiece(4, 7);
        board[4][0].addPiece(new King(this, 4, 0, false));
        blackKing = (King) getPiece(4, 0);
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

    public Tile getTile(int[] coords){
        return board[coords[0]][coords[1]];
    }

    public Deque<Move> getStack(){
        return stack;
    }

    public Move getLastMove(){
        return stack.peek();
    }

    public int[] pixelToTileCoords(double pixelX, double pixelY){
        int tileX = (int) (pixelX - initialX) / (width/widthSquares);
        int tileY = (int) (pixelY - initialY) / (height/heightSquares);
        return new int[] {tileX, tileY};
    }

    public void addPiece(Piece piece){
        int[] position = piece.getPosition();
        board[position[0]][position[1]].addPiece(piece);
    }

    public void move(Move move){
        stack.push(move);
        while(move != null){
            int ix = move.getInitialPosition()[0];
            int iy = move.getInitialPosition()[1];
            int fx = move.getFinalPosition()[0];
            int fy = move.getFinalPosition()[1];
            Piece piece = move.getInitialPiece();

            board[ix][iy].removePiece();
            board[fx][fy].removePiece();
            if(piece != null){
                board[fx][fy].addPiece(piece);
                piece.move(fx, fy);
            }
            move = move.getNextMove();
        }
        isWhiteMove = !isWhiteMove;
        /*System.out.println(getMoves(isWhiteMove).size());
        if(getMoves(isWhiteMove).size() == 2){
            for(int i = 0; i<2; i++){
                System.out.println(getMoves(isWhiteMove).get(i));
            }
        }*/
    }

    public void unmove(){ //Does not account for "special" moves yet
        Move move = stack.pop();
        if(move.getNextMove() != null)
            unmove(move.getNextMove());
            
        int ix = move.getInitialPosition()[0];
        int iy = move.getInitialPosition()[1];
        int fx = move.getFinalPosition()[0];
        int fy = move.getFinalPosition()[1];
        Piece initialPiece = move.getInitialPiece();
        Piece finalPiece = move.getFinalPiece();

        board[fx][fy].removePiece();
        if(initialPiece != null){
            board[ix][iy].addPiece(initialPiece);
            initialPiece.move(ix, iy);
        }
        if(finalPiece != null)
            board[fx][fy].addPiece(finalPiece);
        if(move.getChangedMoveStatus()){
            initialPiece.reverseMove();
        }
        
        isWhiteMove = !isWhiteMove;
    }

    public void unmove(Move move){
        if(move.getNextMove() != null)
            unmove(move.getNextMove());
            
        int ix = move.getInitialPosition()[0];
        int iy = move.getInitialPosition()[1];
        int fx = move.getFinalPosition()[0];
        int fy = move.getFinalPosition()[1];
        Piece initialPiece = move.getInitialPiece();
        Piece finalPiece = move.getFinalPiece();

        board[fx][fy].removePiece();
        if(initialPiece != null){
            board[ix][iy].addPiece(initialPiece);
            initialPiece.move(ix, iy);
        }
        if(finalPiece != null)
            board[fx][fy].addPiece(finalPiece);
        if(move.getChangedMoveStatus()){
            initialPiece.reverseMove();
        }
        
        //isWhiteMove = !isWhiteMove;
    }

    public int getInitialX(){
        return initialX;
    }

    public int getInitialY(){
        return initialY;
    }

    public void switchTiles(int x1, int y1, int x2, int y2){
        Tile tmp = getTile(new int[] {x1, y1});
        board[x1][y1] = getTile(new int[] {x2, y2});
        board[x2][y2] = tmp;
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
