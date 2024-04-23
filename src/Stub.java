package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

public class Stub {
    
    private Socket socketNameService;
    private BufferedReader inNameService;
    private BufferedWriter outNameService;

    private Socket socketAppService;
    private BufferedReader inAppService;
    private BufferedWriter outAppService;

    private String host;
    private int port;

    public Stub() {

        try {

            socketNameService = new Socket("localhost", 8080);
            inNameService = new BufferedReader(new InputStreamReader(socketNameService.getInputStream()));
            outNameService = new BufferedWriter(new OutputStreamWriter(socketNameService.getOutputStream()));
            
            System.out.println("Lista dos serviços disponíveis:");
            System.out.println(inNameService.readLine());

        } catch(Exception e) {

            e.printStackTrace();

        }

    }

    public void getAppServiceInfo(String serviceName) {

        try {

            //passa para o identificador do serviço desejado 
            outNameService.write(serviceName);
            outNameService.flush();

            //recebe host e porta do serviço selecionado e encerra conexão com serviço de nomes
            System.out.println(inNameService.readLine());
            String[] appServiceAddress = inNameService.readLine().split("/");
            host = appServiceAddress[0];
            port = Integer.parseInt(appServiceAddress[1]);

            socketNameService.close();
            inNameService.close();
            outNameService.close();

        } catch (Exception e) {
            
            e.printStackTrace();

        } 

    }

    public void connectToAppService(String parameter) {

        try {

            socketAppService = new Socket(host, port);
            inAppService = new BufferedReader(new InputStreamReader(socketAppService.getInputStream()));
            outAppService = new BufferedWriter(new OutputStreamWriter(socketAppService.getOutputStream()));

            System.out.println(inAppService.readLine());

            outAppService.write(parameter);
            outAppService.flush();

            System.out.println(inAppService.readLine());

            socketAppService.close();
            inAppService.close();
            outAppService.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

    }

    
}
