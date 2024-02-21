import java.util.ArrayList;
import java.util.Map;

public class Student extends User{
    public Student(String username, String password) {
        super(username, password);
    }
    private ArrayList<Map<School , Course>> registeredCourses;

    public ArrayList<Map<School, Course>> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(ArrayList<Map<School, Course>> registedCourses) {
        this.registeredCourses = registedCourses;
    }

    public void addRegisteredCourse(School school, Course course){
        registeredCourses.add(Map.of(school , course));

    }
    public void removeRegisteredCourse(School school, Course course) {
        registeredCourses.remove(Map.of(school, course));
    }
    public void status(){
        System.out.println("list of registered courses\n=============");
        for (Map<School,Course> courseMap : registeredCourses){
            System.out.println("course name: "+ courseMap);
        }
        System.out.println("==============");
    }

}
