import java.util.*;
public class Student {
	String name;
	double cost;
	int group;
	School assignedSchool;
	LinkedList<School> preferences;
	LinkedList<School> remaining;
	
	public Student(String name,int g, double cost) {
		this.name = name;
		this.preferences = new LinkedList<School>();
		this.group = g;
		this.cost = cost;
	}
	public void resetPreference(){ this.remaining = (LinkedList) this.preferences.clone();}
	public School nextChoice() {
		return this.preferences.pop();
	}
	@Override
	public boolean equals(Object o) {
		Student that = (Student) o;
		if(that.name.equals(this.name)){//and eventually if the groups of this and that are equal
			return true;
		}
		return false;
	}
	public boolean compareSchools(School s1, School s2) {
		boolean greaterThan = true;
		for(School e : this.preferences) {
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
}
