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
        Prof AP = new Prof(School.CSMath, "Advanced Programming", "Boomari", 222890, 4, new int[]{1, 3}, new int[]{8 , 9, 10, 11}, 1, new ArrayList<>(), 100);
        Prof SetTheory = new Prof(School.CSMath, "Basic Set Theory", "Ardeshir", 222500, 4, new int[]{1, 3}, new int[]{4 , 5, 6, 7}, 1, new ArrayList<>(), 100);
        General Math1 = new General(School.CSMath, "General Math 1", "Pournaki", 122534, 4, new int[]{0, 2}, new int[]{4 , 5, 6, 7}, 2, new ArrayList<>(), 200);
        Prof Discrete = new Prof(School.CSMath, "Discrete Math", "Rezaee", 222934, 3, new int[]{0, 2}, new int[]{4 , 5, 6}, 1, new ArrayList<>(), 80);
        database.addCourse(AP);
        database.addCourse(SetTheory);
        database.addCourse(Math1);
        database.addCourse(Discrete);
        Prof LogicGate = new Prof(School.EE, "Logic Gate", "Shamsollahi", 322120, 4, new int[]{1, 3}, new int[]{1 , 2, 3, 4}, 1, new ArrayList<>(), 100);
        database.addCourse(LogicGate);
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
        initHardcore();
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
                    System.out.println("please provide course code: ");
                    int code = scanner.nextInt();
                    boolean exists = false;
                    Course courseCode = null;
                    for (Course course : database.getCourses()) {
                        if (course.getCode() == code) {
                            courseCode = course;
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        System.out.println("no such course found.");
                    } else {
                        if (input.equals("1")) {
                            boolean canAdd = true;
                            for (Course course : student.getRegisteredCourses()) {
                                if (course.getCode() == code) {
                                    System.out.println("you have already registered in this course.");
                                    canAdd = false;
                                    break;
                                }
                            }
                            if (canAdd) {
                                student.addRegisteredCourse(courseCode);
                            }
                        } else if (input.equals("2")) {
                            boolean canRemove = true;
                            for (Course course : student.getRegisteredCourses()) {
                                if (course.getCode() == code) {
                                    student.removeRegisteredCourse(courseCode);
                                    canRemove = false;
                                    break;
                                }
                            }
                            if (!canRemove) {
                                System.out.println("you didn't have this course.");
                            }
                        }
                        backAction();
                    }
                    //todo: remove doesn't work
                }
            }else if (input.equals("2")){
                System.out.println("to be completed");
                //todo: complete this
                backAction();
            }
        }
    }

    public Logic getLogic() {
        return logic;
    }
}
