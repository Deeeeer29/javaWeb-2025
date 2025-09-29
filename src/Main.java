import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ImageManager imageManager = new ImageManager();

        // 确保 images 目录存在
        FileUtils.ensureDirectoryExists("images");

        while (true) {
            System.out.println("=== 图片下载系统 ===");
            System.out.println("1. 下载图片");
            System.out.println("2. 查看所有图片");
            System.out.println("3. 搜索图片");
            System.out.println("4. 退出");
            System.out.print("请输入选项: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // 消耗换行符

            switch (choice) {
                case 1:
                    System.out.print("请输入图片URL: ");
                    String url = scanner.nextLine();
                    imageManager.downloadImage(url);
                    break;
                case 2:
                    imageManager.listImages();
                    break;
                case 3:
                    System.out.print("请输入图片名称: ");
                    String name = scanner.nextLine();
                    imageManager.searchImage(name);
                    break;
                case 4:
                    System.out.println("退出系统");
                    scanner.close();
                    return;
                default:
                    System.out.println("无效选项，请重新选择。");
            }
        }
    }
}
