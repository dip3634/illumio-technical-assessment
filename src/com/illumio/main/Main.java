package com.illumio.main;

import java.util.Scanner;

import com.illumio.search.PickWordandSearch;

public class Main {
	private static final Scanner Sc = new Scanner(System.in);

	public static void main(String[] args) {
		PickWordandSearch search = new PickWordandSearch();
		search.storePreDefinedWords();
		search.searchTextFileAndPrint();
	}
	
	//Enter words list file name
	public static String getPredefinedFileName() {
		System.out.println("Please enter predefined words file path: ");
		return Sc.next();
	}
	
	//Enter text file name
	public static String getTextFileName() {
		System.out.println("Please enter Text file path: ");
		return Sc.next();
	}

}
