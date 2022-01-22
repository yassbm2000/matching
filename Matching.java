import java.util.*;
public class Matching {
	HashSet<School> schools;
	LinkedList<Student> students;
	HashMap<School,HashSet<Student>> match;
	int[] groups;
	
	public Matching(HashSet<School> schools, LinkedList<Student> students){
		this.schools = schools; this.students = students; this.match = new HashMap<School, HashSet<Student>>(); int[] groups;
	}
	
	public void basicCaseMatch() {
		LinkedList<Student> temp = students;
		while ( !temp.isEmpty()) {
			Student cur =temp.pop();
			School sco = cur.nextChoice();
			if (sco.admitted.size() < sco.capacity) {sco.admitted.add(cur); cur.assignedSchool = sco;}
			else if(sco.admitted.size()>=sco.capacity){
				Iterator<Student> it = sco.admitted.iterator();
				while(it.hasNext()){
					Student s = it.next();
					if(sco.compare(cur,s)) {
						cur.assignedSchool = sco;
						it.remove();
						break;
					}
				}
				sco.admitted.add(cur);
			}
		}
		for(School s: this.schools)
			this.match.put(s, s.admitted);
	}
	public void groupQuotaMatch() {
		
		this.match = new HashMap<School,HashSet<Student>>(); //reset the match 
		LinkedList<Student> temp = new LinkedList<Student>(students); 
		for (Student s: students){ s.resetPreference();} // reset preferences ( may have been modified from last matching ) 
		while ( !temp.isEmpty()) {
			Student cur =temp.pop();
			School sco = cur.nextChoice();
			int g = cur.group;
			if ( sco.admittedInGroup.isEmpty() || sco.admittedInGroup.get(g).size() < sco.quotas.get(g) ){ //added to School a hashmap field " admitted In group "
				if ( sco.admitted.size() < sco.capacity ) {
				sco.admitted.add(cur); cur.assignedSchool = sco; }  
				else { //if there is no vacant place
					for ( Student s: sco.admitted){ 
						if (sco.compareStudents(cur,s) > 0 ){
							sco.admitted.remove(s);temp.add(s); 
							sco.admitted.add(cur);
							if(sco.admittedInGroup.get(g) == null) {
								HashSet<Student> grp = new HashSet<Student>();
								grp.add(cur); sco.admittedInGroup.put(g, grp);
							}
							else
								sco.admittedInGroup.get(g).add(cur);
							cur.assignedSchool = sco;
							break;
						} //shitty linear worst case search
					}
				}
			}
			else { //if the quota for the group is exceeded
				for ( Student s: sco.admittedInGroup.get(g)){
					if ( sco.compareStudents(cur,s) > 0 ) { 
					sco.admitted.remove(s); temp.add(s);
					sco.admitted.add(cur);
					sco.admittedInGroup.get(g).add(cur);
					sco.admittedInGroup.get(g).remove(s); 
					break;
					} // shitty linear worst case search
				}
			}
			
		}
		for(School s: this.schools) {this.match.put(s, s.admitted);}
		
	}
	@Override
	public String toString() {
		String res = "";
		Matching matching = (Matching) this;
		for(School s : matching.schools) {
			boolean first = true;
			res += s.name + " <- [";
			for(Student e : s.admitted) {
				if(first) {
					res+= e.name;
					first = false;
				}
				else
					res+= ", " + e.name;
			}
			res+= "]" + "\n";
		}
		return res;
		
	}
	//first attempt at task 7
	public HashSet<Student> demand(School s, HashMap<School, int>p):{
		
		HashSet<Student> demand = new HashSet<Student>;
		ArrayList<Student> preference_Array = new ArrayList<>(s.preferences()); // so that we can access elements by their index 
		for ( Student i: this.students ){
		
		boolean favoriteSchool;
		for (School sco: this.schools){
			ArrayList<Student> local_preference_Array = new ArrayList<>(sco.preferences());
			if (sco.compareStudents(i,local_preference_Array[sco.capacity -p.get(sco)]) >=0 && i.compareSchools(sco,s) >=0){ favoriteSchool = false; break;}
		} //add a comparison method "compareSchools" to Student
		if ( s.compareStudents(i,preference_Array[s.capacity-p.get(s)]) >= 0 && i.betterThanNthg(s)  && favoriteschool ) {
				demand.add(i);
				}
		}
			return demand;
		}
		
		
		//idea: maybe add a new class " cutoff profile"
			
		 // leave it for later, trying to encode a demand function 
	          // may be better to build a table or a hashmap so as not to compute the same value more than we actually need, but keep in mind that
	         // it's going to be very costly memory-wise : for each school and each cutoff profile an entire hashset of students...
	
	// Also didnt know what the fuck they meant by " a function that encodes arbitrairy constraints", so I described the constraint as a subset of 2^I
	public void fixedPoint(HashSet<HashSet<Student>> constraint){
		
		   this.match = new HashMap<School,HashSet<Student>>; //reset the match 
		   HashMap<School, int> p = new HashMap<School, int>();  // build a random cutoff profile for starters		   
		   for (School s : this.schools){p.put(s,1);}// just a random cutoff profile as our starting point
		   boolean fixedPointIndicator = false;
		   while (fixedPointIndicator==false){  //iterate until you land on a fixed point, you're guaranteed to -eventually lots of iterations....-
			   fixedPointIndicator = true;
			   for (School s: schools){
				   if (!constraint.contains(this.demand(s,p))){  // dont even know if that's a thing
					  p.get(s)++; fixedPointIndicator = false; } //question: does p.get(s)++ actually modify the object ? 
				       }
				       
				       } //now that you got your fixed point p, derive the matching from it
			
		
		for (School s: this.schools){ this.match.put(s, demand(s,p));}
				       
		}
	
	public void fourFifthsRule(School s){
		int n = groups.length();
		int[] count = int[n];
		int p = s.preferences.size();
		// build a priority queue with all groups being initially with equal maximal priority;
		// priority according to decreasing (count/S)*(I/g) i.e most urgent group is the furthest from the desired ratio.
		for (int k =0, k<p, k++) {
			e=s.preferences[k];
			g=e.group;
			if (count[g] <= (4/5)*(g.size()/students.size())*schools.size()){
				count[g]++; 
				// update g's priority;
			}
			else if ( count[g] > (4/5)*(g.size()/students.size())*schools.size() && count[g] <= (6/5)*(g.size()/students.size())*schools.size()){
				//gp = Queue.maxpriority();
				// fetch closest student in gp from current position in preferences, call it ep;
				// swap(e,ep) in s.preferences; 
				//count[gp]++; count[g]--; 
			       // update priorities accordingly;
			} 
		}
	}
		   
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
}
// question: what's the "assignedSchool" field in class Student used for ? A : the school that has been assigned to the student
// i'm skipping the test questions for now 

