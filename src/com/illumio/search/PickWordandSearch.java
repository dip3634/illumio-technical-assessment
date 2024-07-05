package com.illumio.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.illumio.exceptions.LargeFileSizeException;
import com.illumio.exceptions.LargeWordLengthException;
import com.illumio.exceptions.WordLimitExceedException;
import com.illumio.main.Main;

public class PickWordandSearch {
	
	private Map<String, Integer> predefinedWords;
	private List<String> listOfPredefinedWords;
	private final int increasedBufferSize = 128 * 1024;
	private final int fixedThreadPoolSize = 10;
	
	//Read the words file and store the words in a map
	//Used the list to maintain order and original form of words while printing
	public void storePreDefinedWords() {
		
		predefinedWords = new ConcurrentHashMap<String, Integer>();
		listOfPredefinedWords = new ArrayList<String>();
		final String predefinedFileName = Main.getPredefinedFileName();
		File file = new File(predefinedFileName);
		
		try (BufferedReader br = new BufferedReader(new FileReader(predefinedFileName))){
			
			//Check whether file exists and file size is not greater than 20mb
			if (file.exists() && file.isFile() && file.length() / (1024 * 1024) > 20)
				throw new LargeFileSizeException();
			
			String word;
			
			while ((word = br.readLine()) != null) {
				
				//Check number of words shouldn't exceed 10000
				if (predefinedWords.size() > 10000)
					throw new WordLimitExceedException();
				
				//Check a word length shouldn't exceed 256
				if (word.length() <= 256) {
					//Set initial frequencies to 0
					predefinedWords.put(word.toUpperCase(), 0);
					listOfPredefinedWords.add(word);
					
				} else
					throw new LargeWordLengthException();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		//Catch custom exceptions
		} catch (LargeWordLengthException | LargeFileSizeException | WordLimitExceedException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	public void searchTextFileAndPrint() {
		
		final String textFileName = Main.getTextFileName();
		//Create fixed thread pool
		ExecutorService exec = Executors.newFixedThreadPool(fixedThreadPoolSize);
		File file = new File(textFileName);
		
		try (BufferedReader br = new BufferedReader(new FileReader(textFileName), increasedBufferSize)) {
			
			//Check whether file exists and file size is not greater than 20mb
			if (file.exists() && file.isFile() && file.length() / (1024 * 1024) > 20)
				throw new LargeFileSizeException();
			
			String line;
			
			//Read each line and assign a thread to match words and count frequencies
			while ((line = br.readLine()) != null) {
				Runnable splitLinesAndCheck = new SplitLinesAndCheck(line, predefinedWords);
				exec.execute(splitLinesAndCheck);
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		
		} catch (IOException e) {
			e.printStackTrace();
		
		} catch (LargeFileSizeException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		
		} finally {
			exec.shutdown();
			boolean completed;
			
			try {
				//Await for all the threads to terminate
				completed = exec.awaitTermination(4, TimeUnit.MINUTES);
				if (completed)
					print();
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void print() {
		
		System.out.printf("%-25s %s%n", "Predefined word", "Match count");
		
		//Print frequencies
		for (String word : listOfPredefinedWords) {
			System.out.printf("%-25s %s%n", word, predefinedWords.get(word.toUpperCase()));
		}
	}

}
