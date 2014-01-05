package com.closeuptheapp.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * User: fede
 * Date: Dec 22, 2010
 * Time: 10:50:10 PM
 */
public class TrueFalseQuestionTest {

    @Test
    public void setHintsTest() {
        TrueFalseQuestion question = new TrueFalseQuestion();
        question.setHints(Arrays.asList("True", "False", "He did not play in that movie."));

        Assert.assertEquals("He did not play in that movie.", question.getAnswerWhenFalse());
    }
}
