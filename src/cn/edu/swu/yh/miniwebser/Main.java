package cn.edu.swu.yh.miniwebser;

import cn.edu.swu.yh.miniwebser.core.Config;
import cn.edu.swu.yh.miniwebser.core.Router;
import cn.edu.swu.yh.miniwebser.handler.DefaultHandler;
import cn.edu.swu.yh.miniwebser.handler.EchoHandler;
import cn.edu.swu.yh.miniwebser.handler.TimeHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MiniWebServer主程序
 * 作者：222024321072029/于鸿
 */
public class Main {
    private static final int DEFAULT_PORT = 8080;
    private static final int MAX_THREADS = 10;
    
    public static void main(String[] args) {
        // TODO: 222024321072029/于鸿 添加命令行参数解析功能
        
        // 创建配置
        Config config = new Config(DEFAULT_PORT);
        
        // 创建路由管理器
        Router router = new Router();
        
        // 注册处理器
        router.addRoute("/echo", new EchoHandler());
        router.addRoute("/time", new TimeHandler());
        router.setDefaultHandler(new DefaultHandler());
        
        // 启动服务器
        startServer(config, router);
    }
    
    /**
     * 启动Web服务器
     */
    private static void startServer(Config config, Router router) {
        ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
        
        try (ServerSocket serverSocket = new ServerSocket(config.getPort())) {
            System.out.println("MiniWebServer 启动成功！");
            System.out.println("服务器地址: http://" + config.getHost() + ":" + config.getPort());
            System.out.println("可用路径:");
            System.out.println("  - http://" + config.getHost() + ":" + config.getPort() + "/");
            System.out.println("  - http://" + config.getHost() + ":" + config.getPort() + "/echo");
            System.out.println("  - http://" + config.getHost() + ":" + config.getPort() + "/time");
            System.out.println("  - http://" + config.getHost() + ":" + config.getPort() + "/hello");
            System.out.println("按 Ctrl+C 停止服务器");
            System.out.println("----------------------------------------");
            
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.submit(new RequestHandler(clientSocket, router));
                } catch (IOException e) {
                    System.err.println("接受连接时出错: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("启动服务器失败: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }
    
    /**
     * 请求处理线程
     */
    private static class RequestHandler implements Runnable {
        private final Socket socket;
        private final Router router;
        
        public RequestHandler(Socket socket, Router router) {
            this.socket = socket;
            this.router = router;
        }
        
        @Override
        public void run() {
            try {
                // TODO: 222024321072029/于鸿 添加请求处理异常处理
                
                // 解析HTTP请求
                cn.edu.swu.yh.miniwebser.core.HttpRequest request = 
                    cn.edu.swu.yh.miniwebser.core.HttpParser.parseRequest(socket);
                
                // 处理请求
                String response = router.handleRequest(request);
                
                // 发送响应
                socket.getOutputStream().write(response.getBytes());
                socket.getOutputStream().flush();
                
            } catch (IOException e) {
                System.err.println("处理请求时出错: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("关闭连接时出错: " + e.getMessage());
                }
            }
        }
    }
}