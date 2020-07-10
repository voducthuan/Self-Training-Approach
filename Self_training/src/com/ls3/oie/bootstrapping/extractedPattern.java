package com.ls3.oie.bootstrapping;

import java.util.ArrayList;
import java.util.List;

import com.ls3.oie.tools.txtNormalization;


public class extractedPattern implements Comparable<extractedPattern> {
	
	public final String content;
	private String id;
	private List<termWord> arg1;
	private List<termWord> rel;
	private List<termWord> arg2;
	
	private double arg1Score;
	private double arg2Score;
	private double relScore;
	private double score;
	
	public extractedPattern(){
		id="0";
		content="";
		arg1=new ArrayList<termWord>();
		arg2=new ArrayList<termWord>();
		rel=new ArrayList<termWord>();
		arg1Score=0;
		arg2Score=0;
		relScore=0;
		score=-1;
	}

	public extractedPattern(extractedPattern p){
		arg1=new ArrayList<termWord>();
		arg2=new ArrayList<termWord>();
		rel=new ArrayList<termWord>();
		id=p.getId();
		content=p.getContent();
		arg1=p.getArg1();
		arg2=p.getArg2();
		rel=p.getRel();
		arg1Score=p.getArg1Score();
		arg2Score=p.getArg2Score();
		relScore=p.getRelScore();
		score=p.getScore();
		
	}
	
	public extractedPattern(String pArg1, String pRel, String pArg2, String pID){
		id=pID;
		content=pArg1+" "+pRel+" "+pArg2;
		arg1=new ArrayList<termWord>();
		arg2=new ArrayList<termWord>();
		rel=new ArrayList<termWord>();
		List<String> Arg1List=txtNormalization.stringtoList(pArg1);
		for (int i = 0; i < Arg1List.size(); i++) {
			termWord word=new termWord(Arg1List.get(i));
			this.arg1.add(word);
		}
		
		List<String> Arg2List=txtNormalization.stringtoList(pArg2);
		for (int i = 0; i < Arg2List.size(); i++) {
			termWord word=new termWord(Arg2List.get(i));
			this.arg2.add(word);
		}
		
		List<String> RelList=txtNormalization.stringtoList(pRel);
		for (int i = 0; i < RelList.size(); i++) {
			termWord word=new termWord(RelList.get(i));
			this.rel.add(word);
		}
		
		arg1Score=0;
		arg2Score=0;
		relScore=0;
		score=-1;
		
	}
	
	
	public List<termWord> getArg1() {
		return arg1;
	}

	public void setArg1(ArrayList<termWord> arg1) {
		this.arg1 = arg1;
	}

	public List<termWord> getRel() {
		return rel;
	}

	public void setRel(ArrayList<termWord> rel) {
		this.rel = rel;
	}

	public List<termWord> getArg2() {
		return arg2;
	}

	public void setArg2(ArrayList<termWord> arg2) {
		this.arg2 = arg2;
	}

	public void setArg1(List<termWord> arg1) {
		this.arg1 = arg1;
	}

	public void setRel(List<termWord> rel) {
		this.rel = rel;
	}

	public void setArg2(List<termWord> arg2) {
		this.arg2 = arg2;
	}

	public String getContent() {
		return content;
	}

	public void setArg1Score(double arg1Score) {
		this.arg1Score = arg1Score;
	}

	public void setArg2Score(double arg2Score) {
		this.arg2Score = arg2Score;
	}

	public void setRelScore(double relScore) {
		this.relScore = relScore;
	}

	public double getArg1Score(){
		return arg1Score;
	}
	
	public double getArg2Score(){
		return arg2Score;
	}
	
	public double getRelScore(){
		return relScore;
	}
	
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String pId) {
		this.id = id;
	}

	@Override 
	/**
	 * Two Pattern's are considered equal if they represent the same sequence of characters, case insensitive. 
	 */
	public boolean equals(Object o){
		if(this == o)
			return true;
		if((o instanceof extractedPattern) && ((extractedPattern) o).content.equalsIgnoreCase(content))
			return true;
		return false;
	}
	
	/**
	 * Resets the score of the Pattern to -1.0
	 */
	public void clearScore(){
		arg1Score = 0;
		arg2Score = 0;
		relScore = 0;
		score=-1;
	}
	
	/**
	 * Sorts Pattern in descending order, from highest score to lowest score. See the main class notes about how this affects
	 * collections that Pattern's are stored in.
	 */
	@Override
	public int compareTo(extractedPattern p2) {
		double score1=score;
		double score2=p2.getScore();
		
		if(score1 < score2)
			return -1;
		else if(score1 > score2)
			return 1;
		else return 0;
	}
	
	
}
