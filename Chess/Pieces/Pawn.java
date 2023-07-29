package Chess.Pieces;

import Chess.Board;
import Chess.Piece;
import Chess.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import Chess.Move;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.Position;

public class Pawn extends Piece{
    private static final Image whiteIMG = new Image("./Chess/Assets/Pieces/White/Pawn.png");
    private static final Image blackIMG = new Image("./Chess/Assets/Pieces/Black/Pawn.png");

    public Pawn(Board board, int x, int y, Boolean isWhite){
        super(board, x, y, isWhite);
    }

    @Override
    public Image getImage(){
        return isWhite ? whiteIMG : blackIMG;
    }

    @Override
    public ArrayList<Move> getLegalMoves(){
        ArrayList<Move> moves = new ArrayList<>();

        int yDirection = isWhite ? -1 : 1;

        if(Board.isValid(xPosition, yPosition + yDirection) && board.getTeamAt(xPosition, yPosition + yDirection) == 0){
            addMove(moves, xPosition, yPosition + yDirection);
            if(yPosition == (isWhite ? 6 : 1) && board.getTeamAt(xPosition, yPosition + yDirection*2) == 0){
                addMove(moves, xPosition, yPosition + yDirection * 2);
            }
        }

        if(Board.isValid(xPosition-1, yPosition + yDirection)){
            Tile tile = board.getTile(xPosition-1, yPosition + yDirection);
            if(tile.getIsOccupied() && tile.getPiece().getIsWhite() != isWhite){
                this.addMove(moves, xPosition-1, yPosition + yDirection);
            }
            if(board.getTargetSquare() == tile){
                Move newMove = new Move(this, xPosition-1, yPosition + yDirection);
                newMove.setNextMove(new Move(null, xPosition-1, yPosition));
                addMove(moves, newMove);
            }
        }

        if(Board.isValid(xPosition+1, yPosition + yDirection)){
            Tile tile = board.getTile(xPosition+1, yPosition + yDirection);
            if(tile.getIsOccupied() && tile.getPiece().getIsWhite() != isWhite){
                addMove(moves, xPosition+1, yPosition + yDirection);
            }
            if(board.getTargetSquare() == tile){
                Move newMove = new Move(this, xPosition+1, yPosition + yDirection);
                newMove.setNextMove(new Move(null, xPosition+1, yPosition));
                addMove(moves, newMove);
            }
        }

        return moves;

    }

    @Override
    public void move(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
    }

    @Override
    public Boolean isChecking(){
        ArrayList<Move> moves = new ArrayList<>();

        int yDirection = isWhite ? -1 : 1;
        King king = board.getKing(!isWhite);

        if(Board.isValid(xPosition-1, yPosition + yDirection) && board.getPiece(xPosition-1, yPosition + yDirection) == king)
            return true;

        if(Board.isValid(xPosition+1, yPosition + yDirection) && board.getPiece(xPosition+1, yPosition + yDirection) == king)
            return true;

        return false;
    }

    @Override
    public void addMove(ArrayList<Move> moves, int fx, int fy){
        System.out.println("Spot 1: " + board.getPiece(6, 7));
        Move move = new Move(this, fx, fy);
        if(fy == 7 || fy == 0){
            Move copy = move.copy();
            copy.setNextMove(new Move(new Queen(board, fx, fy, isWhite), fx, fy));
            super.addMove(moves, copy);
            System.out.println("Spot 2: " + board.getPiece(6, 7));

            copy = move.copy();
            move.copy().setNextMove(new Move(new Knight(board, fx, fy, isWhite), fx, fy));
            super.addMove(moves, copy);

            copy = move.copy();
            move.copy().setNextMove(new Move(new Rook(board, fx, fy, isWhite), fx, fy));
            super.addMove(moves, copy);

            move.setNextMove(new Move(new Bishop(board, fx, fy, isWhite), fx, fy));
        }
        super.addMove(moves, move);
    }

    public void promptForPromotion(Move move){
        int x = move.getFinalX(), y = move.getFinalY();
        board.showAction(board.move(move));
        Tile[] affectedTiles = new Tile[5];
        int yDirection = isWhite ? -1 : 1;
        for(int i = 0; i < 5; i++){
            affectedTiles[i] = board.getTile(x, y + -1 * i * yDirection);
        }

        //Piece initialPiece = move.getInitialPiece();
        //int ix = initialPiece.getX(), int iy
        ImageView queenProm = new ImageView(Queen.getImage(isWhite));
        queenProm.setOnMousePressed(e -> {
            board.unmove();
            move.setNextMove(new Move(new Queen(board, x, y, isWhite), x, y));
            board.showAction(board.move(move));
            for(Tile tile : affectedTiles){
                tile.updateDisplay();
            }
        });
        affectedTiles[1].addImageView(queenProm);

        ImageView knightProm = new ImageView(Knight.getImage(isWhite));
        knightProm.setOnMousePressed(e -> {
            board.unmove();
            move.setNextMove(new Move(new Knight(board, x, y, isWhite), x, y));
            board.showAction(board.move(move));
            for(Tile tile : affectedTiles){
                tile.updateDisplay();
            }
        });
        affectedTiles[2].addImageView(knightProm);

        ImageView rookProm = new ImageView(Rook.getImage(isWhite));
        rookProm.setOnMousePressed(e -> {
            board.unmove();
            move.setNextMove(new Move(new Rook(board, x, y, isWhite), x, y));
            board.showAction(board.move(move));
            for(Tile tile : affectedTiles){
                tile.updateDisplay();
            }
        });
        affectedTiles[3].addImageView(rookProm);

        ImageView bishopProm = new ImageView(Bishop.getImage(isWhite));
        bishopProm.setOnMousePressed(e -> {
            board.unmove();
            move.setNextMove(new Move(new Bishop(board, x, y, isWhite), x, y));
            board.showAction(board.move(move));
            for(Tile tile : affectedTiles){
                tile.updateDisplay();
            }
        });
        affectedTiles[4].addImageView(bishopProm);

    }

    @Override
    public String toString(){
        return("("+ xPosition + ", " + yPosition + ")");
    }

    @Override
    public String notation(){
        return(isWhite ? "P" : "p");
    }
}
