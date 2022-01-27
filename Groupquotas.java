package matching;

import java.util.HashSet;

public class Groupquotas extends Constraint {

	@Override
	boolean constraint(School s, HashSet<Student> h) {
		boolean cap = h.size() < s.capacity;
		boolean quot = true;
		for (int g=0; g< s.quotas.size(); g++) {
			if (s.groupSize(g)>s.quotas.get(g)) {
				return false;
			}
			
		}
		return cap && quot;

		
	}

	public static void main(String[] args) {
		Groupquotas gq = new Groupquotas();
		Instance2 t4 = new Instance2(20);
		Instance3 t5 = new Instance3(20);
		t4.match.fixedPoint(gq); t5.match.fixedPoint(gq);
		System.out.println(t4.match.toString());
		System.out.println(t5.match.toString());

	}

}
