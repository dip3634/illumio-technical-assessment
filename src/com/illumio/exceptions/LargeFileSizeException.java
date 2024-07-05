package com.illumio.exceptions;

//Custom Large file size exception, file size should be within 20mb
public class LargeFileSizeException extends Exception {
	public LargeFileSizeException() {
		super("File size more than 20 MB, start the program again!");
	}
}
