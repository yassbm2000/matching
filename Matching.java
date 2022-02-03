package matching;
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
		for (Student s: students){ s.resetPreference();}
		while (!temp.isEmpty()) {
			Student cur =temp.pop();
			School sco = cur.nextChoice();
			if (sco.admitted.size() < sco.capacity) {sco.admitted.add(cur); cur.assignedSchool = sco;}
			else if(sco.admitted.size()>=sco.capacity){
				Student s = null;
				Iterator<Student> it = sco.admitted.iterator();
				while(it.hasNext()){
					s = it.next();
					if(sco.compare(cur,s)) {
						cur.assignedSchool = sco;
						it.remove();
						if(!s.remaining.isEmpty())
							temp.add(s);
						break;
					}
				}
				sco.admitted.add(cur);
				sco.admitted.remove(s);
			}
		}
		for(School s: this.schools) {
			for(Student i : s.admitted)
				i.assignedSchool = s;
			this.match.put(s, s.admitted);
		}
	}
	public void groupQuotaMatch() {
		this.match = new HashMap<School,HashSet<Student>>(); //reset the match 
		LinkedList<Student> temp = new LinkedList<Student>(students); 
		for (Student s: students){ s.resetPreference();} // reset preferences ( may have been modified from last matching ) 
		while (!temp.isEmpty()) {
			Student cur =temp.pop();
			School sco = cur.nextChoice();
			int g = cur.group;
			if ( sco.admittedInGroup.isEmpty() || sco.admittedInGroup.get(g)==null || sco.admittedInGroup.get(g).size() < sco.quotas.get(g) ){ //added to School a hashmap field " admitted In group "
				if ( sco.admitted.size() < sco.capacity ) {
				sco.admitted.add(cur); cur.assignedSchool = sco; }  
				else { //if there is no vacant place
					for (Student s: sco.admitted){ 
						if (sco.compare(cur,s)){
							sco.admitted.remove(s);
							Iterator<School> it = s.remaining.iterator();
							if(it.hasNext())
								temp.add(s);
							sco.admitted.add(cur);
							cur.assignedSchool = sco;
							if(sco.admittedInGroup.get(g) == null) {
								HashSet<Student> grp = new HashSet<Student>();
								grp.add(cur); sco.admittedInGroup.put(g, grp);
							}
							else
								sco.admittedInGroup.get(g).add(cur);
							break;
						} //shitty linear worst case search
					}
				}
			}
			else { //if the quota for the group is exceeded
				for ( Student s: sco.admittedInGroup.get(g)){
					if ( sco.compare(cur,s) ) { 
					sco.admitted.remove(s); 
					Iterator<School> it = s.remaining.iterator();
					if(it.hasNext())
						temp.add(s);
					sco.admitted.add(cur);
					cur.assignedSchool = sco;
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
	
	public HashSet<Student> demand(School s, HashMap<School, Integer>p){
		
		HashSet<Student> demand = new HashSet<Student>();
		for ( Student i: this.students ){
		
		boolean favoriteSchool=true;
		for (School sco: this.schools){
			if ((!s.equals(sco)) && sco.compareStudents(i,sco.preferences.get(sco.preferences.size() -p.get(sco)))  && i.compareSchools(sco,s) )
			{favoriteSchool = false;break;} 
		}//add a comparison method "compareSchools" to Student
		if ( s.compareStudents(i,s.preferences.get(s.preferences.size()-p.get(s)))   && favoriteSchool ) {
				demand.add(i); 
			    
				
				}
		}
		
		
			return demand;
		}
		
		
		//idea: maybe add a new class " cutoff profile"
			
		 // leave it for later, trying to encode a demand function 
	          // may be better to build a table or a hashmap so as not to compute the same value more than we actually need, but keep in mind that
	         // it's going to be very costly memory-wise : for each school and each cutoff profile an entire hashset of students...
	
		public void fixedPoint(Constraint c){
			for ( School s : this.schools) {s.admittedInGroup=new HashMap<Integer,HashSet<Student>>();}
			// réinitialisation
		   this.match = new HashMap<School,HashSet<Student>>(); //reset the match 
		   HashMap<School, Integer> p = new HashMap<School, Integer>(); 		   
		   for (School s : this.schools){p.put(s,1);}// just an arbitrary cutoff profile as our starting point
		   
		   
		   
		   
		   int count = 0;
		   boolean fixedPointIndicator = false;
		   while (fixedPointIndicator==false ){  //iterate until you land on a fixed point, you're guaranteed to 
			   count ++;
			   
			   fixedPointIndicator = true;
			   
			   for (School s: schools){
				   s.admittedInGroup=new HashMap<Integer,HashSet<Student>>();
				   s.admitted=demand(s,p);
				    for ( Student i: s.admitted) {
				    	int gi = i.group;
				    	if (s.admittedInGroup.get(gi)==null) {HashSet<Student> tempo = new HashSet<Student>();
				    	tempo.add(i); s.admittedInGroup.put(gi, tempo);} 
				    	else { HashSet<Student> temphash = s.admittedInGroup.get(i.group);
				    	temphash.add(i); s.admittedInGroup.put(gi, temphash);
				    	}
				    	
				    	// beware of null pointers
				       }
				   
				   if (!c.constraint(s,this.demand(s,p))){ //verify if the constraint is met
					  int ps = p.get(s);
					  if (ps==s.preferences.size()) {ps=1;}
					  else {ps++;}
					  p.put(s,ps); //modify the cutoff
					  fixedPointIndicator = false;} //that was not a fixed point
				   
				   s.admitted=demand(s,p);
				   
				    
				       
				       } //now that you got your fixed point p, derive the matching from it	
			    }
		   for (School s: schools){
				this.match.put(s, demand(s,p));

				}
		  
				       
		}
	
		
	
	
	public void fourFifthsRule(School s){
		int n = groups.length;
		int[] count = new int[n];
		
		int initCapacity=n;
		int p = s.preferences.size();
		class GroupComparator implements Comparator<Integer>{
			
			
		    public int compare(Integer g1, Integer g2) {
		        return Integer.compare((count[g1]/s.capacity)*(p/s.groupSize(g1)), (count[g2]/s.capacity)*(p/s.groupSize(g2)));
		    }
		
	}
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(initCapacity, new GroupComparator());
		for (int g=0; g<n; g++) {pq.add(g);}
		
		// priority according to decreasing (count/S)*(I/g) i.e most urgent group is the furthest from the desired ratio.
		for (int k=0; k<p; k++) {
			
			
			Student e=s.preferences.get(k);
			int g=e.group;
			if (count[g] <= (4/5)*(s.groupSize(g)/students.size())*schools.size()){
				count[g]++; 
				// update g's priority;
			}
			else if ( count[g] > (4/5)*(s.groupSize(g)/students.size())*s.capacity ){
				int gp = pq.peek();
				// fetch closest student in gp from current position in preferences, call it ep;
				for (int i = k; k<p; k++) {
					if (s.preferences.get(i).group==gp) {
						Student eg=s.preferences.get(i);
						// swap(e,ep) in s.preferences;
						Student temp = e;
						s.preferences.set(k, eg);
						s.preferences.set(i, temp);
						break;
					}
				}
				 
				count[gp]++; count[g]--; 
			       // update priorities accordingly;
			} 
		}
	}
	
	
	public boolean verify() {//check if matchig satisfies 4/5 rule
		boolean fairness = true;
		for (School s: this.schools) {
		for ( Student e: s.admitted) {
			int g = e.group;
			
			if ( s.admittedInGroup.get(g) != null 
				&& s.admittedInGroup.get(g).size() <= (6/5)*(s.groupSize(g)/s.preferences.size())*e.preferences.size()
				&& s.admittedInGroup.get(g).size() >= (4/5)*(s.groupSize(g)/s.preferences.size())*e.preferences.size()) 
			{fairness = false; break;}
 		}
	}
		return fairness;
		   		
		
}
		   		
}


