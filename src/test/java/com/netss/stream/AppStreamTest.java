package com.netss.stream;

import org.junit.Assert;
import org.junit.Test;

import static com.netss.stream.AppStream.PATTERN_NOT_FOUND_KEY;
import static org.junit.Assert.*;

public class AppStreamTest {

    @Test
    public void shouldGetPatternWithNoPreviousVowelOccurrence() {
        String input = "aAbBABacafe";
        Stream stream = new StreamImpl(input.toLowerCase());
        char result = AppStream.findPattern(stream);
        Assert.assertEquals('e', result);
    }

    @Test
    public void shouldNotMatchOnlyConsonants() {
        char[] lastTwoElements = new char[2];
        lastTwoElements[0] = 'f';
        lastTwoElements[1] = 'n';
        assertFalse(AppStream.verifyPatternMatch(lastTwoElements));
    }

    @Test
    public void shouldNotMatchOnlyVowels() {
        char[] lastTwoElements = new char[2];
        lastTwoElements[0] = 'a';
        lastTwoElements[1] = 'i';
        assertFalse(AppStream.verifyPatternMatch(lastTwoElements));
    }

    @Test
    public void shoudGetFirstValidPattern() {
        String input = "iIbBiBacIfe";
        Stream stream = new StreamImpl(input.toLowerCase());
        char result = AppStream.findPattern(stream);
        Assert.assertEquals('a', result);
    }

    @Test
    public void shouldNotFindPatternOnlyVowels() {
        String input = "aaaaeeeeeeeiiiiioooooouuuu";
        Stream stream = new StreamImpl(input);
        Assert.assertEquals(PATTERN_NOT_FOUND_KEY, AppStream.findPattern(stream));
    }

}
