# Description
This project is an initial assessment tool that takes two file paths as input. It searches for predefined words listed in one file within the text of another file and then prints the frequency of each word found.

## Hello, Thanks for reviewing my solution.

## How to run:

### Use 'git clone' to clone the repo.

### With the jar file:
  * Go to the home directory that is illumio-technical-assessment.
  * Open cmd here and type command 'java -jar Main.jar' to start execution.

### Using the .class files:
  * Go to the bin directory inside the project.
  * Open cmd here and type command 'java com.illumio.main.Main' to start execution.

#### After the execution starts, the terminal will ask for both the file paths. Use full file paths, eg: C:\Apps\words.txt. The first path is for the predefined words file, second one is for the text file.

## Approach:
  * First the program picks up the words defined in the words file and stores them in a HashMap and a List. (storePreDefinedWords method)
      * I have used a concurrent HashMap here for synchronized updates.
      * I have used the list to maintain the order of words and their original form(not to upper case) while finally printing.
    
  * The program reads the text file and assigns each line to a thread for further processing. (searchTextFileAndPrint method)
      * I have created a fixed thread pool of 10 threads for parallel processing of lines. (Multi threading)
      * Increased the buffer size to 128KB for faster processing of large files.
   
  * Implemented the run method from Runnable interface.
      * First the words are picked up in a String array by splitting the lines into one or multiple spaces.
      * The words are processed to eliminate special characters and then transformed into upper case. Eg: '(defined)' becomes DEFINED, 'attacks.' becomes 'ATTACKS'. I have taken care of these punctuations from            the end: '.' ',' ';' ':' '!' '?' '/' ''' '"' ')' '}' ']' and  ''' '"' ';' ':' '/' '(' '{' '[' from the beginning.
      * Update the frequency of that word in concurrent HashMap.

  * Finally visit each word in the list and print their frequencies from the map.

  * Note:
      * I have created some custom Exception classes like LargeFileSizeException, LargeWordLengthException and WordLimitExceedException to check the file size is not more than 20mb, word lengths are not more              than 256 characters and there are not more than 10000 words in the words file respectively. (As listed in the requirement)
      * Also, if the file size is huge, it may take 2-3 seconds execution time, for all the threads to terminate and finally print the result.
      * I have included 2 sample input files, 'words' file consist of the predefined words and 'text' (6.5 MB) file consist of the text. Feel free to test.

  ## Thanks again for reviewing my submission. Would love to hop on a call and discuss more about the solution. Feel free to reach out if you have any queries. :)
