package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class AppService {

    private final static String NAME = "temperature_converter/";
    private final static String HOST = "localhost/"; 
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

            out.write(HOST);

            out.write(String.valueOf(PORT));

            out.close();
            socket.close();

        } catch (Exception e) {
            
            e.printStackTrace();

        }

    }

    public static String convertFahrenheit(double tempFahrenheit) {

        String result = new String();
        
        result += String.valueOf((tempFahrenheit - 32) * 5/9) + " Celsius\n";
        result += String.valueOf(((tempFahrenheit - 32) * 5/9) + 273.15) + " Kelvin";

        return result;

    }

    public static String convertCelsius(double tempCelsius) {

        String result = new String();
        
        result += String.valueOf((tempCelsius * 5/9) + 32) + " Fahrenheit\n";
        result += String.valueOf((tempCelsius + 273.15)) + " Kelvin";

        return result;

    }

    public static String convertKelvin(double tempKelvin) {

        String result = new String();
        
        result += String.valueOf((tempKelvin - 273.15)) + " Celsius\n";
        result += String.valueOf(((tempKelvin - 273.15) * 1.8) + 273.15) + " Kelvin";

        return result;

    }

    public static void main(String[] args) {

        register();

        try {

            serverSocket = new ServerSocket(PORT);

            System.out.println("Serviço de conversão de temperatura iniciado com sucesso, rodando na porta 8081");
            System.out.println("digite exit e aperte enter para encerrar o programa");

            Scanner scanner = new Scanner(System.in);
            String aux = scanner.nextLine();
            while(!aux.equals("exit")) {

                socket = serverSocket.accept();
                System.out.println("Cliente conectado com sucesso!");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write("Serviço de conversão de temperatura:\n");
                out.write("Recebe uma temperatura numa unidade de medida (Celsius, Fahrenheit ou Kelvin) e retorna ela convertida\n");
                out.write("Formato do input: valor seguido da inicial da unidade de medida (por exemplo, 14.0 C, 28.1 F, 101.9 K)");

                String temp = in.readLine();
                String[] tempSplit = temp.split(" ");
                switch (tempSplit[1]) {
                    case "C":
                        out.write(convertCelsius(Double.parseDouble(tempSplit[0])));
                    case "F":
                        out.write(convertFahrenheit(Double.parseDouble(tempSplit[0])));
                    case "K":
                        out.write(convertKelvin(Double.parseDouble(tempSplit[0])));
                }

                out.close();
                in.close();
                socket.close();
                aux = scanner.nextLine();

            }
            scanner.close();

            serverSocket.close();

        } catch (Exception e) {
            
            e.printStackTrace();

        }

        
    }
}
