package com.closeuptheapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author Federico
 */
@SuppressWarnings({"JpaDataSourceORMInspection"})
@Entity(name = "true_false_question")
public class TrueFalseQuestion extends AbstractQuestion {

    private String answerWhenFalse;

    @Transient
    protected Type getType() {
        return Type.TRUE_FALSE;
    }

    @Transient
    public void setHints(List<String> values) {
        if (values.size() > 2) {
            answerWhenFalse = values.get(2);
        }
    }

    @Column(name = "answer_when_false")
    public String getAnswerWhenFalse() {
        return answerWhenFalse;
    }

    public void setAnswerWhenFalse(String answerWhenFalse) {
        this.answerWhenFalse = answerWhenFalse;
    }
}