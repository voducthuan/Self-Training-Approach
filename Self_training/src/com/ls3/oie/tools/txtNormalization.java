//@author: Duc-Thuan Vo
//@email: thuanvd@gmail.com

package com.ls3.oie.tools;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.trees.international.arabic.ArabicHeadFinder;

public class txtNormalization {
	public static String arrayToString(String[] a) {
	    StringBuffer result = new StringBuffer();
	    String separator=" ";
	    if (a.length > 0) {
	        result.append(a[0]);
	        for (int i=1; i<a.length; i++) {
	            result.append(separator);
	            result.append(a[i]);
	        }
	    }
	    return result.toString();
	}
	
	public static String[] stringtoArray( String txtString) {
		String[] arr=txtString.split(" ");
		return arr;
	}
	
	public static String[] stringtoArray1(String txtString) {
		String[] arr=txtString.split(",");
		return arr;
	}
	
	
	public static long wordcount(String line){
	    long numWords = 0;
	    int index = 0;
	    boolean prevWhiteSpace = true;
	    while(index < line.length()){
	      char c = line.charAt(index++);
	      boolean currWhiteSpace = Character.isWhitespace(c);
	      if(prevWhiteSpace && !currWhiteSpace){
	        numWords++;
	      }
	      prevWhiteSpace = currWhiteSpace;
	    }
	    return numWords;
	  }
	
	public static String convertToUTF8(String s) {
		  String out = null;
		  try {
		    out = new String(s.getBytes("UTF-8"));
		  } catch (java.io.UnsupportedEncodingException e) {
		    return null;
		  }
		  return out;
		}
	
	// Check capital letter for Named recognition
	public static boolean checkCapitalLetter(String context){
		boolean result=false;
		
		boolean a,b;
		a=false;
		b=true;
		
		if (context.length()>0) {
			if (Character.isUpperCase(context.charAt(0))) {
				a=true;
			}
			
			for (int i = 1; i < context.length(); i++) {
				
				if(context.charAt(i)=='_'){
					
					if (!Character.isUpperCase(context.charAt(i+1))) {
						b=false;
					}	
				}
			}
			
			if (a&b){
				result=true;
			}
		}
		return result;
	}
	
	// Text Normalization for Chunking
	public static String textChunking(String context){
		String result="";
		result=context.replace((char)9, (char)47);
		return result;
	}
	
	
	//Pattern format, used for input bootstrap
	//e.g Nguyá»…n_VÄƒn_HÃ²a/Np/B-NP;HÃ²a/Np/I-NP;vÃ /C/I-NP;vá»£/N/I-NP;Nguyá»…n_Thá»‹_ChÃ¢u/Np/I-NP;Ä‘ang/R/B-VP;
	//format: Nguyá»…n_VÄƒn_HÃ²a;HÃ²a;vÃ ;vá»£;Nguyá»…n_Thá»‹_ChÃ¢u;Ä‘ang;
	//em_trai/N/B-NP;Ä‘á»“ng_pháº¡m/N/I-NP;;/;/B-VP;
	public static String pattern_prefix(String context){
		String result="";
		char[] charArr=context.toCharArray();
		int i=0;
		String temp="";
		
		while (charArr[i]!='/') {
			temp=temp+charArr[i];
			i++;
		}
		
		result=temp;
		
		return result;
		
	}
	public static String patternFormat(String context){
		String result="";
		String txt1=context.replace(";,/,/O", "");
		String txt2=txt1.replace(";,/,","");
		
		char[] charArr=txt2.toCharArray();
		
		String temp="";
		for (int i = 0; i < charArr.length; i++) {
			
			temp=temp+charArr[i];
			
			if (charArr[i]==';') {
				String txt=pattern_prefix(temp);
				result=result+txt+",";
				temp="";
			}
			
		}
		String a=result.replace(",(","").replace(",)","").replace(",â€�","");
		String b=a.replace(",â€œ","");
		
		return b.substring(0, b.length()-1); // revise length()-1 -> length()
		
	}
	
	// Check string is number
	// Return boolean
	public static boolean isIntNumber(String num){
	    try{
	        Integer.parseInt(num);
	    } catch(NumberFormatException nfe) {
	        
	    	return false;
	    }
	    
	    return true;
	}
	
	// Check word exists in string
	// Return boolean
	
	public static boolean isWordExistA(String sentence, String word){
	    boolean ans = Boolean.FALSE;        
	    ArrayList<String> wordList = null;
	    try {

	        if(sentence != null && word != null){
	            wordList = new ArrayList<String>(Arrays.asList(sentence.split("[^a-zA-z]+")));              
	            if(wordList.contains(word)){
	                ans = Boolean.TRUE;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // TODO: handle exception
	    }
	    return ans;
	}

	// Return true when the termword belongs to the sentence
	public static boolean isWordExist(String sentence,String word){
		
		boolean ans = Boolean.FALSE;    
	    int i; 
	    int found=-1;

	    String[] s=sentence.split(" ");
	    for(i=0;i<s.length;i++){
	        if(word.equals(s[i]))
	        	 ans = Boolean.TRUE;
	    }
	    
	    return ans;
	}
	
	// Separate sentence in a paragraph 
	public static List<String> sentenceList(String doc){
		List<String> result=new ArrayList<String>();
		//String str = "This is how I tried to split a paragraph into a sentence. But, there is a problem. My paragraph includes dates like Jan.13, 2014 , words like U.S and numbers like 2.2. They all got splitted by the above code.";
		Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
		Matcher reMatcher = re.matcher(doc);
		while (reMatcher.find()) {
		        //System.out.println(reMatcher.group());
		        result.add(reMatcher.group());
		}
		return result;
	}
	
	public static List<String> LoadFile(String fileName) throws IOException{
		
		List<String> result=new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String sentence="";
		while((sentence = br.readLine()) != null){
			result.add(sentence);	
		}
		return result;
	}
	
	public static List<String> prepositionList() throws IOException{
		List<String> result=new ArrayList<String>();
		List<String> sentences= new ArrayList<String>();
		sentences.add("aboard");
		sentences.add("about");
		sentences.add("above");
		sentences.add("across");
		sentences.add("after");
		sentences.add("against");
		sentences.add("along");
		sentences.add("amid");
		sentences.add("among");
		sentences.add("anti");
		sentences.add("around");
		sentences.add("as");
		sentences.add("at");
		sentences.add("before");
		sentences.add("behind");
		sentences.add("below");
		sentences.add("beneath");
		sentences.add("beside");
		sentences.add("besides");
		sentences.add("between");
		sentences.add("beyond");
		sentences.add("but");
		sentences.add("by");
		sentences.add("concerning");
		sentences.add("considering");
		sentences.add("despite");
		sentences.add("down");
		sentences.add("during");
		sentences.add("except");
		sentences.add("excepting");
		sentences.add("excluding");
		sentences.add("following");
		sentences.add("for");
		sentences.add("from");
		sentences.add("in");
		sentences.add("inside");
		sentences.add("into");
		sentences.add("like");
		sentences.add("minus");
		sentences.add("near");
		sentences.add("of");
		sentences.add("off");
		sentences.add("on");
		sentences.add("onto");
		sentences.add("opposite");
		sentences.add("outside");
		sentences.add("over");
		sentences.add("past");
		sentences.add("per");
		sentences.add("plus");
		sentences.add("regarding");
		sentences.add("round");
		sentences.add("save");
		sentences.add("since");
		sentences.add("than");
		sentences.add("through");
		sentences.add("to");
		sentences.add("toward");
		sentences.add("towards");
		sentences.add("under");
		sentences.add("underneath");
		sentences.add("unlike");
		sentences.add("until");
		sentences.add("up");
		sentences.add("upon");
		sentences.add("versus");
		sentences.add("via");
		sentences.add("with");
		sentences.add("within");
		sentences.add("without");
		for (int i = 0; i < sentences.size(); i++) {
			String line=sentences.get(i);
			result.add(line);
		}
		return result;
	}
	
	public static int checkprepositionWord(String pWord, List<String> preList){
		int result=0;
		for (int i = 0; i < preList.size(); i++) {
			if (preList.get(i).toLowerCase().equals(pWord.toLowerCase())) {
				result=1;
				break;
			}
		}
		return result;
	}
	
	public static List<String> stringtoList( String txtString) {
		List<String> result=new ArrayList<String>();
		String[] arr=txtString.split(" ");
		for (int i = 0; i < arr.length; i++) {
			result.add(arr[i]);
		}
		return result;
	}
	
	public static String listtoString( List<String> txtList) {
		String result=txtList.get(0);
		for (int i = 1; i < txtList.size(); i++) {
			String temp=txtList.get(i);
			result=result+" "+temp;
		}
		return result.trim();
	}
	
	public static List<String> hashSetToList(HashSet<String> pHashSet){
		List<String> result=new ArrayList<String>();
		Iterator<String> iter = pHashSet.iterator();
        while (iter.hasNext()){
            String value=(String)iter.next();
            result.add(value);
        }
		return result;
	}

	// reverse Text for Event BEFORE<->AFTER
	public static String reverseTxt(String pTxt){
		String result=pTxt;
		if (pTxt.equals("BEFORE")||pTxt.equals("IBEFORE")) {
			result="AFTER";
					
		}else if (pTxt.equals("AFTER")||pTxt.equals("IAFTER")) {
			result="BEFORE";
		}
		return result;
	}
	
	// reverse Text for Event CLINK<->CLINK-C
	public static String reverseTxtCausal(String pTxt){
		String result=pTxt;
		if (pTxt.equals("CLINK")) {
			result="CLINK-C";
					
		}else if (pTxt.equals("CLINK-C")) {
			result="CLINK";
		}
		return result;
	}

	public static List<String> readFile(String fileName) throws UnsupportedEncodingException, IOException{
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), "UTF-8"));
			
			List<String> data = new ArrayList<String>();
			
			String line = "";
			while((line = reader.readLine()) != null){
				data.add(line);
			}
			reader.close();
		return data;	
			
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}			
	} //end readFile

	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	//main
	public static void main(String args[]) throws IOException{
		// 
		String sentence="720 Duc Thuan";
		String context="Duc";		
		if (isWordExist(sentence,context)) {
			System.out.println("It is true");
		}
		
		String str = "This is how I tried to split a paragraph into a sentence. But, there is a problem. My paragraph includes dates like Jan.13, 2014 , words like U.S and numbers like 2.2. They all got splitted by the above code.";
		List<String> sentences=sentenceList(str);
		//System.out.println(sentences.get(sentences.size()-1));
		//System.out.println(sentences.size());
		
		for (int i = 0; i < sentences.size(); i++) {
			System.out.println(sentences.get(i));
		}
		
		List<String> list=prepositionList();
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		String word="off";
		System.out.println(checkprepositionWord(word, list));
		
		String test="vo duc thuan";
		List<String> testList=stringtoList(test);
		for (int i = 0; i <testList.size(); i++) {
			System.out.println(testList.get(i));
		}
		
		
	}
	
}

