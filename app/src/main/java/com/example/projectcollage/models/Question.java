package com.example.projectcollage.models;

public class Question {
    private String question;
    private String answerLecturer;
    private String answerStudent;

    public Question() {
    }

    public Question(String question, String answerLecturer, String answerStudent) {
        this.question = question;
        this.answerLecturer = answerLecturer;
        this.answerStudent = answerStudent;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerLecturer() {
        return answerLecturer;
    }

    public void setAnswerLecturer(String answerLecturer) {
        this.answerLecturer = answerLecturer;
    }

    public String getAnswerStudent() {
        return answerStudent;
    }

    public void setAnswerStudent(String answerStudent) {
        this.answerStudent = answerStudent;
    }
}
