package com.demo.incampus.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.incampus.Interface.Api;
import com.demo.incampus.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UsernameActivity extends AppCompatActivity {

    EditText username;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        //user stopped typing
        final long delay = 600; // 60 milliseconds after user stops typing
        final long[] last_text_edit = {0};
        final Handler handler = new Handler();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("https://incampusbackend.herokuapp.com/api/v1/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        final Api api = mRetrofit.create(Api.class);

        continueButton = findViewById(R.id.continueButton);

        continueButton.setVisibility(View.GONE);

        final Runnable input_finish_checker = new Runnable() {
            public void run() {
                if (System.currentTimeMillis() > (last_text_edit[0] + delay - 500)) {


                    api.username_search(username.getText().toString()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.code() == 200  && response.body()!=null) {
                                switch (response.body())
                                {
                                    case "Available" :
                                       // Toast.makeText(UsernameActivity.this, "aval", Toast.LENGTH_SHORT).show();
                                        continueButton.setVisibility(View.VISIBLE);
                                        break;

                                    case "Username already exists" :
                                        Toast.makeText(UsernameActivity.this, "Try another one", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            }

                            else if(response.body() == null)
                            {
                                Toast.makeText(UsernameActivity.this, "Server response null error", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.i("Check", t.getMessage() + "  error");
                            Toast.makeText(UsernameActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    //  Toast.makeText(getApplicationContext(), "you stopped typing", Toast.LENGTH_SHORT).show();
                }
            }
        };


        username = findViewById(R.id.username);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {
                continueButton.setVisibility(View.GONE);
                if (s.toString().trim().length() < 3) {
                    username.getBackground().setTint(Color.RED);
                } else {
                    username.getBackground().setTint(Color.parseColor("darkgrey"));
                    last_text_edit[0] = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                }
            }
        });


        //rel_layout covering the amount of screen
        RelativeLayout RL = findViewById(R.id.relLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams paramS = RL.getLayoutParams();
        paramS.height = (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        //back imageview
        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //moving to info activity
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().length() >= 3) {
                    Intent intent = new Intent(UsernameActivity.this, InfoActivity.class);

                    //setting flag
                    intent.putExtra("flag", 0);

                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(UsernameActivity.this).toBundle());
                } else {
                    username.getBackground().setTint(Color.RED);
                    Toast.makeText(UsernameActivity.this, "The username must be three or more characters long", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
