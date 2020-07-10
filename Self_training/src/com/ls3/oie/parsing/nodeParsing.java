package com.ls3.oie.parsing;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.stanford.nlp.parser.lexparser.Item;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeFactory;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.ParserAnnotatorUtils;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeFactory;
import edu.stanford.nlp.util.CollectionFactory;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;

import com.ls3.oie.clause.ClausIE;
import com.ls3.oie.tools.*;

public class nodeParsing {
	
	// convert a sentence to tree parsing
	public static Tree treeParser(String sentence){
		ClausIE clausIE = new ClausIE();
        clausIE.initParser();
        clausIE.parse(sentence);
        return clausIE.getDepTree();
	}
	
	// Tree parsing to dependency 
	public static Collection<TypedDependency> dp(Tree pTree){
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(pTree);
	    Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
		return tdl;
	}
	
	
	//Check node NP in parsing tree
	public static int checkNodeNP(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.equals("NP")){
			value=1;
		} 
		return value;
	}
	
	//Check node VP in parsing tree
	public static int checkNodeVP(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.equals("VP")){
			value=1;
		} 
		return value;
	}
	
	//Check node NP in parsing tree
	public static int checkNodePP(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.indexOf("PP")!=-1){
			value=1;
		} 
		return value;
	}
	
	//Check node DT in parsing tree
	public static int checkNodeDT(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.indexOf("DT")!=-1){
			value=1;
		} 
		return value;
	}
	
	//Check node ROOT in parsing tree
	public static int checkNodeRoot(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.indexOf("ROOT")!=-1){
			value=1;
		} 
		return value;
	}
	
	//Check node NN in parsing tree
	public static int checkNodeNN(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.indexOf("NN")!=-1){
			value=1;
		} 
		return value;
	}
	
	//Check node NN in parsing tree
	public static int checkNodeNNP(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.indexOf("NNP")!=-1){
			value=1;
		} 
		return value;
	}
	
	//Check node NNS in parsing tree
	public static int checkNodeNNS(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.equals("NNS")){
			value=1;
		} 
		return value;
	}
	
	//Check node NNS in parsing tree
	public static int checkNodeJJ(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.equals("JJ")){
			value=1;
		} 
		return value;
	}
	
	//Check node NNS in parsing tree
	public static int checkNodeCD(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.equals("CD")){
			value=1;
		} 
		return value;
	}
	
	// Added on 13 August 2015
	// - Check node containing S
	// - Check node NP containing "There"
	// - Check node VP
	// Revised on 
	
	// return 1 found and 0 not found
	

	public static int checkNodeSwithThrereClause(Tree pTree){
		int value=0;
		String NodeLable=pTree.value();
		int check1=0; // node there
		int check2=0; // node VP
		int check3=0; // node PP
		
		if (NodeLable.equals("S")){
			List<Tree> subTrees=new ArrayList<Tree>();
			subTrees=pTree.getChildrenAsList();
			for (int i = 0; i < subTrees.size(); i++) {
				Tree subTree=subTrees.get(i);
				if (checkNodeNP(subTree)==1){
					String leavesContent=nodeParsing.treeLeaves(subTree).toLowerCase();
					if (leavesContent.indexOf("there")!=-1) {
						check1=1;
					}
				}
				if (checkNodeVP(subTree)==1) {
					check2=1;
					List<Tree> subTreeNodeVP=subTree.getChildrenAsList();
					for (int j = 0; j < subTreeNodeVP.size(); j++) {
						// in case of VP->NP->PP
						if (checkNodeNP(subTreeNodeVP.get(j))==1) {
							List<Tree> subTreeNodeNP=subTreeNodeVP.get(j).getChildrenAsList();
							for (int k = 0; k < subTreeNodeNP.size(); k++) {
								if (checkNodePP(subTreeNodeNP.get(k))==1) {
									check3=1;
								}
							}
						}
						// in case of VP->VP(NP,PP)
						if (checkNodeVP(subTreeNodeVP.get(j))==1) {
							List<Tree> subTreeNodeVPVP=subTreeNodeVP.get(j).getChildrenAsList();
							for (int k = 0; k < subTreeNodeVPVP.size(); k++) {
								if (checkNodeNP(subTreeNodeVPVP.get(k))==1) {
									Tree nodeNP=subTreeNodeVPVP.get(k);
									List<Tree> nodeNPlist=nodeNP.getChildrenAsList();
									for (int l = 0; l < nodeNPlist.size(); l++) {
										if (checkNodePP(nodeNPlist.get(l))==1) {
											check3=1;
										}
									}
										
								}
							}
						}
					}	
				}
				if (checkNodePP(subTree)==1) {
					check3=1;
				}
			}
			if ((check1==1)&&(check2==1)&&(check3==1)) {
				value=1;
			}
		}
		return value;
	}

	// Added on 13 August 2015
	// - Check node containing S
	// - Check node NP containing "There"
	// - Check node VP
	// return 1 found and 0 not found

	public static int checkNodeSwithThrereClause_old(Tree pTree){
		int value=0;
		String NodeLable=pTree.value();
		int check1=0;
		int check2=0;
		
		if (NodeLable.equals("S")){
			List<Tree> subTrees=new ArrayList<Tree>();
			subTrees=pTree.getChildrenAsList();
			for (int i = 0; i < subTrees.size(); i++) {
				Tree subTee=subTrees.get(i);
				if (checkNodeNP(subTee)==1){
					String leavesContent=nodeParsing.treeLeaves(subTee).toLowerCase();
					if (leavesContent.indexOf("there")!=-1) {
						check1=1;
					}
				}
				
				if (checkNodeVP(subTee)==1) {
					check2=1;
				}
			}
			if ((check1==1)&&(check2==1)) {
				value=1;
			}
		}
		return value;
	}
	
	//Check Possessive node with value "PRP$" in NP 
	//revised on 8/9/2015
	//-Filtering poss+V_ing e.g., my writing
	public static int checkNodePossessiveValuePRPinNP(Tree pTree){
		int value=0;
		if (checkNodeNP(pTree)==1){
			String allContent=treeLeaves(pTree);
			String NodeLable=pTree.getChild(0).value();
			String possContent=treeLeaves(pTree.getChild(0));
			if (NodeLable.indexOf("PRP$")!=-1){
				if (!possContent.toLowerCase().equals("its")&&allContent.indexOf("ing")==-1) {
					value=1;
				}
			}
		}
		return value;
	}
	
	//Check Possessive node with value "PRP$" in NP 
	public static int checkNodePossessiveValuePRPinNP_old(Tree pTree){
		int value=0;
		if (checkNodeNP(pTree)==1){
			String NodeLable=pTree.getChild(0).value();
			String possContent=treeLeaves(pTree.getChild(0));
			if (NodeLable.indexOf("PRP$")!=-1){
				if (!possContent.toLowerCase().equals("its")) {
					value=1;
				}
			}
		}
		return value;
	}
	
	//Check Possessive node with value "PRP$" 
	public static int checkNodePossessiveValuePRP(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.indexOf("PRP$")!=-1){
			value=1;
		} 
		return value;
	}
	
	//Check Possessive node with IN is "of" (not yet used)
	public static int checkNodePPwithINisOf(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.equals("PP")){
			Tree subtree=pTree.getChild(0);
			if (subtree.getLeaves().toString().toLowerCase().equals("of")) {
				value=1;
			}
		} 
		return value;
	}
	
	//Check Possessive node with TO is "to"
	public static int checkNodePPwithINisTo(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.equals("PP")){
			Tree subtree=pTree.getChild(0);
			if (subtree.getLeaves().toString().toLowerCase().equals("[to]")) {
				value=1;
			}
		} 
		return value;
	}
	
	//Check Possessive node with IN is "for"
	public static int checkNodePPwithINisFor(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if (NodeLable.equals("PP")){
			Tree subtree=pTree.getChild(0);
			if (subtree.getLeaves().toString().toLowerCase().equals("[for]")) {
				value=1;
			}
		} 
		return value;
	}
	
	//Check NN-ing node 
	public static int checkNodeNNing(Tree pTree){
		int value=0;
		String NodeLable=pTree.label().value();
		if ((NodeLable.equals("NN"))&&(pTree.getChild(0).getLeaves().toString().toLowerCase().indexOf("ing")!=-1)){
			value=1;
		} 
		return value;
	}
	
	/*
	 * Xac dinh vi tri nut - dung xac dinh cu.m nhan trung tam
	 * @TreeParser
	 * @Lable trung tam
	 */
	public static int LabelPosition(Tree parseTree, String pLablel){
		int result =0;
		Iterator iter;
		iter=parseTree.iterator();
		for (int i = 0; iter.hasNext(); i++) {
			Tree tree=(Tree)iter.next();
			// Lay danh sach leaves cua Node
			String stringlistleaves="";
			List<Tree> listLeaves=tree.getLeaves();
			
			for (int j = 0; j < listLeaves.size(); j++) {
				Tree templeave=(Tree)listLeaves.get(j);
				stringlistleaves=stringlistleaves+templeave.nodeString()+" ";
			}
			stringlistleaves=stringlistleaves.substring(0, stringlistleaves.length()-1);
			
			// So sach leaveslist voi Lable
			// equal=> tra ve ket qua vi tri
			
			if (stringlistleaves.equals(pLablel)){
				result = i;
			}
		}
		return result;
	}
	
	public static int RootleavelistNum(Tree Ptree){
		int result=0;
		List<Tree> listLeaves=Ptree.getLeaves();
		
		for (int j = 1; j < listLeaves.size()+1; j++) {
			result=j;
		}		
		return result;
	}
	
	// Covert tree leaves to string
	public static String treeLeaves(Tree ptree){
		String result="";
		String content="";
		List<Tree> listLeaves=ptree.getLeaves();
		for (int i = 0; i < listLeaves.size(); i++) {
			Tree templleave=(Tree)listLeaves.get(i);
			content=content+templleave.nodeString()+" ";
		}
		content=content.substring(0, content.length()-1);
		//result=content.replaceAll("[(-+^:)]","").toLowerCase();
		result=content.replaceAll("[(-+^:)]",""); // remove toLowerCase on 28 Nov. 2015 
		return result;
	}
	
	// Check "number of" in NP node
	public static int checkNumberinNP(Tree pTree){ // text number only
		int result=0;
		String[] subsentence=treeLeaves(pTree).split(" ");
		String lastword=subsentence[subsentence.length-1];
		if (lastword.equals("one")||
				lastword.equals("two")||
				lastword.equals("three")||
				lastword.equals("four")||
				lastword.equals("five")||
				lastword.equals("six")||
				lastword.equals("seven")||
				lastword.equals("eight")||
				lastword.equals("nine")||
				lastword.equals("most")||
				lastword.equals("bit")
				) {
			
			result=1;
		}
		
		return result;
	} 
	
	//Return subtree from mainTree
	//@ position in mainTree
	//output subtree
	
	public static Tree getSubTree(Tree mainTree, int pos){
		Tree result=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		for (Tree subtree:mainTree) {
			if (subtree.nodeNumber(mainTree)==pos) {
				result=subtree;
			}
		}
		return result;
	}
	
	// Added on 8 August 2015
	// Check NP containing PP and NP
	// return 1 found and 0 not found
	
	public static int checkNodekNPPPNP_old(Tree pTree){
		int result=0;
		if (checkNodeNP(pTree)==1) { //check node NP
			for (Tree subtree1: pTree) { 
				if (checkNodePP(subtree1)==1&&checkNodePPwithINisTo(subtree1)!=1) { 
					for (Tree subtree2: subtree1) { //check node NP
						if (checkNodeNP(subtree2)==1) {
							result=1;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	// Added on 4 sep. 2015
	// Check NP containing PP and NP
	//     -revised by adding filtering "for why, to, one of, a bit of
	//     -revised also main procedures
	// return 1 found and 0 not found
	
	public static int checkNodekNPPPNP(Tree pTree){
		int result=0;
		if (checkNodeNP(pTree)==1) { //check node NP
			if (checkNodeNP(pTree.getChild(0))==1&&checkNumberinNP(pTree.getChild(0))!=1) { //need check containing of NP
				if (checkNodePP(pTree.getChild(1))==1&&checkNodePPwithINisTo(pTree.getChild(1))!=1) {
					//if (checkNodeNP(pTree.getChild(1).getChild(1))==1) {
					if (pTree.getChild(1)!=null) {
						if (checkNodeNP(pTree.getChild(1).getChild(1))==1) {
							result =1;
						}
					}
					
				}
			}
		}	
		return result;
	}
	
	// Added on 8 August 2015
	// Check NP containing PP and NP
	// return 1 found and 0 not found
	// Modified on 3/9/2015
	//--Check relationship "nsbjt" between NP and core subject in sentence
	// Modified on 9/9/2015
	//--Coreworks are subject termwords or ojbect termwords
	
	public static int checkNodekNPPPNPhasRelationWithCoreWords(Tree pTree, List<dpItem> pDPlist){
		int result=0;
		Tree rightNode=pTree.getChild(0);
		String coreWord=coreWoreofNodeNP(rightNode);
		
		for (int i = 0; i < pDPlist.size(); i++) {
			if (pDPlist.get(i).getArg1().toLowerCase().equals(coreWord)||pDPlist.get(i).getArg2().toLowerCase().equals(coreWord)) {
				result=1;
			}
		}
		return result;
	}
	
	// Check NP with compound noun (N-ing) containing PP and NP 
	// return 1 found and 0 not found
	
	public static int checkNodekNPPPNPwithNNing(Tree pTree){
		int result=0;
		int check1=0;
		int check2=0;
		
		if (checkNodeNP(pTree)==1) { //check node NP
			if (checkNodeNP(pTree.getChild(0))==1) {
				if (pTree.getChild(0).numChildren()>1) {
					Tree NodeNNing=pTree.getChild(0).getChild(pTree.getChild(0).numChildren()-1);
					if (checkNodeNNing(NodeNNing)==1) {
						check1=1;
					}
				}
				
			}
			
			for (Tree subtree1: pTree) { 
				if (checkNodePP(subtree1)==1) { //check not PP
					for (Tree subtree2: subtree1) { //check node NP
						if (checkNodeNP(subtree2)==1) {
							check2=1;
						}
					}
				}
			}
		}
		
		if (check1==1&&check2==1) {
			result=1;
		}
		return result;
	}
	
	public static void possDPParsing(){
		/*
		String fileInput="data\\reverb\\sentences.txt";
		List<String> sentences=LoadFile(fileInput);
		int k=1;
		for (int i = 0; i < sentences.size(); i++) {
			if (k<41) {
				String[] substr=txtNormalization.stringtoArray(sentences.get(i));
				for (int j = 0; j < substr.length; j++) {
					if ((substr[j].toLowerCase().equals("his"))
					   ||(substr[j].toLowerCase().equals("her"))
					   ||(substr[j].toLowerCase().equals("their"))
					   ||(substr[j].toLowerCase().equals("its"))
					   ||(substr[j].toLowerCase().equals("my"))) {
							System.out.println(k+". "+sentences.get(i));
							Tree testtree=nodeParsing.treeParser(sentences.get(i));
							
						    Collection<TypedDependency> dp = nodeParsing.dp(testtree);
							Iterator iter=dp.iterator();
							
							for (int m = 0; iter.hasNext(); m++) {
						    	String Node=(String)iter.next().toString();
						    	System.out.println(Node);
							}
							k++;
							break;
					}
				}
			}
		}
		*/  
	}
	
	// Adding on 3/9/2015
	// Extract core word in NN or NP
	// Convert to string
	
	public static String coreWoreofNodeNP(Tree pNode){
		String result="";
		String sentence=treeLeaves(pNode);
		String[] segment=txtNormalization.stringtoArray(sentence);
		result=segment[segment.length-1];
		return result;
	}
	
	
	public static void main(String args[]){
		//String sentence="There is a book on table .";
		//String sentence="The principal opposition parties boycotted the polls after accusations of vote-rigging , and the only other name on the ballot was a little-known challenger from a marginal political party .";
		//String sentence="The man teaching in the lake is my father .";
		//String sentence="The principal opposition parties boycotted the polls after accusations of vote-rigging , and the only other name on the ballot was a little-known challenger from a marginal political party .";
		//String sentence="A Spanish official , who had just finished a siesta and seemed not the least bit tense , offered what he believed to be a perfectly reasonable explanation for why the portable facilities were n't in service .";
		//String sentence="The current El Nino , a complicated and vaguely understood series of phenomena that affect weather around the globe , is the weather event of the century .";
		//String sentence="Maxus Energy Corp. discovered a new oil field in the southeast Sumatra area of Indonesia .";
		//String sentence="Tom offered what he believed to be a perfectly reasonable explanation for why the portable facilities were n't in service .";
		//String sentence="One of Tom's student is awared the schoolarship.";
		//String sentence="Jake decides to give Melanie a bit of a hard time because she 's giving him and everyone else she grew up with a megadose of New York attitude , which in this film is almost as grossly exaggerated as the Southern stuff .";	
		//String sentence="There were books on the table .";
		//String sentence="at times there were beautiful moments .";
		//String sentence="one of the most disturbing factors for rohrabacher is that there has never been any formal apology from any of the japanese companies .";
		//String sentence="In the opening game , besides Steinbach and Stewart , there was Walt Weiss , a twiggy-looking , second-year shortstop who had lost a couple months of the season to knee surgery .";
		String sentence="still there is lingering concern that further earthquakes could follow , leading to some transportation problems and mud-slides in colombia , according to tim fallar , president of the fallar co. in west harrison , new york .";
		
		Tree testtree=treeParser(sentence);
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
	    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	    GrammaticalStructure gs = gsf.newGrammaticalStructure(testtree);
	    Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();
		
	    /*
	    Iterator iter=tdl.iterator();
	    for (int i = 0; iter.hasNext(); i++) {
	    	String Node=(String)iter.next().toString();
	    	System.out.println(Node);
		}
		*/
	    
	    /*
	    List<dpItem> dpItemList=dpParsing.dpItemList(tdl);
	    List<dpItem> dpSubjectItem=dpParsing.ListofSjbWords(dpItemList);
	    
	    for (Tree subTree:testtree) {
			if (checkNodekNPPPNP(subTree)==1) {
				if (checkNodekNPPPNPhasRelationWithCoreSubject(subTree, dpSubjectItem)==1) {
					System.out.println("type 1: ");
					System.out.println(subTree);
				}
				else{
					System.out.println("type 2: ");
					System.out.println(subTree);
				}
			}
		}
	    */
	    
	    for (Tree subtree:testtree) {
			if (checkNodeSwithThrereClause(subtree)==1) {
				System.out.println(subtree);
			}
		}
	    
	}
}

