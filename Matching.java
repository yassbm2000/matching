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
			if (sco.admittedInGroup(g).size() < sco.quotas.get(g) && cur.betterThanNthg(sco) ){
				if ( sco.admitted.size() < sco.capacity ) {
				sco.admitted.add(cur); cur.assignedSchool = sco; ;} // add to schools a field " assigned from group" 
		} else {
				for ( Student s: sco.admitted){ 
				if (sco.compareStudents(cur,s) > 0 ){
					sco.admitted.remove(s);temp.add(s); 
					sco.admitted.add(cur); sco.admittedInGroup(g).add(cur);
					cur.assignedSchool = sco;
					break;
				}
			} 
			
		       }
			else  {
				for ( Student s: sco.admittedInGroup(g)){
					if ( sco.compareStudent(cur,s) > 0 ) { 
					sco.admitted.remove(s); temp.add(s);
					sco.admitted.add(cur); sco.admittedInGroup(g).add(cur);
					sco.admittedInGroup(g).remove(s); 
					break;
					}
				}
			}
			
		}
		
	}
		
}
