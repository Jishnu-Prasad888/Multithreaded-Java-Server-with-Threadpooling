import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public void run() throws IOException , UnknownHostException {
        int port = 8010;
        InetAddress address = InetAddress.getByName("localhost"); // gets the ip address
        System.out.println(address);
        Socket socket = new Socket(address, port);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter toServer = new PrintWriter(socket.getOutputStream());
        toServer.println("Hello from client");
        String responseFromServer = fromServer.readLine();
        System.out.println("Response from server : " + responseFromServer);
        toServer.close();
        fromServer.close();
        socket.close();
    }

    public static void main(String [] args){
        try{
            Client client = new Client();
            client.run();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
