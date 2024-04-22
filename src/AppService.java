package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.net.ServerSocket;
import java.net.Socket;

public class AppService {
    
    private static ServerSocket serverSocket;
    private static Socket socket;

    private static BufferedReader in;
    private static BufferedWriter out;

    public static void register() {

        try {

            socket = new Socket("localhost", 8080);

        } catch (Exception e) {
            
            e.printStackTrace();

        }

    }
}
