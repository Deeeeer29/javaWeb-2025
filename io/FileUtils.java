package cn.edu.swu.yh.io;
import java.io.*;
import java.net.URL;
import java.util.Date;

public class FileUtils {
    public static void saveImage(String url, String savePath) {
        try {
            URL imageUrl = new URL(url);
            try (InputStream in = imageUrl.openStream();
                 FileOutputStream out = new FileOutputStream(savePath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            System.out.println("下载图片失败: " + e.getMessage());
        }
    }

    public static void ensureDirectoryExists(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();  // 确保目录存在
        }
    }

    public static void appendLog(String log) {
        // 确保日志文件所在的目录存在
        ensureDirectoryExists("io");

        // 使用标准的路径分隔符
        String logFilePath = "io" + File.separator + "download_log.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(new Date() + " " + log);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("日志记录失败: " + e.getMessage());
        }
    }
}



