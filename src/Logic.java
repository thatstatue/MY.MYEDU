public class Logic {

    public User createUser(String username, String password){
        User newUser;
        if (username.equals("Admin")){
            newUser = new Admin(username,password);

        }else {
            newUser = new Student(username, password);
        }
        return newUser;
    }

}
