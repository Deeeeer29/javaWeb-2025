package cn.edu.swu.yh.miniwebser.core;

/**
 * HTTP请求处理器接口
 */
public interface Handler {
    /**
     * 处理HTTP请求
     * @param request HTTP请求对象
     * @return HTTP响应字符串
     */
    String handle(HttpRequest request);
}
