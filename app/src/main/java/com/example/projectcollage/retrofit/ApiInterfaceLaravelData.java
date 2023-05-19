package com.example.projectcollage.retrofit;

import com.example.projectcollage.model.Admin;
import com.example.projectcollage.model.Chat;
import com.example.projectcollage.model.Classroom;
import com.example.projectcollage.model.Comment;
import com.example.projectcollage.model.Course;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Department;
import com.example.projectcollage.model.Lecturer;
import com.example.projectcollage.model.Message;
import com.example.projectcollage.model.Notification;
import com.example.projectcollage.model.Post;
import com.example.projectcollage.model.Question;
import com.example.projectcollage.model.Quiz;
import com.example.projectcollage.model.Rating;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.model.StudentAffairs;
import com.example.projectcollage.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;


public interface ApiInterfaceLaravelData {
    //=================================================================
    //================================Student==========================
    //=================================================================
    @POST("student/add")
    Call<Data<Student>> addStudent(@Body Student student);
    @Multipart
    @POST("student/add")
    Call<Data<Student>> createStudent(
                @Part("firstname") RequestBody firstname,
                @Part("lastname") RequestBody lastname,
                @Part("national_id") RequestBody national_id,
                @Part("email") RequestBody email,
                @Part("phone_no") RequestBody phone_no,
                @Part("password") RequestBody password,
                @Part("level") RequestBody level,
                @Part("state") RequestBody state,
                @Part("department_code") RequestBody department_code,
                @Part("department_id") Integer department_id,
                @Part MultipartBody.Part image
    );
    @GET("student/login/{national_id}/{password}")
    Call<Data<Student>> loginStudent(@Path("national_id") String national_id, @Path("password") String password);
    @GET("student/getAllStudentByDepartmentId/{departmentId}")
    Call<Data<List<Student>>> getAllStudentByDepartmentId(@Path("departmentId") int departmentId);
    @Multipart
    @POST("student/{student_id}/update-fcm-token")
    Call<Data<Student>> updateFcmToken(@Path("student_id") int student_id, @Part("fcm_token") RequestBody fcm_token);

    //=================================================================
    //=================================Admin===========================
    //=================================================================
    @POST("admin/add")
    Call<Data<Admin>> addAdmin();
    @GET("admin/login/{national_id}/{password}")
    Call<Data<Admin>> loginAdmin(@Path("national_id") String national_id, @Path("password") String password);

    //=================================================================
    //=================================Lecturer==========================
    //=================================================================
    @POST("lecturer/add")
    Call<Data<Lecturer>> addLecturer(@Body Lecturer lecturer);
    @Multipart
    @POST("lecturer/add")
    Call<Data<Lecturer>> createLecturer(
            @Part("firstname") String firstname,
            @Part("lastname") String lastname,
            @Part("national_id") Integer national_id,
            @Part("email") String email,
            @Part("phone_no") String phone_no,
            @Part("password") String password,
            @Part MultipartBody.Part image
         );
    @GET("lecturer/login/{national_id}/{password}")
    Call<Data<Lecturer>> loginLecturer(@Path("national_id") String national_id, @Path("password") String password);
    @GET("lecturer/{id}")
    Call<Data<Lecturer>> getLecturer(@Path("id") int id);
    @GET("lecturer/getLecturerByCourseId/{course_id}")
    Call<Data<Lecturer>> getLecturerByCourseId(@Path("course_id") int course_id);
    //=================================================================
    //=================================Student Affairs=================
    //=================================================================
    @POST("student-affairs/add")
    Call<Data<StudentAffairs>> addAdminStudentAffairs(@Body StudentAffairs studentAffairs);

    @Multipart
    @POST("student-affairs/add")
    Call<Data<StudentAffairs>> createStudentAffairs(
            @Part("firstname") RequestBody firstname,
            @Part("lastname") RequestBody lastname,
            @Part("national_id") Integer national_id,
            @Part("email") RequestBody email,
            @Part("phone_no") RequestBody phone_no,
            @Part("password") RequestBody password,
            @Part("image") MultipartBody.Part image,
            @Part("admin_id") Integer admin_id,
            @Part("date_added") RequestBody date_added,
            @Part("responsible_level") RequestBody responsible_level
    );
    @GET("student-affairs/all")
    Call<Data<List<StudentAffairs>>> getAllStudentAffairs();
    @GET("student-affairs/login/{national_id}/{password}")
    Call<Data<StudentAffairs>> loginStudentAffairs(@Path("national_id") String national_id, @Path("password") String password);
    @DELETE("student-affairs/delete/{id}")
    Call<Data<StudentAffairs>> deleteStudentAffairs(@Path("id") int id);

    //=================================================================
    //=================================Department======================
    //=================================================================
    @GET("department/all")
    Call<Data<List<Department>>> getAllDepartments();
    @POST("department/add")
    Call<Data<Department>> addDepartment(@Body Department department);

    @GET("department/{id}")
    Call<Data<Department>> getDepartment(@Path("id") int id);
    // ================================================================
    //=================================Course==========================
    //=================================================================
    @GET("course/all")
    Call<Data<List<Course>>> getAllCourses();
    @POST("course/add")
    Call<Data<Course>> addCourse(@Body Course course);
    @GET("course/getCoursesByDepartmentId/{department_id}")
    Call<Data<List<Course>>> getCourseByDepartmentId(@Path("department_id") int department_id);

    //=================================================================
    //=================================Classroom=======================
    //=================================================================
    @POST("classroom/add")
    Call<Data<Classroom>> addClassroom(@Body Classroom classroom);
    @GET("classroom/all")
    Call<Data<List<Classroom>>> getAllClassrooms();
    @GET("classroom/getCourseNameByClassroomId/{classroom_id}")
    Call<String[]> getCourseNameByClassroomId(@Path("classroom_id") int classroom_id);
    @GET("classroom/getClassroomsByDepartmentId/{department_id}")
    Call<Data<List<Classroom>>> getClassroomsByDepartmentId(@Path("department_id") int department_id);
    @GET("classroom/getClassroomByLecturerId/{lecturer_id}")
    Call<Data<List<Classroom>>> getClassroomByLecturerId(@Path("lecturer_id") int lecturer_id);


    //=================================================================
    //=================================Chat============================
    //=================================================================
    @GET("chat/all")
    Call<Data<List<Chat>>> getAllChats();
    @POST("chat/add")
    Call<Data<Chat>> addChat(@Body Chat chat);
    @GET("chat/getChatByClassroomId/{classroom_id}")
    Call<Data<Chat>> getChatByClassroomId(@Path("classroom_id") int classroom_id);

    @GET("chat/getChatsByStudentId/{student_id}")
    Call<Data<List<Chat>>> getChatsByStudentId(@Path("student_id") int student_id);
    @GET("chat/getChatsByLecturerId/{lecturer_id}")
    Call<Data<List<Chat>>> getChatsByLecturerId(@Path("lecturer_id") int lecturer_id);
    @GET("chat/getChatsByStudentAffairsId/{student_affairs_id}")
    Call<Data<List<Chat>>> getChatsByStudentAffairsId(@Path("student_affairs_id") int student_affairs_id);



    //=================================================================
    //=================================Quiz============================
    //=================================================================
    @GET("quiz/all")
    Call<Data<List<Chat>>> getAllQuizzes();
    @POST("quiz/add")
    Call<Data<Quiz>> addQuiz(@Body Quiz quiz);



    //=================================================================
    //=================================Question========================
    //=================================================================
    @GET("question/all")
    Call<Data<List<Chat>>> getAllQuestions();
    @POST("question/add")
    Call<Data<Question>> addQuestion(@Body Question question);
    @GET("question/getQuestionsByQuizIdAndLecturerId/{quiz_id}/{lecturer_id}")
    Call<Data<List<Question>>> getQuestionsByQuizIdAndLecturerId(@Path("quiz_id") int quiz_id, @Path("lecturer_id") int lecturer_id);

    //=================================================================
    //==================================post===========================
    //=================================================================
    @POST("post/add")
    Call<Data<Post>> addPost(@Body Post post);
    @Multipart
    @POST("post/add")
    Call<Data<Post>> createPost(
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part MultipartBody.Part image,
            @Part("posted_at") RequestBody posted_at,
            @Part("student_id") Integer student_id,
            @Part("student_affairs_id") Integer student_affairs_id,
            @Part("lecturer_id") Integer lecturer_id,
            @Part("likes") Integer likes,
            @Part("number_of_comments") Integer number_of_comments
    );
    @GET("posts/student/{student_id}")
    Call<Data<List<Post>>> getPostsByStudentId(@Path("student_id") int student_id);
    @GET("posts/lecturer/{lecturer_id}")
    Call<Data<List<Post>>> getPostsByLecturerId(@Path("lecturer_id") int lecturer_id);
    @GET("posts/student-affairs/{student_affairs_id}")
    Call<Data<List<Post>>> getPostsByStudentAffairsId(@Path("student_affairs_id") int student_affairs_id);
    @GET("posts/getAll?sort=created_at,asc")
    Call<Data<List<Post>>> getAllPosts();
    @Multipart
    @PUT("user/photo")
    Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);

    @DELETE("posts/deletebystudentid/{id}/{student_id}")
    Call<Data<Post>> deletePostByStudentId(@Path("id") int id, @Path("student_id") int student_id);
    @DELETE("posts/deletebystudentaffairsid/{id}/{student_affairs_id}")
    Call<Data<Post>> deletePostByStudentAffairsId(@Path("id") int id, @Path("student_affairs_id") int student_affairs_id);
    @DELETE("posts/deletebylecturerid/{id}/{lecturer_id}")
    Call<Data<Post>> deletePostByLecturerId(@Path("id") int id, @Path("lecturer_id") int lecturer_id);


    //=================================================================
    //==================================comment========================
    //=================================================================
    @POST("comment/add")
    Call<Data<Comment>> addComment(@Body Comment comment);
    @GET("comment/getCommentsByPostId/{post_id}")
    Call<Data<List<Comment>>> getCommentsByPostId(@Path("post_id") int post_id);
    @DELETE("comments/deletebystudentid/{id}/{student_id}")
    Call<Data<Comment>> deleteCommentByStudentId(@Path("id") int id, @Path("student_id") int student_id);
    @DELETE("comments/deletebystudentaffairsid/{id}/{student_affairs_id}")
    Call<Data<Comment>> deleteCommentByStudentAffairsId(@Path("id") int id, @Path("student_affairs_id") int student_affairs_id);
    @DELETE("comments/deletebylecturerid/{id}/{lecturer_id}")
    Call<Data<Comment>> deleteCommentByLecturerId(@Path("id") int id, @Path("lecturer_id") int lecturer_id);


    //=================================================================
    //==================================rating=========================
    //=================================================================
    @POST("rating/add")
    Call<Data<Rating>> addRating(@Body Rating rating);




    //=================================================================
    //==================================message========================
    //=================================================================
    @POST("message/add")
    Call<Data<Message>> addMessage(@Body Message message);

    @Multipart
    @POST("message/add")
    Call<Data<Message>> createMessage(
            @Part("content") RequestBody content,
            @Part("sentAt") RequestBody sentAt,
            @Part("classroom_id") Integer classroom_id,
            @Part("chat_id") Integer chat_id,
            @Part("sender") Integer sender,
            @Part("receiver") Integer receiver,
            @Part MultipartBody.Part image
    );
    @GET("message/getMessagesByChatId/{chat_id}")
    Call<Data<List<Message>>> getMessagesByChatId(@Path("chat_id") int chat_id);

    @DELETE("message/deleteMessageById/{id}")
    Call<Data<Message>> deleteMessageById(@Path("id") int id);

    @GET("message/getMessagesByClassroomId/{classroom_id}")
    Call<Data<List<Message>>> getMessagesByClassroomId(@Path("classroom_id") int classroom_id);

    //=================================================================
    //==================================notifications==================
    //=================================================================
    @POST("notification/sendNotificationsForAllStudents")
    Call<Void> sendNotificationsForAllStudents(@Body Notification notification);
    @POST("/notification/sendNotification")
    Call<Data<Notification>> sendNotification(@Body Notification notification);

}
