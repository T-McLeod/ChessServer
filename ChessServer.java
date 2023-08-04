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

        public ChessClient(Socket socket){
            this.clientSocket = socket;

            System.out.println("Client Found: " + socket.toString());
        }

        public void run(){
            Board board = new Game().getBoard();
            MoveExchange inputMove;
            System.out.println("Waiting");
            try{
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                while((inputMove = (MoveExchange) in.readObject()) != null){
                    Move move = inputMove.toMove(board);
                    System.out.println("Move: " + move);
                    out.writeObject(new MoveExchange(board.getMoves().get(0)));
                }

                
            } catch(IOException e){
                System.err.println(e);
            } catch (ClassNotFoundException e){
                System.err.println(e);
            }
        }
    }

    public static void main(String[] args) {
        ChessServer server=new ChessServer();
        server.start(8888
        );
    }

}
