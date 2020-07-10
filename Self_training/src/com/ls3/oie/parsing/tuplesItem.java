package com.ls3.oie.parsing;

public class tuplesItem {

	private String arg1;
	private String rel;
	private String arg2;
	
	public tuplesItem(){
		this.arg1="";
		this.rel="";
		this.arg2="";
	}
	
	public tuplesItem(String pArg1, String pRel, String pArg2){
		this.arg1=pArg1;
		this.rel=pRel;
		this.arg2=pArg2;
	}
	
	public String getArg1() {
		return arg1;
	}
	
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}
	
	public String getRel() {
		return rel;
	}
	
	public void setRel(String rel) {
		this.rel = rel;
	}
	
	public String getArg2() {
		return arg2;
	}
	
	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}
	
	@Override
	public String toString(){
		String content="("+this.getArg1()+", "+this.getRel()+", "+this.getArg2()+")";
		return content;
	}
	
	public String toStringNew(){
		String content=this.getArg1()+"\t"+this.getRel()+"\t"+this.getArg2()+")";
		return content;
	}
	
}
