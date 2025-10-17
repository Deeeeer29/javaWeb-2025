package cn.edu.swu.yh.miniwebser.core;

/**
 * 服务器配置类
 */
public class Config {
    private int port = 8080;
    private String host = "localhost";
    private int maxConnections = 100;
    private int timeout = 30000; // 30秒超时
    
    public Config() {}
    
    public Config(int port) {
        this.port = port;
    }
    
    public Config(int port, String host) {
        this.port = port;
        this.host = host;
    }
    
    // Getters and Setters
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public int getMaxConnections() {
        return maxConnections;
    }
    
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    public int getTimeout() {
        return timeout;
    }
    
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
