package matching;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Instance1 {

	public static void main(String[] args) {
		Student i1 = new Student("i1",1,1); // Here group A is 1 and B is 2
		Student i2 = new Student("i2",1,1);
		Student i3 = new Student("i3",1,1);
		Student i4 = new Student("i4",2,10);
		HashMap<Integer,Integer> quotas = new HashMap<Integer,Integer>();
		quotas.put(1, 2);
		quotas.put(2, 2);
		School s1 = new School("s1",2,quotas);
		School s2 = new School("s2",2,quotas);
		i1.preferences.add(s1); i1.preferences.add(s2);
		i2.preferences.add(s2); i2.preferences.add(s1);
		i3.preferences.add(s1); i4.preferences.add(s2);
		s1.preferences.add(i4); s1.preferences.add(i3); 
		s1.preferences.add(i2); s1.preferences.add(i1);
		s2.preferences.add(i4); s2.preferences.add(i3); 
		s2.preferences.add(i2); s2.preferences.add(i1);
		HashSet<School> schools = new HashSet<School>();
		schools.add(s1); schools.add(s2);
		LinkedList<Student> students = new LinkedList<Student>();
		students.add(i1); students.add(i2); students.add(i3); students.add(i4);
		int[] groups = new int[]{1,2};
		Matching instance1 = new Matching(schools,students);
		instance1.groupQuotaMatch();
		System.out.println(instance1.toString());
		
	}
}
