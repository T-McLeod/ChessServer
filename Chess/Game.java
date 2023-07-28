package Chess;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Game {
    private Board board;
    private float timeWhite;
    private float timeBlack;
    private Pane layout;

    public Game(){
        Long startTime, endTime;
        layout = new Pane();

        //board = new Board("rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d5 0 1");
        board = new Board();
        //System.out.println(board.getLastMove());

        /*System.out.println("Run 2: ");
        startTime = System.nanoTime();
        board = new Board(200, 200, 500, 500);
        endTime = System.nanoTime();
        System.out.println(String.format("New Board: %d miliseconds\n", (endTime-startTime)/1000000));

        System.out.println("Run 3: ");
        startTime = System.nanoTime();
        board = new Board(200, 200, 500, 500, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2");  
        endTime = System.nanoTime();
        System.out.println(String.format("Import Board: %d miliseconds\n", (endTime-startTime)/1000000));
        */

        //System.out.println(board);
        //layout.setLayoutX(board.getInitialX());
        //layout.setLayoutY(board.getInitialY());

        Rectangle banner = new Rectangle(0, 0, board.getWidth(), 60);
        banner.setFill(Color.LIGHTBLUE);

        StackPane blackPlayer = new StackPane(banner);
        blackPlayer.setLayoutX(board.getInitialX());
        blackPlayer.setLayoutY(board.getInitialY() - 60);

        //layout.getChildren().add(blackPlayer);
    }

    public Pane display(){
        layout.getChildren().add(board.display(200, 200, 500, 500));
        return layout;
    }

    public Pane getLayout(){
        return layout;
    }
}
