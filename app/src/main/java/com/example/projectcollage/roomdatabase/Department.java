package com.example.projectcollage.roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

enum Semester{
        First,
        Second
}
@Entity(tableName = "departments")
public class Department {
        @PrimaryKey(autoGenerate = true)
        private int id;
        private String departCode;
        private String name;
        private Semester semester;
        private Level level;
        private List<Course>courses;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getDepartCode() {
                return departCode;
        }

        public void setDepartCode(String departCode) {
                this.departCode = departCode;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Semester getSemester() {
                return semester;
        }

        public void setSemester(Semester semester) {
                this.semester = semester;
        }

        public Level getLevel() {
                return level;
        }

        public void setLevel(Level level) {
                this.level = level;
        }

        public List<Course> getCourses() {
                return courses;
        }

        public void setCourses(List<Course> courses) {
                this.courses = courses;
        }
}
