package com.closeuptheapp.gui;

import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Panel;

/** @author Federico */
public class MainWindowTest {

    @Test
    public void createComponent() {
        init();

    }

    @Before
    public void init() {
        MainWindow mainWindow = new MainWindow();
        Panel panel = new Panel(mainWindow.getMainFrame().getContentPane());
        
    }

}
