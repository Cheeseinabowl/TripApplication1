package com.ict311.task2.tripapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SettingsFragment extends Fragment {
    DatabaseHelper db;
    EditText name;
    EditText idSetting;
    EditText email;
    EditText comment;
    RadioGroup radioGenderGroup;
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settings, container, false);
        db = new DatabaseHelper(getActivity());
        Setting s = db.getSetting();
        name = (EditText) v.findViewById(R.id.name);
        idSetting = (EditText) v.findViewById(R.id.UserIDSetting);
        email = (EditText) v.findViewById(R.id.emailID);
        comment = (EditText) v.findViewById(R.id.comment);
        radioGenderGroup = (RadioGroup) v.findViewById(R.id.radioGender);
        name.setText(s.name);

        String st = String.valueOf(s.ID);
        idSetting.setText(String.valueOf(s.ID));
        email.setText(s.email);
        comment.setText(s.comment);
        if (s.gender.equals("Male"))
            radioGenderGroup.check(R.id.radioMale);
        else
            radioGenderGroup.check(R.id.radioFemale);

        return v;
    }

}
