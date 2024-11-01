import Courses.*;
import Users.*;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cli {
    public final Scanner scanner = new Scanner(System.in);
    private File current;
    private final Logic logic;
    private final ArrayList<String> actions;
    private User thisUser;
    private School thisSchool;
    private final FileManager fileManager;

    public void initHardcode() {
        Admin admin = new Admin("Admin", "nottheAPpass");
        logic.database.addUser(admin);
        Prof AP = new Prof(School.CSMath, "Advanced Programming", "Boomari", 222890, 4, new int[]{1, 3}, new int[]{11, 12, 13, 14}, "1403/03/26", 3, new ArrayList<>(), 100);
        Prof SetTheory = new Prof(School.CSMath, "Basic Set Theory", "Ardeshir", 222500, 4, new int[]{1, 3}, new int[]{6, 7, 8, 9}, "1403/03/22", 3, new ArrayList<>(), 100);
        General Math1 = new General(School.CSMath, "General Math 1", "Qolamzadeh", 125564, 4, new int[]{0, 2}, new int[]{6, 7, 8, 9}, "1403/03/28", 3, new ArrayList<>(), 200);
        General Math2 = new General(School.CSMath, "General Math 2", "Pournaki", 122534, 4, new int[]{0, 2}, new int[]{6, 7, 8, 9}, "1403/03/28", 3, new ArrayList<>(), 200);
        Prof Discrete = new Prof(School.CSMath, "Discrete Math", "Rezaee", 222934, 3, new int[]{0, 2}, new int[]{6, 7, 8}, "1403/03/22", 3, new ArrayList<>(), 80);
        logic.database.addCourse(AP);
        logic.database.addCourse(SetTheory);
        logic.database.addCourse(Math1);
        logic.database.addCourse(Math2);
        logic.database.addCourse(Discrete);
        Prof LogicGate = new Prof(School.EE, "Logic Gate", "Shamsollahi", 32212, 4, new int[]{1, 3}, new int[]{0, 1, 2, 3}, "1403/03/26", 11, new ArrayList<>(), 100);
        Prof OOP = new Prof(School.EE, "Object-Oriented Programming", "Parvizi", 26212, 4, new int[]{1, 3}, new int[]{5, 6, 7, 8}, "1403/03/23", 21, new ArrayList<>(), 40);
        General SAS = new General(School.EE, "Signals & Systems", "Behroozi", 25742, 3, new int[]{1, 3}, new int[]{12, 13, 14}, "1403/03/26", 11, new ArrayList<>(), 80);
        logic.database.addCourse(LogicGate);
        logic.database.addCourse(OOP);
        logic.database.addCourse(SAS);
        Prof comp1 = new Prof(School.CE, "Probability and Statistics", "Sharifi", 40181, 3, new int[]{0, 2}, new int[]{4, 5, 6}, "1403/03/26", 16, new ArrayList<>(), 100);
        Prof comp2 = new Prof(School.CE, "Discrete Systems", "Zarabi Zade", 40123, 3, new int[]{1, 3}, new int[]{4, 5, 6}, "1403/03/22", 3, new ArrayList<>(), 200);
        General comp3 = new General(School.CE, "Linear Algebra", "RabiEi", 40342, 3, new int[]{1, 3}, new int[]{14, 15, 16}, "1403/03/31", 14, new ArrayList<>(), 50);
        logic.database.addCourse(comp1);
        logic.database.addCourse(comp2);
        logic.database.addCourse(comp3);

        Prof chem1 = new Prof(School.Chem, "Som", "Sharifi", 79667, 3, new int[]{0, 2}, new int[]{4, 5, 6}, "1403/03/26", 16, new ArrayList<>(), 100);
        Prof chem2 = new Prof(School.Chem, "Something Chemistry", "Zarabi Zade", 75560, 3, new int[]{1, 3}, new int[]{4, 5, 6}, "1403/03/22", 3, new ArrayList<>(), 200);
        General chem3 = new General(School.Chem, "General Chemistry", "RabiEi", 76353, 3, new int[]{1, 3}, new int[]{14, 15, 16}, "1403/03/31", 14, new ArrayList<>(), 50);
        logic.database.addCourse(chem1);
        logic.database.addCourse(chem2);
        logic.database.addCourse(chem3);
    }

    private void addAction(String action) {
        if (actions.isEmpty() || !actions.getLast().equals(action)) {
            actions.add(action);
        }
    }

    private void acting(String action) {
        switch (action) {
            case "overview" -> overview(thisUser);
            case "login" -> loginPage();
            case "show registered courses" -> {
                if (thisUser instanceof Student) {
                    ((Student) (thisUser)).status();
                    changeInCourses((Student) (thisUser));
                }
            }
            case "show all courses" -> {
                showAllCourses();
                changeInCourses((Student) (thisUser));
            }
            case "fill in info" -> fillInCourseInfo(thisSchool);
            case "import" -> exandimport(true);
            case "export" -> exandimport(false);
            case "view and change courses" -> {
                School school = showAllCourses();
                changeInCourses((Admin) (thisUser), school);
            }
        }
    }

    private void backAction() {
        if (actions.size() > 1) {
            actions.removeLast();
            String action = actions.getLast();
            acting(action);
        }
    }

    private void thisAction() {
        if (!actions.isEmpty()) {
            acting(actions.getLast());
        }
    }

    public Cli(Logic logic) {
        this.logic = logic;
        actions = new ArrayList<>();
        fileManager = new FileManager();
        initHardcode();
    }

    public void run() {
        File file = new File("");
        current = new File(file.getAbsolutePath(), "students_production.txt");
        fileManager.importData(current);
        while (true) {
            loginPage();
        }
    }

    public void redirect(String input) {
        if (input.equals("0")) {
            loginPage();
        } else if (input.equals("back")) {
            backAction();
        }
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }

    public void loginPage() {
        addAction("login");
        System.out.println("1- login\n2- create account");
        String input = scanner.next();
        redirect(input);
        if (input.equals("1") || input.equals("2")) {
            System.out.println("provide username:");
            String username = scanner.next();
            redirect(username);
            System.out.println("provide password:");
            String password = scanner.next();
            boolean exists = false;
            for (User user : Database.getUsers()) {
                if (user.getUsername().equals(username)) {
                    setThisUser(user);
                    exists = true;
                }
            }
            if (input.equals("1")) {
                if (exists && thisUser.getPassword().equals(password)) {
                    overview(thisUser);
                }else {
                    System.out.println("wrong username or password!");
                    thisAction();
                }
            } else {
                if (!exists){
                    User newUser = logic.createUser(username, password);
                    logic.database.addUser(newUser);
                    fileManager.exportData(current);
                    System.out.println("your account was created! redirecting you to login page ...");
                    redirect("0");
                }else{
                    System.out.println("an account with this username already exists");
                    thisAction();
                }
            }
        } else {
            invalidInput();
            System.out.println("WAR: there is no command for this entered code");
        }
    }


    public void overview(User user) {
        addAction("overview");
        System.out.println("you are logged in!");
        System.out.println("0- go to login page");
        if (user instanceof Admin) {
            adminOverview((Admin) user);
        } else {
            studentOverview((Student) user);
        }
    }

    private void adminOverview(Admin admin) {
        System.out.println("1- view and change courses for schools\n2- import data from file\n3- export current data");
        String command = scanner.next();
        redirect(command);
        if (command.equals("1")) {
            addAction("view and change courses");
            School school = showAllCourses();
            changeInCourses(admin, school);
        } else {
            if (command.equals("2")) {
                addAction("import");
                exandimport(true);
            } else if (command.equals("3")) {
                addAction("export");
                exandimport(false);
            } else invalidInput();
        }
        thisAction();
    }

    private void exandimport(boolean isImport) {
        System.out.println("provide an absolute path");
        String absPath = scanner.next();
        redirect(absPath);
        File file = new File(absPath);
        if (file.exists()) {
            String s;
            if (isImport) {
                fileManager.importData(file);
                s = "imported";
            } else {
                fileManager.exportData(file);
                s = "exported";
            }
            System.out.println("data was " + s + " successfully.");
            backAction();//?
        } else {
            System.out.println("this absolute path doesn't belong to any files.");
        }
    }

    private void changeInCourses(Admin admin, School school) {
        System.out.println("0- go to login page");
        System.out.println("1- create a new course\n2- apply changes on a course");
        thisSchool = school;
        String input = scanner.next();
        redirect(input);
        try {
            if (input.equals("1")) {
                addAction("fill in info");
                fillInCourseInfo(school);
            } else if (input.equals("2")) {
                System.out.println("please provide a course code: ");
                int code = scanner.nextInt();
                redirect(String.valueOf(code));
                boolean exists = false;
                Course courseCode = null;
                for (Course course : Database.getCourses()) {
                    if (course.getCode() == code) {
                        courseCode = course;
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    System.out.println("no such course found.");
                } else {
                    System.out.println("1- remove the course\n2- remove a student from the course\n" +
                            "3- add a student to the course\n4- add to the capacity of the course");
                    String command = scanner.next();
                    redirect(command);
                    switch (command) {
                        case "1" -> {
                            for (int i = 0; i < courseCode.getRegStudents().size(); i++) {
                                Student student = courseCode.getRegStudents().get(i);
                                student.removeRegisteredCourse(courseCode);
                            }
                            Database.getCourses().remove(courseCode);
                            System.out.println("this course was removed completely.");
                        }
                        case "2" -> {
                            System.out.println("list of registered students in this course:");
                            for (Student student : courseCode.getRegStudents()) {
                                System.out.println("\t" + student.getUsername());
                            }
                            System.out.println("============\nplease enter the username you want removed:");
                            String username = scanner.next();
                            redirect(username);
                            Student removingStudent = null;
                            boolean exist = false;
                            for (Student student : courseCode.getRegStudents()) {
                                if (student.getUsername().equals(username)) {
                                    removingStudent = student;
                                    exist = true;
                                    break;
                                }
                            }
                            if (exist) {
                                courseCode.removeStudent(removingStudent , true);
                                System.out.println("the provided student is no longer registered in this course.");
                            } else {
                                System.out.println("the provided student did not have this course.");
                            }
                        }
                        case "3" -> {
                            System.out.println("============\nplease enter the username you want added:");
                            String username = scanner.next();
                            redirect(username);
                            Student addingStudent = Database.getStudent(username);
                            boolean exist = false;
                            for (Student student : courseCode.getRegStudents()) {
                                if (student.getUsername().equals(username)) {
                                    addingStudent = student;
                                    exist = true;
                                    break;
                                }
                            }
                            if (!exist) {
                                if (Police.isValid(addingStudent, courseCode)) {
                                    courseCode.addStudent(addingStudent, true);
                                    System.out.println("the provided student is now registered in this course.");
                                } else {
                                    System.out.println("was not able to add the student.");
                                }
                            } else {
                                System.out.println("the provided student already has this course.");
                            }
                        }
                        case "4" -> {
                            System.out.println("the capacity is " + courseCode.getCapacity());
                            System.out.println("please enter the number you want to add to the capacity");
                            String plus = scanner.next();
                            redirect(plus);
                            courseCode.addCapacity(Integer.parseInt(plus));
                            System.out.println("the new capacity is " + courseCode.getCapacity());
                        }
                        default -> {
                            invalidInput();
                            System.out.println("WAR: there is no command for this entered code");
                        }
                    }
                }
            } else {
                invalidInput();
                System.out.println("WAR: there is no command for this entered code");
            }
        } catch (InputMismatchException e) {
            invalidInput();
            System.out.println("WAR: required format was ignored");
        }
        System.out.println("redirecting you to the previous page...");
        thisAction();
    }

    private void changeInCourses(Student student) {
        System.out.println("0- go to login page");
        System.out.println("1- add course\n2- remove course");
        String input = scanner.next();
        redirect(input);
        addAction("show registered courses");
        System.out.println("please provide a course code: ");
        try {
            int code = scanner.nextInt();
            redirect(String.valueOf(code));
            boolean exists = false;
            Course courseCode = null;
            for (Course course : Database.getCourses()) {
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
                        boolean mes = student.addRegisteredCourse(courseCode);
                        if (mes) {
                            System.out.println("course was registered successfully.");
                        } else {
                            System.out.println("course was not registered.");
                        }
                        fileManager.exportData(current);
                    } else {
                        thisAction();
                    }
                } else if (input.equals("2")) {
                    boolean cantRemove = true;
                    for (Course course : student.getRegisteredCourses()) {
                        if (course.getCode() == code) {
                            student.removeRegisteredCourse(courseCode);
                            fileManager.exportData(current);
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
        } catch (InputMismatchException ex) {
            System.out.println("you should enter a code that includes numbers only");
            thisAction();
        }
    }

    private void studentOverview(Student student) {
        System.out.println("1- show registered courses\n2- show all courses for schools");
        String input = scanner.next();
        redirect(input);
        if (input.equals("1")) {
            addAction("show registered courses");
            student.status();
            changeInCourses(student);
        } else if (input.equals("2")) {
            addAction("show all courses");
            showAllCourses();
            changeInCourses(student);
        } else {
            invalidInput();
        }
        thisAction();
    }//done

    private void invalidInput() {
        System.out.println("invalid input!");
        thisAction();
    }//done

    private void fillInCourseInfo(School school) {
        System.out.println("please fill in the information: ");
        System.out.println("School of " + school.name());

        String name;
        String teacher;
        int code;
        int units;
        int[] days;
        int[] hours;
        String examDate;
        int examTime;
        ArrayList<Student> regStudents = new ArrayList<>();
        int capacity;
        boolean isGen = false;

        try {
            System.out.println("\tthe course name:");
            name = scanner.nextLine();
            String name2 = scanner.nextLine();
            name += name2;
            redirect(name);
            System.out.println("\tthe teacher:");
            teacher = scanner.nextLine();
            redirect(teacher);
            System.out.println("\tthe course code:");
            String code1 = scanner.next();
            redirect(code1);
            code = Integer.parseInt(code1);
            System.out.println("\tthe number of units:");
            String units1 = scanner.next();
            redirect(units1);
            units = Integer.parseInt(units1);
            System.out.println("\tcapacity of the course:");
            String capacity1 = scanner.next();
            redirect(capacity1);
            capacity = Integer.parseInt(capacity1);
            System.out.println("\tthe course is: \n1- General\n2- Professional");
            String t1 = scanner.next();
            redirect(t1);
            if (t1.equals("1")) {
                isGen = true;
            } else if (!t1.equals("2")) {
                throw new NumberFormatException();
            }
            System.out.println("\thow many days in week the class is on");
            String t0 = scanner.next();
            redirect(t0);
            int t = Integer.parseInt(t0);
            days = new int[t];
            System.out.println("\tplease put in the numbers for each day the class is on" +
                    "\nthis are the codes for days:");
            for (int i = 0; i < 6; i++) {
                System.out.println(i + 1 + "- " + Course.dayNumbers[i]);
            }

            for (int i = 0; i < t; i++) {
                System.out.println("day " + (i + 1) + " of class: ");
                int dayCode = scanner.nextInt();
                if (dayCode < 0 || dayCode > 5) {
                    invalidInput();
                } else if (dayCode == 0) {
                    redirect("0");
                } else {
                    days[i] = dayCode - 1;
                }
            }

            Course.showStartHours();
            System.out.println("\tcode of the time class starts: ");
            int start = scanner.nextInt();
            if (start < 0 || start > 25) {
                invalidInput();
            } else if (start == 0) {
                redirect("0");
            }
            System.out.println("\tcode of the time class ends: ");
            int end = scanner.nextInt();
            if (end == 0) {
                redirect("0");
            } else if (end <= start) {
                System.out.println("WAR: the class should end after it starts!");
                thisAction();
            } else if (end < 0 || start > 26) {
                invalidInput();
            }
            hours = new int[end - start];
            for (int i = start - 1; i < end - 1; i++) {
                hours[i - start + 1] = i;
            }

            examDate = setExamDate();

            Course.showStartHours();
            System.out.println("\tplease select a time for the exam: ");
            examTime = scanner.nextInt() - 1;
            if (examTime < -1 || examTime > 25) {
                invalidInput();
            } else if (examTime == -1) {
                redirect("0");
            }
            System.out.println("thank you for providing the information!");
            logic.createCourse(isGen, school, name, teacher, code, units, days, hours, examDate, examTime, regStudents, capacity);
            System.out.println("course " + name + " was created.");
        } catch (ArrayIndexOutOfBoundsException | NegativeArraySizeException
                 | NullPointerException | NumberFormatException ex) {
            invalidInput();
            System.out.println("course was not created.");
        }
    }

    private String setExamDate() {
        System.out.println("to set exam date, enter the date in this format: yyyy/mm/dd");
        System.out.println("\texample: 1403/03/04");
        boolean confirmed = false;
        String exam = "";
        while (!confirmed) {
            exam = scanner.next();
            redirect(exam);
            try {
                if (exam.length() == 10 && exam.charAt(4) == '/' && exam.charAt(7) == '/'
                        && Integer.parseInt(exam.substring(5, 7)) < 13
                        && Integer.parseInt(exam.substring(8, 10)) < 32) {
                    confirmed = true;
                } else {
                    System.out.println("WAR: incorrect format. \nplease enter again:\texample: 1403/03/04");
                }
            } catch (NumberFormatException exception) {
                System.out.println("WAR: incorrect format. \nplease enter again:\texample: 1403/04/01");
            }
        }
        return exam;
    }

    private School showAllCourses() {
        System.out.println("please choose a school to view its courses: \n");

        System.out.println("0- go to login page");
        logic.database.showSchools();
        String input = scanner.next();
        redirect(input);
        try {
            School schoolCode = School.values()[Integer.parseInt(input) - 1];
            logic.database.showCourses(schoolCode);
            return schoolCode;
        } catch (ArrayIndexOutOfBoundsException ex) {
            invalidInput();
            System.out.println("WAR: this code doesn't belong to any schools");
        } catch (NumberFormatException ex) {
            System.out.println("please enter a code in numbers");
        }
        return null;
    }


}
