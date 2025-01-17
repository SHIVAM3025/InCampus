package com.demo.incampus.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.incampus.Model.Phone;
import com.demo.incampus.Network.ScalarsClient;
import com.demo.incampus.R;
import com.demo.incampus.Network.RetrofitClient;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPActivity extends AppCompatActivity {
    private EditText[] et = new EditText[6];

    String phoneNumber = "";
    String OTP = "";
    String serverAccessToken = "";
    String sessionID = "";
    GoogleSignInClient mGoogleSignInClient;
    private String jwt_token;

    Button mnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //if the system api is below marshmallow, set status bar to default black
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //Linear Layout covering the amount of screen
        LinearLayout RL = findViewById(R.id.numberlayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        ViewGroup.LayoutParams paramS = RL.getLayoutParams();
        paramS.height = (int) (height * 0.15);
        RL.setLayoutParams(paramS);

        Intent i = getIntent();
        phoneNumber = i.getExtras().getString("phoneNumber");
        Log.i("Phone", phoneNumber);

        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        mnext = findViewById(R.id.next);

        et[0] = findViewById(R.id.etone);
        et[1] = findViewById(R.id.ettwo);
        et[2] = findViewById(R.id.etthree);
        et[3] = findViewById(R.id.etfour);
        et[4] = findViewById(R.id.etfive);
        et[5] = findViewById(R.id.etsix);

        et[0].addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et[1].requestFocus();
                }
            }
        });
        et[1].addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et[2].requestFocus();
                } else {
                    et[0].requestFocus();
                }
            }
        });
        et[2].addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et[3].requestFocus();
                } else {
                    et[1].requestFocus();
                }
            }
        });
        et[3].addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et[4].requestFocus();
                } else {
                    et[2].requestFocus();
                }
            }
        });
        et[4].addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et[5].requestFocus();
                } else {
                    et[3].requestFocus();
                }
            }
        });
        et[5].addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.length() != 1)
                    et[4].requestFocus();
            }
        });

        jwt_token = preferences.getString("JWT", "expires");

        API_POST_receive_OTP(phoneNumber);//RECEIVE SessionID and OTP

        mnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibe_check()) {
                    OTP = "";
                    for (int i = 0; i < 6; i++) {

                        OTP += et[i].getText().toString();
                    }

                    if (!jwt_token.equals("expire")) {
                        API_POST_verify_OTP(OTP, sessionID);

                    } else {
                        Toast.makeText(OTPActivity.this, "can not retrieve jwt token", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    protected boolean vibe_check() {
        for (int i = 0; i < 6; i++) {
            if (et[i].getText().length() != 1)
                return false;
        }
        return true;
    }


    public void API_POST_receive_OTP(String phoneNumber) {
        //API CALL RECEIVE OTP
        Toast.makeText(this, jwt_token, Toast.LENGTH_SHORT).show();

        String token = "Bearer " + jwt_token;
        Call<Phone> otpReceive = RetrofitClient.getInstance().getApi().otpReceive(token, phoneNumber);
        otpReceive.enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                try {

                    if (response.isSuccessful()) {
                        Phone phone = response.body();

                        Toast.makeText(OTPActivity.this, phone.getDetails(), Toast.LENGTH_SHORT).show();
                        sessionID = phone.getDetails();

                    } else {
                        Toast.makeText(OTPActivity.this, "Response is not sucesful", Toast.LENGTH_SHORT).show();
                    }

                    Log.i("sessionID", sessionID);
                } catch (Exception e) {
                    if (e instanceof NullPointerException) {
                        Toast.makeText(OTPActivity.this, "Excenption = >  " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {
                Toast.makeText(OTPActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void API_POST_verify_OTP(String OTP, String sessionID) {
        //API CALL VERIFY OTP

        Call<ResponseBody> verifyotp = RetrofitClient.getInstance().getApi().verifyotp(OTP, sessionID);
        verifyotp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //String s = response.body().string();
                    //  Log.i("verification", s);

                    Intent intent = new Intent(OTPActivity.this, InfoActivity.class);
                    startActivity(intent);

                } catch (Exception e) {

                    if (e instanceof NullPointerException) {

                        Toast.makeText(OTPActivity.this, "WrongOTP", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OTPActivity.this, "Time Out", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(OTPActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        /*Call<String> verifyotp = ScalarsClient.getInstance().getApi().verifyotp(OTP, sessionID);
        verifyotp.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    Intent intent = new Intent(OTPActivity.this, InfoActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(OTPActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OTPActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public void API_POST_login_user(String personGivenName, String personId) {
        //API POST LOGIN USER

        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
/*
      Call<ResponseBody> login = RetrofitClient.getInstance().getApi().otpReceive(preferences.getString("JWT" , ""), personId);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                Toast.makeText(OTPJava.this, response.toString(), Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(OTPJava.this , Walkthrough.class);
                  startActivity(intent);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(OTPJava.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private JsonObject ApiJsonMap() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("key", "value");
            jsonObj_.put("key", "value");
            jsonObj_.put("key", "value");


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }


}
