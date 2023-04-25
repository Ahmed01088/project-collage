package com.example.projectcollage.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectcollage.adapters.ChatUserAdapter;
import com.example.projectcollage.adapters.UserAdapter;
import com.example.projectcollage.database.Database;
import com.example.projectcollage.databinding.FragmentChatsBinding;
import com.example.projectcollage.model.Chat;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Lecturer;
import com.example.projectcollage.model.Student;
import com.example.projectcollage.model.StudentAffairs;
import com.example.projectcollage.model.User;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ChatsFragment extends Fragment {
    FragmentChatsBinding binding;
    List<Chat> chats;
    SharedPreferences data;

    public ChatsFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentChatsBinding.inflate(getLayoutInflater());
        data=getActivity().getSharedPreferences(Constants.DATA, Context.MODE_PRIVATE);
        if (data.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[0])) {
            getChatsByStudentId(data.getInt(Constants.UID, 0));
        }else if (data.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[1])){
            getChatsByLecturerId(data.getInt(Constants.UID, 0));
        }else if (data.getString(Constants.USER_TYPE,"").equals(Constants.USER_TYPES[2])){
            getChatsByStudentAffairsId(data.getInt(Constants.UID, 0));
        }

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return binding.getRoot();
    }
      private void getChatsByStudentId(int studentId){
        Call<Data<List<Chat>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getChatsByStudentId(studentId);
        call.enqueue(new retrofit2.Callback<Data<List<Chat>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Chat>>> call, @NonNull Response<Data<List<Chat>>> response) {
                if (response.isSuccessful()){
                    chats= response.body().getData();
                    UserAdapter<Student>adapter=new UserAdapter<>(getContext(), (ArrayList) chats);
                    binding.rvUsers.setAdapter(adapter);
                    binding.rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));

                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Chat>>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getChatsByStudentAffairsId(int studentAffairsId) {
        Call<Data<List<Chat>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getChatsByStudentAffairsId(studentAffairsId);
        call.enqueue(new retrofit2.Callback<Data<List<Chat>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Chat>>> call, @NonNull Response<Data<List<Chat>>> response) {
                if (response.isSuccessful()){
                    chats= response.body().getData();
                    UserAdapter<StudentAffairs>adapter=new UserAdapter<>(getContext(), (ArrayList) chats);
                    binding.rvUsers.setAdapter(adapter);
                    binding.rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
                }else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Chat>>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getChatsByLecturerId(int lecturerId) {
        Call<Data<List<Chat>>> call= RetrofitClientLaravelData.getInstance().getApiInterface().getChatsByLecturerId(lecturerId);
        call.enqueue(new retrofit2.Callback<Data<List<Chat>>>() {
            @Override
            public void onResponse(@NonNull Call<Data<List<Chat>>> call, @NonNull Response<Data<List<Chat>>> response) {
                if (response.isSuccessful()){
                    chats=response.body().getData();
                    UserAdapter<Lecturer>adapter=new UserAdapter<>(getContext(), (ArrayList) chats);
                    binding.rvUsers.setAdapter(adapter);
                    binding.rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
                }else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<List<Chat>>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}