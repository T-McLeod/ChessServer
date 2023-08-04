package Chess;

import java.util.ArrayList;

public class Game {
    private Board board;
    private float timeWhite;
    private float timeBlack;

    public Game(){
        Long startTime, split1, endTime;

        board = new Board("rnbq1rk1/2p2ppp/p4n2/1p1pp3/1b2P3/2NPBN2/PPP1BPPP/R2Q1RK1 w - 0 1");
    }

    public Board getBoard(){
        return board;
    }
}
