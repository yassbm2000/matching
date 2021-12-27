import java.util.*;
public class Matching {
	HashSet<School> schools;
	HashSet<Student> students;
	HashMap<School,HashSet<Student>> match;
	int[] groups;
	
	public Matching(HashSet<School> schools, HashSet<Student> students){
		this.schools = schools; this.students = students; this.match = new HashMap<School, HashSet<Student>>(); int[] groups;
	}
	
	public void basicCaseMatch() {
		LinkedList<Student> temp = new LinkedList<Student>(students); // how do I turn a hashset into a list 
		while ( !temp.isEmpty()) {
			Student cur =temp.pop();
			School sco = cur.nextChoice();
			if ( cur.betterThanNthg(sco) && sco.admitted.size() < sco.capacity) {sco.admitted.add(cur); cur.assignedSchool = sco;}
			else if(sco.admitted.size()>=sco.capacity){
				for(Student s : sco.admitted){
					if(sco.compareStudents(cur,s)>0) {
						sco.admitted.remove(s);
						sco.admitted.add(cur);
						break;
					}
				}
			}
		}
		for(School s: this.schools)
			this.match.put(s, s.admitted);
	}
}
