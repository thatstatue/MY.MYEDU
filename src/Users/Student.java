package Users;

import Courses.Course;
import Courses.Police;

import java.util.ArrayList;

public class Student extends User {
    private int units;
    private boolean[][] weekSchedule;
    //todo: when u add a course , times should be put to true

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public Student(String username, String password) {
        super(username, password);
        registeredCourses = new ArrayList<>();
        weekSchedule = new boolean[6][26];
    }
    private ArrayList<Course> registeredCourses;

    public boolean[][] getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekSchedule(boolean[][] weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    private void setBusy(Course course){
        for (int j =0; j<course.getDays().length; j++) {
            for (int i = 0; i < course.getUnits(); i++) {
                weekSchedule[course.getDays()[j]][course.getHours()[i]] = true;
            }
        }
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(ArrayList<Course> registedCourses) {
        this.registeredCourses = registedCourses;
    }

    public void addRegisteredCourse(Course course){
        //todo: Police
        if(Police.isValid(this, course)) {
            setBusy(course);
            registeredCourses.add(course);
            System.out.println("course was registered successfully.");
        }else{
            System.out.println("course was not registered.");
        }

    }
    public void removeRegisteredCourse( Course course) {
        registeredCourses.remove(course);
        System.out.println("course was removed successfully.");
    }
    public void status() throws NullPointerException{
        System.out.println("list of registered courses\n==============");
        int i = 1;
        try {
            for ( Course course : registeredCourses) {
                System.out.println(i + ". " + course.display());
                i++;
            }
        }catch (NullPointerException ex){
            System.out.println("you have no registered courses.");
        }
        System.out.println("==============");
    }
}
