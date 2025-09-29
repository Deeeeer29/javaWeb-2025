import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {
    // 下载图片并保存到指定路径，返回最终保存的绝对路径
    public static String saveImage(String url, String savePath) {
        HttpURLConnection connection = null;
        try {
            URL imageUrl = new URL(url);
            connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            connection.connect();

            int status = connection.getResponseCode();
            if (status >= 300 && status < 400) {
                String newLocation = connection.getHeaderField("Location");
                if (newLocation != null) {
                    return saveImage(newLocation, savePath);
                }
            }

            try (InputStream in = connection.getInputStream();
                 FileOutputStream out = new FileOutputStream(savePath)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return new File(savePath).getAbsolutePath();
        } catch (IOException e) {
            System.err.println("下载图片失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // 确保目录存在，不存在则创建
    public static void ensureDirectoryExists(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // 追加日志记录到 download_log.txt
    public static void appendLog(String log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("download_log.txt", true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write(timestamp + " " + log);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("写入日志失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
