package com.netss.stream;

public class StreamImpl implements Stream {

    private final String input;
    private int currentPosition = 0;

    public StreamImpl(String input) {
        this.input = input;
    }

    public boolean hasNext() {
        return currentPosition < input.length();
    }

    public char getNext() {
        char nextChar = input.charAt(currentPosition);
        currentPosition++;
        return nextChar;
    }
}
