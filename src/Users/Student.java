package Users;

import Courses.*;

import java.util.ArrayList;

public class Student extends User {
    private int units, genUnits;
    private boolean[][] weekSchedule;
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

    private void setBusy(Course course , boolean busy){
        for (int j =0; j<course.getDays().length; j++) {
            for (int i = 0; i < course.getUnits(); i++) {
                weekSchedule[course.getDays()[j]][course.getHours()[i]] = busy;
            }
        }
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(ArrayList<Course> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    public boolean addRegisteredCourse(Course course){
        if(Police.isValid(this, course)) {
            setBusy(course , true);
            registeredCourses.add(course);
            if (!course.isInStudents(this)) {
                course.addStudent(this, false);
            }
            if (course instanceof General){
                genUnits += course.getUnits();
            }
            return true;
        }else{
            return false;
        }

    }

    public int getGenUnits() {
        return genUnits;
    }

    public void setGenUnits(int genUnits) {
        this.genUnits = genUnits;
    }

    public void removeRegisteredCourse(Course course) {
        setBusy(course , false);
        registeredCourses.remove(course);
        if (course.isInStudents(this)) {
            course.removeStudent(this, false);
        }
        if (course instanceof General){
            genUnits -= course.getUnits();
        }
        System.out.println("course was removed successfully.");
    }

    public boolean isInCourses (Course course) {
        for (Course course1 : getRegisteredCourses()) {
            if (course.equals(course1)) {
                return true;
            }
        }
        return false;
    }
    public void status() throws NullPointerException{
        System.out.println("\nlist of registered courses:\n==============");
        int i = 1;
        try {
            for ( Course course : registeredCourses) {
                System.out.println(i + ". " + course.display());
                i++;
            }
        }catch (NullPointerException ex){
            System.out.println("you have no registered courses.");
        }
        System.out.println("==============\n");
    }
}
