import Courses.*;
import Users.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class FileManager implements Compiler {


    @Override
    public void exportData(File file) {
        //1. users.size #
        //2.{username,password}\t
        //3.courses.size #
        //4.{school.getName,name,teacher,code|
        // capacity,registeredSize,units,daysSize,hoursSize|
        // day1, day2,...,dayN|hour1,hourN|examDate,examTime|
        // g/p|username1,...,usernameN}

        try {
        StringBuilder usersData = new StringBuilder(Database.getUsers().size()-1 + "#");
        for (User user : Database.getUsers()) {
            if (user instanceof Student student) {
                usersData.append("{").append(student.getUsername());
                usersData.append(",").append(student.getPassword())/*.append("|")*/;
//                for (Course course : student.getRegisteredCourses()) {
//                    usersData.append(course.getCode()).append(",");
//                }
//                usersData.append("|");
//                for (int i = 0; i < 6; i++) {
//                    for (int j = 0; j < 26; j++) {
//                        if(student.getWeekSchedule()[i][j]){
//                            usersData.append("t");
//                        }else{
//                            usersData.append("f");
//                        }
//                    }
//                }
                usersData.append("}");
            }
        }
            //4.{school.getName,name,teacher,code|
            // capacity,registeredSize,units,daysSize,hoursSize|
            // day1, day2,...,dayN|hour1,hourN|examDate,examTime|
            // g/p|username1,...,usernameN}
            StringBuilder coursesData = new StringBuilder("{" +Database.getCourses().size() + "#");
        for (Course course : Database.getCourses()) {
            coursesData.append("{").append(course.getSchool().name());
            coursesData.append(",").append(course.getName());
            coursesData.append(",").append(course.getTeacher());
            coursesData.append(",").append(course.getCode());
            coursesData.append("|").append(course.getCapacity());
            coursesData.append(",").append(course.getRegStudents().size());
            coursesData.append(",").append(course.getUnits());
            int a = course.getDays().length;
            coursesData.append(",").append(a);
            int b = course.getHours().length;
            coursesData.append(",").append(b);
            coursesData.append("|");
            for (int i = 0; i < a; i++) {
                coursesData.append(course.getDays()[i]);
                if (i == a - 1){
                    coursesData.append("|");
                }else{
                    coursesData.append(",");
                }
            }
            for (int i = 0; i < b; i++) {
                coursesData.append(course.getHours()[i]);
                if (i == b - 1){
                    coursesData.append("|");
                }else{
                    coursesData.append(",");
                }
            }
            coursesData.append(course.getExamDate());
            coursesData.append(",").append(course.getExamTime()).append("|");
            if (course instanceof General){
                coursesData.append("G|");
            }else{
                coursesData.append("F|");
            }
            for (Student student : course.getRegStudents()) {
                coursesData.append(student.getUsername()).append(",");
            }
            coursesData.append("}\n");
        }
            Path fileName = Path.of(file.getAbsolutePath());
            try {
                Files.writeString(fileName, usersData + "\n" + coursesData +"}");
            } catch (IOException e) {
                System.out.println("error occurred while exporting data.");;
            }
        } catch (FileSystemNotFoundException e) {
            System.out.println("ERROR: file not found.");
        }
    }
    private String[] configure(String fileIn){
        int endChar = 0;
        while (fileIn.charAt(endChar) != ',' &&
                fileIn.charAt(endChar) != '|' && fileIn.charAt(endChar) != '}') {
            endChar++;
        }
        String username = fileIn.substring(0, endChar);
        fileIn = fileIn.substring(endChar + 1);
        return new String[]{username, fileIn};
    }

    @Override
    public void importData(File file) {
        //1. users.size #
        //2.{username,password}/n
        //3.courses.size #
        //4.{school.getName,name,teacher,code|
        // capacity,registeredSize,units,daysSize,hoursSize|
        // day1, day2,...,dayN|hour1,hourN|examDate,examTime|
        // g/p|username1,...,usernameN}

        Path fileName = Path.of(file.getAbsolutePath());
        try {
            String fileIn = Files.readString(fileName);

            //users :
            int i = 0;
            while (fileIn.charAt(i) != '#') {
                i++;
            }
            int usersT = 0;
            if (i > 0) {
                usersT = Integer.parseInt(fileIn.substring(0, i));
            }
            ArrayList<User> users =new ArrayList<>();
            users.add(new Admin("Admin", "nottheAPpass"));
            fileIn = fileIn.substring(i + 2);
            if (usersT > 0) {
                for (i = 0; i < usersT; i++) {
                    String[] answers = configure(fileIn);
                    String username = answers[0];
                    fileIn = answers [1];
                    answers = configure(fileIn);
                    String password = answers[0];
                    fileIn = answers[1];
                    int endChar = 0;
                    while (fileIn.charAt(endChar) != '{') {
                        endChar++;
                    }
                    users.add(new Student(username, password));
                    fileIn = fileIn.substring(endChar+1);
                }
            }else {
                fileIn = fileIn.substring(1);
            }

            //courses:
            i = 0;
            while (fileIn.charAt(i) != '#') {
                i++;
            }
            int coursesT = 0;
            if (i > 0) {
                coursesT = Integer.parseInt(fileIn.substring(0, i));
            }
            ArrayList<Course> courses =new ArrayList<>();
            fileIn = fileIn.substring(i + 2);
            if (coursesT > 0) {
                //capacity,registeredSize,units,daysSize,hoursSize|examDate,examTime|
                for (i = 0; i < coursesT; i++) {
                    String[] answers = configure(fileIn);
                    School school =School.valueOf( answers[0]);
                    fileIn = answers [1];
                    answers = configure(fileIn);
                    String name = answers[0];
                    fileIn = answers[1];
                    answers = configure(fileIn);
                    String teacher = answers[0];
                    fileIn = answers[1];
                    answers = configure(fileIn);
                    int code = Integer.parseInt(answers[0]);
                    fileIn = answers[1];
                    answers = configure(fileIn);
                    int capacity = Integer.parseInt(answers[0]);
                    fileIn = answers[1];
                    answers = configure(fileIn);
                    int registeredSize = Integer.parseInt(answers[0]);
                    fileIn = answers[1];
                    answers = configure(fileIn);
                    int units = Integer.parseInt(answers[0]);
                    fileIn = answers[1];
                    answers = configure(fileIn);
                    int daysSize = Integer.parseInt(answers[0]);
                    fileIn = answers[1];
                    answers = configure(fileIn);
                    int hoursSize = Integer.parseInt(answers[0]);
                    fileIn = answers[1];
                    int [] days = new int[daysSize];
                    for (int j = 0; j<daysSize;j++){//check
                        days[j] = fileIn.charAt(0) - '0';
                        fileIn = fileIn.substring(2);
                    }
                    int [] hours = new int[hoursSize];
                    answers = configure(fileIn);
                    int hStart = Integer.parseInt(answers[0]);
                    fileIn = answers[1];
                    answers = configure(fileIn);
                    int hEnd = Integer.parseInt(answers[0]);
                    fileIn = answers[1];
                    for (int j = 0 ; j< hEnd - hStart; j++){
                        hours[j] = hStart + j;
                    }
                    answers = configure(fileIn);
                    String examDate = answers[0];
                    fileIn = answers[1];
                    answers = configure(fileIn);
                    int examTime = Integer.parseInt(answers[0]);
                    fileIn = answers[1];
                    if (fileIn.charAt(0) == 'G'){
                        courses.add(new General(school, name, teacher, code, units,
                                days, hours, examDate, examTime, new ArrayList<>(), capacity));
                    }else{
                        courses.add(new Prof(school, name, teacher, code, units,
                                days, hours, examDate, examTime, new ArrayList<>(), capacity));
                    }
                    fileIn= fileIn.substring(2);
                    for (int j = 0; j < registeredSize; j++){
                        answers = configure(fileIn);
                        fileIn = answers[1];
                        Student student = Database.getStudent(answers[0]);
                        student.addRegisteredCourse(courses.getLast());
                    }

                }
            }
        }catch (IOException e){
            System.out.println("error occurred while importing data.");
        }catch (StringIndexOutOfBoundsException | NumberFormatException ex){
            System.out.println("there is no previous readable data.");
        }
    }
}