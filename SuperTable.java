import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

public class SuperTable {
	HashSet<String> wordList;
	Character freq;
	HashMap<Character, HashSet<String>>[] table;
	
	public SuperTable(HashSet<String> wdlist, int wordlength, HashSet<Character> letterSet) {
		System.out.print("Building...");
		wordList = (HashSet<String>) wdlist.clone();
		
		//ArrayList tableList = new ArrayList<HashMap<Character, HashSet<String>>>(wordlength);
		
		//HashMap<Character, HashSet<String>>[] table = (HashMap<Character, HashSet<String>>[]) tableList.toArray(new HashMap<Character, HashSet<String>>[tableList.size()]);
		
		table = (HashMap<Character, HashSet<String>>[]) new HashMap[wordlength];
		
		int i;
		for(i=0; i<wordlength; i++) {
			table[i] = new HashMap<Character, HashSet<String>>();
		}
		
		HashMap<Character, Integer> letterfreq = new HashMap<Character, Integer>();
		int totalLetters = 0;
		
		for(String word : wordList) {
			HashMap<Character, Boolean> letterfound = new HashMap<Character, Boolean>();
			for(i=0; i<wordlength; i++) {
				Character curr = null;
				try {
					curr = new Character(word.charAt(i));
				} catch(StringIndexOutOfBoundsException e) {
					System.out.println("The word is: " + word);
				}
				//If this is the first instance, initalize
				if(letterfreq.get(curr) == null) 
					letterfreq.put(curr, new Integer(0));
				
				if(letterfound.get(curr) == null) {
					letterfreq.put(curr, new Integer(letterfreq.get(curr).intValue() + 1));
					letterfound.put(curr, new Boolean(true));
					totalLetters++;
				}
				
				//If this is the first instance, initalize
				if(table[i].get(curr) == null) 
					table[i].put(curr, new HashSet<String>());
				
				table[i].get(curr).add(word);
			}
		}
		
		/*
		int best = totalLetters;
		
		for(Character curr : letterSet) {
			if(letterfreq.containsKey(curr)) {
				int freq = letterfreq.get(curr).intValue();
				if(freq < best) {
					freq = curr;
					best = letterfreq.get(curr).intValue();
				}
			}
		}
		*/
		
		int best = 0;
		for(Character letterc : letterSet) {
			char letter = letterc.charValue();
			int total = 0;
			for(i=0; i<wordlength; i++)
				if(table[i].containsKey(new Character(letter)))
					total += table[i].get(new Character(letter)).size();
			
			if(total > best) {
				best = total;
				freq = new Character(letter);
			}
		}
		
		System.out.println("Built");
	}
	
	char getFreq() {return freq.charValue();}
	
	HashSet<String> contains(char letter, int[] pos) {
		System.out.print("Creating...");
		HashSet newList = new HashSet<String>();
		int i;
		for(String word : table[pos[0]].get(new Character(letter))) {
			boolean containsLetter = true;
			for(i=0;i<pos.length;i++) {
				if(word.charAt(pos[i]) != letter) {
					containsLetter = false;
					break;
				}
			}
			
			if(containsLetter) {
				newList.add(word);
			}
		}
		System.out.println("Created");
		return newList;
	}
	
	HashSet<String> notContains(char letter) {
		System.out.print("Creating...");

		int i;
		HashSet<String> newList = (HashSet<String>) wordList.clone();
		for(i=0; i<table.length; i++) {
			if(table[i].containsKey(new Character(letter)))
				for(String word : table[i].get(new Character(letter)))
					newList.remove(word);
				
		}
		System.out.println("Created");
		
		return newList;
	}
}