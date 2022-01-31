package matching;

import java.util.HashSet;

public class Fourfifths extends Constraint {

	@Override
	boolean constraint(School s, HashSet<Student> h) {
		boolean cap= h.size() <= s.capacity;
		boolean fairness = true;
		for ( Student e: s.preferences) {
			int g = e.group;
			if ( s.admittedInGroup.get(g) != null 
				&& (s.admittedInGroup.get(g).size() > (6/5)*(s.groupSize(g)/s.preferences.size())*e.preferences.size()
				|| s.admittedInGroup.get(g).size() < (4/5)*(s.groupSize(g)/s.preferences.size())*e.preferences.size())) 
			{fairness = false; break;}
 		}
		return fairness && cap;
	}

	public static void main(String[] args) {
		Fourfifths ff = new Fourfifths();
		Instance3 t3 = new Instance3(20);
		t3.match.fixedPoint(ff);
		
		System.out.println(t3.match.toString());

	}

}
