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
import com.example.projectcollage.databinding.FragmentAddDataStudentBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.User;
import com.example.projectcollage.retrofit.RetrofitClientLaravelData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        binding.send.setOnClickListener(v -> {
            String name=binding.nameOfStudent.getText().toString();
            String email=binding.userEmail.getText().toString();
            String password=binding.password.getText().toString();
            String nationalId=binding.notionalId.getText().toString();
            binding.nameOfStudent.setText("");
            binding.userEmail.setText("");
            binding.password.setText("");
            binding.notionalId.setText("");
            if (name.isEmpty()||email.isEmpty()||password.isEmpty()||nationalId.isEmpty()){
                Toast.makeText(getActivity(), "من فضلك املا كل الحقول", Toast.LENGTH_SHORT).show();
            }else {
                boolean isTrue=email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
                    if (isTrue){
                        User user=new User(name,email,nationalId,password);
                    }else {
                        binding.userEmail.setError("من فضلك ادخل بريد الكتروني صحيح");
                }
            }
        });
    }
    /*private void addUser(User user){
        Call<Data<User>> call= RetrofitClientLaravelData.getInstance().getApiInterfaceUser().addUser(user);
        call.enqueue(new Callback<Data<User>>() {
            @Override
            public void onResponse(@NonNull Call<Data<User>> call, @NonNull Response<Data<User>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), "Success"+response.body().getData().getName(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<Data<User>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "Error"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }*/


}