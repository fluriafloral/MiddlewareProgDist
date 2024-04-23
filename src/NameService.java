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
    private static BufferedReader inAppService;
    private static BufferedWriter outAppService;

    private static Socket socketStub;
    private static BufferedReader inStub;
    private static BufferedWriter outStub;

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
            
            socketAppService = serverSocket.accept();
            inAppService = new BufferedReader(new InputStreamReader(socketAppService.getInputStream()));
            outAppService = new BufferedWriter(new OutputStreamWriter(socketAppService.getOutputStream()));

            s = new Service();
            String[] serviceInfo = inAppService.readLine().split("/");

            s.setName(serviceInfo[0]);
            s.setHost(serviceInfo[1]);
            s.setPort(Integer.parseInt(serviceInfo[2]));

            services.add(s);

            System.out.println(String.format("serviço %s registrado com sucesso", s.getName()));

            outAppService.close();
            inAppService.close();
            socketAppService.close();

        } catch (IOException e) {
            
            e.printStackTrace();

        }

    }

    public static void main(String[] args) {

        try {

            serverSocket = new ServerSocket(8080);
            System.out.println("Serviço de nomes iniciado com sucesso, rodando na porta 8080");
            System.out.println("primeiramente, devemos registrar os serviços de aplicação");
            System.out.println("ao registrar um serviço, aperte enter para atualizar a lista de serviços disponíveis");
            System.out.println("ao registrar todos os serviços de aplicação desejados, digite exit e aperte enter");

            services = new ArrayList<Service>();

            Scanner scanner = new Scanner(System.in);
            String aux = scanner.nextLine();
            do {

                registerService();
                aux = scanner.nextLine();

            } while(!aux.equals("exit"));

            System.out.println(String.format("listagem dos serviços registrados:\n%s", getServicesList()));
            System.out.println("agora daremos início a conexão com os clientes");
            System.out.println("para encerrar o programa, digite end e aperte enter");

            do {

                socketStub = serverSocket.accept();
                inStub = new BufferedReader(new InputStreamReader(socketStub.getInputStream()));
                outStub = new BufferedWriter(new OutputStreamWriter(socketStub.getOutputStream()));

                System.out.println("Cliente conectado com sucesso!");
                outStub.write(getServicesList());
                outStub.flush();

                String serviceName = inStub.readLine();
                outStub.write(getServiceHost(serviceName));
                outStub.write(getServicePort(serviceName));
                outStub.flush();

                outStub.close();
                inStub.close();
                socketStub.close();
                
                aux = scanner.nextLine();

            } while(!aux.equals("end"));
            scanner.close();

            serverSocket.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}