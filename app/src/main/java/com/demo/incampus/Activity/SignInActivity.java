package com.demo.incampus.Activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.incampus.Model.Register;
import com.demo.incampus.R;
import com.demo.incampus.Network.RetrofitClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    GoogleSignInClient mGoogleSignInClient;
    //GoogleApiClient mGoogleApiClient;
    SignInButton signInButtonGoogle;

    private EditText email, password;
    private Button mcontinue;
    private ProgressBar mprogress;

    String jwt_token = "";

    int RC_SIGN_IN = 0, RC_GET_AUTH_CODE = 0;

    String serverClientId = "678341947476-kri1kouc8pite5hnlpefi794rtmagan7.apps.googleusercontent.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //if the system api is below marshmallow, set status bar to default black
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final Boolean[] flag = {false, false};

        email = findViewById(R.id.email);
        password = findViewById(R.id.pwd);
        mcontinue = findViewById(R.id.contd);
        mprogress = findViewById(R.id.progressBar);
        mprogress.setVisibility(View.GONE);
        final SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        Button signup1 = findViewById(R.id.signup1);
        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Int = new Intent(getApplicationContext(), SignUpActivity.class);
                //prevents looping of intents
                Int.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(Int, ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this).toBundle());
            }
        });


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 4) {
                    email.getBackground().setTint(Color.RED);
                    email.setError("Username less than 4 characters");
                    flag[0] = false;
                }
                // TODO(else if(username unique constraint))
                else {
                    email.getBackground().setTint(Color.parseColor("darkgrey"));
                    flag[0] = true;
                }

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 8) {
                    password.getBackground().setTint(Color.RED);
                    password.setError("Password less than 8 characters", null);
                    flag[1] = false;
                } else {
                    flag[1] = true;
                    password.getBackground().setTint(Color.rgb(36, 19, 50));
                }
            }
        });


        mcontinue.setOnClickListener(v -> {
            if (flag[1] && flag[0]) {
                Toast.makeText(getApplicationContext(), "Valid entry!", Toast.LENGTH_SHORT).show();
                Sign_In_Email(email.getText().toString(), password.getText().toString());
            } else {
                if (email.getText().length() < 4) //TODO(add unique constraint again)
                {
                    email.getBackground().setTint(Color.RED);
                } else if (password.getText().length() < 8)
                    password.getBackground().setTint(Color.RED);
            }
        });

        /*_______________________________________START_GOOGLE____________________________________________*/

        signInButtonGoogle = findViewById(R.id.login_ggl);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(serverClientId)
                .requestIdToken(serverClientId)
                //.requestScopes()
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this,this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.login_ggl:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        /*signInButtonGoogle=findViewById(R.id.login_ggl);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.login_ggl:
                        signIn();
                        break;
                    // ...
                }
            }
        });
         */
        /*__________________________________________END_GOOGLE______________________________________________*/

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    /*_______________________________________START_GOOGLE____________________________________________*/
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

//        if(requestCode==RC_GET_AUTH_CODE)
//        {
//            SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
//            final SharedPreferences.Editor editor = preferences.edit();
//
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            Log.d("Success: ", "onActivityResult:GET_AUTH_CODE:success:" + result.getStatus().isSuccess());
//
//            if (result.isSuccess()) {
//                // [START get_auth_code]
//                GoogleSignInAccount acct = result.getSignInAccount();
//                String authCode = acct.getServerAuthCode();
//                String idTokenString = acct.getIdToken();
//
//                // Show signed-in UI.
//                Log.i("Auth: ",authCode);
//
//                //STORING IN SHARED PREFERENCES GOOGLE AUTH CODE
//                editor.putString("Auth", authCode );
//                editor.commit();
//                API_POST_google_auth_response(idTokenString);
//                // TODO(user): send code to server and exchange for access/refresh/ID tokens.
//                // [END get_auth_code]
//            }
        //   }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, account.getIdToken(), Toast.LENGTH_SHORT).show();
            //API_POST_google_auth_response(account.getIdToken());
            API_POST_google_auth_response(account.getIdToken() , account.getPhotoUrl().toString());

            Toast.makeText(this, "No Username", Toast.LENGTH_SHORT).show();
            Log.i("function", "inside handle sign in result");
            //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error:", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    /*__________________________________________END_GOOGLE______________________________________________*/
/*
    public void Sign_In_Email(String personGivenName,String personId)
    {
        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();


        Call<JsonObject> call = RetrofitClient.getInstance().getApi().login(personGivenName,personId);

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful()) {


                    String res = response.body().toString();
                    Log.i("Response:",response.body().toString());


                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        JSONObject jsonObject1=new JSONObject(jsonObject.getString("user"));

                        String user_id=jsonObject1.getString("user_id");
                        String JWT=jsonObject.getString("accessToken");
                        Log.i("ID",user_id);
                        Log.i("JWT",JWT);
                        //STORING USER ID

                        Toast.makeText(SignInActivity.this, user_id + " :user_id", Toast.LENGTH_SHORT).show();

                        editor.putString("user_id", user_id);
                        editor.putString("JWT", JWT);
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Reading value stored in a particular key and store it as a string

                    //Log.i("Weather cotent: ",weatherInfo);
                    mcontinue.setEnabled(true);
                    mprogress.setVisibility(View.GONE);

                    Intent intent = new Intent(SignInActivity.this , HomeActivity.class);
                    startActivity(intent);
                    finish();

                    // Toast.makeText(SignInJava.this, register.getAccessToken(), Toast.LENGTH_SHORT).show();

                }else
                {
                    mcontinue.setEnabled(true);
                    mprogress.setVisibility(View.GONE);
                    Toast.makeText(SignInActivity.this, "User Does not exist", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                mcontinue.setEnabled(true);
                mprogress.setVisibility(View.GONE);

                Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    */

    public void Sign_In_Email(String personGivenName, String personId) {
        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();


        Call<JsonObject> login = RetrofitClient.getInstance().getApi().login(personGivenName, personId);

        login.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {

                    if(response.body() == null){
                        Toast.makeText(SignInActivity.this, "Check Your Credentials", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String res = response.body().toString();
                    Log.i("Response:", response.body().toString());

                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("user"));

                        String user_id = jsonObject1.getString("user_id");

                        Toast.makeText(SignInActivity.this, user_id+" :user_id", Toast.LENGTH_SHORT).show();
                        String JWT = jsonObject.getString("accessToken");
                        Log.i("ID", user_id);
                        Log.i("JWT", JWT);
                        //STORING USER ID
                        editor.putString("user_id", user_id);
                        editor.putString("JWT", JWT);
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Reading value stored in a particular key and store it as a string

                    //Log.i("Weather cotent: ",weatherInfo);
                    mcontinue.setEnabled(true);
                    mprogress.setVisibility(View.GONE);

                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    mcontinue.setEnabled(true);
                    mprogress.setVisibility(View.GONE);
                    Toast.makeText(SignInActivity.this, "User Does not exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                mcontinue.setEnabled(true);
                mprogress.setVisibility(View.GONE);

                Toast.makeText(SignInActivity.this, "Some error occurred, Please try again later", Toast.LENGTH_SHORT).show();
                Log.i("Failure", t.getMessage());
            }
        });
    }


    public void API_POST_google_auth_response(String id_token , String photoUrl) {
        Toast.makeText(this, "called google auth", Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        //API POST REGISTER
        retrofit2.Call<JsonObject> register = RetrofitClient.getInstance().getApi().google_id_token(id_token);
        register.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {


                if (response.isSuccessful()) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("user"));
                        Log.i("user", jsonObject.toString());
                        String user_id = jsonObject1.getString("user_id");
                        String accessToken = jsonObject.getString("accessToken");
                        String signin = jsonObject1.getString("signin");

                        Toast.makeText(SignInActivity.this, user_id + " :user_id", Toast.LENGTH_SHORT).show();

                        editor.putString("user_id", user_id);
                        editor.putString("JWT", accessToken);
                        editor.commit();

                        if (signin.equals("true")) {
                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else if (signin.equals("false")) {
                            Intent intent = new Intent(SignInActivity.this, UsernameActivity.class);
                            startActivity(intent);
                        }


                    } catch (JSONException e) {
                        Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(SignInActivity.this, "Service unavailable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Info", "No SignInActivity for register user post");
            }

        });
    }


}
