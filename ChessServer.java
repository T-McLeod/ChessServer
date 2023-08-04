import java.net.*;

import Chess.*;

import java.io.*;

public class ChessServer {
    private ServerSocket serverSocket;

    public void start(int port) {
        try{
            serverSocket = new ServerSocket(port);
            while(true)
                new ChessClient(serverSocket.accept()).start();
        } catch(IOException e){
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void stop() {
        try{
            serverSocket.close();
        } catch(IOException e){
            System.out.println(e);
        }
    }

    private static class ChessClient extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private Game game;

        public ChessClient(Socket socket){
            this.clientSocket = socket;
            game = new Game();

            System.out.println("Client Found: " + socket.toString());
        }

        public void run(){
            Board board = game.getBoard();
            MoveExchange inputMove;
            System.out.println("Waiting");
            try{
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                while((inputMove = (MoveExchange) in.readObject()) != null){
                    Move move = inputMove.toMove(board);
                    board.move(move);
                    out.writeObject(new MoveExchange(board.getMoves().get(0)));
                }
            } catch(IOException e){
                System.err.println(e);
            } catch (ClassNotFoundException e){
                System.err.println(e);
            }
        }
    }

}
