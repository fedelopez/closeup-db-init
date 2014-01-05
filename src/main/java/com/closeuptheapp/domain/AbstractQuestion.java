package com.closeuptheapp.domain;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Federico
 */
@SuppressWarnings({"JpaDataSourceORMInspection"})
@Entity(name = "question")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractQuestion implements Serializable {

    private int questionIndex;

    private String pictureId;

    private String correctAnswer;

    private String question;

    private int difficulty;

    private Type type;

    public enum Type {
        MULTI_ANSWER, TRUE_FALSE, WHICH_ONE_CAME_FIRST;

        public static Type questionTypeFromString(String questionType) {
            if (StringUtils.isBlank(questionType)) {
                return null;
            }
            if ("m".equalsIgnoreCase(questionType)) {
                return MULTI_ANSWER;
            } else if ("tf".equalsIgnoreCase(questionType)) {
                return TRUE_FALSE;
            } else if ("wocf".equalsIgnoreCase(questionType)) {
                return WHICH_ONE_CAME_FIRST;
            } else {
                return null;
            }
        }
    }

    @Transient
    protected abstract Type getType();

    public abstract void setHints(List<String> values);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", unique = true, nullable = false)
    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    @Column(name = "image_name")
    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    @Column(name = "difficulty")
    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name = "question_text")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Column(name = "correct_answer")
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionIndex=" + questionIndex +
                ", pictureId='" + StringUtils.abbreviate(pictureId, 10) + '\'' +
                ", difficulty=" + difficulty +
                ", type=" + type +
                ", question='" + StringUtils.abbreviate(question, 15) + "'" +
                ", correctAnswer='" + StringUtils.abbreviate(correctAnswer, 15) + "'" +
                '}';
    }
}        
