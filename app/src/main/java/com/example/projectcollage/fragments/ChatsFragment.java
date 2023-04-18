package com.example.projectcollage.fragments;

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
import com.example.projectcollage.models.User;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {
    FragmentChatsBinding binding;
    UserAdapter adapter;
    ArrayList<User> users;

    public ChatsFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentChatsBinding.inflate(getLayoutInflater());
        adapter=new UserAdapter(getContext(), users);
        binding.rvUsers.setAdapter(adapter);
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        adapter.notifyItemInserted(users.size());

    }
}