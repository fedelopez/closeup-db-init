package com.closeuptheapp.gui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jdesktop.application.SingleFrameApplication;

import javax.swing.*;

/**
 * @author Federico
 */
public class MainWindow extends SingleFrameApplication {

    private MainWindowContentPane contentPanel;

    @Override
    protected void startup() {
        Injector injector = Guice.createInjector(new MainModule());
        contentPanel = injector.getInstance(MainWindowContentPane.class);
        show(contentPanel.getContentPanel());
    }

    public JComponent contentPane() {
        return contentPanel.getContentPanel();
    }

    public static void main(String[] args) {
        launch(MainWindow.class, args);
    }

}