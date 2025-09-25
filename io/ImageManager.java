package cn.edu.swu.yh.io;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private Map<String, String> imageMap = new HashMap<>();

    public void downloadImage(String url) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String savePath = "images/" + fileName;
        FileUtils.saveImage(url, savePath);
        imageMap.put(fileName, savePath);
        FileUtils.appendLog("Downloaded: " + url + " -> " + savePath);
    }

    public void listImages() {
        if (imageMap.isEmpty()) {
            System.out.println("没有下载的图片");
        } else {
            imageMap.forEach((name, path) -> System.out.println(name + " -> " + path));
        }
    }

    public void searchImage(String name) {
        String path = imageMap.get(name);
        if (path == null) {
            System.out.println("未找到图片: " + name);
        } else {
            System.out.println("图片路径: " + path);
        }
    }
}
