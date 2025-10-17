package cn.edu.swu.yh.miniwebser.core;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP请求类
 */
public class HttpRequest {
    private String method;
    private String path;
    private String version;
    private Map<String, String> headers;
    private Parameter parameters;
    private String body;
    
    public HttpRequest() {
        this.headers = new HashMap<>();
        this.parameters = new Parameter();
    }
    
    public HttpRequest(String method, String path, String version) {
        this();
        this.method = method;
        this.path = path;
        this.version = version;
    }
    
    // Getters and Setters
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public Map<String, String> getHeaders() {
        return headers;
    }
    
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    
    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }
    
    public String getHeader(String key) {
        return this.headers.get(key);
    }
    
    public Parameter getParameters() {
        return parameters;
    }
    
    public void setParameters(Parameter parameters) {
        this.parameters = parameters;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    /**
     * 检查是否为GET请求
     */
    public boolean isGet() {
        return "GET".equalsIgnoreCase(method);
    }
    
    /**
     * 检查是否为POST请求
     */
    public boolean isPost() {
        return "POST".equalsIgnoreCase(method);
    }
}

