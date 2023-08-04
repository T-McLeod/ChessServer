package Chess;

import java.util.ArrayList;

public class Tile {
    //private final static Image primaryColorTile = new Image("./Chess/Assets/Tiles/DarkBrown.png");
    //private final static Image secondaryColorTile = new Image("./Chess/Assets/Tiles/LightBrown.png");
    //private final static Image cursorIMG = new Image("./Chess/Assets/dot.png");

    private Boolean isOccupied;
    private Board board;
    private int x;
    private int y;
    private Piece piece;
    

    public Tile(Board board, int x, int y){
        this.board = board;
        this.x = x;
        this.y = y;
        isOccupied = false;
    }

    public Boolean getIsOccupied(){
        return isOccupied;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Piece getPiece(){
        return piece;
    }

    public void addPiece(Piece piece){
        isOccupied = true;
        this.piece = piece;
        piece.setPosition(x, y);
    }

    public Piece removePiece(){
        Piece tmp = piece;
        piece = null;
        isOccupied = false;
        return tmp;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    @Override
    public String toString(){
        return(String.format("%c%d", (char) (x+97), 8-y));
    }

}
