import java.util.ArrayList;

public class Database {

    private final ArrayList<User> users;

    public Database() {
        this(null);
    }
    public Database(ArrayList<User> users){
        this.users = users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    //todo: only admins can access this
    public void showStudents(){
        System.out.println("Students: ");
        for (User user : users){
            if (user instanceof Student) {
                System.out.println(user.getUsername());
            }
        }
    }
}
