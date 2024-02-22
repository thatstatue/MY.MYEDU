import java.util.ArrayList;
import java.util.Scanner;

public class Cli {
    public final Scanner scanner = new Scanner(System.in);
    private final Logic logic;

    private Database database;
    private ArrayList<String> actions;
    private User thisUser;

    private void addAction(String action){
        actions.add(action);
    }
    private void backAction() throws NullPointerException{
        try {
            String action = actions.getLast();
            actions.removeLast();
            if (action.equals("overview")) {
                overview(thisUser);
            } else if (action.equals("login")) {
                redirectLogin("0");
            }

        }catch (NullPointerException ex) {
            System.out.println("no tracked history");
        }
    }


    public Cli(Logic logic) {
        this.logic = logic;
        database = new Database();
        actions = new ArrayList<>();
    }
    public void run(){
        loginPage();

    }
    public void redirectLogin(String input){
        if (input.equals("0")){
            loginPage();
        }
    }

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }

    public void loginPage() {
        System.out.println("1- login\n2- create account");
        String input = scanner.next();
        if (input.equals("back")) {
            backAction();
        } else {
            System.out.println("provide username:");
            String username = scanner.next();
            System.out.println("provide password:");
            String password = scanner.next();
            if (input.equals("1")) {
                boolean exists = false;
                for (User user : database.getUsers()) {
                    if (user.getUsername().equals(username) &&
                            user.getPassword().equals(password)) {
                        //login
                        setThisUser(user);
                        overview(thisUser);
                        exists = true;
                    }
                }
                if (!exists) {
                    System.out.println("wrong username or password!");
                }

            } else if (input.equals("2")) {
                User newUser = logic.createUser(username, password);
                database.addUser(newUser);
            }
        }
    }


    public void overview(User user){
        System.out.println("you are logged in!");
        if (user instanceof Admin){
            adminOverview((Admin) user);
        }else {
            studentOverview((Student) user);
        }
    }
    public void adminOverview(Admin admin){
        //todo: view all courses

    }
    public void studentOverview(Student student){

        System.out.println("0- go to login page");
        System.out.println("1- show registered courses\n2- show all courses");
        String input = scanner.next();
        redirectLogin(input);

        if (input.equals("back")) {
            //init(); todo: arraylist of actions
        } else {
            if (input.equals("1")) {
                student.status();
                System.out.println("\n1-add course\n2- remove course");
                input = scanner.next();
                if (input.equals("back")) {
                    //init(); todo: arraylist of actions
                } else {
                    System.out.println("please provide school: ");
                    School sch = School.valueOf(scanner.next());
                    System.out.println("please provide course name: ");
                    String courseName = scanner.next();
                    Course course = new Course(courseName, 1);
                    if (input.equals("1")) {
                        student.addRegisteredCourse(sch, course);
                    }else if (input.equals("2")){
                        student.removeRegisteredCourse(sch, course);
                        //todo: remove doesn't work
                    }
                }

            }else if (input.equals("2")){
                System.out.println("to be completed");
                //todo: complete this
            }
        }
    }

    public Logic getLogic() {
        return logic;
    }
}
