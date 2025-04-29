import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter; 

class Server {

    public void run() throws IOException{
        int port = 8010;
        while(true){
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                serverSocket.setSoTimeout(10000);
                System.out.println("Waiting for connection... on port " + port);
                Socket acceptedConnection = serverSocket.accept(); // will close after 10 sec if no connection is estabished
                System.out.println("Accepted connection from " + acceptedConnection.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
                BufferedReader fromClient = new BufferedReader(new InputStreamReader( acceptedConnection.getInputStream()));
                toClient.println("Hello form the server");
                toClient.close();
                fromClient.close();
                serverSocket.close();
                acceptedConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args){
        // run method is not static so 
        Server server  = new Server();
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();    
        }
    }
}