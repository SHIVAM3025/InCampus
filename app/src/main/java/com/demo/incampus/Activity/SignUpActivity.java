package com.demo.incampus.Activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Boolean pwd_vis = false;

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButtonGoogle;
    String authCode;

    int RC_SIGN_IN = 0;
    EditText email, password, username;
    String serverClientId = "678341947476-kri1kouc8pite5hnlpefi794rtmagan7.apps.googleusercontent.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final Boolean[] flag = {false, false, false};

        email = findViewById(R.id.email);
        password = findViewById(R.id.pwd);
        username = findViewById(R.id.username);

        Button signin2 = findViewById(R.id.signin2);
        signInButtonGoogle = findViewById(R.id.login_ggl);

        AccountManager am = AccountManager.get(this); // "this" references the current Context

        Account[] accounts = am.getAccountsByType("com.google");

        final SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        signin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Int;
                Int = new Intent(getApplicationContext(), SignInActivity.class);
                //prevents looping of intents
                Int.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                startActivity(Int, ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this).toBundle());
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
                if (s.toString().isEmpty())
                    return;
                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.getBackground().setTint(Color.RED);
                    email.setError("Type a valid email address");
                    flag[0] = false;
                }
                //TODO(Add else if email verif)
                else {
                    flag[0] = true;
                    email.getBackground().setTint(Color.parseColor("darkgrey"));
                }
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 4) {
                    username.getBackground().setTint(Color.RED);
                    username.setError("Username less than 4 characters");
                    flag[2] = false;
                }
                // TODO(else if(username unique constraint))
                else {
                    username.getBackground().setTint(Color.parseColor("darkgrey"));
                    flag[2] = true;
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
                    password.getBackground().setTint(Color.parseColor("darkgrey"));
                }
            }
        });
        //TODO(ADD PASSWORD TOGGLE)
        Button contd = findViewById(R.id.contd);
        contd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag[0] && flag[1] && flag[2]) {
                    API_POST_register_user(email.getText().toString(), username.getText().toString(), password.getText().toString());
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
                        email.getBackground().setTint(Color.RED);
                    else if (username.getText().length() < 4) //TODO(add unique constraint again)
                        username.getBackground().setTint(Color.RED);
                    else if (password.getText().length() < 8)
                        password.getBackground().setTint(Color.RED);
                }
            }
        });


        /*_______________________________________START_GOOGLE____________________________________________*/

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(serverClientId)
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);


        //START
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (GoogleSignIn.hasPermissions(account, gso.getScopeArray())) {
//            // Already signed in.
//            // The signed in account is stored in the 'account' variable.
//            GoogleSignInAccount signedInAccount = account;
//            Toast.makeText(this, signedInAccount.getIdToken(), Toast.LENGTH_SHORT).show();
//        }

//        } else {
//            // Haven't been signed-in before. Try the silent sign-in first.
//            GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);
//            signInClient
//                    .silentSignIn()
//                    .addOnCompleteListener(
//                            this,
//                            task -> {
//                                if (task.isSuccessful()) {
//                                    // The signed in account is stored in the task's result.
//                                    GoogleSignInAccount signedInAccount = task.getResult();
//                                    Toast.makeText(SignUpActivity.this, "SignEd In", Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(SignUpActivity.this, signedInAccount.getIdToken(), Toast.LENGTH_SHORT).show();
//                                } else {
//                                    // Player will need to sign-in explicitly using via UI.
//                                    // See [sign-in best practices](http://developers.google.com/games/services/checklist) for guidance on how and when to implement Interactive Sign-in,
//                                    // and [Performing Interactive Sign-in](http://developers.google.com/games/services/android/signin#performing_interactive_sign-in) for details on how to implement
//                                    // Interactive Sign-in.
//                                }
//                            });
//
//        }

        //FINISH


        signInButtonGoogle.setOnClickListener(v -> signIn());


        /*__________________________________________END_GOOGLE______________________________________________*/


    }

    @Override
    public void onBackPressed() {
        Intent Int = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(Int);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /*_______________________________________START_GOOGLE____________________________________________*/
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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

//        if (requestCode == RC_GET_AUTH_CODE) {
//            SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
//            final SharedPreferences.Editor editor = preferences.edit();
//
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            Log.d("Success: ", "onActivityResult:GET_AUTH_CODE:success:" + result.getStatus().isSuccess());
//            Log.i("Request", "request code is rc auth code");
//            if (result.isSuccess()) {
//                // [START get_auth_code]
//                GoogleSignInAccount acct = result.getSignInAccount();
//                authCode = acct.getServerAuthCode();
//                String idTokenString = acct.getIdToken();
//
//                Toast.makeText(this, idTokenString , Toast.LENGTH_SHORT).show();
//                // Show signed-in UI.
//                Log.i("Auth: ", authCode);
//                //STORING IN SHARED PREFERENCES GOOGLE AUTH CODE
//                editor.putString("Auth", authCode);
//                editor.commit();
//                // TODO(user): send code to server and exchange for access/refresh/ID tokens.
//                // [END get_auth_code]
//                Log.i("Request", "rc auth code has success result");
//               // API_POST_google_auth_response(idTokenString);
//            }
//        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, account.getIdToken(), Toast.LENGTH_SHORT).show();
           // API_POST_google_auth_response(account.getIdToken());
            Log.i("Token", account.getIdToken());
            Log.i("function", "inside handle sign in result");
            Toast.makeText(this, "Username", Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error:", "signInResult:failed code=" + e.getStatusCode());
        }
    }



    /*__________________________________________END_GOOGLE______________________________________________*/

    public void API_POST_register_user(String personEmail, String personGivenName, String personPassword) {
        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        //API POST REGISTER
        retrofit2.Call<JsonObject> register = RetrofitClient.getInstance().getApi().register(personEmail, personGivenName, personPassword);
        register.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful()) {

                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("user"));

                        String user_id = jsonObject1.getString("user_id");
                        String JWT = jsonObject.getString("accessToken");
                        Log.i("ID", user_id);
                        Log.i("JWT", JWT);

                        Toast.makeText(SignUpActivity.this, user_id + " :user_id", Toast.LENGTH_SHORT).show();
                        //STORING USER ID
                        editor.putString("user_id", user_id);
                        editor.putString("JWT", JWT);
                        editor.commit();

                        Intent intent = new Intent(SignUpActivity.this , PhoneNumberActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.i("response", "Got response from user");
                }else
                {
                    Toast.makeText(SignUpActivity.this, Integer.toString(response.code()), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Info", "No response for register user post");
            }

        });
    }

    public void API_POST_google_auth_response(String id_token) {
        Toast.makeText(this, "called google auth", Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        //API POST REGISTER
        retrofit2.Call<JsonObject> register = RetrofitClient.getInstance().getApi().google_id_token(id_token);
        register.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    if (response.isSuccessful()) {
                     //   ResponseBody s = response.body();
                       // editor.putString("JWT", s.string());
                        editor.commit();
                        Toast.makeText(SignUpActivity.this, "lion", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(SignUpActivity.this, PhoneNumberActivity.class);
                        startActivity(i);
                        Log.i("response", "Got response from user");
                    } else {
                        Toast.makeText(SignUpActivity.this, "Service unavailable", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "User already registered!", Toast.LENGTH_SHORT).show();
                    Log.i("No response", "exception");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Info", "No response for register user post");
            }

        });
    }


}
