package com.closeuptheapp.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: fede
 * Date: Dec 20, 2010
 * Time: 8:37:48 AM
 */
public class QuestionFactoryTest {

    @Test
    public void instanceFromType() {
        AbstractQuestion question = QuestionFactory.instanceFromType("M");
        Assert.assertTrue(question instanceof MultiAnswerQuestion);

        question = QuestionFactory.instanceFromType("TF");
        Assert.assertTrue(question instanceof TrueFalseQuestion);

        question = QuestionFactory.instanceFromType("WOCF");
        Assert.assertTrue(question instanceof WhichOneCameFirstQuestion);

        question = QuestionFactory.instanceFromType("unknown");
        Assert.assertNull(question);
    }

}
