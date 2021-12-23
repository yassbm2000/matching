import java.util.*;
public class Student {
	String name;
	double cost;
	School assignedSchool;
	HashSet<School> schools;
	LinkedList<School> preferences;
	
	public Student(String name, HashSet<School> schools, LinkedList<School> preferences) {
		this.name = name;
		this.schools = schools;
		this.preferences = preferences;
	}
	public School nextChoice() {
		return this.preferences.pop();
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
	@Override
	public boolean equals(Object o) {
		Student that = (Student) o;
		if(that.name.equals(this.name)){//and eventually if the groups of this and that are equal
			return true;
		}
		return false;
	}
}
