package com.closeuptheapp.domain;

/**
 * User: fede
 * Date: Dec 20, 2010
 * Time: 8:35:34 AM
 */
public class QuestionFactory {

    public static AbstractQuestion instanceFromType(String typeString) {
        AbstractQuestion.Type type = AbstractQuestion.Type.questionTypeFromString(typeString);
        if (type == null) {
            return null;
        }
        switch (type) {
            case MULTI_ANSWER:
                return new MultiAnswerQuestion();
            case TRUE_FALSE:
                return new TrueFalseQuestion();
            case WHICH_ONE_CAME_FIRST:
                return new WhichOneCameFirstQuestion();

        }
        return null;
    }
}
