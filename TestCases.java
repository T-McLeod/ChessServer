import Chess.Board;
import Chess.Move;

import org.junit.Test;

public class TestCases {
    @Test
    public void givenClient1_whenServerResponds_thenCorrect() {
        Board board = new Board();
        ChessClient client1 = new ChessClient();
        client1.startConnection("127.0.0.1", 8888);
        Move moveBack = client1.sendMove(board.getMoves().get(0));
    }
}
