package com.closeuptheapp.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * User: fede
 * Date: Dec 22, 2010
 * Time: 10:45:50 PM
 */
public class MultiAnswerQuestionTest {

    @Test
    public void setHintsTest() {
        MultiAnswerQuestion question = new MultiAnswerQuestion();
        question.setHints(Arrays.asList("q1", "q2", "q3", "q4"));

        Assert.assertEquals("q1", question.getAnswer1());
        Assert.assertEquals("q2", question.getAnswer2());
        Assert.assertEquals("q3", question.getAnswer3());
        Assert.assertEquals("q4", question.getAnswer4());
    }
}
