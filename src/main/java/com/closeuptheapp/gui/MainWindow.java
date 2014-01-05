package com.closeuptheapp.gui;

import com.closeuptheapp.task.ImportExcelTask;
import com.closeuptheapp.task.PopulateTablesTask;
import org.jdesktop.application.SingleFrameApplication;

import javax.swing.*;

/**
 * @author Federico
 */
public class MainWindow extends SingleFrameApplication {

    private MainWindowContentPane contentPanel;

    @Override
    protected void startup() {
        contentPanel = new MainWindowContentPane(new ImportExcelTask(), new PopulateTablesTask());
        show(contentPanel.getContentPanel());
    }

    public JComponent contentPane() {
        return contentPanel.getContentPanel();
    }

    public static void main(String[] args) {
        launch(MainWindow.class, args);
    }
}
