package pe.pucp.dduu.tel306.lab4g3.Entities;

import java.io.Serializable;

public class AnswerStats implements Serializable {
    private Answer answer;
    private int count;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
