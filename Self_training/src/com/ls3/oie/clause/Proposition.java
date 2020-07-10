
package com.ls3.oie.clause;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Stores a proposition.
 * 
 * @date $LastChangedDate: 2013-04-24 11:54:36 +0200 (Wed, 24 Apr 2013) $
 * @version $LastChangedRevision: 741 $ */
public class Proposition {
	
	/** Constituents of the proposition */
	List<String> constituents = new ArrayList<String>();
	/** Position of optional constituents */
	Set<Integer> optional = new HashSet<Integer>();
	
	// TODO: types of constituents (e.g., optionality)
	// sentence ID etc.
	
	public Proposition() {
	}

	/** Returns the subject of the proposition */
	public String subject() {
		return constituents.get(0);
	}
	
	// added on 20 Sept. 2015
	public void setSubject(String pSub) {
		//constituents.remove(0);
		constituents.add(0, pSub);
		
		
	}
	
	/** Returns the relation of the proposition */
	public String relation() {
		return constituents.get(1);
	}
	
	// added on 20 Sept. 2015
	public void setRelation(String pRel) {
		//constituents.remove(1);
		constituents.add(1, pRel);
	}
	
	/** Returns a constituent in a given position*/
	public String argument(int i) {
		return constituents.get(i+2);
	}
	
	// added on 20 Sept. 2015
	public void setArg(String pArg) {
		//constituents.remove(2);
		constituents.add(2, pArg);
	}
	
	/** Returns the number of arguments*/
	public int noArguments() {
		return constituents.size() -2;
	}
	
	/** Checks if an argument is optional*/
	public boolean isOptionalArgument(int i) {
		return optional.contains(i+2);
	}
	
	// Added on 20 Sept. 2015
	
	public void setProposition(List<String> pPro){
		constituents=pPro;
	}
	
	//----------------------
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String sep = "(";
		for (int i=0; i<constituents.size(); i++) {
			String constituent = constituents.get(i);
			sb.append(sep);
			sep = ", ";
			sb.append("\"");
			sb.append(constituent);
			sb.append("\"");
			if (optional.contains(i)) {
				sb.append("?");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	public String toStringNew(){
		String result="";
		String temp="";
		for (int i=0; i<constituents.size()-1; i++) {
			String constituent = constituents.get(i);
			temp=temp+constituent+"\t";
		}
		String last=constituents.get(constituents.size()-1);
		result=temp+last;
		
		return result;
	}
	
	// Update on 10 June 2018
	public int isContain(Proposition pProp){
		int result=0;
		// get arguments
		String arg="";
		if (this.noArguments()>0) {
			arg=this.argument(0).toLowerCase();
		}
		
		String externalArg="";
		if (pProp.noArguments()>0) {
			externalArg=pProp.argument(0).toLowerCase();
		}
		
		String _sub1=this.subject().toLowerCase();
		String sub1=_sub1.replace("the ","");
		String _sub2=pProp.subject().toLowerCase();
		String sub2=_sub2.replace("the ","");
        /*
		System.out.println("-----------------");
		System.out.println(sub1);
		System.err.println(sub2);
		System.out.println("-----------------");		
		*/
		if (sub1.toLowerCase().indexOf(sub2.toLowerCase())==0
				||sub2.toLowerCase().indexOf(sub1.toLowerCase())==0
				||arg.toLowerCase().indexOf(externalArg.toLowerCase())==0
				||externalArg.toLowerCase().indexOf(arg.toLowerCase())==0
				) {

				result=1;
		}
		
		return result;
	}
	
	public int isContainFull(Proposition pProp){
		int result=0;
		// get arguments
		String arg="";
		if (this.noArguments()>0) {
			arg=this.argument(0).toLowerCase();
		}
		
		String externalArg="";
		if (pProp.noArguments()>0) {
			externalArg=pProp.argument(0).toLowerCase();
		}
		
		String _sub1=this.subject().toLowerCase();
		String sub1=_sub1.replace("the ","");
		
		String _sub2=pProp.subject().toLowerCase();
		String sub2=_sub2.replace("the ","");
		
		if (sub1.toLowerCase().indexOf(sub2.toLowerCase())!=-1
			||sub1.toLowerCase().indexOf(externalArg.toLowerCase())!=-1
			||sub2.toLowerCase().indexOf(sub1.toLowerCase())!=-1
			||sub2.toLowerCase().indexOf(arg.toLowerCase())!=-1
			||arg.toLowerCase().indexOf(sub2.toLowerCase())!=-1
			||arg.toLowerCase().indexOf(externalArg.toLowerCase())!=-1
			||externalArg.toLowerCase().indexOf(sub1.toLowerCase())!=-1
			||externalArg.toLowerCase().indexOf(arg.toLowerCase())!=-1
			) {
			result=1;
		}
		
		
		return result;
	}
	
	
	@Override
	public Proposition clone() {
		Proposition clone = new Proposition();
		clone.constituents = new ArrayList<String>(this.constituents);
		clone.optional = new HashSet<Integer>(this.optional);
		return clone;
	}
}
