package com.ls3.oie.parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ls3.oie.tools.txtNormalization;

import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeFactory;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class mainProcess {
	
	// Load txt file then store in String list
	public static List<String> LoadFile(String fileName) throws IOException{
		
		List<String> result=new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String sentence="";
		while((sentence = br.readLine()) != null){
			result.add(sentence);	
		}
		return result;
	}
	
	public static List<tuplesItem> OIE(String sentence){
		List<tuplesItem> result=new ArrayList<tuplesItem>();
		int total=0;
		Tree testTree=nodeParsing.treeParser(sentence);
		// Depedency tree
		Collection<TypedDependency> dptree=dpParsing.dpTree(testTree);
		// List of collection of subject and object
		List<dpItem> dpItemList=dpParsing.dpItemList(dptree);
	    List<dpItem> dpSubjectItem=dpParsing.ListofSjbWords(dpItemList);
	    List<dpItem> dpObjectItem=dpParsing.ListofOjbWords(dpItemList);
		
		//initiation setting
		Tree nodeVP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		for (Tree subtree:testTree) {
			if (nodeParsing.checkNodeVP(subtree)==1) {
				nodeVP=subtree;
				break;
			}
		}
		
		for (Tree subTree:testTree) {
			// There clause
			/*
			if (nodeParsing.checkNodeSwithThrereClause(subTree)==1) {
				System.out.println("There clause found:");
				System.out.println(subTree);
				standardTree.ClauseWithThere2NPVPNP(subTree);
				System.out.println();
			}
			*/
			//Possessive clause
			if (nodeParsing.checkNodePossessiveValuePRPinNP(subTree)==1) {
				//System.out.println("possessive clause found:");
				//System.out.println(subTree);
				tuplesItem tuple=standardTree.Prossessive2NPVPNP(subTree,nodeVP);
				result.add(tuple);
				//System.out.println(tuple.toString());
				//System.out.println();
			}
			//NPPPNP clause
			
			if ((nodeParsing.checkNodekNPPPNP(subTree)==1)
					&&(nodeParsing.checkNodekNPPPNPwithNNing(subTree)!=1)
					&&(nodeParsing.checkNodekNPPPNPhasRelationWithCoreWords(subTree, dpSubjectItem)!=1)
					&&(nodeParsing.checkNodekNPPPNPhasRelationWithCoreWords(subTree, dpObjectItem)!=1)) {
				//System.out.println("NPPPNP clause found:");
				//System.out.println(subTree);
				tuplesItem tuple=standardTree.NPPPNP2NPVPNP(subTree, nodeVP);
				result.add(tuple);
				//System.out.println(tuple.toString());
				//System.out.println();
			}
			
			/*
			//NNingPPNP clause
			if ((nodeParsing.checkNodekNPPPNP(subTree)==1)&&(nodeParsing.checkNodekNPPPNPwithNNing(subTree)==1)) {
				System.out.println("NNingPPNP found:");
				System.out.println(subTree);
				standardTree.NPPPNPWithNNing2NPVPNP(subTree, nodeVP);
				System.out.println();
			}
			*/
		}
		return result;
	}
	
	public static void main(String args[]) throws IOException{
		List<tuplesItem> listTup=new ArrayList<tuplesItem>();
		String fileInput="data\\reverb\\test.txt";
		List<String> sentences=LoadFile(fileInput);
		for (int i = 0; i < sentences.size(); i++) {
			System.out.println(i+". "+sentences.get(i));
			listTup=OIE(sentences.get(i));
		}
		
		/*
		// There clause testing only
		for (int i = 0; i < sentences.size(); i++) {
			String sentence=sentences.get(i).toLowerCase();
			if (sentence.indexOf("there")!=-1) {
				System.out.println(i+": "+ sentence);
				OIEoutput(sentences.get(i));
			}
		}
		*/
		/*
		// Possessive clause testing only
		for (int i = 0; i < sentences.size(); i++) {
			String sentence=sentences.get(i).toLowerCase();
			if (sentence.indexOf("his")!=-1||sentence.indexOf("her")!=-1
					||sentence.indexOf("their")!=-1||sentence.indexOf("my")!=-1) {
				System.out.println(i+": "+sentences.get(i));
				OIEoutput(sentences.get(i));
			}
		}
		*/
	}
	
}
