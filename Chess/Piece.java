package Chess;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public abstract class Piece {
    static double mouseIX;
    static double mouseIY;
    static double pieceIX;
    static double pieceIY;
    protected Boolean isWhite;
    protected Board board;
    protected int xPosition;
    protected int yPosition;
    protected ImageView graphic;

    public Piece(Board board, int x, int y, Boolean isWhite){
        this.board = board;
        xPosition = x;
        yPosition = y;
        this.isWhite = isWhite;

        graphic = new ImageView();
        graphic.setPreserveRatio(true);

        graphic.setOnMousePressed(e -> {
            if(board.isWhiteMove() == isWhite){
                board.getTile(new int[] {this.xPosition, this.yPosition}).toFront();
                mouseIX = e.getSceneX();
                mouseIY = e.getSceneY();
                pieceIX = graphic.getTranslateX();
                pieceIY = graphic.getTranslateY();
                ArrayList<Move> moves = getLegalMoves();
                for(int i = 0; i < moves.size(); ++i){
                    Move move = moves.get(i);
                    int[] coords = move.getFinalPosition();
                    board.getTile(coords).addCursor();
                }
            }
        });

        graphic.setOnMouseDragged(e -> {
            if(board.isWhiteMove() == isWhite){
                graphic.setTranslateX(pieceIX + e.getSceneX() - mouseIX);
                graphic.setTranslateY(pieceIY + e.getSceneY() - mouseIY);
            }
        });

        graphic.setOnMouseReleased(e -> { //Redundant, may want to revise how it calls getMoves() again everytime
            if(board.isWhiteMove() == isWhite){
                graphic.setTranslateX(0);
                graphic.setTranslateY(0);

                ArrayList<Move> moves = getLegalMoves();
                for(int i = 0; i < moves.size(); ++i){
                    Move move = moves.get(i);
                    int[] coords = move.getFinalPosition();
                    board.getTile(coords).removeCursor();
                }

                int[] finalPosition = board.pixelToTileCoords(e.getSceneX(), e.getSceneY());
                if(Board.isValid(finalPosition[0], finalPosition[1])){
                    Move move = new Move(board, getPosition(), finalPosition);
                    for(int i = 0; i < moves.size(); ++i){
                        if(move.equals(moves.get(i))){
                            board.move(moves.get(i));
                            break;
                        }
                    }
                }
            }
        });
    }

    public Boolean getIsWhite(){
        return isWhite;
    }

    public int[] getPosition(){
        return new int[] {xPosition, yPosition};
    }

    public void setPosition(int x, int y){
        xPosition = x;
        yPosition = y;
    }

    public ArrayList<Move> getLegalMoves(){
        return null;
    }

    public ImageView getGraphic(){
        return graphic;
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
        Piece takenPiece = board.getPiece(x, y);
        Move move = new Move(board, new int[] {xPosition, yPosition}, new int[] {x, y});
        board.move(move);

        // getMoves() for other color
        if(board.isInCheck(isWhite)){
            board.unmove();
            return;
        }
        // switch tiles back
        board.unmove();

        moves.add(new Move(board, new int[] {xPosition, yPosition}, new int[] {x, y}));
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
