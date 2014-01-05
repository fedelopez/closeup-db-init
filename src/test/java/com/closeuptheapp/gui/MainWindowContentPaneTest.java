package com.closeuptheapp.gui;

import com.closeuptheapp.domain.AbstractQuestion;
import com.closeuptheapp.domain.TrueFalseQuestion;
import com.closeuptheapp.task.ImportExcelTask;
import com.closeuptheapp.task.PopulateTablesTask;
import com.closeuptheapp.task.TaskCallback;
import org.mockito.Mockito;
import org.uispec4j.Button;
import org.uispec4j.Panel;
import org.uispec4j.TextBox;
import org.uispec4j.UISpecTestCase;

import java.io.File;
import java.util.ArrayList;

/**
 * User: fede
 * Date: Dec 23, 2010
 * Time: 6:28:21 PM
 */
public class MainWindowContentPaneTest extends UISpecTestCase {

    private TextBox excelFilePath;
    private Button initDB;

    private PopulateTablesTask populateTask;
    private ImportExcelTask importTaskTask;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        populateTask = Mockito.mock(PopulateTablesTask.class);
        importTaskTask = Mockito.mock(ImportExcelTask.class);
        MainWindowContentPane mainWindowPane = new MainWindowContentPane(importTaskTask, populateTask);
        Panel panel = new Panel(mainWindowPane.getContentPanel());
        excelFilePath = panel.getTextBox(MainWindowContentPane.EXCEL_FILE_PATH_NAME);
        initDB = panel.getButton(MainWindowContentPane.INIT_DB_NAME);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testExcelFilePath() {
        excelFilePath.setText("not a valid excel file");
        assertFalse(initDB.isEnabled());

        File excelFile = new File(getClass().getResource("sample.xlsx").getFile());
        excelFilePath.setText(excelFile.getAbsolutePath());
        assertTrue(initDB.isEnabled());
    }

    public void testInitDBInvoked() throws Exception {
        File excelFile = new File(getClass().getResource("sample.xlsx").getFile());
        excelFilePath.setText(excelFile.getAbsolutePath());

        ArrayList<AbstractQuestion> questions = new ArrayList<AbstractQuestion>();
        questions.add(new TrueFalseQuestion());
        Mockito.when(importTaskTask.execute(Mockito.any(TaskCallback.class))).thenReturn(questions);

        initDB.click();

        Thread.sleep(300);

        Mockito.verify(importTaskTask).init(Mockito.eq(excelFile));
        Mockito.verify(populateTask).init(Mockito.eq(questions));
        Mockito.verify(populateTask).execute(Mockito.any(TaskCallback.class));
    }

}
