package com.closeuptheapp.task;

import com.closeuptheapp.domain.AbstractQuestion;
import com.closeuptheapp.domain.MultiAnswerQuestion;
import com.closeuptheapp.domain.TrueFalseQuestion;
import com.closeuptheapp.domain.WhichOneCameFirstQuestion;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.XmlDataSet;
import org.mockito.Mockito;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Federico
 */
public class PopulateTablesTaskTest extends DBTestCase {

    private static final String SQLITE_JDBC_CLASS = "org.sqlite.JDBC";
    private static final String CONNECTION_URL = "jdbc:sqlite:target/test-classes/closeup-tests.sqlite";

    private TaskCallback callback;

    private TrueFalseQuestion trueFalseQuestion1;
    private TrueFalseQuestion trueFalseQuestion2;

    public PopulateTablesTaskTest() {
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, SQLITE_JDBC_CLASS);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, CONNECTION_URL);
    }

    public void testPopulateMultiAnswerQuestions() throws Exception {
        PopulateTablesTask task = new PopulateTablesTask();
        task.init(new ArrayList<AbstractQuestion>(multiAnswerQuestions()));
        task.execute(callback);

        assertTable("question", "ExpectedMultiAnswerQuestions.xml");
        assertTable("multi_answer_question", "ExpectedMultiAnswerQuestions.xml");
    }

    public void testPopulateTrueFalseQuestions() throws Exception {
        PopulateTablesTask task = new PopulateTablesTask();
        task.init(new ArrayList<AbstractQuestion>(trueFalseQuestions()));
        task.execute(callback);

        assertTable("question", "ExpectedTrueFalseQuestions.xml");
        assertTable("true_false_question", "ExpectedTrueFalseQuestions.xml");
    }

    public void testPopulateWhichOneCameFirstQuestions() throws Exception {
        List<AbstractQuestion> list = new ArrayList<AbstractQuestion>();
        list.addAll(whichOneCameFirstQuestions());
        PopulateTablesTask task = new PopulateTablesTask();
        task.init(list);
        task.execute(callback);

        assertTable("question", "ExpectedWhichOneCameFirstQuestions.xml");
        assertTable("which_one_came_first", "ExpectedWhichOneCameFirstQuestions.xml");
    }

    public void testCallback() throws Exception {
        PopulateTablesTask task = new PopulateTablesTask();
        task.init(new ArrayList<AbstractQuestion>(trueFalseQuestions()));
        task.execute(callback);

        Mockito.verify(callback).taskDone(Mockito.contains(trueFalseQuestion1.toString()));
        Mockito.verify(callback).taskDone(Mockito.contains(trueFalseQuestion2.toString()));
    }


    private List<? extends AbstractQuestion> multiAnswerQuestions() {
        MultiAnswerQuestion question1 = new MultiAnswerQuestion();
        question1.setQuestionIndex(1);
        question1.setPictureId("sagrada_familia.jpg");
        question1.setQuestion("What is the capital of Catalonia?");
        question1.setCorrectAnswer("Barcelona");
        question1.setDifficulty(50);
        question1.setAnswer1("Barcelona");
        question1.setAnswer2("Tarragona");
        question1.setAnswer3("Lleida");
        question1.setAnswer4("Girona");

        MultiAnswerQuestion question2 = new MultiAnswerQuestion();
        question2.setQuestionIndex(2);
        question2.setPictureId("opera_house.jpg");
        question2.setQuestion("What is the name of this venue?");
        question2.setCorrectAnswer("Sydney Opera House");
        question2.setDifficulty(55);
        question2.setAnswer1("ANZ Stadium");
        question2.setAnswer2("State Theatre");
        question2.setAnswer3("Town Hall");
        question2.setAnswer4("Sydney Opera House");

        return Arrays.asList(question1, question2);
    }

    private List<? extends AbstractQuestion> trueFalseQuestions() {
        trueFalseQuestion1 = new TrueFalseQuestion();
        trueFalseQuestion1.setQuestionIndex(1);
        trueFalseQuestion1.setPictureId("sagrada_familia.jpg");
        trueFalseQuestion1.setQuestion("The capital of Catalonia is Barcelona.");
        trueFalseQuestion1.setCorrectAnswer("True");
        trueFalseQuestion1.setDifficulty(150);

        trueFalseQuestion2 = new TrueFalseQuestion();
        trueFalseQuestion2.setQuestionIndex(2);
        trueFalseQuestion2.setPictureId("opera_house.jpg");
        trueFalseQuestion2.setQuestion("The capital of Australia is Melbourne.");
        trueFalseQuestion2.setCorrectAnswer("False");
        trueFalseQuestion2.setDifficulty(200);
        trueFalseQuestion2.setAnswerWhenFalse("Canberra");

        return Arrays.asList(trueFalseQuestion1, trueFalseQuestion2);
    }

    private List<? extends AbstractQuestion> whichOneCameFirstQuestions() {
        WhichOneCameFirstQuestion question1 = new WhichOneCameFirstQuestion();
        question1.setQuestionIndex(1);
        question1.setPictureId("sunrise.jpg");
        question1.setQuestion("Which one came first?");
        question1.setCorrectAnswer("Film 1");
        question1.setDifficulty(150);
        question1.setHints(Arrays.asList("Film 1", "Film 2", "image 1, 1987", "image 2, 1999"));

        WhichOneCameFirstQuestion question2 = new WhichOneCameFirstQuestion();
        question2.setQuestionIndex(2);
        question2.setPictureId("nasa.jpg");
        question2.setQuestion("Which one came first?");
        question2.setCorrectAnswer("Apollo XIII");
        question2.setDifficulty(50);
        question2.setHints(Arrays.asList("Apollo XX", "Apollo XIII", "image 1, 2010", "image 2, 1984"));

        return Arrays.asList(question1, question2);
    }

    private void assertTable(String tableName, String expectedSchema) throws Exception {
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable(tableName);
        InputStream stream = getClass().getResourceAsStream(expectedSchema);
        IDataSet expectedDataSet = new XmlDataSet(stream);
        ITable expectedTable = expectedDataSet.getTable(tableName);
        Assertion.assertEquals(expectedTable, actualTable);
    }


    @Override
    protected void setUp() throws Exception {
        callback = Mockito.mock(TaskCallback.class);
        super.setUp();
    }

    protected IDataSet getDataSet() throws Exception {
        InputStream stream = getClass().getResourceAsStream("PopulateTablesTaskTest.xml");
        return new XmlDataSet(stream);
    }
}