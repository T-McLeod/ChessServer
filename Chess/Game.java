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
        layout = new Pane();
        board = new Board(200, 200, 500, 500);
        layout.getChildren().add(board.getButtons());
        //layout.setLayoutX(board.getInitialX());
        //layout.setLayoutY(board.getInitialY());

        Rectangle banner = new Rectangle(0, 0, board.getWidth(), 60);
        banner.setFill(Color.LIGHTBLUE);

        StackPane blackPlayer = new StackPane(banner);
        blackPlayer.setLayoutX(board.getInitialX());
        blackPlayer.setLayoutY(board.getInitialY() - 60);

        //layout.getChildren().add(blackPlayer);
    }

    public Pane getLayout(){
        return layout;
    }
}
