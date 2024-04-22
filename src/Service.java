package src;

public class Service {
    
    private String name;
    private String host;
    private int port;

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }
    
    public int getPort() {
        return port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
