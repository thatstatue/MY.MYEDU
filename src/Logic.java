import Courses.Course;
import Courses.Prof;
import Users.*;

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

    public void createCourse(){

        database.addCourse(new Prof());
    }

}
