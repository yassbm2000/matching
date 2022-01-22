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
}
