package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NameService {

    private static List<Service> services;

    private static ServerSocket serverSocket;
    private static Socket socketAppService;
    private static Socket socketStub;

    private static BufferedReader in;
    private static BufferedWriter out;

    public static String getServicesList() {
        
        String servicesList = new String();

        for(Service service: services) {

            servicesList += String.format("%s\n", service.getName());

        }

        return servicesList;

    }

    public static String getServiceHost(String name) {

        String host = "";

        for(Service service : services) {
           
            if(service.getName() == name) {
 
                host = service.getHost();
 
            } 
           
        }

        return host;

    }

    public static int getServicePort(String name) {

        int port = 0;

        for(Service service : services) {
           
            if(service.getName() == name) {

                port = service.getPort();

            } 
          
        }

        return port;

    }

    public static void registerService() {
        
        Service s;

        try {

            s = new Service();
            s.setName(in.readLine());
            s.setHost(in.readLine());
            s.setPort(Integer.parseInt(in.readLine()));

            services.add(s);

            System.out.println(String.format("serviço %s registrado com sucesso, aperte enter para poder registrar o próximo", s.getName()));

        } catch (IOException e) {
            
            e.printStackTrace();

        }

    }

    public static void startSocket(Socket socket) {

        try {

            socket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (Exception e) {
            
            e.printStackTrace();

        }

    }

    public static void closeSocket(Socket socket) {

        try {

            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args) {

        try {

            serverSocket = new ServerSocket(8080);
            System.out.println("Serviço de nomes iniciado com sucesso, rodando na porta 8080");
            System.out.println("primeiramente, devemos registrar os serviços de aplicação");
            System.out.println("ao registrar todos os serviços de aplicação desejados, digite exit e aperte enter");

            services = new ArrayList<Service>();

            Scanner scanner = new Scanner(System.in);
            String aux = scanner.nextLine();
            while(!aux.equals("exit")) {

                startSocket(socketAppService);
                registerService();
                closeSocket(socketAppService);
                aux = scanner.nextLine();

            }
            scanner.close();

            System.out.println(String.format("listagem dos serviços registrados:\n %s", getServicesList()));
            System.out.println("agora daremos início a conexão com os stubs");
            System.out.println("para encerrar o programa, digite end e aperte enter");

            scanner = new Scanner(System.in);
            aux = scanner.nextLine();
            while(!aux.equals("end")) {

                startSocket(socketStub);

                String serviceName = in.readLine();
                out.write(getServiceHost(serviceName));
                out.write(getServicePort(serviceName));

                closeSocket(socketStub);
                aux = scanner.nextLine();
            }
            scanner.close();

            serverSocket.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}