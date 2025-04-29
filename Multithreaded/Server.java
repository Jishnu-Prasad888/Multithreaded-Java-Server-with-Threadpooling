package Multithreaded;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return (clientSocket)->{
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println("Hello from server");
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String responsefromClient = fromClient.readLine(); 
                System.out.println(responsefromClient);
                fromClient.close();
                toClient.close();
                clientSocket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.err.println("Server i listening on " + port);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Recieved Shutdown signal");
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

            while (true) {
                try {
                    Socket acceptedConnection = serverSocket.accept();
                    Thread thread = new Thread(() -> server.getConsumer().accept(acceptedConnection));
                    thread.start();
                } catch (SocketTimeoutException e) {
                    System.out.println("Socket connection time out");
                }
            }
        } catch (IOException e) {
            System.out.println("");
            e.printStackTrace();
        }
    }
}
