package com.demo.incampus.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.incampus.R;

public class PhoneNumberActivity extends AppCompatActivity {

    EditText number;
    int count = 0;
    String phoneNumber;

    public void back(View view) {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        //rel_layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams paramS = RL.getLayoutParams();
        paramS.height = (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        number = findViewById(R.id.enterNumber);

        //limit number of inputs
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(14);
        number.setFilters(filterArray);

        //add a space after 2 characters
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*Empty*/}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { /*Empty*/ }

            @Override
            public void afterTextChanged(Editable s) {

                int inputlength = number.getText().toString().length();

                if (count <= inputlength && (inputlength == 2 || inputlength == 5 || inputlength == 8 || inputlength == 11)) {
                    number.setText(number.getText().toString() + " ");

                    int pos = number.getText().length();
                    number.setSelection(pos);

                } else if (count >= inputlength && (inputlength == 2 || inputlength == 5 || inputlength == 8 || inputlength == 11)) {
                    number.setText(number.getText().toString().substring(0, number.getText().toString().length() - 1));

                    int pos = number.getText().length();
                    number.setSelection(pos);
                }
                count = number.getText().toString().length();

                if (count != 14) {
                    number.getBackground().setTint(Color.RED);
                } else {
                    number.getBackground().setTint(Color.parseColor("#8A56AC"));
                }

            }
        });

    }

    public void verify(View view) {
        if (number.getText().length() == 14) {
            phoneNumber = number.getText().toString();
            Intent intent = new Intent(this, OTPActivity.class);
            intent.putExtra("phoneNumber", phoneNumber);
            startActivity(intent);
        } else {
            number.getBackground().setTint(Color.RED);
            Toast.makeText(this, "Phone Number should be of 10 digits", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SignUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }


}
