import java.util.*;
public class School {
	String name;
	int capacity;
	HashMap<Integer,Integer> quotas;
	HashSet<Student> admitted;
	LinkedList<Student> preferences;
	HashMap<Integer, HashSet<Student> > admittedInGroup; 
	
	public School(String name, int capacity, LinkedList<Student> preferences, HashMap<Integer,Integer> quotas) {
		this.name = name; this.admitted = new HashSet<Student>(); this.preferences = preferences; this.quotas = quotas;
	}
	
	@Override 
	public boolean equals(Object o) {
		School that = (School) o;
		if(that.name.equals(this.name))
			return true;
		return false;
	}
	public int compareStudents(Student s1,Student s2) {//returns -1 if s2 is preferred by this school than s1 and 1 otherwise
		return Integer.compare(this.preferences.indexOf(s2), this.preferences.indexOf(s1));
	}
	public int groupSize(int g){//returns the number of students belonging to group g
		int count = 0;
		for(Student e : this.preferences){
			if(e.group == g) {count++;}
		}
		return count;
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
