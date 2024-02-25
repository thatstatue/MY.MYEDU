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
        Prof AP = new Prof(School.CSMath, "Advanced Programming", "Boomari", 222890, 4, new int[]{1, 3}, new int[]{11 , 12, 13, 14}, "1403/03/26", 3, new ArrayList<>(), 100);
        Prof SetTheory = new Prof(School.CSMath, "Basic Set Theory", "Ardeshir", 222500, 4, new int[]{1, 3}, new int[]{6 , 7, 8, 9}, "1403/03/22", 3,  new ArrayList<>(), 100);
        General Math1 = new General(School.CSMath, "General Math 1", "Pournaki", 122534, 4, new int[]{0, 2}, new int[]{6 , 7, 8, 9}, "1403/03/28",3, new ArrayList<>(), 200);
        Prof Discrete = new Prof(School.CSMath, "Discrete Math", "Rezaee", 222934, 3, new int[]{0, 2}, new int[]{6 , 7, 8}, "1403/03/22",3, new ArrayList<>(), 80);
        logic.database.addCourse(AP);
        logic.database.addCourse(SetTheory);
        logic.database.addCourse(Math1);
        logic.database.addCourse(Discrete);
        Prof LogicGate = new Prof(School.EE, "Logic Gate", "Shamsollahi", 322120, 4, new int[]{1, 3}, new int[]{0 , 1, 2, 3}, "1403/03/26" , 11, new ArrayList<>(), 100);
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

        //remove course : aval hame kasaii ke darso daran in ro remove kon az darsashon bad course ro az list remove kone
        System.out.println("1- show all students\n2- view and change courses for schools");
        //todo: fix the bug here
        String input = initInput();
        if (input.equals("back")) {
            backAction();
        } else {
            if (input.equals("1")) {
                addAction("show all students"); //todo: add to this and back
                logic.database.showStudents();
                changeInCourses(admin, false);
            }else if (input.equals("2")){
                addAction("view and change courses"); //todo: add to this and back
                showAllCourses();
                changeInCourses(admin , true);
            }else{
                invalidInput();
            }
            thisAction();
        }

    }
    private String initInput(){
        String input = scanner.next();
        redirectLogin(input);
        return input;
    }

    private void changeInCourses(Admin admin, boolean isCourse){
        System.out.println("0- go to login page");
        if (isCourse) {
            System.out.println("1- create a new course\n2- apply changes on a course");
            String input = initInput();
            if (input.equals("back")) {
                backAction();
            } else {
                if (input.equals("1")) {
                    fillInCourseInfo();
                    thisAction();
                } else if (input.equals("2")) {
                    System.out.println("please provide a course code: ");
                    int code = scanner.nextInt();
                    redirectLogin(String.valueOf(code));
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
                        System.out.println("1- remove the course\n2- remove a student from the course\n3- add to the capacity of the course");
                        String command = initInput();
                        if (command.equals("back")) {
                            backAction();
                        } else {
                            if (command.equals("1")) {
                                //todo

                            } else if (command.equals("2")) {
//todo
                            } else if (command.equals("3")) {
//todo
                            } else {
//todo
                            }
                        }
                    }
                }
            }
        }else{
            // TODO
        }

    }

    private void changeInCourses(Student student){
        System.out.println("0- go to login page");
        System.out.println("1- add course\n2- remove course");
        String input = initInput();
        if (input.equals("back")) {
            backAction();
        } else {
            addAction("show registered courses");
            System.out.println("please provide a course code: ");
            int code = scanner.nextInt();
            redirectLogin(String.valueOf(code));
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
                    boolean cantRemove = true;
                    for (Course course : student.getRegisteredCourses()) {
                        if (course.getCode() == code) {
                            student.removeRegisteredCourse(courseCode);
                            cantRemove = false;
                            break;
                        }
                    }
                    if (cantRemove) {
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
        String input = initInput();
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
        }
    }//done

    private void invalidInput(){
        System.out.println("invalid input!");
        thisAction();
    }//done
    private void fillInCourseInfo(){//todo: add to thisaction
        System.out.println("please fill in the information: ");
        System.out.println("\twhich school is offering the course? ");
        logic.database.showSchools();
        String input = initInput();
        School school = null;
        String name = "";
        String teacher = "";
        int code = 11;
        int units = 21;
        int[] days;
        int[] hours;
        String examDate = "";
        int examTime = 27;
        ArrayList<Student> regStudents = null;
        int capacity = 1;
        boolean isGen = false;
        //todo: changes in capacity should be handled

        try {
            school = School.values()[Integer.parseInt(input)-1];
            System.out.println("\tthe course name:");
            name = initInput();
            System.out.println("\tthe teacher:");
            teacher = initInput();
            System.out.println("\tthe course code:");
            code = Integer.parseInt(initInput());
            System.out.println("\tthe number of units:");
            units = Integer.parseInt(initInput());
            System.out.println("\tcapacity of the course:");
            capacity = Integer.parseInt(initInput());
            System.out.println("\tthe course is: \n1- General\n2- Professional");
            if (initInput().equals("1")){
                isGen = true;
            }
            System.out.println("\thow many days in week the class is on");
            int t = Integer.parseInt(initInput());

            days = new int[t];
            System.out.println("\tplease put in the numbers for each day the class is on" +
                    "\nthis are the codes for days:");
            for (int i = 0; i<6; i++){
                System.out.println(i+1 + "- " + Course.dayNumbers[i]);
            }
            for (int i = 0; i < t; i++){
                System.out.println("day " + (i+1)+ " of class: ");
                int dayCode = scanner.nextInt();
                if (dayCode<0 || dayCode>6){
                    invalidInput();
                }else if (dayCode == 0){
                    redirectLogin("0");
                }else{
                    days[i] = dayCode - 1;
                }
            }

            System.out.println("\tthis is the list of time tables: \n=============");
            for (int i = 0; i<26; i++){
                System.out.println(i+1 + "- " + Course.startHours[i]);
            }
            System.out.println("=============\n");
            System.out.println("\tcode of the time class starts: ");
            int start = scanner.nextInt();
            if (start<0 || start>26){
                invalidInput();
            }else if (start == 0){
                redirectLogin("0");
            }
            System.out.println("\tcode of the time class ends: ");
            int end = scanner.nextInt();
            if (end == 0){
                redirectLogin("0");
            }else if (end<=start){
                System.out.println("WAR: the class should end after it starts!");
                thisAction();
            } else if (end<0 || start>26){
                invalidInput();
            }
            hours = new int[end-start];
            for (int i = start; i < end; i++){
                hours[i- start] = i;
            }

            System.out.println("thank you for providing the information, creating the course ...");
            logic.createCourse(isGen, school, name, teacher, code, units, days, hours, examDate, examTime, null, capacity);

        }catch (ArrayIndexOutOfBoundsException | NegativeArraySizeException
                | NullPointerException | NumberFormatException ex){
            invalidInput();
        }
    }

    private void showAllCourses(){

        System.out.println("0- go to login page");
        logic.database.showSchools();
        String input = initInput();
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
    }//done
    //todo: account qablan sakhti
    public Logic getLogic() {
        return logic;
    }
}
