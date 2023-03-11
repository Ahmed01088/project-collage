package com.example.projectcollage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.FragmentAddDataCourseBinding;

public class AddDataCourseFragment extends Fragment {
    FragmentAddDataCourseBinding binding;
    String[] studentLevels={"الربعة","التالتة ","التانية","الاولي "};
    String[] studentDepartment={"Computer Science","Biology","Chemistry","Math","Physics","Math&Physics","Chemistry&Physics"};
    String []course_class={"التاني","الاول"};

    public AddDataCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentAddDataCourseBinding.inflate(getLayoutInflater());
       preparationSpinner();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     return binding.getRoot();
    }
    public void preparationSpinner(){
        ArrayAdapter<String> adapterLevels=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,studentLevels);
        ArrayAdapter<String>adapterClass=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,course_class);
        adapterLevels.setDropDownViewResource(R.layout.item_spinner);
        adapterClass.setDropDownViewResource(R.layout.item_spinner);
        binding.courseLevel.setAdapter(adapterLevels);
        binding.courseClass.setAdapter(adapterClass);
    }
}