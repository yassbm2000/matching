public HashSet<Student> demand(School s, HashMap<School, Integer>p){
		
		HashSet<Student> demand = new HashSet<Student>();
		for ( Student i: this.students ){
		
		boolean favoriteSchool=true;
		for (School sco: this.schools){
			if ((!s.equals(sco)) && sco.compare(i,sco.preferences.get(sco.preferences.size() -p.get(sco)))  && i.compareSchools(sco,s) )
			{favoriteSchool = false;break;} 
		}//add a comparison method "compareSchools" to Student
		if ( s.compare(i,s.preferences.get(s.preferences.size()-p.get(s)))   && favoriteSchool ) {
				demand.add(i); 
			    
				
				}
		}
		
		
			return demand;
		}







public void fixedPoint(Constraint c){
			for ( School s : this.schools) {s.admittedInGroup=new HashMap<Integer,HashSet<Student>>();}
			// réinitialisation
		   this.match = new HashMap<School,HashSet<Student>>(); //reset the match 
		   HashMap<School, Integer> p = new HashMap<School, Integer>(); 		   
		   for (School s : this.schools){p.put(s,1);}// just an arbitrary cutoff profile as our starting point
		   
		   
		   
		   
		   int count = 0;
		   boolean fixedPointIndicator = false;
		   while (fixedPointIndicator==false ){  //iterate until you land on a fixed point, you're guaranteed to 
			   count ++;
			   
			   fixedPointIndicator = true;
			   
			   for (School s: schools){
				   s.admittedInGroup=new HashMap<Integer,HashSet<Student>>();
				   s.admitted=demand(s,p);
				    for ( Student i: s.admitted) {
				    	int gi = i.group;
				    	if (s.admittedInGroup.get(gi)==null) {HashSet<Student> tempo = new HashSet<Student>();
				    	tempo.add(i); s.admittedInGroup.put(gi, tempo);} 
				    	else { HashSet<Student> temphash = s.admittedInGroup.get(i.group);
				    	temphash.add(i); s.admittedInGroup.put(gi, temphash);
				    	}
				    	
				    	// beware of null pointers
				       }
				   
				   if (!c.constraint(s,this.demand(s,p))){ //verify if the constraint is met
					  int ps = p.get(s);
					  if (ps==s.preferences.size()) {ps=1;}
					  else {ps++;}
					  p.put(s,ps); //modify the cutoff
					  fixedPointIndicator = false;} //that was not a fixed point
				   
				   s.admitted=demand(s,p);
				   
				    
				       
				       } //now that you got your fixed point p, derive the matching from it	
			    }
		   for (School s: schools){
				this.match.put(s, demand(s,p));

				}
		  System.out.println( count);
				       
		}
	
