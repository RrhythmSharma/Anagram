package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class AnagramDictionary {

    // Constant Variables
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;

    private static int wordLength = DEFAULT_WORD_LENGTH;

    private Random random = new Random();


    HashSet<String> wordSet = new HashSet<>();
    ArrayList<String> wordList = new ArrayList<>();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        ArrayList<String> wordMapList;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);


            if (sizeToWords.containsKey(word.length())) {
                wordMapList = sizeToWords.get(word.length());
                wordMapList.add(word);
                sizeToWords.put(word.length(), wordMapList);
            } else {
                ArrayList<String> newWordList = new ArrayList<>();
                newWordList.add(word);
                sizeToWords.put(word.length(), newWordList);
            }

            ArrayList<String> sortedList = new ArrayList<>();
            String sortedWord = sortLetters(word);


            if ( !(lettersToWord.containsKey(sortedWord)) ) {
                sortedList.add(word);
                lettersToWord.put(sortedWord, sortedList);
            } else {
                sortedList = lettersToWord.get(sortedWord);
                sortedList.add(word);
                lettersToWord.put(sortedWord, sortedList);
            }
        }
    }


    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word) && !(base.contains(word))) {
            return true;
        } else {
            return false;
        }

    }


    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> resultList = new ArrayList<>();
        return resultList;
    }


    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> tempList;
        ArrayList<String> resultList = new ArrayList<>();

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String anagram = word + alphabet;
            String sortedAnagram = sortLetters(anagram);

            if (lettersToWord.containsKey(sortedAnagram)) {
                tempList = lettersToWord.get(sortedAnagram);

                for (int i = 0; i < tempList.size(); i++) {
                    if ( !(tempList.get(i).contains(word)) ) {
                        resultList.add(tempList.get(i));
                    }
                }
            }
        }

        return resultList;
    }


    public ArrayList<String> getAnagramsWithTwoMoreLetters(String word) {
        ArrayList<String> tempList;
        ArrayList<String> resultList = new ArrayList<>();

        for (char firstAlphabet = 'a'; firstAlphabet <= 'z'; firstAlphabet++) {
            for (char secondAlphabet = 'a'; secondAlphabet <= 'z'; secondAlphabet++) {
                String anagram = word + firstAlphabet + secondAlphabet;
                String sortedAnagram = sortLetters(anagram);

                if (lettersToWord.containsKey(sortedAnagram)) {
                    tempList = lettersToWord.get(sortedAnagram);

                    for (int i = 0; i < tempList.size(); i++) {
                        if (!(tempList.get(i).contains(word))) {
                            resultList.add(tempList.get(i));
                        }
                    }
                }
            }
        }

        return resultList;
    }

      public String pickGoodStarterWord() {
        int randomNumber;
        String starterWord;

        do {
            randomNumber = random.nextInt(sizeToWords.get(wordLength).size());
            starterWord = sizeToWords.get(wordLength).get(randomNumber);
        } while (getAnagramsWithTwoMoreLetters(starterWord).size() < MIN_NUM_ANAGRAMS);

        if (wordLength < MAX_WORD_LENGTH) {
            wordLength++;
        }

        return starterWord;
    }

    public String sortLetters(String word) {
        char[] characters = word.toCharArray();
        Arrays.sort(characters);
        String sortedWord = new String(characters);
        return sortedWord;
    }
}
