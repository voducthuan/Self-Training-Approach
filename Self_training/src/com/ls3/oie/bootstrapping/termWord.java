package com.ls3.oie.bootstrapping;


public class termWord implements Comparable<termWord> {

	
	private String term;
	private double score;
	
	public double getScore() {
		return score;
	}
	public void setScore(double pScore) {
		this.score = pScore;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String pTerm) {
		this.term = pTerm;
	}
	public termWord(){
		term = "";
	}
	public termWord(String s){
		term = s;
	}
	
	@Override 
	/**
	 * Two nouns are considered equal so long as they contain the same sequence of characters, case insensitve.
	 */
	public boolean equals(Object o){
		if(this == o) return true;
		
		if((o instanceof termWord) && ((termWord) o).term.equalsIgnoreCase(term))
			return true;
		return false;
	}
	
	/**
	 * Calls the hashCode method on the String contained by this Noun, case insensitive.
	 */
	public int hashCode(){
		return term.toLowerCase().hashCode();
	}
	
	@Override
	/**
	 * Calls the compareTo method on the String that this Noun represents, case insensitive. Nouns will therefore be sorted
	 * alphabetically. 
	 */
	public int compareTo(termWord n2) {
		return term.toLowerCase().compareTo(n2.term.toLowerCase());
	}
	
	@Override
	/**
	 * Returns the string represented by the Noun.
	 */
	public String toString(){
		return term;
	}

}
