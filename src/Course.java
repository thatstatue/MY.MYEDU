import java.util.ArrayList;

public abstract class Course {
    private final String name;
    private final String teacher;
    private final int code;
    private final int units;
    private final String days;
    private final int hours;
    private final int group;
    private ArrayList<Student> regStudents;

    private int capacity;

    public Course(String name, String teacher, int code, int units,
                  String days, int hours, int group, int capacity,
                  ArrayList<Student> regStudents) {
        this.name = name;
        this.teacher = teacher;
        this.code = code;
        this.units = units;
        this.days = days;
        this.hours = hours;
        this.group = group;
        this.capacity = capacity;
        this.regStudents = regStudents;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getCode() {
        return code;
    }

    public int getUnits() {
        return units;
    }

    public String getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getGroup() {
        return group;
    }

    public void addCapacity (int add){
        setCapacity(getCapacity()+add);
    }
    public void addStudent (Student student){
        regStudents.add(student);
        //todo: check isValid
        System.out.println("student was added to the course");
        showRegStudents();
    }
    public void removeStudent (Student student){
        regStudents.remove(student);
        System.out.println("student was removed from the course");
        showRegStudents();
    }
    public void showRegStudents (){
        System.out.println("registered students:");
        for (Student student: regStudents){
            System.out.println(student.getUsername());
        }
        System.out.println("______________\n");
    }
}
