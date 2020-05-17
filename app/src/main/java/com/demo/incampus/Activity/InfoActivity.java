package com.demo.incampus.Activity;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.incampus.R;
import com.demo.incampus.Network.GraphqlClient;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;


public class InfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner univ, course, gender_et, major;
    int flag;
    private EditText dob_et;
    private Calendar c;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //if the system api is below marshmallow, set status bar to default black
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //rel_layout covering the amount of screen
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        RelativeLayout relLayout = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams[] paramS = {relativeLayout.getLayoutParams(), relLayout.getLayoutParams()};
        paramS[0].height = (int) (height * 0.45);
        paramS[1].height = (int) (height * 0.15);
        relativeLayout.setLayoutParams(paramS[0]);
        relLayout.setLayoutParams(paramS[1]);

        //check condition
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);

        dob_et = findViewById(R.id.dob_et);
        c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000);
        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        final DatePickerDialog dpd = new DatePickerDialog(InfoActivity.this, date,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );
        //need to add minimum and maximum time
        dob_et.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dpd.show();
            }
        });

        univ = findViewById(R.id.univ);
        course = findViewById(R.id.course);
        gender_et = findViewById(R.id.gender_et);
        major = findViewById(R.id.major);

        univ.setOnItemSelectedListener(this); //univ.prompt="Select your institute"
        course.setOnItemSelectedListener(this); //course.prompt="Select your degree"
        gender_et.setOnItemSelectedListener(this); //gender_et.prompt="Select your gender"

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.institute,
                R.layout.spinner_item2
        );
        adapter1.setDropDownViewResource(R.layout.spinner_dialog_item);
        univ.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this,
                R.array.courses,
                R.layout.spinner_item2
        );
        adapter3.setDropDownViewResource(R.layout.spinner_dialog_item);
        course.setAdapter(adapter3);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(
                this,
                R.array.genders,
                R.layout.spinner_item
        );
        adapter4.setDropDownViewResource(R.layout.spinner_dialog_item);
        gender_et.setAdapter(adapter4);

       name = findViewById(R.id.name_et);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() < 3) {
                    name.getBackground().setTint(Color.RED);
                } else {
                    name.getBackground().setTint(Color.parseColor("darkgrey"));
                }
            }
        });

        dob_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() != 10) {
                    dob_et.getBackground().setTint(Color.RED);
                } else {
                    dob_et.getBackground().setTint(Color.parseColor("darkgrey"));
                }
            }
        });

        Button savechanges = findViewById(R.id.savechanges);
        savechanges.setOnClickListener(v -> {
            if (name.getText().toString().trim().length() >= 3 && dob_et.getText().length() == 10 && univ.getSelectedItemPosition() != 0 && course.getSelectedItemPosition() != 0 &&
                    gender_et.getSelectedItemPosition() != 0 && major.getSelectedItemPosition() != 0) {
                if (flag == 0) {
                    register_user_mutation_function();
                } else {
                    Toast.makeText(getApplicationContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                }
            } else if (name.getText().toString().trim().length() < 3) {
                name.getBackground().setTint(Color.RED);
                Toast.makeText(InfoActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            } else if (dob_et.getText().length() == 0) {
                dob_et.getBackground().setTint(Color.RED);
                Toast.makeText(InfoActivity.this, "Please Enter your Date of Birth", Toast.LENGTH_SHORT).show();
            } else if (gender_et.getSelectedItemPosition() == 0) {
                Toast.makeText(InfoActivity.this, "Please Select your Gender", Toast.LENGTH_SHORT).show();
            } else if (univ.getSelectedItemPosition() == 0) {
                Toast.makeText(InfoActivity.this, "Please Select your College/University", Toast.LENGTH_SHORT).show();
            } else if (course.getSelectedItemPosition() == 0) {
                Toast.makeText(InfoActivity.this, "Please Select your Degree", Toast.LENGTH_SHORT).show();
            } else if (major.getSelectedItemPosition() == 0) {
                Toast.makeText(InfoActivity.this, "Please select your Major", Toast.LENGTH_SHORT).show();
            }
        });

        //on back image pressed
        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(v -> onBackPressed());
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob_et.setText(sdf.format(c.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);

        //populate "major" spinner based on course spinner
        ArrayAdapter<CharSequence> adapter5;
        switch (course.getSelectedItemPosition()) {
            case 1:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.btech, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 2:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.mtechcivil, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 3:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.mtechchemical, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 4:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.mtechcomputer, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 5:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.mtechelectrical, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 6:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.mtechmechanical, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 7:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.mtechelectronics, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 8:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.bca, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 9:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.mca, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 10:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.bsc, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 11:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.msc, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 12:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.bcom, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 13:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.mcom, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 14:
            case 15:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.bama, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            case 16:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.research, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;
            default:
                adapter5 = ArrayAdapter.createFromResource(this, R.array.major, R.layout.spinner_item2);
                adapter5.setDropDownViewResource(R.layout.spinner_dialog_item);
                adapter5.notifyDataSetChanged();
                major.setAdapter(adapter5);
                break;

        }

    }


    public void register_user_mutation_function()
    {

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("query","mutation MyMutation {\n" +
                "  update_User(where: {user_id: {_eq: \"27\"}}, _set: {date_of_birth: \""+dob_et.getText().toString()+"\", gender: \""+gender_et.getSelectedItem().toString()+"\", name: \""+name.getText().toString()+"\", university: \""+univ.getSelectedItem().toString()+"\", course: \""+course.getSelectedItem().toString()+"\", education: \""+major.getSelectedItem().toString()+"\"}) {\n" +
                "    affected_rows\n" +
                "  }\n" +
                "}\n");

        Call<JsonObject> register = GraphqlClient.getApi().graphql(jsonObject1);

        register.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(InfoActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AddProfilePhotoActivity.class), ActivityOptions.makeSceneTransitionAnimation(InfoActivity.this).toBundle());
                }else
                {
                    Toast.makeText(InfoActivity.this, "Server Connection Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(InfoActivity.this,"Failure:\n"+ t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.i("Failure",t.getMessage());
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
