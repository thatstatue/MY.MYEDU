import Courses.*;
import Users.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Cli {
    public final Scanner scanner = new Scanner(System.in);
    private final Logic logic;
    private final ArrayList<String> actions;
    private User thisUser;

    public void initHardcore(){
        Admin admin = new Admin("Admin", "nottheAPpass");
        logic.database.addUser(admin);
        Prof AP = new Prof(School.CSMath, "Advanced Programming", "Boomari", 222890, 4, new int[]{1, 3}, new int[]{11 , 12, 13, 14}, 1, new ArrayList<>(), 100);
        Prof SetTheory = new Prof(School.CSMath, "Basic Set Theory", "Ardeshir", 222500, 4, new int[]{1, 3}, new int[]{6 , 7, 8, 9}, 1, new ArrayList<>(), 100);
        General Math1 = new General(School.CSMath, "General Math 1", "Pournaki", 122534, 4, new int[]{0, 2}, new int[]{6 , 7, 8, 9}, 2, new ArrayList<>(), 200);
        Prof Discrete = new Prof(School.CSMath, "Discrete Math", "Rezaee", 222934, 3, new int[]{0, 2}, new int[]{6 , 7, 8}, 1, new ArrayList<>(), 80);
        logic.database.addCourse(AP);
        logic.database.addCourse(SetTheory);
        logic.database.addCourse(Math1);
        logic.database.addCourse(Discrete);
        Prof LogicGate = new Prof(School.EE, "Logic Gate", "Shamsollahi", 322120, 4, new int[]{1, 3}, new int[]{0 , 1, 2, 3}, 1, new ArrayList<>(), 100);
        logic.database.addCourse(LogicGate);
    }

    private void addAction(String action){
        if (actions.isEmpty() || !actions.getLast().equals(action)) {
            actions.add(action);
        }
    }
    private void acting(String action){
        switch (action) {
            case "overview" -> overview(thisUser);
            case "login" -> redirectLogin("0");
            case "show registered courses" -> {
                if (thisUser instanceof Student){
                    ((Student)(thisUser)).status();
                    changeInCourses((Student) (thisUser));
                }else{
                    //todo: complete for admin
                }

            }
            case "show all courses" -> {
                if (thisUser instanceof Student) {
                    showAllCourses();
                    changeInCourses((Student) (thisUser));
                }else{
                    //todo: complete for admin
                }
            }
        }
    }
    private void backAction() {
        if (actions.size()>1){
            actions.removeLast();
            String action = actions.getLast();
            // /*
            System.out.println("\n----");
            for (String a : actions) {
                System.out.println(a);
            }
            System.out.println("----\n");
            // */
            acting(action);
        }
    }

    private void thisAction() {
        if (!actions.isEmpty()){
            acting(actions.getLast());
        }
    }

    public Cli(Logic logic) {
        this.logic = logic;
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
                for (User user : logic.database.getUsers()) {
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
                    thisAction();
                }

            } else {
                User newUser = logic.createUser(username, password);
                logic.database.addUser(newUser);
                System.out.println("your account was created!");
                redirectLogin("0");
            }
        }else {
            invalidInput();
        }
    }


    public void overview(User user){
        addAction("overview");
        System.out.println("you are logged in!");
        System.out.println("0- go to login page");
        if (user instanceof Admin){
            adminOverview((Admin) user);
        }else {
            studentOverview((Student) user);
        }
    }
    private void adminOverview(Admin admin){
        //todo: view all courses
    }

    private void changeInCourses(Student student){
        System.out.println("\n1- add course\n2- remove course");
        String input = scanner.next();
        if (input.equals("back")) {
            backAction();
        } else {
            addAction("show registered courses");
            System.out.println("please provide course code: ");
            int code = scanner.nextInt();
            boolean exists = false;
            Course courseCode = null;
            for (Course course : logic.database.getCourses()) {
                if (course.getCode() == code) {
                    courseCode = course;
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                System.out.println("no such course found.");
                thisAction();
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
                    }else{
                        thisAction();
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
                    if (canRemove) {
                        System.out.println("you didn't have this course.");
                        thisAction();
                    }
                }
                thisAction();
            }
        }
    }

    private void studentOverview(Student student){
        System.out.println("1- show registered courses\n2- show all courses for schools");
//        for (int i = 0; i< 6; i++){
//            for (int j = 0;j< 26; j++){
//                System.out.println(student.getWeekSchedule()[i][j]);
//            }
//        }

        String input = scanner.next();
        redirectLogin(input);
        if (input.equals("back")) {
            backAction();
        } else {
            if (input.equals("1")) {
                addAction("show registered courses");
                student.status();
                changeInCourses(student);
            }else if (input.equals("2")){
                addAction("show all courses");
                showAllCourses();
                changeInCourses(student);
            }else{
                invalidInput();
            }
            thisAction();
            //backAction();
        }
    }

    private void invalidInput(){
        System.out.println("invalid input!");
        thisAction();
    }

    private void showAllCourses(){
        logic.database.showSchools();
        System.out.println("please choose a school: ");
        String input = scanner.next();
        redirectLogin(input);
        if (input.equals("back")){
            backAction();
        }else{
            try {
                School schoolCode = School.values()[Integer.parseInt(input)-1];
                logic.database.showCourses(schoolCode);
                System.out.println();

            }catch (ArrayIndexOutOfBoundsException ex){
                invalidInput();
            }
        }
        //todo: complete this
    }

    public Logic getLogic() {
        return logic;
    }
}
//todo: qablan sakhti