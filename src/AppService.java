package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class AppService {

    private final static String NAME = "temperature_converter";
    private final static String HOST = "localhost"; 
    private final static int PORT = 8081;
    
    private static ServerSocket serverSocket;
    private static Socket socket;

    private static BufferedReader in;
    private static BufferedWriter out;

    public static void register() {

        try {

            socket = new Socket("localhost", 8080);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write(NAME);
            out.newLine();

            out.write(HOST);
            out.newLine();

            out.write(PORT);
            out.newLine();

            socket.close();

        } catch (Exception e) {
            
            e.printStackTrace();

        }

    }

    public static void serviceMenu() {

        try {

        } catch(Exception e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args) {

        register();

        try {

            serverSocket = new ServerSocket(PORT);

            System.out.println("Serviço de conversão iniciado com sucesso, rodando na porta 8081");
            System.out.println("digite exit e aperte enter para encerrar o programa");

            Scanner scanner = new Scanner(System.in);
            String aux = scanner.nextLine();
            while(!aux.equals("exit")) {

                socket = serverSocket.accept();

                socket.close();
                aux = scanner.nextLine();

            }
            scanner.close();

            serverSocket.close();

        } catch (IOException e) {
            
            e.printStackTrace();

        }

        
    }
}
