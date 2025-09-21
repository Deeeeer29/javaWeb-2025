package cn.edu.swu.yh;
public class Course {
    private String courseId;
    private String courseName;
    private String teacher;

    public Course(String courseId,String courseName,String teacher){
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacher = teacher;
    }

    // Getter methods
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName(){ return courseName;}

    public String getTeacher(){ return teacher;}

    public void showInfo() {
        System.out.println("课程编号：" + getCourseId());
        System.out.println("课程名称：" + getCourseName());
        System.out.println("任课教师：" + getTeacher());
        System.out.println("-----------------");
    }
}