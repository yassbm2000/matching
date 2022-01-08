import java.util.*;
public class Student {
	String name;
	double cost;
	int group;
	School assignedSchool;
	LinkedList<School> preferences;
	LinkedList<School> remaining;
	
	public Student(String name,LinkedList<School> preferences,int g) {
		this.name = name;
		this.preferences = preferences;
		this.remaining = (LinkedList) preferences.clone();
		this.group = g;
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
	public void resetPreference(){ this.remaining = (LinkedList) this.preferences.clone();}
		
	@Override
	public boolean equals(Object o) {
		Student that = (Student) o;
		if(that.name.equals(this.name)){//and eventually if the groups of this and that are equal
			return true;
		}
		return false;
	}
}
