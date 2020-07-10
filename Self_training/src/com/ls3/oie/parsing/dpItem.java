package com.ls3.oie.parsing;

public class dpItem {
	private String typeRel;
	private String arg1;
	private String pos1;
	private String arg2;
	private String pos2;
	
	public dpItem(){
		this.typeRel="";
		this.arg1="";
		this.pos1="";
		this.arg2="";
		this.pos2="";
	}

	public String getTypeRel() {
		return typeRel;
	}

	public void setTypeRel(String typeRel) {
		this.typeRel = typeRel;
	}

	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public String getPos1() {
		return pos1;
	}

	public void setPos1(String pos1) {
		this.pos1 = pos1;
	}

	public String getArg2() {
		return arg2;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public String getPos2() {
		return pos2;
	}

	public void setPos2(String pos2) {
		this.pos2 = pos2;
	}
	
	
	
}
