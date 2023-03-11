package com.example.projectcollage.model;

public class Question {
    private int qid;
    private int quid; //quiz id
    private String question;
    private String  answerA;
    private String  answerB;
    private String  answerC;
    private String  answerD;
    private boolean  correctAnswer;

    public Question() {
    }

    public Question(int qid, boolean correctAnswer) {
        this.qid = qid;
        this.correctAnswer = correctAnswer;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
