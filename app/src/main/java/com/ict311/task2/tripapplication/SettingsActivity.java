package com.ict311.task2.tripapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SettingsActivity extends FragmentActivity {
    private static final String TAG = "SettingsActivity";
    FragmentTransaction ft = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new SettingsFragment(), TAG);

            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment fm = getSupportFragmentManager().findFragmentByTag(TAG);

        if (fm != null)
            if (fm.isVisible()) {
                View v = fm.getView();

                String gender;
                EditText name = (EditText) v.findViewById(R.id.name);
                EditText idSetting = (EditText) v.findViewById(R.id.UserIDSetting);
                EditText email = (EditText) v.findViewById(R.id.emailID);
                EditText comment = (EditText) v.findViewById(R.id.comment);
                RadioGroup radioGenderGroup = (RadioGroup) v.findViewById(R.id.radioGender);
                int selectedGenderId = radioGenderGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioGenderButton = (RadioButton) v.findViewById(selectedGenderId);

                if (radioGenderButton.getText().equals("Male"))
                    gender = "Male";
                else
                    gender = "Female";

                DatabaseHelper db = new DatabaseHelper(this);
                db.updateSetting(name.getText().toString(), Integer.parseInt(idSetting.getText().toString()), email.getText().toString(), gender, comment.getText().toString());

            }
    }

}