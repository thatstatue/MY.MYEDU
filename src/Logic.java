import Courses.*;
import Users.*;
import java.util.ArrayList;

public class Logic {
    public final Database database;

    public Logic(Database database) {
        this.database = database;
    }


    public User createUser(String username, String password){
        User newUser;
        if (username.equals("Admin")){
            newUser = new Admin(username,password);

        }else {
            newUser = new Student(username, password);
        }
        return newUser;
    }

    public void createCourse(boolean isGen, School school, String name, String teacher, int code, int units, int[] days, int[] hours, String examDate, int examTime, ArrayList<Student> regStudents, int capacity){
        if (isGen){
            database.addCourse(new General(school, name, teacher, code, units, days, hours, examDate, examTime, regStudents, capacity));
        }else{
            database.addCourse(new Prof(school, name, teacher, code, units, days, hours, examDate, examTime, regStudents, capacity));
        }
    }

}
