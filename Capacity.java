package matching;

import java.util.HashSet;

public class Capacity extends Constraint {

	@Override
	boolean constraint(School s, HashSet<Student> h) {
		return (h.size() <= s.capacity) ;     
	}

	public static void main(String[] args) {
	Capacity c = new Capacity();
	Instance3 t3 = new Instance3(20);
	t3.match.fixedPoint(c);
	
	System.out.println(t3.match.toString());
	

	}

}
