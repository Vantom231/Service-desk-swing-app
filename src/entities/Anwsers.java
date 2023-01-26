package entities;

import java.util.ArrayList;

public class Anwsers {
    private int lastId;
    private ArrayList<Question> questions;
    public Anwsers(){
        questions = new ArrayList<>();
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Anwsers{" +
                "lastId=" + lastId +
                "," + questions +
                '}';
    }
}
