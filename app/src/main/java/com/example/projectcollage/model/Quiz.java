package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Quiz {
    @SerializedName("id")
    private int quizId;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("number_question")
    private int numberQuestion;
    @SerializedName("time_limit")
    private int timeLimit;
    @SerializedName("score")
    private int score;
    @SerializedName("lecturer_id")
    private int lecturerId;

    public Quiz(String title, String description, int number_question, int time_limit, int score, int lecturer_id) {
        this.title = title;
        this.description = description;
        this.numberQuestion = number_question;
        this.timeLimit = time_limit;
        this.score = score;
        this.lecturerId = lecturer_id;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    // getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberQuestion() {
        return numberQuestion;
    }

    public void setNumberQuestion(int numberQuestion) {
        this.numberQuestion = numberQuestion;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }
}

