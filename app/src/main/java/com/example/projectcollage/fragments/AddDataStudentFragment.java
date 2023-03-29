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
import com.example.projectcollage.activities.LoginActivity;
import com.example.projectcollage.databinding.FragmentAddDataStudentBinding;
import com.example.projectcollage.model.Data;
import com.example.projectcollage.model.User;
import com.example.projectcollage.retrofit.RetrofitClientUser;

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
    private void addUser(User user){
        Call<Data> call= RetrofitClientUser.getInstance().getApiInterfaceUser().addUser(user);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}