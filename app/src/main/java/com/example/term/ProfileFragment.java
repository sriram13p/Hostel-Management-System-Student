package com.example.term;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    TextView name1,id1,parentname1,phone1;
    Button logout;

    SharedPreferences sharedPreferences;

    public static final String fileName="data";
    public static final String userId="userId";
    public static final String name="name";
    public static final String parent="parent";
    public static final String phone="phone";
    public static final String photoUrl="photoUrl";



    public ProfileFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name1 = view.findViewById(R.id.name);
        id1 = view.findViewById(R.id.id);
        parentname1 = view.findViewById(R.id.parentname);
        phone1 = view.findViewById(R.id.phone);
        logout= view.findViewById(R.id.logout);

        sharedPreferences=this.getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);




        name1.setText(sharedPreferences.getString(name,""));
        id1.setText(sharedPreferences.getString(userId,""));
        parentname1.setText(sharedPreferences.getString(parent,""));
        phone1.setText(sharedPreferences.getString(phone,""));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getActivity(),"Logging Out!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);


            }
        });




        // Inflate the layout for this fragment
        return view;
    }
}