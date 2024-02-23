import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Cli {
    public final Scanner scanner = new Scanner(System.in);
    private final Logic logic;

    private final Database database;
    private final ArrayList<String> actions;
    private User thisUser;

    public void initHardcore(){
        Admin admin = new Admin("Admin", "nottheAPpass");
        database.addUser(admin);
    }

    private void addAction(String action){
        actions.add(action);
    }
    private void backAction() throws NoSuchElementException {
        try {
            actions.removeLast();
            String action = actions.getLast();
            System.out.println("\n----");
            for (String a : actions) {
                System.out.println(a);
            }
            System.out.println("----\n");

            if (action.equals("overview")) {
                overview(thisUser);
            } else if (action.equals("login")) {
                redirectLogin("0");
            }

        }catch (NoSuchElementException ex) {
            System.out.println("no tracked history");
            redirectLogin("0");
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
        addAction("login");
        System.out.println("1- login\n2- create account");
        String input = scanner.next();
        if (input.equals("back")) {
            backAction();
        } else if (input.equals("1") || input.equals("2")){
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
                    redirectLogin("0");
                }

            } else {
                User newUser = logic.createUser(username, password);
                database.addUser(newUser);
                backAction();
            }
        }else {
            System.out.println("invalid command");
            redirectLogin("0");
        }
    }


    public void overview(User user){
        addAction("overview");
        System.out.println("you are logged in!");
        if (user instanceof Admin){
            adminOverview((Admin) user);
        }else {
            studentOverview((Student) user);
        }
    }
    private void adminOverview(Admin admin){
        //todo: view all courses

    }
    private void studentOverview(Student student) throws IllegalArgumentException{

        System.out.println("0- go to login page");
        System.out.println("1- show registered courses\n2- show all courses");
        String input = scanner.next();
        redirectLogin(input);
//todo: for now it only switches pages when "back" is commanded, could be improved

        if (input.equals("back")) {
            backAction();
        } else {
            if (input.equals("1")) {
                student.status();
                System.out.println("\n1- add course\n2- remove course");
                input = scanner.next();
                if (input.equals("back")) {
                    backAction();
                } else {
                    //addAction("overview");
                    try {
                        System.out.println("please provide school: ");
                        School sch = School.valueOf(scanner.next());
                        System.out.println("please provide course name: ");
                        String courseName = scanner.next();
//                        if (courseName.charAt(0)== '1') {
//                            Course course = new Course(courseName, 1);
//                        }else{
//                            Course course = new Course(courseName, 1)
//                        }
//                        if (input.equals("1")) {
//                            student.addRegisteredCourse(sch, course);
//                        } else if (input.equals("2")) {
//                            student.removeRegisteredCourse(sch, course);
//                            //todo: remove doesn't work
//                        }
                    }catch (IllegalArgumentException e){
                        System.out.println("there is no such course in the school you provided!");
                    }
                    backAction();
                }

            }else if (input.equals("2")){
                System.out.println("to be completed");
                //todo: complete this
                backAction();
            }
        }
//        backAction();
    }

    public Logic getLogic() {
        return logic;
    }
}
