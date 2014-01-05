package com.closeuptheapp.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author Federico
 */
@SuppressWarnings({"JpaDataSourceORMInspection"})
@Entity(name = "multi_answer_question")
public class MultiAnswerQuestion extends AbstractQuestion {

    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    @Transient
    protected Type getType() {
        return Type.MULTI_ANSWER;
    }

    @Transient
    public void setHints(List<String> values) {
        answer1 = values.get(0);
        answer2 = values.get(1);
        answer3 = values.get(2);
        answer4 = values.get(3);
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String value) {
        answer1 = value;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String value) {
        answer2 = value;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String value) {
        answer3 = value;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String value) {
        answer4 = value;
    }

}