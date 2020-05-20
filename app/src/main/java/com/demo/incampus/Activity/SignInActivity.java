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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButtonGoogle;

    private EditText email , password;
    private Button mcontinue;
    private ProgressBar mprogress;

    String jwt_token="";

    int RC_SIGN_IN=0,RC_GET_AUTH_CODE=0;

    String serverClientId="678341947476-kri1kouc8pite5hnlpefi794rtmagan7.apps.googleusercontent.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //if the system api is below marshmallow, set status bar to default black
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final Boolean[] flag= {false,false};

        email = findViewById(R.id.email);
        password = findViewById(R.id.pwd);
        mcontinue = findViewById(R.id.contd);
        mprogress = findViewById(R.id.progressBar);
        mprogress.setVisibility(View.GONE);
        final SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        Button signup1=findViewById(R.id.signup1);
        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Int= new Intent(getApplicationContext(), SignUpActivity.class);
                //prevents looping of intents
                Int.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(Int, ActivityOptions.makeSceneTransitionAnimation(SignInActivity.this).toBundle());
            }
        });


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    return;
                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.getBackground().setTint(Color.RED);
                    email.setError("Type a valid email address");
                    flag[0] = false;
                }
                else{
                    flag[0]= true;
                    email.getBackground().setTint(Color.rgb(36,19,50));
                }
            }
        });

        password.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() < 8){
                    password.getBackground().setTint(Color.RED);
                    password.setError("Password less than 8 characters",null);
                    flag[1]=false;
                }
                else{
                    flag[1]=true;
                    password.getBackground().setTint(Color.rgb(36,19,50));
                }
            }
        });


        mcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag[1] && flag[0]){
                    Toast.makeText(getApplicationContext(),"Valid entry!",Toast.LENGTH_SHORT).show();
                    Sign_In_Email(email.getText().toString(),password.getText().toString());
                }
                else{
                    if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                        email.getBackground().setTint(Color.RED);
                    }
                    else if(password.getText().length()<8)
                        password.getBackground().setTint(Color.RED);
                }
            }
        });

        /*_______________________________________START_GOOGLE____________________________________________*/

        signInButtonGoogle=findViewById(R.id.login_ggl);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(serverClientId)
                .requestIdToken(serverClientId)
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
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent i=new Intent(this,PhoneNumberActivity.class);
            startActivity(i);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error:", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void getAuthCode() {
        // Start the retrieval process for a server auth code.  If requested, ask for a refresh
        // token.  Otherwise, only get an access token if a refresh token has been previously
        // retrieved.  Getting a new access token for an existing grant does not require
        // user consent.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_AUTH_CODE);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Intent i=new Intent(this,OTP.class);
        startActivity(i);
    }
    */

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

        if(requestCode==RC_GET_AUTH_CODE)
        {
            SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = preferences.edit();

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d("Success: ", "onActivityResult:GET_AUTH_CODE:success:" + result.getStatus().isSuccess());

            if (result.isSuccess()) {
                // [START get_auth_code]
                GoogleSignInAccount acct = result.getSignInAccount();
                String authCode = acct.getServerAuthCode();
                String idTokenString = acct.getIdToken();

                Toast.makeText(this, idTokenString + "tgr", Toast.LENGTH_SHORT).show();

                // Show signed-in UI.
                Log.i("Auth: ",authCode);

                //STORING IN SHARED PREFERENCES GOOGLE AUTH CODE
                editor.putString("Auth", authCode );
                editor.commit();
                // TODO(user): send code to server and exchange for access/refresh/ID tokens.
                // [END get_auth_code]
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    /*__________________________________________END_GOOGLE______________________________________________*/

    public void Sign_In_Email(String personGivenName,String personId)
    {
        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();


        Call<Register> call = RetrofitClient.getInstance().getApi().login(personGivenName,personId);

        call.enqueue(new Callback<Register>() {

            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {

                if(response.isSuccessful()) {

                    Register register = response.body();

                    editor.putString("JWT", register.getAccessToken());
                    editor.commit();

                    Log.d("JWT" , register.getAccessToken());

                    Toast.makeText(SignInActivity.this, register.getAccessToken(), Toast.LENGTH_SHORT).show();

                    mcontinue.setEnabled(true);
                    mprogress.setVisibility(View.GONE);

                    Intent intent = new Intent(SignInActivity.this , PhoneNumberActivity.class);
                    startActivity(intent);


                    // Toast.makeText(SignInJava.this, register.getAccessToken(), Toast.LENGTH_SHORT).show();

                }else
                {
                    mcontinue.setEnabled(true);
                    mprogress.setVisibility(View.GONE);
                    Toast.makeText(SignInActivity.this, "User Does not exist", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

                mcontinue.setEnabled(true);
                mprogress.setVisibility(View.GONE);

                Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        //API POST LOGIN USER
        /*
        Call<ResponseBody> login=RetrofitClient.getInstance().getApi().login("rajatmw1999","password");
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.isSuccessful())
                    {


                        ResponseBody register = response.body();

                        Toast.makeText(SignInJava.this,register.string() ,Toast.LENGTH_SHORT).show();


                    }else
                    {
                        Toast.makeText(SignInJava.this, "Response is not Successful !!", Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e)
                {
                    Toast.makeText(SignInJava.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                try {


                    ResponseBody responseBody = response.body();

                    String dds= responseBody.string();

                    JSONObject jsonObject=new JSONObject(dds);

                    Toast.makeText(SignInJava.this, dds, Toast.LENGTH_SHORT).show();
                    jwt_token = jsonObject.getString("accessToken");

                    editor.putString("JWT", jwt_token);

                    Log.i("access Token Server:",jwt_token);

                } catch (IOException | JSONException e) {

                    mcontinue.setEnabled(true);
                    if( e instanceof NullPointerException){
                        Toast.makeText(SignInJava.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                    }else{
                    Toast.makeText(SignInJava.this, e.getMessage(), Toast.LENGTH_SHORT).show();}
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }

        });  */
    }
}
