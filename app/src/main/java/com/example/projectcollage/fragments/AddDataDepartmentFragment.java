package com.example.projectcollage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.projectcollage.R;
import com.example.projectcollage.adapters.SpinnerAdapter;
import com.example.projectcollage.databinding.FragmentAddDataDepartmentBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


public class AddDataDepartmentFragment extends Fragment {
    FragmentAddDataDepartmentBinding binding;
    String[] level={"الربعة","التالتة ","التانية","الاولي "};
    String[] classS={"الاول","التاني"};
    String[] depart={"Computer Science","Biology","Chemistry","Math","Physics","Math&Physics","Chemistry&Physics"};
    List<String >courses= Arrays.asList("Data Structure","Formal Language","Complex Analysis"
    , "Function Analysis","Probability","Tensors","Cyber security","Project");
//    String[] courses={"Data Structure","Formal Language","Complex Analysis"};

    public AddDataDepartmentFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentAddDataDepartmentBinding.inflate(getLayoutInflater());
        preparationSpinner();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }
    public void preparationSpinner(){
        ArrayAdapter<String> adapterLevels=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,level);
        ArrayAdapter<String>adapterClass=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,classS);
        SpinnerAdapter adapter=new SpinnerAdapter(getActivity(),R.layout.multi_item_spinner,courses);
        binding.send.setOnClickListener(view -> {
            if (SpinnerAdapter.checked==null){
                Toast.makeText(getActivity(), "لم يتم تحديد اي عناصر ", Toast.LENGTH_SHORT).show();
            }else{
                for (int i = 0; i < SpinnerAdapter.checked.length; i++) {
                    Toast.makeText(getActivity(), ""+SpinnerAdapter.checked[i], Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapterLevels.setDropDownViewResource(R.layout.item_spinner);
        adapterClass.setDropDownViewResource(R.layout.item_spinner);
        binding.spinLevel.setAdapter(adapterLevels);
        binding.spinClass.setAdapter(adapterClass);
        binding.departCourses.setAdapter(adapter);
    }
}