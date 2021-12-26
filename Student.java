import java.util.*;
public class Student {
	String name;
	double cost;
	School assignedSchool;
	HashSet<School> schools;
	LinkedList<School> preferences;
	LinkedList<School> remaining;
	
	public Student(String name, HashSet<School> schools, LinkedList<School> preferences) {
		this.name = name;
		this.schools = schools;
		this.preferences = preferences;
		this.remaining = (LinkedList<School>) preferences.clone(); 
	}
	public School nextChoice() {
		return this.remaining.pop();
	}
	public boolean betterThanNthg(School s) {
		for(School e : preferences) {
			if(s == null)
				return false;
			else if(e.equals(s))
				break;
		}
		return true;
	}
	
	public void resetPreferences(){
		this.remaining = (LinkedList<School>) preferences.clone(); 
	}
	
	@Override
	public boolean equals(Object o) {
		Student that = (Student) o;
		if(that.name.equals(this.name)){//and eventually if the groups of this and that are equal
			return true;
		}
		return false;
	}
}
