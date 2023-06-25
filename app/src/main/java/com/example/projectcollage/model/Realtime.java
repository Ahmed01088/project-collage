package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

public class Realtime {
    @SerializedName("student_id")
    private Integer studentId;
    @SerializedName("lecturer_id")
    private Integer lecturerId;
    @SerializedName("is_online")
    private Integer isOnline;
    @SerializedName("is_quiz_started")
    private Integer isQuizStarted;
    @SerializedName("is_live")
    private Integer isLive;
    @SerializedName("classroom_id")
    private Integer classroomId;
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

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public boolean getIsQuizStarted() {
        return isQuizStarted != 0;
    }

    public void setIsQuizStarted(Integer isQuizStarted) {
        this.isQuizStarted = isQuizStarted;
    }

    public boolean getIsLive() {
        return isLive != 0;
    }

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }


}
