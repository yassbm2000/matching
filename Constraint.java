package matching;
import java.util.*;
public abstract class Constraint {
	abstract boolean constraint(School s, HashSet<Student> h);
}
