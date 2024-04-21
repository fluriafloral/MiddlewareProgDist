package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NameService {

    private static Map<String, Integer> nameAddress;

    public static String getChatServicesList() {
        
        String chatServicesNames = new String();

        int counter = 1;
        for(String chatServiceName : nameAddress.keySet()) {

            chatServicesNames += String.format("%i - %s\n", counter, chatServiceName);
            counter++;

        }

        return chatServicesNames;

    }

    public static int getPort(String key) {

        return nameAddress.get(key);

    }

    public static void main(String[] args) {

        ServerSocket serverSocket;
        Socket socket;
        BufferedReader in;
        BufferedWriter out;

        try {

            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Serviço de nomes iniciado com sucesso, rodando na porta 8080");

            nameAddress = new HashMap<>();
            nameAddress.put("VASCO", 8081);
            nameAddress.put("FLAMENGO", 8082);
            nameAddress.put("FLU", 8083);

            out.write(getChatServicesList());
            out.write(getPort(in.readLine()));
            
            in.close();
            out.close();
            socket.close();
            serverSocket.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}