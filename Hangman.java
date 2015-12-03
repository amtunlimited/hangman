import java.util.Scanner;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class Hangman {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("How long is the word? ");
		int wordlength = in.nextInt();
		try {
			Scanner file = new Scanner(new File("wordlist/" + wordlength + ".txt"));
			HashSet<String> wordList = new HashSet<String>();
			while(file.hasNext()) {
				wordList.add(file.next());
			}
			/*
			for(String word : wordList) {
				System.out.println("\t"+word);
			}
			*/
			
			
			SuperTable table;
			HashSet<Character> letterSet = new HashSet<Character>();
			
			for(char letter='a'; letter < 'z'; letter++) {
				letterSet.add(new Character(letter));
			}
			
			int i;
			for(i=0; i<10 && wordList.size() > 1; i++) {
				table = new SuperTable(wordList, wordlength, letterSet);
				System.out.println("My guess is: "+table.getFreq());
				System.out.print("How many times is the letter in the word? ");
				
				int count = in.nextInt();
				
				if(count == 0) {
					wordList = table.notContains(table.getFreq());
				} else {
					int[] pos = new int[count];
					System.out.print("Please enter the position(s) of the letter (seperated by spaces): ");
					for(int j=0; j<count; j++)
						pos[j] = in.nextInt();
					wordList = table.contains(table.getFreq(), pos);
				}
				
				letterSet.remove(new Character(table.getFreq()));
			}
			
			System.out.println("Guess: " + i + "\nHere are my guesses");
			
			for(String word : wordList) {
				System.out.println("\t"+word);
			}
			
		} catch(IOException e) {
			System.err.println("Couldn't find the file! Or whatever!");
		}
	}
}