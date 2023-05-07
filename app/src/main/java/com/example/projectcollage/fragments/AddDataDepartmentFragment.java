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
import com.example.projectcollage.databinding.FragmentAddDataDepartmentBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.Department;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;
import com.example.projectcollage.utiltis.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddDataDepartmentFragment extends Fragment {
    FragmentAddDataDepartmentBinding binding;

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
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner, Constants.LEVEL);
        ArrayAdapter<String>adapterClass=
                new ArrayAdapter<>(getActivity(), R.layout.item_spinner,Constants.SEMESTER);
        adapterLevels.setDropDownViewResource(R.layout.item_spinner);
        adapterClass.setDropDownViewResource(R.layout.item_spinner);
        binding.spinLevel.setAdapter(adapterLevels);
        binding.semisterSpinner.setAdapter(adapterClass);
        binding.send.setOnClickListener(v -> {
            String departmentCode=binding.codeDepartment.getText().toString();
            String level=binding.spinLevel.getSelectedItem().toString();
            String semester=binding.semisterSpinner.getSelectedItem().toString();
            String nameDepartment = null;
            if (level.equals(Constants.LEVEL[0])){
                nameDepartment=binding.nameDepartment.getText().toString()+ "  1";
            }else if (level.equals(Constants.LEVEL[1])){
                nameDepartment=binding.nameDepartment.getText().toString()+ "  2";
            }else if (level.equals(Constants.LEVEL[2])){
                nameDepartment=binding.nameDepartment.getText().toString()+ "  3";
            }else if (level.equals(Constants.LEVEL[3])){
                nameDepartment=binding.nameDepartment.getText().toString()+ "  4";
            }
            Department department=new Department(nameDepartment,departmentCode,level,semester);
            addDepartment(department);
            binding.nameDepartment.setText("");
            binding.codeDepartment.setText("");
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