package com.example.projectcollage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.FragmentAddDataLecturerBinding;

public class AddDataLecturerFragment extends Fragment {
    FragmentAddDataLecturerBinding binding;
//    String[] studentLevels={"الربعة","التالتة ","التانية","الاولي "};
//    String[] nameCourses={"Data Structure","Formal Language","Complex Analysis"};
//    String[] studentDepartment={"Computer Science","Biology","Chemistry","Math","Physics","Math&Physics","Chemistry&Physics"};

    public AddDataLecturerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
preparationSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
    public void preparationSpinner(){
        binding=FragmentAddDataLecturerBinding.inflate(getLayoutInflater());

    }
}