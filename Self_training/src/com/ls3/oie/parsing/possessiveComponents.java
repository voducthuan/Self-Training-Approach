package com.ls3.oie.parsing;

import javax.naming.spi.DirStateFactory.Result;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import com.ls3.oie.clause.ClausIE;
import com.ls3.oie.clause.Constituent.Flag;

import edu.stanford.nlp.io.EncodingPrintWriter.out;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.lexparser.LexicalizedParserQuery;
import edu.stanford.nlp.pipeline.ParserAnnotatorUtils;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeFactory;
import edu.stanford.nlp.trees.semgraph.SemanticGraph;

public class possessiveComponents {
	private Tree MainTree;
	private Tree NodeNP;
	private Tree NodeVP;
	private Tree NodeDT;
	
	public possessiveComponents(){
		MainTree=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		NodeVP=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		NodeDT=new Tree() {
			@Override
			public TreeFactory treeFactory() {
				return null;
			}
			@Override
			public Tree[] children() {
				return null;
			}
		};
		
	}
	
	public Tree getMainTree() {
		return MainTree;
	}

	public void setMainTree() {
		String sentence="He has a book";
		ClausIE clausIE = new ClausIE();
        clausIE.initParser();
        clausIE.parse(sentence);
        MainTree= clausIE.getDepTree();
	}
	
	public Tree getNodeNP() {
		return NodeNP;
	}
	
	public void setNodeNP() {
		for (Tree subtree:MainTree) {
			if (nodeParsing.checkNodeNP(subtree)==1) {
				NodeNP=subtree;
				break;
			}
		}
	}

	public Tree getNodeVP() {
		return NodeVP;
	}

	public void setNodeVP() {
		for (Tree subtree:MainTree) {
			if (nodeParsing.checkNodeVP(subtree)==1) {
				NodeVP=subtree;
				break;
			}
		}
	}

	public Tree getNodeDT() {
		return NodeDT;
	}

	public void setNodeDT() {
		for (Tree subtree:MainTree) {
			if (nodeParsing.checkNodeDT(subtree)==1) {
				NodeDT=subtree;
				break;
			}
		}
	}
	
	public static void main(String args[]){
		
		possessiveComponents tree=new possessiveComponents();
		tree.setMainTree();
		tree.setNodeNP();
		tree.setNodeVP();
		tree.setNodeDT();
		System.out.println(tree.getMainTree());
		System.out.println(tree.getNodeNP());
		System.out.println(tree.getNodeVP());
		System.out.println(tree.getNodeDT());
	}
}
