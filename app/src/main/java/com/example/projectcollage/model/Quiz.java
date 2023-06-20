package com.example.projectcollage.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Quiz {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("classroom_id")
    private int classroomId;
    @SerializedName("course_id")
    private int courseId;
    @SerializedName("limit_time")
    private int limitTime;
    @SerializedName("lecturer_id")
    private int lecturerId;
    @SerializedName("number_questions")
    private int numberQuestions;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("created_at")
    private String createdAt;

    public Quiz() {
    }

    public Quiz(String titleQuiz, String descriptionQuiz, int numberQuestionQuiz, int quizTimeQuiz, int courseId, int classroomId, int uid) {
        this.title = titleQuiz;
        this.description = descriptionQuiz;
        this.numberQuestions = numberQuestionQuiz;
        this.limitTime = quizTimeQuiz;
        this.courseId = courseId;
        this.classroomId = classroomId;
        this.lecturerId = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(int limitTime) {
        this.limitTime = limitTime;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public int getNumberQuestions() {
        return numberQuestions;
    }

    public void setNumberQuestions(int numberQuestions) {
        this.numberQuestions = numberQuestions;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", classroomId=" + classroomId +
                ", courseId=" + courseId +
                ", limitTime=" + limitTime +
                ", lecturerId=" + lecturerId +
                ", numberQuestions=" + numberQuestions +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}

