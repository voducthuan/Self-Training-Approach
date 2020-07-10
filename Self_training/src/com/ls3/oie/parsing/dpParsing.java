package com.ls3.oie.parsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ls3.oie.tools.txtNormalization;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeFactory;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class dpParsing {
	
	// Tree parsing to dependency 
	public static Collection<TypedDependency> dpTree(Tree pTree){
		
	    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(pTree);
	    Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
	    return tdl;
		
		
//		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
//	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
//	    GrammaticalStructure gs = gsf.newGrammaticalStructure(pTree);
//	    Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
//		return tdl;
	}
	
	// convert dp node to dpItem
	public static dpItem dpNodeTodpItem(String pDpNode){
		dpItem result=new dpItem();
		String dpNode=pDpNode.replace(")", "").replace("(", "#1#");
		String[] dpNodeSplit=dpNode.split("#1#");
		result.setTypeRel(dpNodeSplit[0]);
		String[] wordlist=dpNodeSplit[1].split(", ");
		String[] part1=wordlist[0].split("-");
		result.setArg1(part1[0]);
		result.setPos1(part1[1]); 
		String part2[]=wordlist[1].split("-");
		result.setArg2(part2[0]);
		result.setPos2(part2[1]);
		return result;
		
	}
	
	// Adding on 31/8/2015
	public static void checkingPossissive(){
		String sentence="Mr. Eskandarian , who resigned his Della Femina post in September , becomes chairman and chief executive of Arnold.";
		Tree testtree=nodeParsing.treeParser(sentence);
		Collection<TypedDependency> dpTree=dpTree(testtree);
		
		// get Possessive node
		
		Tree possissiveTree= new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		for (Tree subtree:testtree) {
			if (nodeParsing.checkNodePossessiveValuePRPinNP(subtree)==1) {
				possissiveTree=subtree;
			}
		}
		
		System.out.println(possissiveTree);
		
		List<dpItem> testdpItemList=new ArrayList<dpItem>();
		testdpItemList=dpItemlistFromTree(possissiveTree, dpTree);
		for (int i = 0; i < testdpItemList.size(); i++) {
			System.out.println("-------------------");
			System.out.println(testdpItemList.get(i).getArg1());
			System.out.println(testdpItemList.get(i).getTypeRel());
			System.out.println(testdpItemList.get(i).getArg2());
			System.out.println("-------------------");
		}
	}
	// Adding on 2/9/2015
	// List of dpItems converting from standard tree  (NNPPNP or Possessive trees)
	// pTree is subnode
	public static List<dpItem> dpItemlistFromTree(Tree pTree, Collection<TypedDependency> dpTree){
		List<dpItem> result=new ArrayList<dpItem>();
		String[] subSentences=txtNormalization.stringtoArray(nodeParsing.treeLeaves(pTree));
		
		Iterator iter=dpTree.iterator();
		for (int i = 0; iter.hasNext(); i++) {
			String dpNode=(String)iter.next().toString();
			dpItem pDPItem=dpNodeTodpItem(dpNode);
			for (int j = 0; j < subSentences.length; j++) {
				if ((pDPItem.getArg1().toLowerCase().equals(subSentences[j].toLowerCase()))
						||(pDPItem.getArg2().toLowerCase().equals(subSentences[j].toLowerCase()))) {
						
					result.add(pDPItem);
					
				}
			}
			
		}
		
		return result;
	}
	
	// Adding on 3/9/2015
	// List of dpItem from dpTree
	
	public static List<dpItem> dpItemList(Collection<TypedDependency> pDPTree){
		List<dpItem> result=new ArrayList<dpItem>();
		Iterator iter=pDPTree.iterator();
		for (int i = 0; iter.hasNext(); i++) {
			String dpNode=(String)iter.next().toString();
			dpItem pDPitem=dpNodeTodpItem(dpNode);
			result.add(pDPitem);
		}
		return result;
	}
	
	// Extract dpItem with relation "nsubj"
	public static List<dpItem> ListofSjbWords(List<dpItem> pDPItemList){
		List<dpItem> result=new ArrayList<dpItem>();
		for (int i = 0; i < pDPItemList.size(); i++) {
			if (pDPItemList.get(i).getTypeRel().equals("nsubj")) {
				result.add(pDPItemList.get(i));
			}
		}
		return result;
	}
	
	// Extract dpItem with relation "dobj"
	// Adding on 9/9/2015
	public static List<dpItem> ListofOjbWords(List<dpItem> pDPItemList){
		List<dpItem> result=new ArrayList<dpItem>();
		for (int i = 0; i < pDPItemList.size(); i++) {
			if (pDPItemList.get(i).getTypeRel().equals("dobj")) {
				result.add(pDPItemList.get(i));
			}
		}
		return result;
	}
	
	
	public static void main(String args[]){
		//checkingPossissive();
		
		String sentence="He stepped out of his Mercedes and stood among dozens of other stupefied commuters , all staring helplessly at the smoke and flames in the distance .";
		Tree testtree=nodeParsing.treeParser(sentence);
		Collection<TypedDependency> dpTree=dpTree(testtree);
		
		List<dpItem> listDP=dpItemList(dpTree);
		List<dpItem> sjbList=ListofSjbWords(listDP);
		List<dpItem> objList=ListofOjbWords(listDP);
		for (int i = 0; i < sjbList.size(); i++) {
			dpItem dp=sjbList.get(i);
			System.out.println("----------");
			System.out.println(dp.getArg1());
			System.out.println(dp.getArg2());
			System.out.println("----------");
		}
		System.out.println("aaaaaaaaaaaaaaaa");
		for (int i = 0; i < objList.size(); i++) {
			dpItem dp=objList.get(i);
			System.out.println("----------");
			System.out.println(dp.getArg1());
			System.out.println(dp.getArg2());
			System.out.println("----------");
		}
		
	}
}
