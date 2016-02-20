package com.placediscovery.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.placediscovery.HelperClasses.SessionManager;
import com.placediscovery.MongoLabUser.User;
import com.placediscovery.MongoLabUser.UserQueryBuilder;
import com.placediscovery.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    protected EditText _nameText;
    protected EditText _emailText;
    protected EditText _passwordText;
    protected Button _signupButton;
    protected TextView _loginLink;
    private TextView info;
    private ProgressDialog progressDialog;

    //To be retreived from JSON received after Signup
    private String response;
    private String result;

    User user;
    SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _loginLink = (TextView) findViewById(R.id.link_login);
        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        user = new User();
        user.name = _nameText.getText().toString();
        user.email = _emailText.getText().toString();
        user.password = _passwordText.getText().toString();

        CreateUserAsyncTask tsk = new CreateUserAsyncTask();
        tsk.execute(user);

    }


    public void onSignupSuccess() {
        session.createLoginSession(user.name,user.email,result);
        Intent intent = new Intent(SignupActivity.this, HomePageActivity.class);
        Toast.makeText(getApplicationContext(), "Welcome " + user.name +
                ", You are now logged in.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    class CreateUserAsyncTask extends AsyncTask<User, Void, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SignupActivity.this, "",
                    "Creating Account...", false);
        }

        @Override
        protected JSONObject doInBackground(User... params) {

            JSONObject jObj = null;
            String json = "";

            try {
                User user = params[0];

                UserQueryBuilder qb = new UserQueryBuilder();

                URL url = new URL("http://52.192.70.192:80/register");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setDoInput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Content-Type", "application/json");

                OutputStream os = conn.getOutputStream();
                os.write(qb.createUser(user).getBytes());
                os.flush();
                os.close();

                InputStream is = conn.getInputStream();

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    json = sb.toString();

                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                    return null;
                }


                try {
                    jObj = new JSONObject(json);
                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                    return null;
                }

                return jObj;

            } catch (IOException e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            if(jsonObject == null){
                onSignupFailed();
            } else
                try {
                    if(jsonObject.getString("response").equals("SUCCESS")) {
                        result = jsonObject.getString("result");    //this result is to be used as signin token
                        onSignupSuccess(); //TODO: have to edit signupsuccess
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }


    }
}