package com.netss.stream;

import java.util.*;

import static java.util.Arrays.asList;

public class AppStream {

    public static final Character[] VOWELS = {'a', 'e', 'i', 'o', 'u'};
    public static final Character[] CONSONANTS = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'x', 'z', 'y', 'w'};

    public static final Set<Character> VOWEL_SET = new HashSet<>(asList(VOWELS));
    public static final Set<Character> CONSONANTS_SET = new HashSet<>(asList(CONSONANTS));

    public static final char PATTERN_NOT_FOUND_KEY = Character.MIN_VALUE;

    public static void main(String[] args) {
        System.out.println("Enter your text: ");

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.next();
        Stream stream = new StreamImpl(userInput.toLowerCase());

        char resultLetter = findPattern(stream);

        if (resultLetter != PATTERN_NOT_FOUND_KEY)
            System.out.printf("Pattern found. Letter: %s%n", resultLetter);
        else
            System.out.println("Pattern not found");

    }

    public static char findPattern(Stream stream) {

        int noOccurrenceValue = 0;
        int firstTwoElementsBufferSize = 2;
        char[] lastTwoElements = new char[firstTwoElementsBufferSize];

        HashMap<Character, Integer> elementOccurrence = new HashMap<>();
        LinkedHashSet<Character> matchOccurrence = new LinkedHashSet<>();

        for (int currentPosition = 0; stream.hasNext(); currentPosition++) {

            char currentChar = stream.getNext();

            updateElementOccurrence(noOccurrenceValue, elementOccurrence, currentChar);

            if (currentPosition < firstTwoElementsBufferSize) {
                lastTwoElements[currentPosition] = currentChar;
                continue;
            }

            if (verifyPatternMatch(lastTwoElements) && isVowel(currentChar))
                matchOccurrence.add(currentChar);

            updateLastTwoElements(lastTwoElements, currentChar);
        }

        return getFirstMatchWithOnlyOneOccurrence(matchOccurrence, elementOccurrence);
    }

    private static void updateElementOccurrence(int noOccurrenceValue, HashMap<Character, Integer> charOccurrence, char currentChar) {
        charOccurrence.putIfAbsent(currentChar, noOccurrenceValue);
        charOccurrence.compute(currentChar, (key, value) -> value + 1);
    }

    private static char getFirstMatchWithOnlyOneOccurrence(Set<Character> matches, Map<Character, Integer> elementOccurrence) {

        return elementOccurrence.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .filter(entry -> matches.contains(entry.getKey()))
                .findFirst()
                .map(entry -> entry.getKey())
                .orElse(PATTERN_NOT_FOUND_KEY);
    }

    public static void updateLastTwoElements(char[] lastTwoStreamElements, char newValue) {
        lastTwoStreamElements[0] = lastTwoStreamElements[1];
        lastTwoStreamElements[1] = newValue;
    }

    public static boolean verifyPatternMatch(char[] lastTwoStreamElements) {
        return isVowel(lastTwoStreamElements[0]) && isConsonant(lastTwoStreamElements[1]);
    }

    private static boolean isConsonant(char c) {
        return CONSONANTS_SET.contains(c);
    }

    private static boolean isVowel(char c) {
        return VOWEL_SET.contains(c);
    }

}
