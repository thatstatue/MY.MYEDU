import java.util.Scanner;

public class Cli {
    public final Scanner scanner = new Scanner(System.in);
    private final Logic logic;

    private Database database;


    public Cli(Logic logic) {
        this.logic = logic;
        database = new Database();
    }

    public void init() {
        while (true) {
            System.out.println("1- login\n2- create account");
            String input = scanner.next();
            if (input.equals("back")) {
                break;
            } else {
                System.out.println("provide username:");
                String username = scanner.next();
                System.out.println("provide password:");
                String password = scanner.next();

                if (input.equals("1")) {

                    boolean exists = false;
                    for (User user : database.getUsers()) {
                        if (user.getUsername().equals(username) &&
                                user.getPassword().equals(password)) {
                            //login
                            exists = true;
                        }
                    }
                    if (!exists){
                        System.out.println("wrong username or password!");
                    }

                } else if (input.equals("2")) {
                    User newUser = logic.createUser(username, password);
                    database.addUser(newUser);
                }

            }
        }
    }

    public Logic getLogic() {
        return logic;
    }
}
