package cn.edu.swu.yh.miniwebser.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP请求解析器
 */
public class HttpParser {
    
    /**
     * 解析HTTP请求
     */
    public static HttpRequest parseRequest(Socket socket) throws IOException {
        // TODO: 222024321072029/于鸿 优化HTTP请求解析性能
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        HttpRequest request = new HttpRequest();
        
        // 解析请求行
        String requestLine = reader.readLine();
        if (requestLine != null) {
            String[] parts = requestLine.split(" ");
            if (parts.length >= 3) {
                request.setMethod(parts[0]);
                request.setPath(parts[1]);
                request.setVersion(parts[2]);
            }
        }
        
        // 解析请求头
        String headerLine;
        while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
            int colonIndex = headerLine.indexOf(':');
            if (colonIndex > 0) {
                String key = headerLine.substring(0, colonIndex).trim();
                String value = headerLine.substring(colonIndex + 1).trim();
                request.addHeader(key, value);
            }
        }
        
        // 解析请求体（如果是POST请求）
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            StringBuilder body = new StringBuilder();
            int contentLength = 0;
            String contentLengthHeader = request.getHeader("Content-Length");
            if (contentLengthHeader != null) {
                try {
                    contentLength = Integer.parseInt(contentLengthHeader);
                } catch (NumberFormatException e) {
                    // 忽略解析错误
                }
            }
            
            if (contentLength > 0) {
                char[] buffer = new char[contentLength];
                int bytesRead = reader.read(buffer, 0, contentLength);
                if (bytesRead > 0) {
                    body.append(buffer, 0, bytesRead);
                }
            }
            request.setBody(body.toString());
        }
        
        // 解析URL参数
        parseUrlParameters(request);
        
        return request;
    }
    
    /**
     * 解析URL中的参数
     */
    private static void parseUrlParameters(HttpRequest request) {
        // TODO: 222024321072029/于鸿 添加URL参数验证和错误处理
        String path = request.getPath();
        int questionMarkIndex = path.indexOf('?');
        
        if (questionMarkIndex >= 0) {
            String queryString = path.substring(questionMarkIndex + 1);
            request.setPath(path.substring(0, questionMarkIndex));
            request.setParameters(Parameter.parseQueryString(queryString));
        }
        
        // 如果是POST请求，也解析POST数据
        if (request.isPost() && request.getBody() != null && !request.getBody().isEmpty()) {
            Parameter postParams = Parameter.parsePostData(request.getBody());
            // 合并GET和POST参数
            Map<String, String> allParams = new HashMap<>(request.getParameters().getAllParameters());
            allParams.putAll(postParams.getAllParameters());
            request.setParameters(new Parameter(allParams));
        }
    }
    
    /**
     * 生成HTTP响应
     */
    public static String generateResponse(int statusCode, String statusMessage, String contentType, String body) {
        StringBuilder response = new StringBuilder();
        
        // 状态行
        response.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusMessage).append("\r\n");
        
        // 响应头
        response.append("Content-Type: ").append(contentType).append("\r\n");
        response.append("Content-Length: ").append(body.getBytes().length).append("\r\n");
        response.append("Connection: close\r\n");
        
        // 空行
        response.append("\r\n");
        
        // 响应体
        response.append(body);
        
        return response.toString();
    }
    
    /**
     * 生成简单的HTML响应
     */
    public static String generateHtmlResponse(String title, String content) {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>" + title + "</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>" + title + "</h1>\n" +
                "    <p>" + content + "</p>\n" +
                "</body>\n" +
                "</html>";
        
        return generateResponse(200, "OK", "text/html; charset=UTF-8", html);
    }
    
    /**
     * 生成JSON响应
     */
    public static String generateJsonResponse(String json) {
        return generateResponse(200, "OK", "application/json; charset=UTF-8", json);
    }
    
    /**
     * 生成404响应
     */
    public static String generate404Response() {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>404 Not Found</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>404 Not Found</h1>\n" +
                "    <p>The requested resource was not found.</p>\n" +
                "</body>\n" +
                "</html>";
        
        return generateResponse(404, "Not Found", "text/html; charset=UTF-8", html);
    }
}
