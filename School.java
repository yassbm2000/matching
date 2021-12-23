import java.util.*;
public class School {
	String name;
	int capacity;
	HashSet<Student> students;
	LinkedList<Student> preferences;
	
	public School(String name, int capacity, HashSet<Student> students, LinkedList<Student> preferences) {
		this.name = name; this.students = students; this.preferences = preferences;
	}
	
	@Override 
	public boolean equals(Object o) {
		School that = (School) o;
		if(that.name.equals(this.name))
			return true;
		return false;
	}
	
	
}
