import java.util.ArrayList;

public class General extends Course{
    public General(String name, String teacher, int code, int units,
                   String days, int hours, int group, int capacity,
                   ArrayList<Student> regStudents) {
        super(name, teacher, code, units, days, hours, group, capacity, regStudents);
    }
}
