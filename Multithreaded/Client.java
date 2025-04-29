package Multithreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    int port = 8010;
                    InetAddress address = InetAddress.getByName("localhost"); // gets the ip address
                    System.out.println(address);
                    Socket socket = new Socket(address, port);
                    BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                    toServer.println("Hello from client");
                    String responseFromServer = fromServer.readLine();
                    System.out.println("Response from server : " + responseFromServer);
                    toServer.close();
                    fromServer.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();
        for(int i = 0 ; i < 100 ; i++){
            try{
                Thread thread = new Thread(client.getRunnable());
                thread.start();
                Thread.sleep(10);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
