package com.ls3.oie.bootstrapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.ls3.oie.tools.txtNormalization;


public class seedItem {
	
	private HashSet<termWord> arg;
	private HashSet<termWord> rel;
	
	public seedItem(){
		arg=new HashSet<termWord>();
		rel=new HashSet<termWord>();
	}
	
	public HashSet<termWord> getArg(){
		return arg;
	}
	
	public HashSet<termWord> getRel(){
		return rel;
	}
	
	public void argAddingTerm(termWord term){
		arg.add(term);
	}
	
	public void relAddingTerm(termWord term){
		rel.add(term);
	}
	
	public int checkWordinArg(termWord word){
		int result=0;
		Iterator iter=arg.iterator();
		while (iter.hasNext()) {
			termWord pTermword=(termWord)iter.next();
			if (pTermword.getTerm().equals(word.getTerm())) {  // still consider sensitive
				result=1;
			}
		}
		
		return result;
	}
	
	public int checkWordinRel(termWord word){
		int result=0;
		Iterator iter=rel.iterator();
		while (iter.hasNext()) {
			termWord pTermword=(termWord)iter.next();
			if (pTermword.getTerm().toLowerCase().equals(word.getTerm())) {  // not consider sensitive
				result=1;
			}
		}
		
		return result;
	}
	
	// Add Seed to Rel
	// input list of termWord
	// output updated Rel
	// - consider 5 cases such as "to be", "possessive have, has" and "preposition", and future tense
	public void addSeedtoRel(List<termWord> pListWords) throws IOException{
		
		List<String> pPrepoList=txtNormalization.prepositionList();
		//System.out.println(pListWords);
		for (int i = 0; i < pListWords.size(); i++) {
			termWord word=new termWord(pListWords.get(i).getTerm().toLowerCase()); // consider word sensitive
			
			if (pListWords.size()==1&&!word.getTerm().equals("have")&&!word.getTerm().equals("has")&&!word.getTerm().equals("said")
					&&!word.getTerm().equals("set")&&!word.getTerm().equals("not")&&!word.getTerm().equals("based")) {
				this.rel.add(word);
			}else{
			
				// "to be" case, e.g., is, are, was, were, be
				if (word.getTerm().equals("be")||word.getTerm().equals("is")||word.getTerm().equals("are")
					||word.getTerm().equals("was")||word.getTerm().equals("were")) {
					if (i+1<pListWords.size()&&txtNormalization.checkprepositionWord(word.getTerm().toLowerCase(), pPrepoList)==1
							&&!pListWords.get(i+1).getTerm().equals("have")&&!pListWords.get(i+1).getTerm().equals("has")&&!pListWords.get(i+1).getTerm().equals("said")
							&&!pListWords.get(i+1).getTerm().equals("set")&&!pListWords.get(i+1).getTerm().equals("not")
							&&!pListWords.get(i+1).getTerm().equals("based")) {
						
						this.rel.add(pListWords.get(i+1));
					}
				}
				
				// "has, have, had" case, e.g., have awarded...
				if (word.getTerm().equals("has")||word.getTerm().equals("have")||word.getTerm().equals("had")) {
					if (i+1<pListWords.size()&&!pListWords.get(i+1).getTerm().equals("have")&&!pListWords.get(i+1).getTerm().equals("has")&&!pListWords.get(i+1).getTerm().equals("said")
							&&!pListWords.get(i+1).getTerm().equals("set")&&!pListWords.get(i+1).getTerm().equals("not")
							&&!pListWords.get(i+1).getTerm().equals("based")) {
						this.rel.add(pListWords.get(i+1));
					}
				}
				// preposition case, e.g., "presented on"
				if (txtNormalization.checkprepositionWord(word.getTerm().toLowerCase(), pPrepoList)==1) {
					if (!pListWords.get(i).getTerm().equals("to")) {
						if (i>0) {
							this.rel.add(pListWords.get(i-1));	
						}
						
					}
				}
				// future tense case, e.g., " will win"
				if (word.getTerm().equals("will")||word.getTerm().equals("would")||word.getTerm().equals("could")||word.getTerm().equals("can")) {
					if (i+1<pListWords.size()&&!pListWords.get(i+1).getTerm().equals("have")&&!pListWords.get(i+1).getTerm().equals("has")&&!pListWords.get(i+1).getTerm().equals("said")
							&&!pListWords.get(i+1).getTerm().equals("set")&&!pListWords.get(i+1).getTerm().equals("not")
							&&!pListWords.get(i+1).getTerm().equals("based")) {
						this.rel.add(pListWords.get(i+1));
					}
				}
			}
		
		//end for
		}
	}
	
	// Add seed to Arg
	// input list of termWord
	// output update Arg
	// - consider sensitive case of word such as "New York", "John"
	
	public void addSeedtoArg(List<termWord> pListWord){
		
		for (int i = 0; i < pListWord.size(); i++) {
			termWord word=pListWord.get(i);
			
			if (txtNormalization.checkCapitalLetter(word.getTerm())) {
				if (!word.getTerm().equals("The")&&!word.getTerm().equals("He")&&!word.getTerm().equals("She")
					&&!word.getTerm().equals("They")&&!word.getTerm().equals("I")&&!word.getTerm().equals("We")
					&&!word.getTerm().equals("It")&&!word.getTerm().equals("You")&&!word.getTerm().equals("Mother")
					&&!word.getTerm().equals("Father")) {
					this.arg.add(word);
				}
			}
		}
		
	}
	
	public void updateSeed(seedItem pSeedItem){
		Iterator IterArg=pSeedItem.getArg().iterator();
		while (IterArg.hasNext()) {
			termWord word=(termWord)IterArg.next();
			this.arg.add(word);
		}
		
		Iterator IterRel=pSeedItem.getRel().iterator();
		while (IterRel.hasNext()) {
			termWord word=(termWord)IterRel.next();
			this.rel.add(word);
		}
		
	}
	
}
