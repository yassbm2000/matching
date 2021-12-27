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
		LinkedList<Student> temp = new LinkedList<Student>(students); 
		while ( !temp.isEmpty()) {
			Student cur =temp.pop();
			School sco = cur.nextChoice();
			if ( cur.betterThanNthg(sco) && sco.admitted.size() < sco.capacity) {sco.admitted.add(cur); cur.assignedSchool = sco;}
			else if(sco.admitted.size()>=sco.capacity){
				for(Student s : sco.admitted){
					if(sco.compareStudents(cur,s)>0) {
						sco.admitted.remove(s);
						temp.add(s); // add the fired student to the waiting pile
						sco.admitted.add(cur);
						break;
					}
				}
			}
		}
		for(School s: this.schools)
			this.match.put(s, s.admitted);
	}
	
	public void groupQuotaMatch() {
		LinkedList<Student> temp = new LinkedList<Student>(students); 
		for ( Student s: students){ s.resetPreference();} // reset preferences ( may have been modified from last matching ) 
		while ( !temp.isEmpty()) {
			Student cur =temp.pop();
			School sco = cur.nextChoice();
			if (sco.
		
		
}
