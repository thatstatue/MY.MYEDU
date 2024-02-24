package Courses;
import Users.Student;

public class Police {

    public static boolean checkBusy(Course course, boolean [][] weekSchedule){
        for (int j =0; j<course.getDays().length; j++) {
            for (int i = 0; i < course.getUnits(); i++) {
                if(weekSchedule[course.getDays()[j]][course.getHours()[i]]) {
                    System.out.println(course.getDays()[j] + " " + course.getHours()[i]);
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isValid(Student student, Course course){
        if (student.getUnits() + course.getUnits() > 20){
            System.out.println("WAR: maximum units per semaster is 20.");
            return false;
        }
        boolean [][] ws = student.getWeekSchedule();
        if(!checkBusy(course, ws)){
            System.out.println("WAR: you have another course in this time period.");
            return false;
        }
        //WAR: you have another final exam at this date and time.
        //WAR: the course capacity is full.

        return true;
    }

}
