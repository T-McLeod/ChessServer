package Chess;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Tile {
    private final static Image primaryColorTile = new Image("./Chess/Assets/Tiles/DarkBrown.png");
    private final static Image secondaryColorTile = new Image("./Chess/Assets/Tiles/LightBrown.png");
    private final static Image cursorIMG = new Image("./Chess/Assets/dot.png");

    private Boolean isPrimary;
    private Boolean isOccupied;
    private Board board;
    private int width;
    private int height;
    private int x;
    private int y;
    private Piece piece;
    private StackPane stack;
    private ImageView graphic;
    private ImageView cursor;
    

    public Tile(Board board, int width, int height, int x, int y){
        this.board = board;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        isPrimary = ((x + y) % 2) == 0;
        isOccupied = false;

        Image img = isPrimary ? primaryColorTile : secondaryColorTile;
        graphic = new ImageView(img);
        graphic.setPreserveRatio(true);
        graphic.setFitWidth(width);
        graphic.setFitHeight(height);
        stack = new StackPane(graphic);

        cursor = new ImageView(cursorIMG);
        cursor.setPreserveRatio(true);
        cursor.setFitWidth(width/4);
        cursor.setFitHeight(height/4);
        cursor.setOpacity(.5);
    }

    public Boolean getIsOccupied(){
        return isOccupied;
    }

    public int[] getPosition(){
        return(new int[] {x, y});
    }

    public Piece getPiece(){
        return piece;
    }

    public StackPane getStackPane(){
        return stack;
    }

    public void addPiece(Piece piece){
        isOccupied = true;
        this.piece = piece;
        piece.setPosition(x, y);

        piece.getGraphic().setFitWidth(width/4*3);
        piece.getGraphic().setFitHeight(height/4*3);

        stack.getChildren().add(piece.getGraphic());
    }

    public void addCursor(){
        stack.getChildren().add(this.cursor);
    }

    public void removeCursor(){
        stack.getChildren().remove(cursor);
    }

    public void removePiece(){
        if(!isOccupied)
            return;
        stack.getChildren().remove(piece.getGraphic());
        piece = null;
        isOccupied = false;
    }

    public void toFront(){
        stack.toFront();
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }

    @Override
    public String toString(){
        return(String.format("%s at (%d, %d)", piece, x, y));
    }

}
