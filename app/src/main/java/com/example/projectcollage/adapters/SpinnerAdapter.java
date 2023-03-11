package com.example.projectcollage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.projectcollage.R;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    public static String[] checked;
    private List<String> items;
    private int resourcedID;

    public SpinnerAdapter(@NonNull Context context, int resourcedID,List<String>items) {
        super(context, resourcedID,items);
        this.context=context;
        this.items=items;
        this.resourcedID=resourcedID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       if (convertView==null){
           LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView=inflater.inflate(resourcedID, parent,false);
       }
        CheckBox box=convertView.findViewById(R.id.checkBox);
        box.setText(getItem(position));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
           if (convertView==null){
               LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               convertView=inflater.inflate(resourcedID, parent,false);
           }
            checked=new String[items.size()];
            CheckBox box=convertView.findViewById(R.id.checkBox);
            box.setText(getItem(position));
            box.setOnClickListener(view -> {
                if (box.isChecked()){
                    checked[position]=box.getText().toString();
                }
            });
        return convertView;
    }
}
