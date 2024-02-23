import java.util.ArrayList;

public class General extends Course{
    public General(School school, String name, String teacher, int code, int units, int[] days, int[] hours, int group, ArrayList<Student> regStudents, int capacity) {
        super(school, name, teacher, code, units, days, hours, group, regStudents, capacity);
    }
}
