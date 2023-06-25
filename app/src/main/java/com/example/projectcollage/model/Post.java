package com.example.projectcollage.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.Part;

public class Post {
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;
        @SerializedName("content")
        private String content;
        @SerializedName("image")
        private String image;
        @SerializedName("posted_at")
        private String posted_at;
        @SerializedName("created_at")
        private String created_at;
        @SerializedName("updated_at")
        private String updated_at;
        @SerializedName("student_id")
        private Integer studentId;
        @SerializedName("student_affairs_id")
        private Integer studentAffairsId;
        @SerializedName("lecturer_id")
        private Integer lecturerId;
        @SerializedName("person_name")
        private String personName;
        @SerializedName("person_image")
        private String personImage;
        @SerializedName("likes")
        private int likes;
        @SerializedName("number_of_comments")
        private int numberOfComments;
        @SerializedName("reaction")
        private List<Reaction> reactions;

    public Post(String title, String content, String posted_at) {
        this.title = title;
        this.content = content;
        this.posted_at = posted_at;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public int getLikes() {
        return likes;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPosted_at() {
        return posted_at;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentAffairsId() {
        return studentAffairsId;
    }

    public void setStudentAffairsId(Integer studentAffairsId) {
        this.studentAffairsId = studentAffairsId;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void setPosted_at(String posted_at) {
        this.posted_at = posted_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }





    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
