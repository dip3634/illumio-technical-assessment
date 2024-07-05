package com.illumio.exceptions;

//Custom Large word length exception, word length should be within 256 characters
public class LargeWordLengthException extends Exception {
	public LargeWordLengthException() {
		super("Some word lengths are more than 256, change word lengths and try again. ");
	}

}
