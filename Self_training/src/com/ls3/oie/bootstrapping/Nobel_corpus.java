package com.ls3.oie.bootstrapping;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

public class Nobel_corpus {
	
	
	public static void SplitCat() throws IOException{
		//Out File 1 
		String outputFile="data\\bootstrapping\\dare\\cat1.txt";
		FileOutputStream fo = new FileOutputStream(outputFile);
	    OutputStreamWriter out = new OutputStreamWriter(fo, "utf-8");
		
	    //Out File 2
	    String outputFile2="data\\bootstrapping\\dare\\cat2.txt";
		FileOutputStream fo2 = new FileOutputStream(outputFile2);
	    OutputStreamWriter out2 = new OutputStreamWriter(fo2, "utf-8");
		
	    
		String file="data\\bootstrapping\\dare\\all.txt";
		
		BufferedReader br = new BufferedReader(new FileReader(file));	
		String line = null;
		while((line = br.readLine()) != null){
			//if (!line.isEmpty()&&line.indexOf("dependence tree")==-1) {
			if (line.indexOf("- 1999 ")!=-1||line.indexOf("- 2000 ")!=-1||line.indexOf("- 2001 ")!=-1||line.indexOf("- 2002 ")!=-1
				||line.indexOf("- 2003 ")!=-1||line.indexOf("- 2004 ")!=-1||line.indexOf("- 2005 ")!=-1) {
				System.out.println(line);
				out.write(line);
				out.write("\n");
			}else{
				System.out.println(line);
				out2.write(line);
				out2.write("\n");
			}
			
		}
		out.close();
		out2.close();
		
	}
	
	public static void ReviseRecord() throws IOException{
		
		String file="data\\bootstrapping\\dare\\cat2_label.txt";
		BufferedReader br = new BufferedReader(new FileReader(file));	
		String line = null;
		int num=0;
		while((line = br.readLine()) != null){
			String[] arr=line.split(",");
			if (arr.length>3) {
				System.out.println(num);
			}
			num++;
		
		}
		
		
	}
	

	public static void main(String args[]) throws IOException{
		
		ReviseRecord();
	
	}
}
