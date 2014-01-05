package com.closeuptheapp.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * User: fede
 * Date: Dec 22, 2010
 * Time: 10:45:50 PM
 */
public class WhichOneCameFirstQuestionTest {

    @Test
    public void setHintsTest() {
        WhichOneCameFirstQuestion question = new WhichOneCameFirstQuestion();
        Assert.assertNull(question.getMovieTitle1Image());
        Assert.assertNull(question.getMovieTitle2Image());
        Assert.assertEquals(0, question.getMovieTitle1Year());
        Assert.assertEquals(0, question.getMovieTitle2Year());

        question.setHints(Arrays.asList("East of Eden", "The Misfits", "B84A53, 1955", "AFPMD1, 1961"));

        Assert.assertEquals("East of Eden", question.getMovieTitle1());
        Assert.assertEquals("The Misfits", question.getMovieTitle2());
        Assert.assertEquals("B84A53.jpg", question.getMovieTitle1Image());
        Assert.assertEquals("AFPMD1.jpg", question.getMovieTitle2Image());
        Assert.assertEquals(1955, question.getMovieTitle1Year());
        Assert.assertEquals(1961, question.getMovieTitle2Year());

    }
}