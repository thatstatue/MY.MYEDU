import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("");
        File studentsP = new File(file, "students_production.txt");
        System.out.println(file.getAbsolutePath());
        new Cli(new Logic(new Database())).run();
    }
    // (سافت کور از فایل بخونم کورسا رو
}