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
}
