package com.illumio.exceptions;

//Number of words should be within 10000
public class WordLimitExceedException extends Exception {
	public WordLimitExceedException() {
		super("Number of words shouldn't exceed 10000, please restart and try again!");
	}

}
