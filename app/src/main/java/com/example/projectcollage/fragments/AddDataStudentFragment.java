package com.example.projectcollage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.projectcollage.R;
import com.example.projectcollage.databinding.FragmentAddDataStudentBinding;

public class AddDataStudentFragment extends Fragment {
    FragmentAddDataStudentBinding binding;
    String[] studentLevels={"الربعة","التالتة ","التانية","الاولي "};
    String[] studentState={"باقي","مستجد"};
    String[] studentDepartment={"Computer Science","Biology","Chemistry","Math","Physics","Math&Physics","Chemistry&Physics"};

    public AddDataStudentFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentAddDataStudentBinding.inflate(getLayoutInflater());
   preparationSpinner();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    public void preparationSpinner(){

        ArrayAdapter<String>adapterLevels=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,studentLevels);
        ArrayAdapter<String>adapterDepartment=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,studentDepartment);
        ArrayAdapter<String>adapterState=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,studentState);
        adapterState.setDropDownViewResource(R.layout.item_spinner);
        adapterDepartment.setDropDownViewResource(R.layout.item_spinner);
        adapterLevels.setDropDownViewResource(R.layout.item_spinner);
        binding.studentState.setAdapter(adapterState);
        binding.studentDepartment.setAdapter(adapterDepartment);
        binding.studentLevel.setAdapter(adapterLevels);
    }

}