package com.illumio.search;

import java.util.Map;

public class SplitLinesAndCheck implements Runnable {
	private String line;
	private Map<String, Integer> preDefinedWordsMap;
	private final String startPattern = "^[\"';:/({\\[]+";
	private final String endPattern = "[.,;:!?/'\"})\\]}]+$";

	public SplitLinesAndCheck(String line, Map<String, Integer> preDefinedWordsMap) {
		this.line = line;
		this.preDefinedWordsMap = preDefinedWordsMap;
	}

	@Override
	public void run() {
		//Split words on one or multiple spaces
		String[] words = line.split("\\s+");
		
		/*Visit each word and replace special characters with empty string,
		  transform it to uppercase and update it's value in the map*/
		for (String word : words) {
			String processedWord = word.replaceAll(startPattern, "").
										replaceAll(endPattern, "").toUpperCase();
			
			if (preDefinedWordsMap.containsKey(processedWord))
				preDefinedWordsMap.compute(processedWord, (key, value) -> value + 1);
		}
	}

}
