package cn.edu.swu.yh.miniwebser.handler;

import cn.edu.swu.yh.miniwebser.core.Handler;
import cn.edu.swu.yh.miniwebser.core.HttpParser;
import cn.edu.swu.yh.miniwebser.core.HttpRequest;

/**
 * 回显处理器 - 将请求参数原样返回
 */
public class EchoHandler implements Handler {
    
    @Override
    public String handle(HttpRequest request) {
        StringBuilder response = new StringBuilder();
        
        // 显示请求信息
        response.append("请求方法: ").append(request.getMethod()).append("<br>");
        response.append("请求路径: ").append(request.getPath()).append("<br>");
        response.append("HTTP版本: ").append(request.getVersion()).append("<br><br>");
        
        // 显示请求头
        response.append("<h3>请求头:</h3>");
        if (request.getHeaders().isEmpty()) {
            response.append("无请求头<br>");
        } else {
            for (String key : request.getHeaders().keySet()) {
                response.append(key).append(": ").append(request.getHeader(key)).append("<br>");
            }
        }
        
        // 显示请求参数
        response.append("<br><h3>请求参数:</h3>");
        if (request.getParameters().getAllParameters().isEmpty()) {
            response.append("无请求参数<br>");
        } else {
            for (String key : request.getParameters().getAllParameters().keySet()) {
                response.append(key).append(" = ").append(request.getParameters().getParameter(key)).append("<br>");
            }
        }
        
        // 显示请求体（如果是POST请求）
        if (request.isPost() && request.getBody() != null && !request.getBody().isEmpty()) {
            response.append("<br><h3>请求体:</h3>");
            response.append("<pre>").append(request.getBody()).append("</pre>");
        }
        
        return HttpParser.generateHtmlResponse("Echo Handler", response.toString());
    }
}
