package pe.pucp.dduu.tel306.lab4g3.Entities;

import java.io.Serializable;

public class Pregunta implements Serializable {
    private int id;
    private String questionText;
    private Answer[] answers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }
}
