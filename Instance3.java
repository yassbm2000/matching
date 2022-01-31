import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
public class Instance3 {
	int n;
	Random rd;
	Matching match;
	
	public Instance3(int n) {
		LinkedList<Student> students = new LinkedList<Student>();
		this.rd = new Random();
		double ke = Math.floor(0.9*n);
		int m = (int)ke;
		
		HashMap<Integer,Integer> quotas = new HashMap<Integer,Integer>();
		quotas.put(1, m/4); quotas.put(2, m/4); quotas.put(3, m/4); quotas.put(4,m/4); //the groups are {1,2}
		quotas.put(1, m/4); quotas.put(2, m/4); quotas.put(3, m/4); quotas.put(4,m/4); //the groups are {1,2}
		School s1 = new School("s1",n/4,quotas); School s2 = new School("s2",n/4,quotas);
		HashSet<School> schools = new HashSet<School>(); schools.add(s1); schools.add(s2);
		double[] W_1 = new double[n];//array of numeric values of the qualities for s1
		double[] W_2 = new double[n];
		HashMap<Double,Student> qualities_1 = new HashMap<Double,Student>();//qualities perceived by school s1 for each student
		HashMap<Double,Student> qualities_2 = new HashMap<Double,Student>();//qualities perceived by school s2 for each student
		for(int k=0; k<n;k++) {
			Student e;
			String name = "i" + k;
			if(k<(10*n)/20) e = new Student(name,1,1);
			else if(k>=(10*n)/20 || k<(6*n)/20) e = new Student(name,2,5);
			else if(k>=(6*n)/20 || k<(3*n)/20) e = new Student(name,3,6);
			else e = new Student(name,4,10);
			students.add(e);
			double w = rd.nextGaussian();
			W_1[k] = w + rd.nextGaussian();
			W_2[k] = w + rd.nextGaussian();
			qualities_1.put(W_1[k], e); //noisy quality perceived by s1
			qualities_2.put(W_2[k], e);//noisy quality perceived by s2
			//choice of a student's preferences
			double p = rd.nextDouble();
			if(p<0.5) {e.preferences.add(s1); e.preferences.add(s2);}
			else {e.preferences.add(s2); e.preferences.add(s1);}
		}
		//now on to the schools' preferences
		Arrays.sort(W_1); Arrays.sort(W_2);
		for(int i=n-1; i>=0; i--) {
			double w1 = W_1[i]; double w2 = W_2[i];
			s1.preferences.add(qualities_1.get(w1));
			s2.preferences.add(qualities_2.get(w2));
		}
		this.match = new Matching(schools,students);
	}
	public static void main(String[] args) {
		Instance2 test1 = new Instance2(20);
		test1.match.groupQuotaMatch();
		System.out.println(test1.match.toString());
		Capacity c = new Capacity();
		test1.match.fixedPoint(c);
		System.out.println("Fixed point approach");
		System.out.println(test1.match.toString());
	}

}
