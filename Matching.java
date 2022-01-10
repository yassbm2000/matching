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
	//first attempt at task 7
	public HashSet<Student> demand(School s, HashMap<School, int>p):{
		
		HashSet<Student> demand = new HashSet<Student>;
		for ( Student i: this.students ){
		boolean favoriteSchool; //leave that here
		if ( s.compareStudents(i,s[s.preference.size()-p.get(s)]) >= 0 && i.betterThanNthg(s)  && favoriteschool ) {
				demand.add(i)
				}
			return demand;
		}
		
		
		
		
		 // leave it for later, trying to encode a demand function 
	          // may be better to build a table or a hashmap so as not to compute the same value more than we actually need, but keep in mind that
	         // it's going to be very costly memory-wise : for each school and each cutoff profile an entire hashset of students...
	
	// Also didnt know what the fuck they meant by " a function that encodes arbitrairy constraints", so I described the constraint a subset of 2^I
	public void fixedPoint(HashSet<HashSet<Student>> constraint){
		
		   this.match = new HashMap<School,HashSet<Student>>; //reset the match 
		   HashMap<School, int> p = new HashMap<School, int>();  // build a random cutoff profile for starters		   
		   int k =1;
		   for (School s : this.schools){p.put(s,k); k++;} // just a random cutoff profile as our starting point
		   boolean fixedPointIndicator = true;
		   while (!fixedPointIndicator){  //iterate until you land on a fixed point, you're guaranteed to -eventually lots of iterations....-
			   fixedPointIndicator = true;
			   for (School s: schools){
				   if (!constraint.contains(demand(s,p)){  // dont even know if that's a thing
					  p.get(s)++; fixedPointIndicator = false; } //question: does p.get(s)++ actually modify the object ? 
				       }
				       
				       } //now that you got your fixed point p, derive the matching from it
			
		
		for (School s: this.schools){ this.match.put(s, demand(s,p));}
				       
		}
					   
				   
		   
		
		
		
		
		
		
	
		
		
		
		
		
		
		
	}	
		
		
		
}
// question: what's the "assignedSchool" field in class Student used for ? A : the school that has been assigned to the student
// i'm skipping the test questions for now 

