package cn.edu.swu.yh.miniwebser.handler;

import cn.edu.swu.yh.miniwebser.core.Handler;
import cn.edu.swu.yh.miniwebser.core.HttpParser;
import cn.edu.swu.yh.miniwebser.core.HttpRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间处理器 - 返回当前时间
 */
public class TimeHandler implements Handler {
    
    @Override
    public String handle(HttpRequest request) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = now.format(formatter);
        
        // 检查请求格式
        String format = request.getParameters().getParameter("format", "html");
        
        if ("json".equalsIgnoreCase(format)) {
            String json = "{\n" +
                    "  \"currentTime\": \"" + currentTime + "\",\n" +
                    "  \"timestamp\": " + System.currentTimeMillis() + ",\n" +
                    "  \"timezone\": \"Asia/Shanghai\"\n" +
                    "}";
            return HttpParser.generateJsonResponse(json);
        } else {
            StringBuilder response = new StringBuilder();
            response.append("当前时间: ").append(currentTime).append("<br>");
            response.append("时间戳: ").append(System.currentTimeMillis()).append("<br>");
            response.append("时区: Asia/Shanghai<br><br>");
            response.append("提示: 添加 ?format=json 参数可以获取JSON格式的时间信息");
            
            return HttpParser.generateHtmlResponse("Time Handler", response.toString());
        }
    }
}
