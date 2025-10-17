package cn.edu.swu.yh.miniwebser.handler;

import cn.edu.swu.yh.miniwebser.core.Handler;
import cn.edu.swu.yh.miniwebser.core.HttpParser;
import cn.edu.swu.yh.miniwebser.core.HttpRequest;

/**
 * 默认请求处理器
 */
public class DefaultHandler implements Handler {
    
    @Override
    public String handle(HttpRequest request) {
        // TODO: 222024321072029/于鸿 添加请求日志记录功能
        String path = request.getPath();
        
        // 处理根路径
        if ("/".equals(path)) {
            return HttpParser.generateHtmlResponse(
                "欢迎使用MiniWebServer", 
                "这是一个简单的Web服务器实现。<br>" +
                "可用的路径：<br>" +
                "- <a href='/echo'>/echo</a> - 回显处理器<br>" +
                "- <a href='/time'>/time</a> - 时间处理器<br>" +
                "- <a href='/hello'>/hello</a> - 问候处理器"
            );
        }
        
        // 处理hello路径
        if ("/hello".equals(path)) {
            String name = request.getParameters().getParameter("name", "World");
            return HttpParser.generateHtmlResponse(
                "Hello " + name, 
                "你好，" + name + "！欢迎使用MiniWebServer！"
            );
        }
        
        // 其他路径返回404
        return HttpParser.generate404Response();
    }
}
