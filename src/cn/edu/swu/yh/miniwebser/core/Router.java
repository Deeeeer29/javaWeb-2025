package cn.edu.swu.yh.miniwebser.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 路由管理器
 */
public class Router {
    private Map<String, Handler> routes;
    private Handler defaultHandler;
    
    public Router() {
        this.routes = new HashMap<>();
    }
    
    /**
     * 添加路由
     */
    public void addRoute(String path, Handler handler) {
        routes.put(path, handler);
    }
    
    /**
     * 设置默认处理器
     */
    public void setDefaultHandler(Handler handler) {
        this.defaultHandler = handler;
    }
    
    /**
     * 根据路径获取处理器
     */
    public Handler getHandler(String path) {
        // TODO: 222024321072029/于鸿 实现路由匹配算法优化
        // 精确匹配
        Handler handler = routes.get(path);
        if (handler != null) {
            return handler;
        }
        
        // 如果没有找到匹配的路由，返回默认处理器
        return defaultHandler;
    }
    
    /**
     * 处理请求
     */
    public String handleRequest(HttpRequest request) {
        Handler handler = getHandler(request.getPath());
        if (handler != null) {
            return handler.handle(request);
        }
        
        // 如果没有处理器，返回404
        return HttpParser.generate404Response();
    }
    
    /**
     * 获取所有注册的路由
     */
    public Map<String, Handler> getAllRoutes() {
        return new HashMap<>(routes);
    }
    
    /**
     * 检查路由是否存在
     */
    public boolean hasRoute(String path) {
        return routes.containsKey(path);
    }
}
