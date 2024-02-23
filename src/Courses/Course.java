package Courses;

import Users.Student;

import java.util.ArrayList;

public abstract class Course {
    private final String name;
    private final School school;
    private final String teacher;
    private final int code;
    private final int units;
    private final int[] days;
    private final int[] hours;
    private final int group;
    private ArrayList<Student> regStudents;

    private final String[] dayNumbers = new String[]{
            "sat", "sun","mon","tue","wed","thu"
    };
    private final String[] startHours = new String[]{
            "07:30", "08:00", "08:30", "09:00",
            "09:30", "10:00", "10:30", "11:00",
            "11:30", "12:00", "12:30", "13:00",
            "13:30", "14:00", "14:30", "15:00",
            "15:30", "16:00", "16:30", "17:00",
            "17:30", "18:00", "18:30", "19:00",
            "19:30", "20:00", "20:30"
    };


    private int capacity;

    public Course(School school, String name, String teacher, int code, int units, int[] days, int[] hours, int group, ArrayList<Student> regStudents, int capacity) {
        this.school = school;
        this.name = name;
        this.teacher = teacher;
        this.code = code;
        this.units = units;
        this.days = days;
        this.hours = hours;
        this.group = group;
        this.regStudents = regStudents;
        this.capacity = capacity;
    }
    //todo: هرکس رجیستر کرد باید یکی به ثبتنامیا اضافه شه و فقط وقتی ولید باشه رجیستر کردن که  کپسیتی منهای ثبتنامیا<0 باشه
    public String display(){
        String s = getCode() + " - " +getName()+"\n"+"school: "+ getSchool().name() + "\tteacher: "+ getTeacher()+ "\tgroup: " + getGroup()+
               "\ncapacity: "+ getCapacity() + "\tregistered: " + getRegStudents().size() + "\tunits: "+ getUnits()+
                "\ndays: ";
        String d = "";
        for (int num: days){
            d += dayNumbers[num] + " ";
        }
        d += "{from " + startHours[getHours()[0]] +
                " to "+ startHours[getHours()[getHours().length-1] + 1] + "}\n";
        return s+ d;
    }

    public School getSchool() {
        return school;
    }

    public ArrayList<Student> getRegStudents() {
        return regStudents;
    }

    public void setRegStudents(ArrayList<Student> regStudents) {
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

    public int[] getDays() {
        return days;
    }

    public int[] getHours() {
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
