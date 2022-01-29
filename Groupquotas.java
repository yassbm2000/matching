package matching;

import java.util.HashSet;

public class Groupquotas extends Constraint {

	@Override
	boolean constraint(School s, HashSet<Student> h) {
		boolean cap = h.size() <= s.capacity; //capacity constraint
		boolean quota = true;
		for (Integer g=0; g< s.quotas.size(); g++) {
			if (s.admittedInGroup.get(g)!=null && s.admittedInGroup.get(g).size()>s.quotas.get(g)) {
				System.out.println(s.admittedInGroup.get(g).size()); quota =false; break;
			}
			
		}
		return cap && quota;

		
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
