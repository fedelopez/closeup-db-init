package com.closeuptheapp.gui;

import com.closeuptheapp.task.ImportExcelTask;
import com.closeuptheapp.task.PopulateTablesTask;
import com.google.inject.AbstractModule;

/**
 * @author fede
 */
public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PopulateTablesTask.class).asEagerSingleton();
        bind(ImportExcelTask.class).asEagerSingleton();
    }
}
