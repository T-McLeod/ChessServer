import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Chess.Move;
import Chess.MoveExchange;

public class ChessClient {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void startConnection(String ip, int port){
        try{
            clientSocket = new Socket(ip, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch(IOException e){
            System.err.println(e);
        }
    }

    public Move sendMove(Move move){
        MoveExchange resp = null;
        try{
            out.writeObject(new MoveExchange(move));
            resp = (MoveExchange) in.readObject();
        } catch(IOException e){
            System.err.println(e);
        } catch(ClassNotFoundException e){
            System.err.println(e);
        }
        return null;
    }
}
