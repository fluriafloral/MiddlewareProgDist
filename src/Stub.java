package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;
import java.util.Scanner;

public class Stub {
    
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public Stub() {

        connectToChat();

    }

    public void connectToChat() {

        try {

            startConnection("localhost", 8080);
            System.out.println("Conexão com o serviço de nomes realizada com sucesso");

            //passa para o identificador do serviço desejado (nesse caso, o canal de chat que deseja acessar)
            System.out.println("Insira o nome do canal de chat que deseja acessar:");
            Scanner scanner = new Scanner(System.in);
            System.out.println(in.readLine());
            out.write(scanner.nextLine());
            scanner.close();

            //recebe host e porta do serviço selecionado e encerra conexão com serviço de nomes
            int port = Integer.parseInt(in.readLine());
            endConnection();



        } catch (Exception e) {
            
            e.printStackTrace();

        } 

    }

    public String getMessages() {

        String messages = new String();

        try {

            String message = in.readLine();
            while(message != null) {
                
                messages += message;

            }

        } catch (IOException e) {
           
            e.printStackTrace();

        }

        return messages;

    }

    public void sendMessage(String message) {

        try {

            out.write(message);

        } catch (Exception e) {
            
            e.printStackTrace();

        }

    }

    public void startConnection(String host, int port) {

        try {

            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void endConnection() {

        try {

            socket.close();
            in.close();
            out.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    
}
