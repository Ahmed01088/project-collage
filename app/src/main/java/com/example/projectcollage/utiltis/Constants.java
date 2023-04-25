package com.example.projectcollage.utiltis;

public class Constants {
    public static final String BASE_URL = "http://192.168.1.31:80/api/";
    public static final String BASE_URL_PATH_POSTS = "http://192.168.1.31:80/images/posts/";
    public static final String BASE_URL_PATH_USERS = "http://192.168.1.31:80/images/users/";
    public static final String BASE_URL_PATH_MESSAGES = "http://192.168.1.31:80/images/messages/";

    public static final String FILE_NAME ="filename" ;
    public static final String PATH = "path";
    public static final String IMAGE = "image";
    public static final String SENDER_ID= "sender";
    public static final String RECEIVER_ID= "receiver";
    public static final String [] USER_TYPES={"Student","Lecturer","Student Affairs","Admin"};
    public static final String [] LEVEL={"الاولي","الثانية","الثالثة","الرابعة"};
    public static final String [] SEMESTER={"الاول","الثاني"};
    public static final String []ADD_DATA_TYPE={"بيانات دكتور" ,"بيانات طالب","بيانات قسم","بيانات كورس"};
    public static final String []STUDENT_STATUS={"باقي","مستجد"};
    public static final String UID="uid";
    public static final String FULL_NAME="fullname";
    public static final String NATIONAL_ID="national_id";
    public static final String STUDENT_AFFAIRS="Student Affairs";
    public static final String STUDENT="Student";
    public static final String LECTURER="Lecturer";
    public static final String POST_ID="post_id";
    public static final String ADMIN="Admin";
    public static final String FIRSTNAME="firstname";
    public static final String LASTNAME="lastname";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    public static final String PHONE="phone";
    public static final String STUDENT_LEVEL="level";
    public static final String STUDENT_STAT="status";
    public static final String STUDENT_DEPARTMENT="department";
    public static final String DEPARTMENT_ID="department_id";
    public static final String USER_TYPE="user_type";
    public static final String DATA="data";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$\n";
    public static final String REGEX_LINKS= "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))";

    public static final String CHAT = "chat";
    public static final String CHAT_ID = "chat_id";
}
