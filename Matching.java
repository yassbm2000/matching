import java.util.*;
public class Matching {
	HashSet<School> schools;
	HashSet<Student> students;
	HashMap<School,HashSet<Student>> match;
	int[] groups;
	
	
	public Matching(HashSet<School> schools, HashSet<Student> students, HashMap<School,HashSet<Student>> match ){
		this.schools = schools; this.students = students; this.match = match; int[] groups;
	}
	
	public void basiccasematch() {
	LinkedList<Student> temp = students; // how do I turn a hashset into a list 
	while ( temp.isNotEmpty()) {
		Student cur =temp.pop();
		School sco = cur.nextChoice();
		if ( cur.betterThanNthg(sco) && sco.students.length() < sco.capacity) {sco.students.add(cur);}
		this.match.put(sco,cur);
		
	}
}
