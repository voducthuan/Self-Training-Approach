package com.ls3.oie.parsing;

import java.util.ArrayList;
import java.util.List;

import com.ls3.oie.clause.ClausIE;
import com.ls3.oie.clause.Clause;
import com.ls3.oie.clause.Proposition;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeFactory;

public class standardTree {
	
	//updated on 24 Sept.
	//covert NPVPNP to tupleItems
	public static tuplesItem NPPPNP2NPVPNP(Tree pTree, Tree pNodeVP){
		tuplesItem result=new tuplesItem();
		//initiation setting
		possessiveComponents possTree=new possessiveComponents();
		possTree.setMainTree();
		possTree.setNodeNP();
		possTree.setNodeVP();
		possTree.setNodeDT();
		
		Tree NodeNP=pTree;
		int checkNNS=0;
		Tree NodePP=NodeNP.getChild(1);
		NodeNP.removeChild(1);
		// Check NNS in NodeNP
		for (Tree subtree:NodeNP) {
			if (nodeParsing.checkNodeNNS(subtree)==1) {
				checkNNS=1;
				break;
			}
		}
		
		Tree NodeVP=pNodeVP;
		// need to be revised with VBD (past) and VBP (present)
		if (NodeVP.getChild(0).value().equals("VBD")||NodeVP.getChild(0).value().equals("VBN")) {
			possTree.getNodeVP().getChild(0).setValue("VBD");
			if (checkNNS==1) {
				possTree.getNodeVP().getChild(0).getChild(0).setValue("were");
				
			}else{
				possTree.getNodeVP().getChild(0).getChild(0).setValue("was");
			}
		}else{
			possTree.getNodeVP().getChild(0).setValue("VBP");
			if (checkNNS==1) {
				possTree.getNodeVP().getChild(0).getChild(0).setValue("are");
				
			}else{
				possTree.getNodeVP().getChild(0).getChild(0).setValue("is");
			}
		}
		possTree.getNodeVP().removeChild(1);
		possTree.getNodeVP().addChild(1, NodePP);
		NodeNP.addChild(1, possTree.getNodeVP());
		result=ConvertNNVPNP2Tuples(NodeNP);
		System.out.println("------------------------------------");
		return result;
	}
	
	public static tuplesItem ConvertNNVPNP2Tuples(Tree NPVPNPTree){
		tuplesItem result=new tuplesItem();
		Tree nodeNP1=NPVPNPTree.getChild(0);
		Tree nodeVP=NPVPNPTree.getChild(1);
		Tree nodePP=nodeVP.getChild(1);
		Tree nodeNP2=nodePP.getChild(1);
		result.setArg1(nodeParsing.treeLeaves(nodeNP1));
		result.setRel(nodeParsing.treeLeaves(nodeVP.getChild(0))+" "+nodeParsing.treeLeaves(nodePP.getChild(0)));
		result.setArg2(nodeParsing.treeLeaves(nodeNP2));
		return result;
	}
	
	public static void NPPPNP2NPVPNP_old(Tree pTree, Tree pNodeVP){
		//initiation setting
		possessiveComponents possTree=new possessiveComponents();
		possTree.setMainTree();
		possTree.setNodeNP();
		possTree.setNodeVP();
		possTree.setNodeDT();
		
		Tree NodeNP=pTree;
		int checkNNS=0;
		Tree NodePP=NodeNP.getChild(1);
		NodeNP.removeChild(1);
		// Check NNS in NodeNP
		for (Tree subtree:NodeNP) {
			if (nodeParsing.checkNodeNNS(subtree)==1) {
				checkNNS=1;
				break;
			}
		}
		
		Tree NodeVP=pNodeVP;
		// need to be revised with VBD (past) and VBP (present)
		if (NodeVP.getChild(0).value().equals("VBD")||NodeVP.getChild(0).value().equals("VBN")) {
			possTree.getNodeVP().getChild(0).setValue("VBD");
			if (checkNNS==1) {
				possTree.getNodeVP().getChild(0).getChild(0).setValue("were");
				
			}else{
				possTree.getNodeVP().getChild(0).getChild(0).setValue("was");
			}
		}else{
			possTree.getNodeVP().getChild(0).setValue("VBP");
			if (checkNNS==1) {
				possTree.getNodeVP().getChild(0).getChild(0).setValue("are");
				
			}else{
				possTree.getNodeVP().getChild(0).getChild(0).setValue("is");
			}
		}
		possTree.getNodeVP().removeChild(1);
		System.out.println(possTree.getNodeVP());
		possTree.getNodeVP().addChild(1, NodePP);
		NodeNP.addChild(1, possTree.getNodeVP());
		System.out.println(NodeNP);
		System.out.println("------------------------------------");
		
	}
	
	public static void NPPPNPWithNNing2NPVPNP(Tree pTree, Tree pNodeVP){
		//initiation setting
		possessiveComponents possTree=new possessiveComponents();
		possTree.setMainTree();
		possTree.setNodeNP();
		possTree.setNodeVP();
		possTree.setNodeDT();
		
		Tree NodeNP=pTree;
		int checkNNS=0;
		
		Tree NodePP=NodeNP.getChild(1);
		NodeNP.removeChild(1);
		// Check NNS in NodeNP
		for (Tree subtree:NodeNP) {
			if (nodeParsing.checkNodeNNS(subtree)==1) {
				checkNNS=1;
				break;
			}
		}
		//Move Ning to left 1 position
		Tree NodeNNing=NodeNP.getChild(0).getChild(NodeNP.getChild(0).numChildren()-1);
		NodeNP.getChild(0).removeChild(NodeNP.getChild(0).numChildren()-1);
		NodeNP.getChild(0).addChild(NodeNP.getChild(0).numChildren()-1, NodeNNing);
		
		Tree NodeVP=pNodeVP;
		// need to be revised with VBD (past) and VBP (present)
		if (NodeVP.getChild(0).value().equals("VBD")||NodeVP.getChild(0).value().equals("VBN")) {
			possTree.getNodeVP().getChild(0).setValue("VBD");
			if (checkNNS==1) {
				possTree.getNodeVP().getChild(0).getChild(0).setValue("were");
				
			}else{
				possTree.getNodeVP().getChild(0).getChild(0).setValue("was");
			}
		}else{
			possTree.getNodeVP().getChild(0).setValue("VBP");
			if (checkNNS==1) {
				possTree.getNodeVP().getChild(0).getChild(0).setValue("are");
				
			}else{
				possTree.getNodeVP().getChild(0).getChild(0).setValue("is");
			}
		}
		possTree.getNodeVP().removeChild(1);
		System.out.println(possTree.getNodeVP());
		possTree.getNodeVP().addChild(1, NodePP);
		NodeNP.addChild(1, possTree.getNodeVP());
		System.out.println(NodeNP);
		System.out.println("------------------------------------");
	}
	
	public static void testNNPPNNing(){
		
		//NPPPNP testing
		//String sentence="Maxus Energy Corp. , Dallas , said it discovered a new oil field northeast of its previously discovered Intan Field in the southeast Sumatra area of Indonesia .";
		//String sentence="the man swimming in the lake is my father ."; 
		//String sentence="The man in the house is my father.";
		//String sentence="The dogs in the house are belonged to my father.";
		//String sentence="The principal opposition parties boycotted the polls after accusations of vote-rigging , and the only other name on the ballot was a little-known challenger from a marginal political party .";
		String sentence="President Bush deserves much credit for sharply increasing United States financing for AIDS prevention programs overseas.";
		Tree testTree=nodeParsing.treeParser(sentence);
		
		Tree nodeNP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
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
		
		for (Tree subTree:testTree) {
			if (nodeParsing.checkNodeVP(subTree)==1) {
				nodeVP=subTree;
				break;
			}
		}
		
		for (Tree subTree:testTree) {
			if ((nodeParsing.checkNodekNPPPNPwithNNing(subTree)==1)) {
				nodeNP=subTree;
				NPPPNPWithNNing2NPVPNP(nodeNP, nodeVP);
			}
		}
	}
	
	public static void ClauseWithThere2NPVPNP(Tree pTreeS){
		// initial VP, NP, PP
		Tree NodeVP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		Tree NodeNP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		Tree NodePP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		int PPchecking=0;
		List<Tree> subTrees=new ArrayList<Tree>();
		subTrees=pTreeS.getChildrenAsList();
		//get NodePP if it is in the begging of the sentence 
		if (nodeParsing.checkNodePP(subTrees.get(0))==1) {
			NodePP=subTrees.get(0);
			PPchecking=1;
		}
		// extract NodeVP
		System.out.println(pTreeS.getChild(1));
		for (int i = 0; i < subTrees.size(); i++) {
			// get NodeVP
			if (nodeParsing.checkNodeVP(subTrees.get(i))==1) {
				NodeVP=subTrees.get(i);
			}
		}
		// extract node NP which is NodeVP's child
		Tree superNodeNP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		for (Tree subnode:NodeVP) {
			if (nodeParsing.checkNodeNP(subnode)==1) {
				superNodeNP=subnode;
				break;
			}
		}
		for (Tree psubNodeNP:superNodeNP) {
			if (nodeParsing.checkNodePP(psubNodeNP.getChild(psubNodeNP.numChildren()-1))!=1){ 
				NodeNP=psubNodeNP;
				break;
			}else{ 
					if (nodeParsing.checkNodeNP(psubNodeNP.getChild(0))==1&&psubNodeNP.getChild(0).depth()==2) { // fixed errors
						NodeNP=psubNodeNP;
						break;
					}	
				}
		}
		
		// final Tree
		if (PPchecking==0) {
			List<Tree> pSubtrees=NodeNP.getChildrenAsList();
			for (int i = 0; i < pSubtrees.size(); i++) {
				if (nodeParsing.checkNodePP(pSubtrees.get(i))==1) {
					NodePP=pSubtrees.get(i);
					break;
				}
			}
		}
		if (nodeParsing.checkNodeNP(NodeNP.getChild(0))==1) {
			int count=NodeNP.getChildrenAsList().size();
			//remove all subnode of NodeNP excepting first sub node NP
			while (count>1) {
				NodeNP.removeChild(count-1);
				count=NodeNP.getChildrenAsList().size();
			}
		}
		System.out.println(NodeNP);
		System.out.println(NodeVP);
		System.out.println(NodePP);  // fixed errors
		System.out.println("------------------------------------");
    }
	
	
	public static tuplesItem Prossessive2NPVPNP(Tree pTree, Tree pNodeVP){
		// initial NP1, VP, and NP2
		tuplesItem result=new tuplesItem();
		Tree NodeNP1=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		Tree NodeVP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		Tree NodeNP2=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		//----------end initial
		
		possessiveComponents possTree=new possessiveComponents();
		possTree.setMainTree();
		possTree.setNodeNP();
		possTree.setNodeVP();
		possTree.setNodeDT();
		
		possTree.getNodeVP().getChild(1).removeChild(1);
		
		int i=1;
		for (Tree subtree:pTree) {
			if (nodeParsing.checkNodePossessiveValuePRP(subtree)==1) {
				if (subtree.getChild(0).value().toLowerCase().equals("his")) {
					possTree.getNodeNP().getChild(0).getChild(0).setValue("He");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("has");
					}
				}
				if (subtree.getChild(0).value().toLowerCase().equals("her")) {
					possTree.getNodeNP().getChild(0).getChild(0).setValue("She");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("has");
					}
				}
				if (subtree.getChild(0).value().toLowerCase().equals("its")) {
					possTree.getNodeNP().getChild(0).setValue("It");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("has");
					}
				}
				if (subtree.getChild(0).value().toLowerCase().equals("their")) {
					possTree.getNodeNP().getChild(0).getChild(0).setValue("They");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("have");
					}
				}
				if (subtree.getChild(0).value().toLowerCase().equals("my")) {
					possTree.getNodeNP().getChild(0).getChild(0).setValue("I");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("have");
					}
				}
			}
			// Can chinh sua khuc nay NP co' the bao gom DT and JJ, nen can phai lay tat ca childs from NP
			if (nodeParsing.checkNodeNN(subtree)==1||nodeParsing.checkNodeNNP(subtree)==1
					||nodeParsing.checkNodeJJ(subtree)==1||nodeParsing.checkNodeCD(subtree)==1) {
				possTree.getNodeVP().getChild(1).addChild(i, subtree);
				i++;
			}
		}
		//Check NNS, if found then remove DT in VP
		for (Tree subtree:possTree.getNodeVP()) {
			if (nodeParsing.checkNodeNNS(subtree)==1) {
				possTree.getNodeVP().getChild(1).removeChild(0);
				break;
			}
		}
		
		NodeNP1=possTree.getNodeNP();
		NodeVP=possTree.getNodeVP();
		System.out.println(NodeNP1);
		System.out.println(NodeVP);
		result=ConvertProcessive2Tuples(NodeNP1, NodeVP);
		return result;
	}
	// added on 24 Sept. 2015
	
	public static tuplesItem ConvertProcessive2Tuples(Tree NPTree, Tree VPTree){
		tuplesItem result=new tuplesItem();
		Tree nodeNP1=NPTree.getChild(0);
		Tree nodeVP=VPTree.getChild(0);
		Tree nodeNP2=VPTree.getChild(1);
		result.setArg1(nodeParsing.treeLeaves(nodeNP1));
		result.setRel(nodeParsing.treeLeaves(nodeVP));
		result.setArg2(nodeParsing.treeLeaves(nodeNP2));
		return result;
	}
	
	
	public static void Prossessive2NPVPNP_old(Tree pTree, Tree pNodeVP){
		// initial NP1, VP, and NP2
		Tree NodeNP1=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		Tree NodeVP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		Tree NodeNP2=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		//----------end initial
		
		possessiveComponents possTree=new possessiveComponents();
		possTree.setMainTree();
		possTree.setNodeNP();
		possTree.setNodeVP();
		possTree.setNodeDT();
		
		possTree.getNodeVP().getChild(1).removeChild(1);
		
		int i=1;
		for (Tree subtree:pTree) {
			if (nodeParsing.checkNodePossessiveValuePRP(subtree)==1) {
				if (subtree.getChild(0).value().toLowerCase().equals("his")) {
					possTree.getNodeNP().getChild(0).getChild(0).setValue("He");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("has");
					}
				}
				if (subtree.getChild(0).value().toLowerCase().equals("her")) {
					possTree.getNodeNP().getChild(0).getChild(0).setValue("She");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("has");
					}
				}
				if (subtree.getChild(0).value().toLowerCase().equals("its")) {
					possTree.getNodeNP().getChild(0).setValue("It");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("has");
					}
				}
				if (subtree.getChild(0).value().toLowerCase().equals("their")) {
					possTree.getNodeNP().getChild(0).getChild(0).setValue("They");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("have");
					}
				}
				if (subtree.getChild(0).value().toLowerCase().equals("my")) {
					possTree.getNodeNP().getChild(0).getChild(0).setValue("I");
					if (pNodeVP.getChild(0).value().equals("VBD")) {
						possTree.getNodeVP().getChild(0).setValue("VBD");
						possTree.getNodeVP().getChild(0).getChild(0).setValue("had");
					}else{
						
						possTree.getNodeVP().getChild(0).getChild(0).setValue("have");
					}
				}
			}
			// Can chinh sua khuc nay NP co' the bao gom DT and JJ, nen can phai lay tat ca childs from NP
			if (nodeParsing.checkNodeNN(subtree)==1||nodeParsing.checkNodeNNP(subtree)==1
					||nodeParsing.checkNodeJJ(subtree)==1||nodeParsing.checkNodeCD(subtree)==1) {
				possTree.getNodeVP().getChild(1).addChild(i, subtree);
				i++;
			}
		}
		//Check NNS, if found then remove DT in VP
		for (Tree subtree:possTree.getNodeVP()) {
			if (nodeParsing.checkNodeNNS(subtree)==1) {
				possTree.getNodeVP().getChild(1).removeChild(0);
				break;
			}
		}
		
		NodeNP1=possTree.getNodeNP();
		NodeVP=possTree.getNodeVP();
		System.out.println(NodeNP1);
		System.out.println(NodeVP);
		System.out.println("------------------------------------");
	}

	public static void main(String args[]){
		//testNNPPNNing();
		/*
		//There clause testing
		//String sentence="There is a book on the table .";
		//String sentence="There are books on the table .";
		//String sentence="There were books on the table .";
		//String sentence="at times there were beautiful moments .";
		String sentence="In the opening game , besides Steinbach and Stewart , there was Walt Weiss , a twiggy-looking , second-year shortstop who had lost a couple months of the season to knee surgery .";
		//String sentence="one of the most disturbing factors for rohrabacher is that there has never been any formal apology from any of the japanese companies .";
		Tree testTree=nodeParsing.treeParser(sentence);
		Tree treeS=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
		for (Tree subtree: testTree){		
			if (nodeParsing.checkNodeSwithThrereClause(subtree)==1) {
				treeS=subtree;
			}
		}
		ClauseWithThere2NPVPNP(treeS);
		*/
		
		//----------------------------------------------------------------------------------------------------
		/*
		//Possessive clause testing "revised on 21/08/2015"
		String sentence="Mr. Eskandarian , who resigned his Della Femina post in September , becomes chairman and chief executive of Arnold.";
		//String sentence="Maxus Energy Corp. , Dallas , said it discovered a new oil field northeast of its previously discovered Intan Field in the southeast Sumatra area of Indonesia .";
		Tree testTree=nodeParsing.treeParser(sentence);
		Tree NodeVP=new Tree() {
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
				NodeVP=subtree;
				break;
			}
		}
		
		for (Tree subtree:testTree) {
			if (nodeParsing.checkNodePossessiveValuePRPinNP(subtree)==1) {
				Prossessive2NPVPNP(subtree,NodeVP);
			}
		}
		*/
		
		//----------------------------------------------------------------------------------------------------
		
		//NPPPNP testing
		//String sentence="Maxus Energy Corp. , Dallas , said it discovered a new oil field northeast of its previously discovered Intan Field in the southeast Sumatra area of Indonesia .";
		//String sentence="Jake decides to give Melanie a bit of a hard time because she 's giving him and everyone else she grew up with a megadose of New York attitude , which in this film is almost as grossly exaggerated as the Southern stuff .";
		//String sentence="the man swimming in the lake is my father ."; 
		//String sentence="The men in the house is my father.";
		//String sentence="The dogs in the house are belonged to my father.";
		//String sentence="The principal opposition parties boycotted the polls after accusations of vote-rigging , and the only other name on the ballot was a little-known challenger from a marginal political party .";
		String sentence="Nobel Peace Prize to N. Ireland's Hume and Trimble, Adams of Sinn Fein excluded Blair, Ahern praise choice Related stories and sites .";
		Tree testTree=nodeParsing.treeParser(sentence);
		
		Tree nodeNP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
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
		
		for (Tree subTree:testTree) {
			if (nodeParsing.checkNodeVP(subTree)==1) {
				nodeVP=subTree;
				break;
			}
		}
		
		for (Tree subTree:testTree) {
			if ((nodeParsing.checkNodekNPPPNP(subTree)==1)&&(nodeParsing.checkNodekNPPPNPwithNNing(subTree)!=1)) {
				nodeNP=subTree;
				System.out.println(nodeNP);
				tuplesItem tuples=NPPPNP2NPVPNP(nodeNP, nodeVP);
				System.out.println(tuples.toString());
			}
			
		}
		
		
		//----------------------------------------------------------------------------------------------------
		/*
		//NPPPNP with NNing testing
		String sentence="the man swimming in the lake is my father ."; 
		//String sentence="The man in the house is my father.";
		//String sentence="President Bush deserves much credit for sharply increasing United States financing for AIDS prevention programs overseas.";
		Tree testTree=nodeParsing.treeParser(sentence);
		System.out.println(testTree.pennString()
                .replaceAll("\n", "\n ").trim());
		
		Tree nodeNP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
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
		
		for (Tree subTree:testTree) {
			if (nodeParsing.checkNodeVP(subTree)==1) {
				nodeVP=subTree;
				break;
			}
		}
		
		for (Tree subTree:testTree) {
			if (nodeParsing.checkNodekNPPPNPwithNNing(subTree)==1) {
				nodeNP=subTree;
				System.out.println(nodeNP);
				NPPPNPWithNNing2NPVPNP(nodeNP, nodeVP);
			}
			
		}
		*/
		
	}
}
