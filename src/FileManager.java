import Courses.Course;
import Users.Student;
import Users.User;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileManager implements Compiler {


    @Override
    public void exportData(File file) {

        //1. users.size #
        //2.{username,password|courseCode1,courseCode2,..,courseCodeN|tftffftfttttffff} 6*26
        //3.# courses.size #
        //4.{school.getName,name,teacher,code|capacity,registeredSize,units,daysSize,hoursSize|examDate,examTime|[username1,...,usernameN]}
        try {
        StringBuilder usersData = new StringBuilder(Database.getUsers().size() + "#");
        for (User user : Database.getUsers()) {
            if (user instanceof Student student) {
                usersData.append("{").append(student.getUsername());
                usersData.append(",").append(student.getPassword()).append("|");
                for (Course course : student.getRegisteredCourses()) {
                    usersData.append(course.getCode()).append(",");
                }
                usersData.append("|");
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 26; j++) {
                        if(student.getWeekSchedule()[i][j]){
                            usersData.append("t");
                        }else{
                            usersData.append("f");
                        }
                    }
                }
                usersData.append("}\n");
            }
        }
        StringBuilder coursesData = new StringBuilder(Database.getCourses().size() + "#");
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
            coursesData.append("[");
            for (int i = 0; i < a; i++) {
                coursesData.append(course.getDays()[i]).append(";");
            }
            coursesData.append("][");
            for (int i = 0; i < b; i++) {
                coursesData.append(course.getDays()[i]).append(";");
            }
            coursesData.append("]|").append(course.getExamDate());
            coursesData.append(",").append(course.getExamTime()).append("|");
            for (Student student : course.getRegStudents()) {
                coursesData.append(student.getUsername()).append(",");
            }
            coursesData.append("}\n");
        }
            Path fileName = Path.of(file.getAbsolutePath());
            try {
                Files.writeString(fileName, usersData + "~" + coursesData);
            } catch (IOException e) {
                System.out.println("io exeception");;
            }
        } catch (FileSystemNotFoundException e) {
            System.out.println("ERROR: file not found.");
        }
    }

    @Override
    public void importData(File file) {
        Path fileName = Path.of(file.getAbsolutePath());
        try {
            String fileIn = Files.readString(fileName);
            int i = 0;
            while (fileIn.charAt(i) != '#') {
                i++;
            }
            int t = 0;
            if (i > 0) {
                t = Integer.parseInt(fileIn.substring(0, i));
            }
            if (t > 0) {
                fileIn = fileIn.substring(i + 1);
                for (i = 0; i < t; i++) {
                    int endChar = 0;
                    while (fileIn.charAt(endChar) != ')') {
                        endChar++;
                    }
                    endChar++;
                    String content = fileIn.substring(0, endChar);
                    char col = content.charAt(1);
                    int rowEnd = 2;
                    while (content.charAt(rowEnd) != ',') {
                        rowEnd++;
                    }
                    int row = Integer.parseInt(content.substring(2, rowEnd));
                    int textEnd = rowEnd;
                    while (content.charAt(textEnd) != '~') {
                        textEnd++;
                    }
                    String text = "";
                    if (textEnd > rowEnd + 1) {
                        text = content.substring(rowEnd + 1, textEnd);
                    }
                    int start = textEnd + 1;

                    while (content.charAt(start) != '=') {
                        start++;
                    }
                }
            }
        }catch (IOException e){
            
        }
    }
}
