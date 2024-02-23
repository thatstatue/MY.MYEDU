public class Police {
    public static boolean isValid(Student student, Course course){
        if (student.getUnits() + course.getUnits() > 20){
            System.out.println("maximum units per semaster is 20.");
            return false;
        }
        return true;
    }

}
