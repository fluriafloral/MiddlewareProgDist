package src;

public class Client {

    private static Stub stub;
    
    public static void main (String[] args) {

        stub = new Stub();

        stub.getAppServiceInfo("temperature_converter");
        stub.connectToAppService("30.0 C");

    }
}
