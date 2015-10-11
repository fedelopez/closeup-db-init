package com.closeuptheapp.gui;

import com.closeuptheapp.domain.AbstractQuestion;
import com.closeuptheapp.domain.TrueFalseQuestion;
import com.closeuptheapp.task.ImportExcelTask;
import com.closeuptheapp.task.PopulateTablesTask;
import com.closeuptheapp.task.TaskCallback;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.fixture.Containers.showInFrame;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

/**
 * @author fede
 */
public class MainWindowContentPaneTest {

    private JTextComponentFixture excelFilePath;
    private JButtonFixture initDB;
    private FrameFixture frameFixture;

    private PopulateTablesTask populateTablesTask;
    private ImportExcelTask importExcelTask;

    @Test
    public void excelFilePath() {
        excelFilePath.setText("not a valid excel file");
        assertThat(initDB.isEnabled()).isFalse();

        File excelFile = new File(getClass().getResource("sample.xlsx").getFile());
        excelFilePath.setText(excelFile.getAbsolutePath());
        assertThat(initDB.isEnabled()).isTrue();
    }

    @Test
    public void initDBInvoked() throws Exception {
        File excelFile = new File(getClass().getResource("sample.xlsx").getFile());
        excelFilePath.setText(excelFile.getAbsolutePath());

        List<AbstractQuestion> questions = new ArrayList<>();
        questions.add(new TrueFalseQuestion());
        Mockito.when(importExcelTask.execute(Mockito.any(TaskCallback.class))).thenReturn(questions);

        initDB.click();

        verify(importExcelTask, timeout(300)).init(Mockito.eq(excelFile));
        verify(populateTablesTask, timeout(300)).init(Mockito.eq(questions));
        verify(populateTablesTask, timeout(300)).execute(Mockito.any(TaskCallback.class));
    }

    private Module mockModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                populateTablesTask = Mockito.mock(PopulateTablesTask.class);
                bind(PopulateTablesTask.class).toInstance(populateTablesTask);

                importExcelTask = Mockito.mock(ImportExcelTask.class);
                bind(ImportExcelTask.class).toInstance(importExcelTask);
            }
        };
    }

    @Before
    public void setUp() {
        final Injector injector = Guice.createInjector(mockModule());
        injector.injectMembers(this);
        JPanel panel = GuiActionRunner.execute(new GuiQuery<JPanel>() {
            public JPanel executeInEDT() {
                MainWindowContentPane mainWindowPane = injector.getInstance(MainWindowContentPane.class);
                return mainWindowPane.getContentPanel();
            }
        });
        frameFixture = showInFrame(panel);
        excelFilePath = frameFixture.textBox(MainWindowContentPane.EXCEL_FILE_PATH_NAME);
        initDB = frameFixture.button(MainWindowContentPane.INIT_DB_NAME);
    }

    @After
    public void tearDown() {
        frameFixture.cleanUp();
    }

}
