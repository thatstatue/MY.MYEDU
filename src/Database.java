import Courses.*;
import Users.*;

import java.util.ArrayList;

public class Database {

    static ArrayList<User> users;
    static ArrayList<Course> courses;

    public Database() {
        this(new ArrayList<>(), new ArrayList<>());
    }
    public Database(ArrayList<User> users, ArrayList<Course> courses){
        Database.users = users;
        Database.courses = courses;
    }

    public void addUser(User user){
        users.add(user);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
    public Student getStudent (String username){
        for (User user: getUsers()){
            if (user instanceof  Student && user.getUsername().equals(username)){
                return (Student)(user);
            }
        }
        System.out.println("this username does not belong to any student.");
        return null;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public static ArrayList<Course> getCourses() {
        return courses;
    }

    //todo: only admins can access this
    public void showStudents() {
        System.out.println("Students: ");
        for (User user : users) {
            if (user instanceof Student) {
                System.out.println(user.getUsername());
            }
        }
    }

    public void showCourses(School school){
        System.out.println("Courses of "+ school.name() + ": ");
        int i = 1;
        for (Course course : courses){
            if (course.getSchool().equals(school)) {
                System.out.println(i + "- " + course.display());
                i++;
            }
        }
    }

    public void showSchools(){
        for (int i = 1; i<=6; i++){
            System.out.println(i + "- " +School.values()[i-1]);
        }
    }
}
