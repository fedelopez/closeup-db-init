package com.closeuptheapp.task;

/**
 * User: fede
 * Date: Dec 20, 2010
 * Time: 8:31:52 PM
 */
public interface Task<T, U> {

    void init(T initObject);

    U execute(TaskCallback callback) throws Exception;

    String name();
}
