package com.ls3.oie.bootstrapping;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class test {

	public static HashSet<extractedPattern> PatternList(String pFile){
		HashSet<extractedPattern> result =new HashSet<extractedPattern>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(pFile));	
			String line = null;
			int id=1;;
			while((line = br.readLine()) != null){
				line = line.trim();
				String formattedStr = line.replaceAll("\t", "-tab-");
				String[] arr1=formattedStr.split("-tab-");
				
				String label=String.valueOf(id)+arr1[0];
				
				String[] arr2=arr1[1].split(", ");
				String arg1=arr2[0].replace("(", "");
				String rel=arr2[1];
				String arg2_temp=arr2[2].replace(")","");
				String[] arr_arg2=arg2_temp.split(" ");
				String arg2="";
				for (int i = 0; i < arr_arg2.length-1; i++) {
					arg2=arg2+" "+arr_arg2[i];
				}
				
				extractedPattern patterItem=new extractedPattern(arg1.trim(), rel.trim(), arg2.trim(),label);
				result.add(patterItem);
				id++;
			}
		}
		
		catch (IOException e){
			System.err.println(e.getMessage());
		}
		return result;
	}
	
	public static List<String> PatternListWithScore(String pFile){
		List<String> result =new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(pFile));	
			String line = null;
			int id=1;;
			while((line = br.readLine()) != null){
				line = line.trim();
				String formattedStr = line.replaceAll("\t", "-tab-");
				String[] arr1=formattedStr.split("-tab-");
				
				String label=String.valueOf(id)+arr1[0];
				
				String[] arr2=arr1[1].split(", ");
				String arg1=arr2[0].replace("(", "");
				String rel=arr2[1];
				String arg2_temp=arr2[2].replace(")","");
				String[] arr_arg2=arg2_temp.split(" ");
				String arg2="";
				for (int i = 0; i < arr_arg2.length-1; i++) {
					arg2=arg2+" "+arr_arg2[i];
				}
				String record=arr_arg2[arr_arg2.length-1]+" "+arg1.trim()+" "+rel.trim()+" "+arg2.trim();
				result.add(record);
				id++;
			}
		}
		
		catch (IOException e){
			System.err.println(e.getMessage());
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		
		String outputFile="data\\bootstrapping\\demo\\OrderSelectedPatterns_label.txt";
		FileOutputStream fo = new FileOutputStream(outputFile);
	    OutputStreamWriter out = new OutputStreamWriter(fo, "utf-8");
		
		String file="data\\bootstrapping\\demo\\selectedPatterns_label.txt";
		List<String>exPattern=PatternListWithScore(file);
		//HashSet<extractedPattern> exPattern=PatternList(file);
		Iterator iter=exPattern.iterator();
		while (iter.hasNext()) {
			String line=(String)iter.next();
			System.out.println(line);
		   	out.write(line);
			out.write("\n");		
		}
		
		out.close();
	}

}
