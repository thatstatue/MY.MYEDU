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
        if (student!= null) {
            if (student.getUnits() + course.getUnits() > 20) {
                System.out.println("WAR: maximum units amount per semester is 20.");
                return false;
            }
            boolean[][] ws = student.getWeekSchedule();
            if (!checkBusy(course, ws)) {
                System.out.println("WAR: you have another course in this time period.");
                return false;
            }
            for (Course course1 : student.getRegisteredCourses()) {
                if (course1.getExamDate().equals(course.getExamDate())) {
                    if (course1.getExamTime() == course.getExamTime()) {
                        System.out.println("WAR: you have another final exam at this date and time.");
                        return false;
                    }
                }
            }
            if (course.getCapacity() == course.getRegStudents().size()){
                System.out.println("WAR: the course capacity is full.");
                return false;
            }
            if (course.getUnits() + student.getGenUnits() > 5){
                System.out.println("WAR: maximum general units number per semester is 5.");
                return false;
            }

            return true;
        }else {
            return false;
        }
    }

}
