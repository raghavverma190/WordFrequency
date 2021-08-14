package com.example.solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordFrequency {

     /**
      *This function reads all words from .txt file, removes special characters and replaces it with spaces, and in the end
      * each word is trimmed. Each cleaned word is stored in a HashMap along with its frequency and returned to main function
      *
      * @param fileLoc location of file from which text is to be read
      * @return        HashMap with all words and their frequencies
      */
    HashMap<String,Integer> FileInput(String fileLoc) throws FileNotFoundException {

        // Creating an empty HashMap
        HashMap<String, Integer> hash_map = new HashMap<String, Integer>();

        //Initialising file
        File file = new File(fileLoc);

        if(file.length()==0){
            return null;
        }

        //Reading file
        Scanner sc = new Scanner(file);



        //Iterating over each line in .txt file
        while (sc.hasNextLine()) {

            String line = sc.nextLine();

            //Removing special characters
            line=line.replaceAll("[^a-zA-Z0-9]", " ");

            //Iterating over each word in line
            for (String word : line.split("\\s")) {

                if (!word.isEmpty()){
                    //Converting word to lower case and trimming extra blank spaces
                    word = word.toLowerCase();
                    word=word.trim();

                    //If word not present as key, hash_map(<word>) is initialised
                    if (!hash_map.containsKey(word))
                    {
                        hash_map.put(word, 0);
                    }

                    //Frequency of word is incremented if word already present
                    int frequency = hash_map.get(word);
                    hash_map.replace(word, ++frequency);
                }
            }
        }

        return hash_map;
    }

    /**
     * This function calculate k MOST and LEAST frequent words in the text file.
     * HashMap passed to function is iterated and, Max and min frequency of respective words is calculated.
     * A 2D ArrayList which will store ArrayLists is initialised to null till the max index.
     * HashMap is iterated and each word in HashMap is stored at it's frequency index in the ArrayList.
     * If there are multiple words with the same frequency, word is appended as it is a 2D ArrayList OF ArrayLists.
     * This way 2D ArrayList is sorted according to frequency of words.
     * After ArrayList is sorted, it is iterated from beginning and end for only 'k' times to find least frequent and max frequent words respectively
     *
     *
     * @param hash_map
     * @param k
     */
    void calculate(HashMap<String,Integer> hash_map,int k){

        //Max and min is set to lowest and highest values of an Integer respectively
        int max = (int) Math.pow(-2, 31);
        int min = (int) Math.pow(2, 31);

        //HashMap is iterated to find out max and min value
        for (Map.Entry m : hash_map.entrySet()) {
            if (max < (int) m.getValue()) {
                max = (int) m.getValue();
            }
            if (min > (int) m.getValue()) {
                min = (int) m.getValue();
            }
        }

        //2D ArrayList Declared
        ArrayList<ArrayList<String>> frequencies = new ArrayList<ArrayList<String>>();

        //2D ArrayList initialised to null till max index
        for (int i = 0; i <= max; i++) {
            frequencies.add(null);
        }


        //HashMap iterated and 2D ArrayList is sorted according to frequency of words
        for (Map.Entry m : hash_map.entrySet()) {
            if (frequencies.get((int) m.getValue()) == null) {
                ArrayList<String> words = new ArrayList<String>();
                words.add(m.getKey().toString());
                frequencies.set((int) m.getValue(), words);
                continue;
            }
            ArrayList<String> words = frequencies.get((int) m.getValue());
            words.add(m.getKey().toString());
            frequencies.set((int) m.getValue(), words);
        }

        //Most and Least Frequent words are printed according to value of k
        int k2 = k;

        System.out.println("Most Frequent: ");
        for (int i = max; i >= min; i--) {
            if (frequencies.get(i) == null) {
                continue;
            }
            if (k2 == 0) {
                break;
            }
            ArrayList<String> words = frequencies.get(i);
            for (String word : words) {
                if (k2 == 0) {
                    break;
                }
                System.out.print(String.format("(%s : %d), ", word, i));
                --k2;
            }
        }

        //k2 set to k again10
        k2 = k;

        System.out.println("\nLeast Frequent: ");
        for (int i = min; i <= max; i++) {
            if (frequencies.get(i) == null) {
                continue;
            }
            if (k2 == 0) {
                break;
            }
            ArrayList<String> words = frequencies.get(i);
            for (String word : words) {
                if (k2 == 0) {
                    break;
                }
                System.out.print(String.format("(%s : %d), ", word, i));
                --k2;
            }
        }


    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc= new Scanner(System.in);

        //Taking input of file location
        System.out.println("Enter file location: ");
        String fileLoc=sc.nextLine();

        //Taking input of K
        System.out.println("Enter value of k: ");
        int k=sc.nextInt();

        //Initialising class object
        WordFrequency obj = new WordFrequency();

        //Invoking function where words from file are read and stored in HashMap
        HashMap<String,Integer>hash_map=obj.FileInput(fileLoc);

        if(hash_map==null){
            System.out.println("Text file is empty");
            System.exit(1);
        }

        //Invoking function where Most and Least frequent words are calculated
        obj.calculate(hash_map,k);
    }
}
