package com.ls3.oie.bootstrapping;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.ls3.oie.tools.txtNormalization;

public class selfTraining {

	// Load listOfKnownCategoryWords from word categories to HashMap(String, HashSet<Noun>)
	// Need modify
	public static HashMap<String, seedItem> loadCategoriesFromList(String categorySeedsSlistFile){
		String folder="data\\seeds\\";
		HashMap<String, seedItem> result = new HashMap<String, seedItem>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(folder+categorySeedsSlistFile));	
			String line = null;
			while((line = br.readLine()) != null){
				line = line.trim();
				BufferedReader br1 = new BufferedReader(new FileReader(folder+line));	
				//read each seed category
				String line1 = "";
				seedItem pSeed=new seedItem();
				int linecount=0;
				while((line1=br1.readLine())!=null){
					line1=line1.trim();
					
					// for adding arguments
					if (linecount==0) {
						String[] arr=txtNormalization.stringtoArray(line1);
						for (int i = 0; i < arr.length; i++) {
							termWord word=new termWord(arr[i]);
							pSeed.argAddingTerm(word);
							//System.out.println(word.term);
							//System.out.println(linecount);
						}
					}
					// for adding relations
					if (linecount==1) {
						String[] arr=txtNormalization.stringtoArray(line1);
						for (int i = 0; i < arr.length; i++) {
							termWord word=new termWord(arr[i]);
							pSeed.relAddingTerm(word);
							//System.out.println(word.term);
							//System.out.println(linecount);
						}
					}
					linecount++;
				}
				result.put(line, pSeed);
				br1.close();
			}
			br.close();
		}
		catch (IOException e){
			System.err.println(e.getMessage());
		}
		
		return result;
	}
	
	// Load extracted patters from text file
	public static HashSet<extractedPattern> LoadPatterns(String pFile){
		HashSet<extractedPattern> result =new HashSet<extractedPattern>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(pFile));	
			String line = null;
			int id=1;
			while((line = br.readLine()) != null){
				line = line.trim();
				//System.out.println(id);
				//System.out.println(line);
				String formattedStr = line.replaceAll("\t", "-tab-");
				String[] arr1=formattedStr.split("-tab-");
				
				String label=String.valueOf(id)+arr1[0];
				
				String[] arr2=arr1[1].split(", ");
				String arg1=arr2[0].replace("(", "");
				String rel=arr2[1];
				//System.out.println(arr2[2]);
				String arg2="";
				if (arr2.length==3) {
					String arg2_temp=arr2[2].replace(")","");
					String[] arr_arg2=arg2_temp.split(" ");
					
					for (int i = 0; i < arr_arg2.length-1; i++) {
						arg2=arg2+" "+arr_arg2[i];
					}
				}else {
					System.out.println(id);
					System.out.println(line);
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
	
	// Print out scored pattern in each category
	public static void printOutTopScoredPatterns(HashMap<String, HashSet<extractedPattern>> pPatternList){
		for (String category:pPatternList.keySet()) {
			HashSet<extractedPattern> scoredPatterns = pPatternList.get(category);
			TreeSet<extractedPattern> sortedPatterns = new TreeSet<extractedPattern>();
			sortedPatterns.addAll(scoredPatterns);
			Iterator<extractedPattern> iter = sortedPatterns.descendingIterator();
			System.out.println("Category: "+category);
			while (iter.hasNext()) {
				extractedPattern pattern=(extractedPattern)iter.next();
				System.out.println(pattern.getArg1()+" "+pattern.getRel()+" "+pattern.getArg2()+" "+pattern.getScore()
						           +" "+pattern.getId()) ;
			}
		}
	}
	
	// Print out scored Seeds in each category
	public static void printOutScoredSeeds(HashMap<String, seedItem> pSeedList){
		for (String category:pSeedList.keySet()) {
			System.out.println("Scoring Seeds in category: "+category);
			seedItem pSeed=pSeedList.get(category);
			System.out.println("Arg seed: "+pSeed.getArg());
			Iterator iterArg=pSeed.getArg().iterator();
			for (int i = 0; iterArg.hasNext(); i++) {
				termWord word=(termWord)iterArg.next();
				System.out.println(word.getTerm()+": "+word.getScore());
			}	
			System.out.println("Rel seed:"+pSeed.getRel());
			Iterator iterRel=pSeed.getRel().iterator();
			for (int i = 0; iterRel.hasNext(); i++) {
				termWord word=(termWord)iterRel.next();
				System.out.println(word.getTerm()+": "+word.getScore());
			}
		}
	}
	
	// Print out Top Seeds in each category
	public static void printOutTopSeeds(HashMap<String, seedItem> pSeedList){
		for (String category:pSeedList.keySet()) {
			System.out.println("Category: "+category);
			seedItem pSeed=pSeedList.get(category);
			
			System.out.println("Arg seeds:");
			TreeSet<termWord> sortedSeedsArg = new TreeSet<termWord>();
			sortedSeedsArg.addAll(pSeed.getArg());
			Iterator<termWord> iterArg=sortedSeedsArg.descendingIterator();
			while(iterArg.hasNext()) {
				termWord word=iterArg.next();
				System.out.println(word.getTerm()+": "+word.getScore());
			}
			
			System.out.println("Rel seeds:");
			TreeSet<termWord> sortedSeedsRel=new TreeSet<termWord>();
			sortedSeedsRel.addAll(pSeed.getRel());
			Iterator iterRel=sortedSeedsRel.descendingIterator();
			for (int i = 0; iterRel.hasNext(); i++) {
				termWord word=(termWord)iterRel.next();
				System.out.println(word.getTerm()+": "+word.getScore());
			}
		}
	}
	
	// Print out scored Seeds in each category
	public static void printOutSeeds(HashMap<String, seedItem> pSeedList){
		for (String category:pSeedList.keySet()) {
			System.out.println("Category: "+category);
			seedItem pSeed=pSeedList.get(category);
			System.out.println("Arg seeds:");
			System.out.println(pSeed.getArg());
			System.out.println("Rel seeds:");
			System.out.println(pSeed.getRel());
		}
	}
	
	
	//Score the patterns for each category
	//HashMap<String, HashSet<Pattern>> listsOfScoredPatterns = scorePatternsInEachCategory(_listsOfKnownCategoryWords, _patternsToExtractedNounMap);
	
	public static HashMap<String, HashSet<extractedPattern>> scorePatternsInEachCategory(HashMap<String, seedItem> listsofKnownCategoryWords,
																		 HashSet<extractedPattern> patterns) {
		HashMap<String, HashSet<extractedPattern>> result = new HashMap<String, HashSet<extractedPattern>>();
		for(String category: listsofKnownCategoryWords.keySet()){
			seedItem knownWords = listsofKnownCategoryWords.get(category);
			result.put(category, scorePatterns(patterns, knownWords));
		}
		return result;
	}
	
	public static HashSet<extractedPattern> scorePatterns_old(HashSet<extractedPattern> patterns, seedItem knownCategoryMembers) {
		
		HashSet<extractedPattern> result = new HashSet<extractedPattern>();
		//System.out.println(patterns.size());
		Iterator iter=patterns.iterator();
		
		for (int i = 0; iter.hasNext(); i++) {
			
			int f_arg1 = 0;	//total number of known category members extracted in argument 1
			int f_arg2 = 0;	//total number of known category members extracted in argument 2
			int f_rel = 0;	//total number of known category members extracted in rel
			int n_arg1 = 0; //total words extracted in argument 1 by pattern
			int n_arg2 = 0; //total words extracted in argument 2 by pattern
			int n_rel = 0; 	//total words extracted in argument 3 by pattern
			double rLogF=0;
			extractedPattern pattern=(extractedPattern)iter.next();
			
			List<termWord> arg1=pattern.getArg1();
			List<termWord> arg2=pattern.getArg2();
			List<termWord> rel=pattern.getRel();
			
			// process arg1
			for (int j = 0; j < arg1.size(); j++) {
				termWord pTermword=arg1.get(j);
				if (knownCategoryMembers.checkWordinArg(pTermword)==1) {
					f_arg1++;
				}
			}
			double rLogF_arg1 = (f_arg1/(double) arg1.size())*(Math.log(f_arg1)/Math.log(2));
			//-------------------------------------
			// process arg2
			for (int j = 0; j < arg2.size(); j++) {
				termWord pTermword=arg2.get(j);
				System.out.println(pTermword.getTerm());
				if (knownCategoryMembers.checkWordinArg(pTermword)==1) {
					f_arg2++;
				}
			}
			System.out.println(f_arg2);
			System.out.println(arg2.size());
			double rLogF_arg2 = (f_arg2/(double) arg2.size())*(Math.log(f_arg2)/Math.log(2));
			System.out.println(rLogF_arg2);
			System.out.println("--------------");
			//-------------------------------------
			// process arg2
			for (int j = 0; j < rel.size(); j++) {
				termWord pTermword=rel.get(j);
				if (knownCategoryMembers.checkWordinRel(pTermword)==1) {
					f_rel++;
				}
			}
			double rLogF_rel = (f_rel/(double) rel.size())*(Math.log(f_rel)/Math.log(2));
			//------------------------------------
			
			rLogF=rLogF_arg1+rLogF_arg2+rLogF_rel;
			
			//Store the pattern (with it's new score) into the result
			extractedPattern scoredPattern=new extractedPattern(pattern);
			scoredPattern.setScore(rLogF);
			result.add(scoredPattern);	
		}
		return result;
	}
	
	public static HashSet<extractedPattern> scorePatterns(HashSet<extractedPattern> patterns, seedItem knownCategoryMembers) {
		
		HashSet<extractedPattern> result = new HashSet<extractedPattern>();
		//System.out.println(patterns.size());
		Iterator iter=patterns.iterator();
		
		while (iter.hasNext()) {
			
			int f= 1;	//total number of known category members extracted in argument 1
			int n=0;
			double rLogF=0;
			extractedPattern pattern=(extractedPattern)iter.next();
			List<termWord> arg1=pattern.getArg1();
			List<termWord> arg2=pattern.getArg2();
			List<termWord> rel=pattern.getRel();
			
			// process arg1
			for (int j = 0; j < arg1.size(); j++) {
				termWord pTermword=arg1.get(j);
				if (knownCategoryMembers.checkWordinArg(pTermword)==1) {
					f++;
				}
			}
			
			//-------------------------------------
			// process arg2
			for (int j = 0; j < arg2.size(); j++) {
				termWord pTermword=arg2.get(j);
				if (knownCategoryMembers.checkWordinArg(pTermword)==1) {
					f++;
				}
			}
			
			//-------------------------------------
			// process arg2
			for (int j = 0; j < rel.size(); j++) {
				termWord pTermword=rel.get(j);
				if (knownCategoryMembers.checkWordinRel(pTermword)==1) {
					f++;
				}
			}
			//------------------------------------
			n=arg1.size()+arg2.size()+rel.size();
			rLogF = (f/(double) n)*(Math.log(f)/Math.log(2));
			
			//If we get -infinity as a result, change it to -1
			if(Double.compare(rLogF, Double.NaN) == 0)
				rLogF = -1.0;
			
			//Store the pattern (with it's new score) into the result
			extractedPattern scoredPattern=new extractedPattern(pattern);
			scoredPattern.setScore(rLogF);
			result.add(scoredPattern);	
		}
		return result;
	}
	
	public static HashMap<String, HashSet<extractedPattern>> selectTopNPatternsInEachCategory(
					  HashMap<String, HashSet<extractedPattern>> listsOfScoredPatterns, int i) {
		
		HashMap<String, HashSet<extractedPattern>> result = new HashMap<String, HashSet<extractedPattern>>();
		for(String category: listsOfScoredPatterns.keySet()){
			HashSet<extractedPattern> scoredPatterns = listsOfScoredPatterns.get(category);
			HashSet<extractedPattern> patternPool = selectTopNPatterns(scoredPatterns, i);
			result.put(category, patternPool);
		}
		return result;
	}
	
	public static HashSet<extractedPattern> selectTopNPatterns(HashSet<extractedPattern> patterns, int n) {
		
		HashSet<extractedPattern> result = new HashSet<extractedPattern>();
		TreeSet<extractedPattern> sortedPatterns = new TreeSet<extractedPattern>();
		sortedPatterns.addAll(patterns);
		Iterator<extractedPattern> it = sortedPatterns.descendingIterator();
		
		for(int i = 0; i < n && it.hasNext();){
			extractedPattern nextPattern = it.next();
			if (nextPattern.getScore()>0) {
				result.add(nextPattern);
			}
			i++;
			
		}
		return result;
	}
	
	// Select Seeds from scored patterns in each category
	
	public static HashMap<String, seedItem> selectSeedFromPatternsInEachCategory(HashMap<String, HashSet<extractedPattern>> listsOfPatternPools) throws IOException {
		HashMap<String, seedItem> result = new HashMap<String, seedItem>();
		for(String category: listsOfPatternPools.keySet()){
			HashSet<extractedPattern> patternPool = listsOfPatternPools.get(category);
			result.put(category, selectSeedFromPatterns(patternPool));
		}
		return result;
	}
	
	// Select Seeds
	
	public static seedItem selectSeedFromPatterns(HashSet<extractedPattern> patternPool) throws IOException {
		seedItem result = new seedItem();
		for(extractedPattern p: patternPool){
			//System.out.println("--------------test---------------");
			//System.out.println(p.getRel());
			//System.out.println(p.getContent());
			//System.out.println("---------------------------------");
			List<termWord> pArg1=p.getArg1();
			List<termWord> pArg2=p.getArg2();
			List<termWord> pRel=p.getRel();
			// process on Argument 1
			result.addSeedtoArg(pArg1);
			// process on Argument 2
			result.addSeedtoArg(pArg2);
			// process on Rel
			result.addSeedtoRel(pRel);
		}
		return result;
	}
	
	// Score Seeds
	public static HashMap<String, seedItem> scoreSeedsInEachCategory( HashMap<String, seedItem> listsOfCandidateSeedPools,
																	  HashSet<extractedPattern> listOfPatterns,
																	  HashMap<String, seedItem> listsOfKnownCategoryWords) {

		HashMap<String, seedItem> result = new HashMap<String, seedItem>();

		for(String category: listsOfCandidateSeedPools.keySet()){
			seedItem candidateSeedPool = listsOfCandidateSeedPools.get(category);
			result.put(category, scoreAllSeeds(candidateSeedPool, listOfPatterns,  listsOfKnownCategoryWords.get(category)));	
		}
		
		return result;
	}
		
	public static seedItem scoreAllSeeds(seedItem candidateSeedPool, 
										HashSet<extractedPattern> listOfPatterns, 
										seedItem knownCategoryWords) {
		
		seedItem result = new seedItem();
		//Score each word in candidate pool
		
		HashSet<termWord> ListArgWords=candidateSeedPool.getArg();
		HashSet<termWord> ListRelWords=candidateSeedPool.getRel();
		
		Iterator iterArg=ListArgWords.iterator();
		for (int i = 0; iterArg.hasNext(); i++) {
			termWord ScoredWord=(termWord)iterArg.next();
			double avgScore = scoreCandidateWordinArg(ScoredWord, listOfPatterns, knownCategoryWords);
			ScoredWord.setScore(avgScore);
			result.getArg().add(ScoredWord);
		}
		Iterator iterRel=ListRelWords.iterator();
		for (int i = 0; iterRel.hasNext(); i++) {
			termWord ScoredWord=(termWord)iterRel.next();
			double avgScore = scoreCandidateWordinRel(ScoredWord, listOfPatterns, knownCategoryWords);
			ScoredWord.setScore(avgScore);
			result.getRel().add(ScoredWord);
		}
		return result;
	}

	public static double scoreCandidateWordinArg(termWord pWord, 
			 								HashSet<extractedPattern> listOfPatterns,
			 								seedItem knownCategoryMembers){

		double sumLog2 = 0.0; 	//Sum of (log2(F+1))
		int numPatterns = 0; 	//P number of patterns that extracted candidate noun
		Iterator iter=listOfPatterns.iterator();
		int FPlusOne = 1;
		
		while (iter.hasNext()) {
			extractedPattern pattern=(extractedPattern)iter.next();
			if (pattern.getArg1().contains(pWord)||pattern.getArg2().contains(pWord)) {
				numPatterns++;	
			}
			// Calculating Sumlog2Fj
			if (pattern.getArg1().contains(pWord)||pattern.getArg2().contains(pWord)) {
				for (int j = 0; j < pattern.getArg1().size(); j++) {
					termWord patternWord=pattern.getArg1().get(j);
					if (knownCategoryMembers.getArg().contains(patternWord)) {
						FPlusOne++;
					}
				}
				
				for (int j = 0; j < pattern.getArg2().size(); j++) {
					termWord patternWord=pattern.getArg2().get(j);
					if (knownCategoryMembers.getArg().contains(patternWord)) {
						FPlusOne++;
					}
				}
				
				sumLog2 += Math.log(FPlusOne)/Math.log(2);
			}
			
		}
		//Average the nounScore over the number of patterns that contributed to it
		double avgScore = (sumLog2/(double) numPatterns);
		return avgScore;
	}
	
	public static double scoreCandidateWordinRel(termWord pWord, 
												 HashSet<extractedPattern> listOfPatterns,
												 seedItem knownCategoryMembers){
		double sumLog2 = 0.0; 	//Sum of (log2(F+1))
		int numPatterns = 0; 	//P number of patterns that extracted candidate noun
		
		Iterator iter=listOfPatterns.iterator();
		int FPlusOne = 1;
		while (iter.hasNext()) {

			extractedPattern pattern=(extractedPattern)iter.next();
			
			if (pattern.getRel().contains(pWord)||pattern.getArg1().contains(pWord)||pattern.getArg2().contains(pWord)) {
				numPatterns++;	
			}

			// Calculating Sumlog2Fj for word in Rel
			if (pattern.getRel().contains(pWord)) {
				for (int j = 0; j < pattern.getRel().size(); j++) {
					termWord patternWordinRel=pattern.getRel().get(j);
					if (knownCategoryMembers.getRel().contains(patternWordinRel)) {
						FPlusOne++;
					}
				}
				
				for (int j = 0; j < pattern.getArg1().size(); j++) {
					termWord patternWord=pattern.getArg1().get(j);
					if (knownCategoryMembers.getArg().contains(patternWord)) {
						FPlusOne++;
					}
				}
				
				for (int j = 0; j < pattern.getArg2().size(); j++) {
					termWord patternWord=pattern.getArg2().get(j);
					if (knownCategoryMembers.getArg().contains(patternWord)) {
						FPlusOne++;
					}
				}
				
				sumLog2 += Math.log(FPlusOne)/Math.log(2);
			}
		}
		double avgScore = (sumLog2/(double) numPatterns);
		return avgScore;
	}	
	
	// select Top Seeds
	public static HashMap<String, seedItem> selectTopNSeedsInEachCategory(
			  HashMap<String, seedItem> listsOfScoredSeeds, int i) {

		HashMap<String, seedItem> result = new HashMap<String, seedItem>();
		for(String category: listsOfScoredSeeds.keySet()){
			seedItem scoredSeed = listsOfScoredSeeds.get(category);
			seedItem SeedPool = selectTopNSeed(scoredSeed, i);
			result.put(category, SeedPool);
		}
		return result;
	}

	public static seedItem selectTopNSeed(seedItem pSeed, int n) {
		seedItem result = new seedItem();
		TreeSet<termWord> sortedWordArg = new TreeSet<termWord>();
		TreeSet<termWord> sortedWordRel = new TreeSet<termWord>();
		sortedWordArg.addAll(pSeed.getArg());
		sortedWordRel.addAll(pSeed.getRel());
		Iterator itArg = sortedWordArg.descendingIterator();
		for(int i = 0; i < n && itArg.hasNext();){
			termWord nextTermWord = (termWord)itArg.next();
			//Check to make sure the pattern isn't depleted
			if (nextTermWord.getScore()>0) {
				result.getArg().add(nextTermWord);
			}
			i++;
		}
		
		Iterator itRel = sortedWordRel.descendingIterator();
		for(int i = 0; i < n && itRel.hasNext();){
			termWord nextTermWord = (termWord)itRel.next();
			//Check to make sure the pattern isn't depleted
			if (nextTermWord.getScore()>0) {
				result.getRel().add(nextTermWord);
			}
			i++;
		}
		return result;
	}
	
	// Check conflict Seed in each category
	public static HashMap<String, seedItem> removeAlreadyKnownSeedsInEachCategory( HashMap<String, seedItem> listsOfScoredSeeds,
																			HashMap<String, seedItem> listsOfKnownCategorySeeds) {
		HashMap<String, seedItem> result = new HashMap<String, seedItem>();
		
		for(String category: listsOfScoredSeeds.keySet()){
			seedItem NewSeed=new seedItem();
			//seedItem scoredSeeds = listsOfScoredSeeds.get(category);
			seedItem plistsOfKnownCategorySeed=listsOfKnownCategorySeeds.get(category);
			// Process in Argument 
			Iterator iterArgofScoredSeeds=listsOfScoredSeeds.get(category).getArg().iterator();
			while (iterArgofScoredSeeds.hasNext()) {
				termWord word=(termWord)iterArgofScoredSeeds.next();
				if (plistsOfKnownCategorySeed.checkWordinArg(word)!=1) {
					NewSeed.getArg().add(word);
				}
			}
			
			Iterator iterRelofScoredSeeds=listsOfScoredSeeds.get(category).getRel().iterator();
			while (iterRelofScoredSeeds.hasNext()) {
				termWord word=(termWord)iterRelofScoredSeeds.next();
				if (plistsOfKnownCategorySeed.checkWordinRel(word)!=1) {
					NewSeed.getRel().add(word);
				}
			}
			
			result.put(category, NewSeed);
		}
		return result;
	}

	// Check conflict Seed in all category
	public static HashMap<String, seedItem> removeAlreadyKnownSeedsInAllCategory( HashMap<String, seedItem> listsOfScoredSeeds,
																			HashMap<String, seedItem> listsOfKnownCategorySeeds) {
		HashMap<String, seedItem> result = new HashMap<String, seedItem>();
		
		for(String category: listsOfScoredSeeds.keySet()){
			seedItem NewSeed=new seedItem();
			seedItem scoredSeeds = listsOfScoredSeeds.get(category);
			
			// Process in Argument 
			Iterator iterArgofScoredSeeds=scoredSeeds.getArg().iterator();
			while (iterArgofScoredSeeds.hasNext()) {
				termWord word=(termWord)iterArgofScoredSeeds.next();
				if (checkWordInAllKnownSeedCategoryArg(word, listsOfKnownCategorySeeds)!=1) {
					NewSeed.getArg().add(word);
				}
			}
			// Process in Rel
			Iterator iterRelofScoredSeeds=scoredSeeds.getRel().iterator();
			while (iterRelofScoredSeeds.hasNext()) {
				termWord word=(termWord)iterRelofScoredSeeds.next();
				if (checkWordInAllKnownSeedCategoryRel(word, listsOfKnownCategorySeeds)!=1) {
					NewSeed.getRel().add(word);
				}
			}
			
			result.put(category, NewSeed);
		}
		return result;
	}
	
	// Check word in all known seeds category (Arg)
	public static int checkWordInAllKnownSeedCategoryArg(termWord pWord, HashMap<String, seedItem> listsOfKnownCategorySeeds){
		int result=0;
		for (String category:listsOfKnownCategorySeeds.keySet()) {
			seedItem Seed=listsOfKnownCategorySeeds.get(category);
			if (Seed.checkWordinArg(pWord)==1) {
				result=1;
				break;
			}
		}
		return result;
	}
	
	// Check word in all known seeds category (Rel)
	public static int checkWordInAllKnownSeedCategoryRel(termWord pWord, HashMap<String, seedItem> listsOfKnownCategorySeeds){
		int result=0;
		for (String category:listsOfKnownCategorySeeds.keySet()) {
			seedItem Seed=listsOfKnownCategorySeeds.get(category);
			if (Seed.checkWordinRel(pWord)==1) {
				result=1;
				break;
			}
		}
		return result;
	}
	// Output the result
	public static void Output(int PatternNum, int SeedNum, int IterNum) throws IOException{
		
		//Out File 
		String outputFile="data\\bootstrapping\\muc\\label\\muc_clause_3-5.txt";
		FileOutputStream fo = new FileOutputStream(outputFile);
	    OutputStreamWriter out = new OutputStreamWriter(fo, "utf-8");
		
	    // Testing load seed
		HashMap<String, seedItem> seedwordsCategory=loadCategoriesFromList("category");
		// Input
		String pPatternInput="data\\bootstrapping\\muc\\label\\muc_clause227b.txt";
		int TopPatterns=PatternNum;
		int TopSeeds=SeedNum;
		int pIteration=IterNum;
		HashMap<String, HashSet<extractedPattern>> pLearnedPattern= new HashMap<String, HashSet<extractedPattern>>();
		HashMap<String, seedItem> pLearnedSeeds=new HashMap<String, seedItem>();
		HashSet<extractedPattern> patterns=new HashSet<extractedPattern>();
		patterns=LoadPatterns(pPatternInput);
		//Starting here...
		for (int i = 0; i < pIteration; i++) {
			System.out.println("Iteration: "+i);
			out.write("Iteration: "+i);
			out.write("\n");
			HashMap<String, HashSet<extractedPattern>> pSoredPattern=scorePatternsInEachCategory(seedwordsCategory, patterns);
			HashMap<String, HashSet<extractedPattern>> TopSoredPattern=selectTopNPatternsInEachCategory(pSoredPattern, TopPatterns);
			
			// Writing to output file
			for (String category:TopSoredPattern.keySet()) {
				HashSet<extractedPattern> scoredPatterns = TopSoredPattern.get(category);
				TreeSet<extractedPattern> sortedPatterns = new TreeSet<extractedPattern>();
				sortedPatterns.addAll(scoredPatterns);
				Iterator<extractedPattern> iter = sortedPatterns.descendingIterator();
				System.out.println("Category: "+category);
				out.write("Category: "+category);
				out.write("\n");
				while (iter.hasNext()) {
					extractedPattern pattern=(extractedPattern)iter.next();
					String record=pattern.getArg1()+" "+pattern.getRel()+" "+pattern.getArg2()+" "+pattern.getScore()
					           +" "+pattern.getId();
					System.out.println(record) ;
					out.write(record);
					out.write("\n");
				}
			}
			// end writing
			
			//printOutTopScoredPatterns(TopSoredPattern);
			System.out.println("-------------------------------------------------");
			// Select Seeds from Top N scored extracted pattern
			HashMap<String, seedItem> Seeds = selectSeedFromPatternsInEachCategory(TopSoredPattern);
			HashMap<String, seedItem> ScoredSeedList=scoreSeedsInEachCategory(Seeds, patterns, seedwordsCategory);
			//System.out.println("-------------------------------------------------");
			HashMap<String, seedItem> TopSeed=selectTopNSeedsInEachCategory(ScoredSeedList, TopSeeds);
			//System.out.println("-------------------------------------------------");
			HashMap<String, seedItem> selectedSeeds=removeAlreadyKnownSeedsInAllCategory(TopSeed, seedwordsCategory);
			// Update know seeds category
			for(String category: seedwordsCategory.keySet()){
				seedwordsCategory.get(category).updateSeed(selectedSeeds.get(category));;
			}
			//System.out.println("-------------------------------------------------");
			
			for(String category: TopSoredPattern.keySet()){
				HashSet<extractedPattern> scoredPatterns = TopSoredPattern.get(category);
				Iterator iterScoredPatterns=scoredPatterns.iterator();
				while (iterScoredPatterns.hasNext()) {
					extractedPattern pScoredPattern=(extractedPattern)iterScoredPatterns.next();
					Iterator iterPattern=patterns.iterator();
					while (iterPattern.hasNext()) {
						 extractedPattern pPattern= (extractedPattern) iterPattern.next();
						 if (pPattern.getId()==pScoredPattern.getId()) {
							patterns.remove(pPattern);
							break;
						}
					}
				}
			}
			
			
		}
		out.close();
	}
	
	public static void main(String args[]) throws IOException{
		
		int PatternNum=5;
		int SeedNum=3;
		int IterNum=90;
		Output(PatternNum,SeedNum,IterNum);
		
		/*
		// Testing load seed
		HashMap<String, seedItem> seedwordsCategory=loadCategoriesFromList("category");
		String pPatternInput="data\\bootstrapping\\demo\\selectedPatterns_label.txt";
		int TopPatterns=5;
		int TopSeeds=5;
		int pIteration=50;
		HashMap<String, HashSet<extractedPattern>> pLearnedPattern= new HashMap<String, HashSet<extractedPattern>>();
		HashMap<String, seedItem> pLearnedSeeds=new HashMap<String, seedItem>();
		HashSet<extractedPattern> patterns=new HashSet<extractedPattern>();
		patterns=LoadPatterns(pPatternInput);
		//Starting here...
		for (int i = 0; i < pIteration; i++) {
			System.out.println("Iteration: "+i);
			HashMap<String, HashSet<extractedPattern>> pSoredPattern=scorePatternsInEachCategory(seedwordsCategory, patterns);
			HashMap<String, HashSet<extractedPattern>> TopSoredPattern=selectTopNPatternsInEachCategory(pSoredPattern, TopPatterns);
			printOutTopScoredPatterns(TopSoredPattern);
			System.out.println("-------------------------------------------------");
			// Select Seeds from Top N scored extracted pattern
			HashMap<String, seedItem> Seeds = selectSeedFromPatternsInEachCategory(TopSoredPattern);
			HashMap<String, seedItem> ScoredSeedList=scoreSeedsInEachCategory(Seeds, patterns, seedwordsCategory);
			//System.out.println("Scored Seeds");
			//printOutScoredSeeds(ScoredSeedList);
			//System.out.println("-------------------------------------------------");
			HashMap<String, seedItem> TopSeed=selectTopNSeedsInEachCategory(ScoredSeedList, TopSeeds);
			//System.out.println("Top Scored Seeds");
			//printOutScoredSeeds(TopSeed);
			//System.out.println("-------------------------------------------------");
			//HashMap<String, seedItem> selectedSeeds=removeAlreadyKnownSeedsInEachCategory(TopSeed, seedwordsCategory);	
			HashMap<String, seedItem> selectedSeeds=removeAlreadyKnownSeedsInAllCategory(TopSeed, seedwordsCategory);
			// Update know seeds category
			for(String category: seedwordsCategory.keySet()){
				seedwordsCategory.get(category).updateSeed(selectedSeeds.get(category));;
			}
			//printOutSeeds(seedwordsCategory);
			//System.out.println("-------------------------------------------------");
			
			for(String category: TopSoredPattern.keySet()){
				HashSet<extractedPattern> scoredPatterns = TopSoredPattern.get(category);
				Iterator iterScoredPatterns=scoredPatterns.iterator();
				while (iterScoredPatterns.hasNext()) {
					extractedPattern pScoredPattern=(extractedPattern)iterScoredPatterns.next();
					Iterator iterPattern=patterns.iterator();
					while (iterPattern.hasNext()) {
						 extractedPattern pPattern= (extractedPattern) iterPattern.next();
						 if (pPattern.getId()==pScoredPattern.getId()) {
							patterns.remove(pPattern);
							break;
						}
						
					}
				}
			}
		
		}
		/*
		// Pattern remaining
		System.out.println("List of updated pattern:");
		Iterator iter=patterns.iterator();
		for (int i = 0; iter.hasNext(); i++) {
			extractedPattern pPattern=(extractedPattern)iter.next();
			System.out.println(pPattern.getId()+" "+pPattern.getArg1()+" "+pPattern.getRel()+" "+pPattern.getArg2()+": "+pPattern.getScore() );
		}
		System.out.println("--------------------------------------------------");
		*/
			
	}
}
