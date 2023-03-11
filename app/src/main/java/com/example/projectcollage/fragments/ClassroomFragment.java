package com.example.projectcollage.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.ClassroomAdapter;
import com.example.projectcollage.databinding.FragmentClassroomBinding;
import com.example.projectcollage.models.Course;

import java.util.ArrayList;
public class ClassroomFragment extends Fragment {
    FragmentClassroomBinding binding;

    public ClassroomFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentClassroomBinding.inflate(getLayoutInflater());
        ArrayList<Course> courses=new ArrayList<>();
        courses.add(new Course("Formal Language"));
        courses.add(new Course("Probability"));
        courses.add(new Course("Cyber Security"));
        courses.add(new Course("Function Analysis"));
        courses.add(new Course("Complex Number"));
        courses.add(new Course("Tensor"));
        courses.add(new Course("DataStructure Algorithms"));
        LinearLayoutManager manager=new LinearLayoutManager(binding.getRoot().getContext());
        ClassroomAdapter adapter=new ClassroomAdapter(binding.getRoot().getContext(), courses);
        binding.rvMessage.setAdapter(adapter);
        binding.rvMessage.setLayoutManager(manager);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
}