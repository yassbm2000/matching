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
						temp.add(s); // add the "fired" student to the waiting pile
						sco.admitted.add(cur);
						cur.assignedSchool = sco;
						break;
					}
				}
			}
		}
		for(School s: this.schools)
			this.match.put(s, s.admitted);
	}
	
	public void groupQuotaMatch() {
		
		this.match = new HashMap<School,HashSet<Student>>; //reset the match 
		LinkedList<Student> temp = new LinkedList<Student>(students); 
		for ( Student s: students){ s.resetPreference();} // reset preferences ( may have been modified from last matching ) 
		while ( !temp.isEmpty()) {
			Student cur =temp.pop();
			School sco = cur.nextChoice();
			int g = cur.group;
			if (sco.admittedInGroup.get(g).size() < sco.quotas.get(g) && cur.betterThanNthg(sco) ){ //added to School a hashmap field " admitted In group "
				if ( sco.admitted.size() < sco.capacity ) {
				sco.admitted.add(cur); cur.assignedSchool = sco; ;}  
		} else { //if there is no vacant place
				for ( Student s: sco.admitted){ 
				if (sco.compareStudents(cur,s) > 0 ){
					sco.admitted.remove(s);temp.add(s); 
					sco.admitted.add(cur); sco.admittedInGroup.put(g,cur);
					cur.assignedSchool = sco;
					break;
				} //shitty linear worst case search
			} 
			
		       }
			else  { //if the quota for the group is exceeded
				for ( Student s: sco.admittedInGroup.get(g)){
					if ( sco.compareStudent(cur,s) > 0 ) { 
					sco.admitted.remove(s); temp.add(s);
					sco.admitted.add(cur); sco.admittedInGroup.put(g,cur);
					sco.admittedInGroup.get(g).remove(s); 
					break;
					} // shitty linear worst case search
				}
			}
			
		}
		for(School s: this.schools)
		{this.match.put(s, s.admitted);}
		
	}
		
}
// question: what's the "assignedSchool" field in class Student used for ? A : the school that has been assigned to the student
// i'm skipping the test questions for now 

