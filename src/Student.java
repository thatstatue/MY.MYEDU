import java.util.ArrayList;
import java.util.Map;

public class Student extends User{
    public Student(String username, String password) {
        super(username, password);
        registeredCourses = new ArrayList<>();
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
    public void status() throws NullPointerException{
        System.out.println("list of registered courses\n=============");
        int i = 1;
        try {
            for (Map<School, Course> courseMap : registeredCourses) {
                System.out.println(i + "- course name: " + courseMap);
                i++;
            }
        }catch (NullPointerException ex){
            System.out.println("you have no registered courses.");
        }
        System.out.println("==============");
    }

}
