package com.ls3.oie.clause;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.ls3.oie.parsing.tuplesItem;
import com.ls3.oie.tools.txtNormalization;

public class mainProcess {
	
	
	public static void output(String content) throws IOException{
		
		String outputFile="";
		FileOutputStream fo = new FileOutputStream(outputFile);
	    OutputStreamWriter out = new OutputStreamWriter(fo, "utf-8");
		
	   	out.write("");
		out.write(" ");
		out.write("\n");		
		out.close();
	}
	// -- added on 18 Sept. 2015 --
	// remove wrong possessive clause
	// remove wrong there clause
	
	public static void process1() throws IOException{
		// update on 6 Oct
		double scorce=0;
		// Output txt file
		String outputFile="data\\test\\questions_clauses.txt";
		FileOutputStream fo = new FileOutputStream(outputFile);
	    OutputStreamWriter out = new OutputStreamWriter(fo, "utf-8");
		
		ClausIE clausIE = new ClausIE();
        clausIE.initParser();
        String fileInput="data\\test\\questions.txt"; 
        //String fileInput="data\\matrix\\errors.txt";
		List<String> sentences=txtNormalization.LoadFile(fileInput);
		for (int i = 0; i < sentences.size(); i++) {
			
			System.out.println(i+". "+sentences.get(i));
			out.write(i+". "+sentences.get(i));
			out.write("\n");
			// parse tree
	        clausIE.parse(sentences.get(i));
	        clausIE.detectClauses();
	        clausIE.generatePropositions();
	        
	        
	        for (Clause clause : clausIE.getClauses()) {
	            System.out.println(clause.toString(clausIE.getOptions()));
	        }
	        System.out.println("ClausIE Propositions: ");
	        out.write("ClausIE Propositions: ");
	        out.write("\n");
	        for (Proposition prop : clausIE.getPropositions()) {
	            System.out.println(prop.toString()+" "+clausIE.getLP().getPCFGScore());
	            out.write(prop.toString()+" "+clausIE.getLP().getPCFGScore());
	            out.write("\n");
	            scorce=clausIE.getLP().getPCFGScore();
	        }
	        /*
	        // added on 19 Sept. 2015
	        clausIE.updatePropositions();
	        //--------------
	        // generate propositions
	        System.out.println("Updated Propositions: ");
	        out.write("Updated Propositions: ");
	        out.write("\n");
	        for (Proposition prop : clausIE.getPropositions()) {
	            System.out.println(sep + prop.toString());
	            sep = "         ";
	            out.write(sep + prop.toString()+" "+clausIE.getLP().getPCFGScore());
	            out.write("\n");
	        }
	        // updated on 25 Sept.
	        System.out.println("New Propositions:");
	        out.write("New Propositions:");
	        out.write("\n");
	        List<tuplesItem> listtupItems= new ArrayList<tuplesItem>();
	        listtupItems=com.ls3.oie.parsing.mainProcess.OIE(sentences.get(i));
	        for (int j = 0; j < listtupItems.size(); j++) {
	        	System.out.println(listtupItems.get(j).toString());
	            sep = "         ";
	            out.write(sep + listtupItems.get(j).toString()+" "+scorce);
	            out.write("\n");
			}
	        */
	        
		}
		out.close();
		
	}

	public static void process2() throws IOException{
		// update on 6 Oct
		double scorce=0;
		// Output txt file
		String outputFile="data\\test\\10_sentences_output.txt";
		FileOutputStream fo = new FileOutputStream(outputFile);
	    OutputStreamWriter out = new OutputStreamWriter(fo, "utf-8");
		
		ClausIE clausIE = new ClausIE();
        clausIE.initParser();
		String fileInput="data\\test\\10_sentences.txt"; 
		List<String> sentences=txtNormalization.LoadFile(fileInput);
		for (int i = 0; i < sentences.size(); i++) {
			System.out.println(i+". "+sentences.get(i));
			out.write(i+". "+sentences.get(i));
			out.write("\n");
			// parse tree
	        clausIE.parse(sentences.get(i));
	        clausIE.detectClauses();
	        clausIE.generatePropositions();
	        
			clausIE.updatePropositions();
	        // clause relation
			for (Proposition prop : clausIE.getPropositions()) {
	            System.out.println(prop.toString());
	            out.write(prop.toString()+" "+clausIE.getLP().getPCFGScore());
	            out.write("\n");
	            scorce=clausIE.getLP().getPCFGScore();
	        }
	        // new clause relation
	        List<tuplesItem> listtupItems= new ArrayList<tuplesItem>();
	        listtupItems=com.ls3.oie.parsing.mainProcess.OIE(sentences.get(i));
	        for (int j = 0; j < listtupItems.size(); j++) {
	        	System.out.println(listtupItems.get(j).toString());
	            out.write(listtupItems.get(j).toString()+" "+scorce);
	            out.write("\n");
			}
			
		}
		out.close();
	}
	
	public static void main(String args[]) throws IOException{
		//process1();
		process2();
	}
}
