package cn.edu.swu.yh;

public class main {
    public static void main(String[] argc){
        Student s1 = new Student("S2024001", "张三", "男", 20);
        Student s2 = new Student("S2024002", "李四", "女", 19);

        Course c1 = new Course("C001", "JavaWeb应用开发", "刘教授");
        Course c2 = new Course("C002", "数据库原理", "王老师");

        System.out.println("--- 学生信息 ---");
        s1.showInfo();
        s2.showInfo();


        System.out.println("\n--- 课程信息 ---");
        c1.showInfo();
        c2.showInfo();
    }
}