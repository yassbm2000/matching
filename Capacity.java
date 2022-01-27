package matching;

import java.util.HashSet;

public class Capacity extends Constraint {

	@Override
	boolean constraint(School s, HashSet<Student> h) {
		int n = h.size();
		return n <s.capacity ; 
	}

	public static void main(String[] args) {
	Capacity c = new Capacity();
	Instance2 t2 = new Instance2(20);
	Instance3 t3 = new Instance3(20);
	t2.match.fixedPoint(c); 
	t3.match.fixedPoint(c);
	System.out.println(t2.match.toString());
	System.out.println(t3.match.toString());


	}

}
