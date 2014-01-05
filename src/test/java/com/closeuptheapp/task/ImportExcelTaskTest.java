package com.closeuptheapp.task;

import com.closeuptheapp.domain.AbstractQuestion;
import com.closeuptheapp.domain.MultiAnswerQuestion;
import com.closeuptheapp.domain.TrueFalseQuestion;
import com.closeuptheapp.domain.WhichOneCameFirstQuestion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.List;

/**
 * User: fede
 * Date: May 22, 2010
 * Time: 1:47:43 PM
 */
public class ImportExcelTaskTest {

    private ImportExcelTask importExcelTask;
    private TaskCallback callback;

    @Test
    public void exportMultiAnswerOneLine() throws Exception {
        File excelFile = new File(getClass().getResource("exportMultiAnswerOneLine.xlsx").getPath());
        importExcelTask.init(excelFile);
        List<AbstractQuestion> questions = importExcelTask.execute(callback);
        Assert.assertEquals(1, questions.size());
        MultiAnswerQuestion actual = (MultiAnswerQuestion) questions.get(0);

        Assert.assertEquals(1, actual.getQuestionIndex());
        Assert.assertEquals("B7TMDW.jpg", actual.getPictureId());
        Assert.assertEquals("Circles and a triangle", actual.getCorrectAnswer());
        Assert.assertEquals("In the Seven Samurais, which symbols on their war banner represent these brave warriors?", actual.getQuestion());
        Assert.assertEquals(100, actual.getDifficulty());
        Assert.assertEquals("Kanji symbols", actual.getAnswer1());
        Assert.assertEquals("Circles and a triangle", actual.getAnswer2());
        Assert.assertEquals("Dragons", actual.getAnswer3());
        Assert.assertEquals("Great waves", actual.getAnswer4());
    }

    @Test
    public void exportMultiAnswerMovieTitleIsANumber() throws Exception {
        File excelFile = new File(getClass().getResource("exportWithNumbers.xlsx").getPath());
        importExcelTask.init(excelFile);
        List<AbstractQuestion> questions = importExcelTask.execute(callback);
        Assert.assertEquals(1, questions.size());
        MultiAnswerQuestion actual = (MultiAnswerQuestion) questions.get(0);

        Assert.assertEquals(1, actual.getQuestionIndex());
        Assert.assertEquals("1984", actual.getAnswer3());
    }


    @Test
    public void exportTrueFalseQuestions() throws Exception {
        File excelFile = new File(getClass().getResource("exportTrueFalseQuestions.xlsx").getPath());
        importExcelTask.init(excelFile);
        List<AbstractQuestion> questions = importExcelTask.execute(callback);
        Assert.assertEquals(2, questions.size());

        TrueFalseQuestion actual = (TrueFalseQuestion) questions.get(0);
        Assert.assertEquals("B7TMDW.jpg", actual.getPictureId());
        Assert.assertEquals("TRUE", actual.getCorrectAnswer());
        Assert.assertEquals("The Seven Samurais was directed by Akira Kurosawa. True or false?", actual.getQuestion());
        Assert.assertEquals(100, actual.getDifficulty());
        Assert.assertNull(actual.getAnswerWhenFalse());

        actual = (TrueFalseQuestion) questions.get(1);
        Assert.assertEquals("B7TMDY.jpg", actual.getPictureId());
        Assert.assertEquals("FALSE", actual.getCorrectAnswer());
        Assert.assertEquals("this is a false question?", actual.getQuestion());
        Assert.assertEquals(134, actual.getDifficulty());
        Assert.assertEquals("it was actually a true question", actual.getAnswerWhenFalse());
    }

    @Test
    public void exportMultipleLines() throws Exception {
        File excelFile = new File(getClass().getResource("exportMultipleLines.xlsx").getPath());
        importExcelTask.init(excelFile);
        List<AbstractQuestion> questions = importExcelTask.execute(callback);

        Assert.assertEquals(3, questions.size());
        MultiAnswerQuestion question1 = (MultiAnswerQuestion) questions.get(0);
        Assert.assertEquals("First question", question1.getQuestion());

        MultiAnswerQuestion question2 = (MultiAnswerQuestion) questions.get(1);
        Assert.assertEquals("Another question", question2.getQuestion());

        TrueFalseQuestion question6 = (TrueFalseQuestion) questions.get(2);
        Assert.assertEquals("And the last question!", question6.getQuestion());
    }

    @Test
    public void exportWhichOneCameFirst() throws Exception {
        File excelFile = new File(getClass().getResource("exportWhichOneCameFirstQuestions.xlsx").getPath());
        importExcelTask.init(excelFile);
        List<AbstractQuestion> questions = importExcelTask.execute(callback);

        Assert.assertEquals(2, questions.size());
        WhichOneCameFirstQuestion question = (WhichOneCameFirstQuestion) questions.get(0);
        Assert.assertEquals("Which one came first?", question.getQuestion());
        Assert.assertEquals("background.jpg", question.getPictureId());
        Assert.assertEquals("East of Eden", question.getCorrectAnswer());
        Assert.assertEquals(125, question.getDifficulty());
        Assert.assertEquals("East of Eden", question.getMovieTitle1());
        Assert.assertEquals("The Misfits", question.getMovieTitle2());
        Assert.assertEquals("B84A53.jpg", question.getMovieTitle1Image());
        Assert.assertEquals("AFPMD1.jpg", question.getMovieTitle2Image());
        Assert.assertEquals(1955, question.getMovieTitle1Year());
        Assert.assertEquals(1961, question.getMovieTitle2Year());

        question = (WhichOneCameFirstQuestion) questions.get(1);
        Assert.assertEquals("Which one came first?", question.getQuestion());
        Assert.assertEquals("background.jpg", question.getPictureId());
        Assert.assertEquals("Blades of Glory", question.getCorrectAnswer());
        Assert.assertEquals(125, question.getDifficulty());
        Assert.assertEquals("Blades of Glory", question.getMovieTitle1());
        Assert.assertEquals("Step Brothers", question.getMovieTitle2());
        Assert.assertEquals("B84A53.jpg", question.getMovieTitle1Image());
        Assert.assertEquals("AFPMD1.jpg", question.getMovieTitle2Image());
        Assert.assertEquals(2007, question.getMovieTitle1Year());
        Assert.assertEquals(2008, question.getMovieTitle2Year());

    }

    @Test
    public void callback() throws Exception {
        File excelFile = new File(getClass().getResource("exportTrueFalseQuestions.xlsx").getPath());
        importExcelTask.init(excelFile);
        List<AbstractQuestion> questions = importExcelTask.execute(callback);
        Assert.assertEquals(2, questions.size());

        Mockito.verify(callback).taskDone(Mockito.contains(questions.get(0).toString()));
        Mockito.verify(callback).taskDone(Mockito.contains(questions.get(1).toString()));
    }

    @Before
    public void init() {
        importExcelTask = new ImportExcelTask();
        callback = Mockito.mock(TaskCallback.class);
    }

}
