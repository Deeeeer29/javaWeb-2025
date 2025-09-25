package cn.edu.swu.yh.io;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ImageManager imageManager = new ImageManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("=== 图片下载系统 ===");
            System.out.println("1. 下载图片");
            System.out.println("2. 查看所有图片");
            System.out.println("3. 搜索图片");
            System.out.println("4. 退出");
            System.out.print("请输入选项: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 消耗掉换行符

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
                    System.out.print("请输入图片名: ");
                    String name = scanner.nextLine();
                    imageManager.searchImage(name);
                    break;
                case 4:
                    System.out.println("退出系统");
                    break;
                default:
                    System.out.println("无效选项，请重新选择");
            }
        } while (choice != 4);

        scanner.close();
    }
}

