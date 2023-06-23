package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Realtime {
    @SerializedName("student_id")
    private Integer studentId;
    @SerializedName("lecturer_id")
    private Integer lecturerId;
    @SerializedName("is_online")
    private String isOnline;
    @SerializedName("is_quiz_started")
    private String isQuizStarted;
    @SerializedName("is_live")
    private String isLive;
    @SerializedName("quiz")
    private Quiz quiz;
    @SerializedName("quiz_id")
    private Integer quizId;

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public boolean getIsQuizStarted() {
        if (isQuizStarted == null) {
            return false;
        }
        return isQuizStarted.equals("1");
    }

    public void setIsQuizStarted(String isQuizStarted) {
        this.isQuizStarted = isQuizStarted;
    }

    public boolean getIsLive() {
        if (isLive == null) {
            return false;
        }
        return isLive.equals("1");
    }

    public void setIsLive(String isLive) {
        this.isLive = isLive;
    }


}
