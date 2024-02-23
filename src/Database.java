import java.util.ArrayList;

public class Database {

    private final ArrayList<User> users;
    private final ArrayList<Course> courses;

    public Database() {
        this(new ArrayList<>(), new ArrayList<>());
    }
    public Database(ArrayList<User> users, ArrayList<Course> courses){
        this.users = users;
        this.courses = courses;
    }

    public void addUser(User user){
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public ArrayList<Course> getCourses() {
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
        for (Course course : courses){
            if (course.getSchool().equals(school)) {
                System.out.println(course);
            }
        }
    }
}
