package com.closeuptheapp.task;

import com.closeuptheapp.domain.AbstractQuestion;
import com.google.code.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * User: fede
 * Date: Dec 20, 2010
 * Time: 8:31:25 PM
 */
public class PopulateTablesTask implements Task<List<AbstractQuestion>, Void> {
    private List<AbstractQuestion> questions;

    public void init(List<AbstractQuestion> questions) {
        this.questions = questions;
    }


    public Void execute(TaskCallback callback) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        for (AbstractQuestion question : questions) {
            session.save(question);
            callback.taskDone("Record inserted:" + question.toString());
        }
        tx.commit();
        session.close();
        HibernateUtil.shutdown();
        return null;
    }

    public String name() {
        return "Populate DB Tables task";
    }
}
