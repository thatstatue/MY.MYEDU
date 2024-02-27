package Courses;

import Users.Student;

import java.util.ArrayList;

public abstract class Course {
    private final String name;
    private final School school;
    private final String teacher;
    private final int code;
    private final int units, examTime;
    private final int[] days;
    private final int[] hours;
    private final String examDate;
    private ArrayList<Student> regStudents;

    public static final String[] dayNumbers = new String[]{
            "sat", "sun","mon","tue","wed","thu"
    };
    public static final String[] startHours = new String[]{
            "07:30", "08:00", "08:30", "09:00",
            "09:30", "10:00", "10:30", "11:00",
            "11:30", "12:00", "12:30", "13:00",
            "13:30", "14:00", "14:30", "15:00",
            "15:30", "16:00", "16:30", "17:00",
            "17:30", "18:00", "18:30", "19:00",
            "19:30", "20:00", "20:30"
    };
    public static final void showStartHours(){
        System.out.println("\tthis is the list of time tables: \n=============");
        for (int i = 0; i<26; i++){
            System.out.print(i+1 + "- " + startHours[i] + "\t\t");
            if (i%3 == 2){
                System.out.println();
            }
        }
        System.out.println("=============\n");
    }


    private int capacity;

    public Course(School school, String name, String teacher, int code, int units, int[] days, int[] hours, String examDate, int examTime, ArrayList<Student> regStudents, int capacity) {
        this.school = school;
        this.name = name;
        this.teacher = teacher;
        this.code = code;
        this.units = units;
        this.days = days;
        this.hours = hours;
        this.examDate = examDate;
        this.examTime = examTime;
        this.regStudents = regStudents;
        this.capacity = capacity;
    }
    public String display(){
        String s = getCode() + " - " +getName()+"\n"+"school: "+ getSchool().name() + "\tprofessor:: "+ getTeacher()+
               "\t\tcapacity: "+ getCapacity() + "\tregistered: " + getRegStudents().size() + "\tunits: "+ getUnits()+
                "\ndays: ";
        String d = "";
        for (int num: days){
            d += dayNumbers[num] + " ";
        }
        d += "{from " + startHours[getHours()[0]] +
                " to "+ startHours[getHours()[getHours().length-1] + 1] + "}\t\t";
        d += "exam date: "+ getExamDate()+ "\t exam time: " + startHours[getExamTime()];
        return s+ d +"\n";
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

    public String getExamDate() {
        return examDate;
    }

    public int getExamTime() {
        return examTime;
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

    public void addCapacity (int add){
        setCapacity(getCapacity()+add);
    }
    public void addStudent (Student student){
        regStudents.add(student);
        if (!student.isInCourses(this)) {
            student.addRegisteredCourse(this);
        }
        System.out.println("student was added to the course");
        showRegStudents();
    }
    public boolean isInStudents (Student student){
        for (Student student1: getRegStudents()){
            if (student1.equals(student)) {
                return true;
            }
        }
        return false;
    }
    public void removeStudent (Student student){
        regStudents.remove(student);
        if (student.isInCourses(this)) {
            student.removeRegisteredCourse(this);
        }
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
