
public class Main {
    public static void main(String[] args) {
        new Cli(new Logic(new Database())).run();
    }
}