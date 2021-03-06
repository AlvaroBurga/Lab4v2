package pe.pucp.dduu.tel306.lab4g3.Entities;

import java.io.Serializable;

public class Stats implements Serializable {
    private int id;
    private String questionText;
    private AnswerStats[] answerstats;

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

    public AnswerStats[] getAnswerstats() {
        return answerstats;
    }

    public void setAnswerstats(AnswerStats[] answerstats) {
        this.answerstats = answerstats;
    }
}
