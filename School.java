package matching;
import java.util.*;
public class School {
	String name;
	int capacity;
	HashMap<Integer,Integer> quotas;
	HashSet<Student> admitted;
	HashMap<Integer, HashSet<Student> > admittedInGroup;
	LinkedList<Student> preferences;
	
	public School(String name, int capacity, HashMap<Integer,Integer> quotas) {
		this.name = name; this.capacity = capacity; this.admitted = new HashSet<Student>(); this.preferences = new LinkedList<Student>(); this.quotas = quotas;
		this.admittedInGroup = new HashMap<Integer, HashSet<Student> > ();
	}
	
	@Override 
	public boolean equals(Object o) {
		School that = (School) o;
		if(that.name.equals(this.name))
			return true;
		return false;
	}
	
	public boolean compareStudents(Student s1, Student s2) {
		boolean greaterThan = true;
		for(Student e : preferences) {
			if(e.equals(s1)) {
				greaterThan = true;
				break;
			}
			else if(e.equals(s2)) {
				greaterThan = false;
				break;
			}	
		}
		return greaterThan;
	}
	public int groupSize(int g){//returns the number of students belonging to group g
		return this.admittedInGroup.get(g).size();
	}
	public void truncate() {
		boolean a = false;
		for(Student e : this.preferences) {
			if(e==null) {
				a = true;
			}
			if(a)
				this.preferences.remove(e);
		}
	}
	//fair rankings
	public void fairRanking(){
		//the goal is to apply the 4/5 rules to every school's preferences
		this.truncate();
		HashMap<Integer,Integer> groupCount = new HashMap<Integer,Integer>();
		for(Student e : this.preferences){
			int ge = e.group;
			if(groupCount.get(ge)==null)
				groupCount.put(ge,0);
			else {
				int c = groupCount.get(ge);
				double grpFraction = (((double) this.groupSize(ge))/this.preferences.size());
				if( (c+1)/ ((double) this.capacity) < 6*grpFraction/5 )
					groupCount.put(ge, c+1);
				else 
					this.preferences.remove(e);	
			}
		}
	}
}
