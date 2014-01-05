package com.closeuptheapp.gui;

import com.closeuptheapp.domain.AbstractQuestion;
import com.closeuptheapp.task.ImportExcelTask;
import com.closeuptheapp.task.PopulateTablesTask;
import com.closeuptheapp.task.TaskCallback;
import org.apache.commons.io.FilenameUtils;
import org.jdesktop.swingx.JXBusyLabel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * @author Federico
 */
public class MainWindowContentPane {

    public static final String EXCEL_FILE_PATH_NAME = "EXCEL_FILE_PATH_NAME";
    public static final String BROWSE_NAME = "BROWSE_NAME";
    public static final String INIT_DB_NAME = "INIT_DB_NAME";

    private JPanel contentPanel;
    private JTextField excelFilePath;
    private JButton browse;
    private JButton initialiseDatabaseButton;
    private JTextArea logConsole;
    private JXBusyLabel busyLabel;

    private PopulateTablesTask dbTask;
    private ImportExcelTask importTask;

    public MainWindowContentPane(ImportExcelTask importTaskTask, PopulateTablesTask dbTask) {
        this.dbTask = dbTask;
        this.importTask = importTaskTask;
        excelFilePath.setName(EXCEL_FILE_PATH_NAME);
        browse.setName(BROWSE_NAME);
        initialiseDatabaseButton.setName(INIT_DB_NAME);
        initListeners();
        busyLabel.setVisible(false);
    }

    private void initListeners() {
        excelFilePath.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent documentEvent) {
                updateInitDatabaseButton();
            }

            public void removeUpdate(DocumentEvent documentEvent) {
                updateInitDatabaseButton();
            }

            public void changedUpdate(DocumentEvent documentEvent) {
                updateInitDatabaseButton();
            }
        });
        initialiseDatabaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                busyLabel.setBusy(true);
                busyLabel.setVisible(true);
                appendText(">>>>>>>>>> Import process started...");
                LogConsoleUpdater updater = new LogConsoleUpdater();
                updater.execute();
            }
        });
        browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Excel 2007 files", "xlsx"));
                int returnVal = chooser.showOpenDialog(contentPanel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    excelFilePath.setText(chooser.getSelectedFile().getAbsolutePath());
                }

            }
        });
    }

    private void updateInitDatabaseButton() {
        String filePath = excelFilePath.getText();
        File file = new File(filePath);
        boolean enabled = file.exists() && FilenameUtils.getExtension(filePath).equalsIgnoreCase("xlsx");
        initialiseDatabaseButton.setEnabled(enabled);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    private void appendText(String message) {
        logConsole.append("\n" + message);
    }

    class LogConsoleUpdater extends SwingWorker<Void, String> implements TaskCallback {

        @Override
        public Void doInBackground() {
            importTask.init(new File(excelFilePath.getText()));
            try {
                List<AbstractQuestion> questions = importTask.execute(this);
                dbTask.init(questions);
                dbTask.execute(this);
            } catch (Exception exception) {
                busyLabel.setBusy(false);
                busyLabel.setVisible(false);
                appendText("Could not import Excel spreadsheet: " + exception);
                appendText("Aborting...");
                cancel(true);
            }
            return null;
        }

        @Override
        protected void done() {
            busyLabel.setBusy(false);
            busyLabel.setVisible(false);
            appendText(">>>>>>>>>> Import process completed.");
        }

        public void taskDone(String summary) {
            appendText(summary);
        }

    }

}
