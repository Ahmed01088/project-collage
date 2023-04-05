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
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Department;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        binding.semisterSpinner.setAdapter(adapterClass);
        binding.departCourses.setAdapter(adapter);
        binding.send.setOnClickListener(v -> {
            String nameDepartment=binding.nameDepartment.getText().toString();
            String departmentCode=binding.codeDepartment.getText().toString();
            String level=binding.spinLevel.getSelectedItem().toString();
            String semister=binding.semisterSpinner.getSelectedItem().toString();
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String dateNow=simpleDateFormat.format(date);
            Department department=new Department(nameDepartment,departmentCode,level,semister,dateNow);
            addDepartment(department);

        });
    }


    private void addDepartment(Department department){
        Call<Data<Department>> call= RetrofitClientLaravelData.getInstance().getApiInterface().addDepartment(department);
        call.enqueue(new Callback<Data<Department>>() {
            @Override
            public void onResponse(@NonNull Call<Data<Department>> call, @NonNull Response<Data<Department>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), "تم اضافة القسم بنجاح", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<Department>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "حدث خطأ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}