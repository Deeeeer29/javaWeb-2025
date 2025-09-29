import java.util.*;

public class ImageManager {
    private Map<String, String> imageMap = new HashMap<>();

    // 下载图片并记录信息
    public void downloadImage(String url) {
        String rawName = url.substring(url.lastIndexOf("/") + 1);
        String safeBaseName = sanitizeFileName(removeExtension(rawName));
        String ext = extractExtension(rawName);
        if (ext.isEmpty()) {
            ext = guessExtensionFromUrl(url);
        }

        FileUtils.ensureDirectoryExists("images");
        String finalPath = resolveUniquePath("images", safeBaseName, ext);
        String savedAbs = FileUtils.saveImage(url, finalPath);
        if (savedAbs != null) {
            String finalName = new java.io.File(finalPath).getName();
            imageMap.put(finalName, finalPath);
            FileUtils.appendLog("Downloaded: " + url + " -> " + finalPath);
            System.out.println("下载成功: " + finalPath);
        } else {
            System.out.println("下载失败: " + url);
        }
    }

    // 列出所有已下载的图片
    public void listImages() {
        if (imageMap.isEmpty()) {
            System.out.println("尚未下载任何图片。");
        } else {
            for (Map.Entry<String, String> entry : imageMap.entrySet()) {
                System.out.println("名称: " + entry.getKey() + " | 路径: " + entry.getValue());
            }
        }
    }

    // 根据图片名称搜索图片路径
    public void searchImage(String name) {
        String path = imageMap.get(name);
        if (path == null) {
            System.out.println("未找到该图片。");
        } else {
            System.out.println("图片路径: " + path);
        }
    }

    private String sanitizeFileName(String name) {
        String cleaned = name.replaceAll("[\\\\/:*?\"<>|]", "_");
        if (cleaned.trim().isEmpty()) {
            cleaned = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        }
        return cleaned;
    }

    private String removeExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        if (dot <= 0) return fileName; // no dot or leading dot
        return fileName.substring(0, dot);
    }

    private String extractExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        if (dot < 0 || dot == fileName.length() - 1) return "";
        String ext = fileName.substring(dot + 1).toLowerCase(Locale.ROOT);
        // 常见图片后缀白名单
        if (ext.matches("(?i)jpg|jpeg|png|gif|bmp|webp|svg")) {
            return ext;
        }
        return "";
    }

    private String guessExtensionFromUrl(String url) {
        String lower = url.toLowerCase(Locale.ROOT);
        if (lower.contains(".jpeg")) return "jpeg";
        if (lower.contains(".jpg")) return "jpg";
        if (lower.contains(".png")) return "png";
        if (lower.contains(".gif")) return "gif";
        if (lower.contains(".bmp")) return "bmp";
        if (lower.contains(".webp")) return "webp";
        if (lower.contains(".svg")) return "svg";
        return "jpg"; // 默认使用 jpg
    }

    private String resolveUniquePath(String dir, String baseName, String ext) {
        String suffix = ext.isEmpty() ? "" : "." + ext;
        java.io.File candidate = new java.io.File(dir, baseName + suffix);
        int index = 1;
        while (candidate.exists()) {
            candidate = new java.io.File(dir, baseName + "(" + index + ")" + suffix);
            index++;
        }
        return candidate.getPath();
    }
}
