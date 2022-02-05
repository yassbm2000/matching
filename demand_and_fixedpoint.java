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
	
