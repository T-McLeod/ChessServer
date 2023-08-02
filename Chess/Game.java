package Chess;

import java.util.ArrayList;

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
        Long startTime, split1, endTime;
        layout = new Pane();

        board = new Board("rnbq1rk1/2p2ppp/p4n2/1p1pp3/1b2P3/2NPBN2/PPP1BPPP/R2Q1RK1 w - 0 1");
        board.getMoves();
        System.out.println("Run: ");
        ArrayList<Move> moves;
        int totalMovesChecked = 0;
        startTime = System.nanoTime();
        board = new Board();
        board.getMoves();
        board.move(new Move(board.getPiece(4, 6), 4, 4));
        /*split1 = System.nanoTime();
        board.getMoves();
        for(int i = 0; i < 100; ++i){
            moves = board.getMoves();
            for (Move move : moves) {
                board.move(move);
                board.unmove();
            }
            board.move(moves.get(0));
            totalMovesChecked += moves.size();
        }*/
        endTime = System.nanoTime();
        //System.out.println(String.format("  New Board: %d microseconds\n", (split1-startTime)/1000));
        //System.out.println(String.format("  Checked 100 Positions (AVG Moves: %d): %d microseconds\n", totalMovesChecked/100, (endTime-split1)/1000));

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
